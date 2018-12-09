package basePackage.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import basePackage.entity.Orders;
import basePackage.entity.Weachat;
import basePackage.service.OrdersService;
import basePackage.service.impl.LongTimeAsyncCallService;
import basePackage.utils.HttpUtil;
import basePackage.utils.IpUtils;
import basePackage.utils.MessageBox;
import basePackage.wxUtils.WXPayUtil;
import cn.javaer.wechat.pay.util.ObjectUtils;

/**
 * 微信controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/weachat")
@ConfigurationProperties(prefix="wx")
public class WeachatController {
	@Autowired
	private OrdersService os;
	private String appId;
	private String appSecret;
	private String grantType;
	private String MCH_ID ; //商户号
	private String appkey;
	@Autowired
	private LongTimeAsyncCallService longTimeAsyncCallService;
	@Autowired
	   private RedisTemplate<String, String> redisTemplate;
	@RequestMapping("/login/{code}")
	public MessageBox login(@PathVariable("code")String code) {
		String	requestUrl="https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+appSecret+"&grant_type="+grantType+"&js_code="+code;
		String result = HttpUtil.get(requestUrl);
		Weachat weachat = JSON.parseObject(result,Weachat.class);
		String errcode=weachat.getErrcode();
		String msg = null;
		if("-1".equals(errcode)) {
			msg =  "系统繁忙";
		}else if("40029".equals(errcode)) {
			msg =  "无效用户";
		}else if("45011".equals(errcode)) {
			msg =  "登录过于频繁，请稍后再试";
		}
		if(msg != null) {
			return MessageBox.fail(msg);
		}
		String  key = UUID.randomUUID().toString();
		//存储key，15天过期
		redisTemplate.opsForValue().set(key, weachat.getOpenid(), 60*60*60*24*15, TimeUnit.SECONDS);
		return MessageBox.success(key);
	}
	/**
	 * 微信预支付
	 * @return 
	 * @return
	 */
	@RequestMapping("prePayment")
	private MessageBox payment(String str,HttpServletRequest request) {
		JSONObject obj = JSON.parseObject(str);
		boolean flag = true;
		Map<String,String>params = new HashMap<>();
		String openid=	redisTemplate.opsForValue().get(obj.get("token"));
		Orders order=JSON.parseObject(obj.getString("orders"), Orders.class);
		if(openid == null) { //登录失效
			return MessageBox.fail("登录失效请重新登录");
		}
		params.put("appid",appId);
		params.put("mch_id",MCH_ID);
		params.put("nonce_str",ObjectUtils.uuid32());
		params.put("body","腾讯充值");
		params.put("detail","会员充值中心");
		String tradeNo = ObjectUtils.uuid32();
		params.put("out_trade_no",tradeNo); //商户订单号
		order.setId(tradeNo);
		Double price = order.getTotalMoney()*100;
		if(price.toString().indexOf(".")!=-1) {
			String totalPrice = price.toString().split("\\.")[0];
			params.put("total_fee",Integer.parseInt(totalPrice)+"");  //总价格
		}else {
			params.put("total_fee",Integer.parseInt(price.toString())+"");  //总价格
		}
		try {
			params.put("spbill_create_ip",IpUtils.getIpAddress(request));//终端ip
		} catch (IOException e) {
			e.printStackTrace();
		}  
		params.put("openid",openid);
		params.put("trade_type","JSAPI"); //交易类型
//	 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。 // TODO 
		params.put("notify_url","http://localhost:8080/buy");
		String sign = null;
		try {
			sign = WXPayUtil.generateSignature(params,"WKD3910pklz93ms8NXU0Mmlaeowv95B2");
		} catch (Exception e) {
			flag =false;
			e.printStackTrace();
		}
		params.put("sign",sign);  //签名
		String reqXml = null;
		try {
			reqXml = WXPayUtil.mapToXml(params);
		} catch (Exception e) {
			flag =false;
			e.printStackTrace();
		}
		String result = HttpUtil.postData("https://api.mch.weixin.qq.com/pay/unifiedorder", reqXml);
		Map resultMap = null;
		try {
			 resultMap = WXPayUtil.xmlToMap(result);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		if(flag) {
			order.setOpenid(openid);
			//异步插入订单数据
			Callable callable = new Callable() {
				@Override
				public Object call() throws Exception {
					return os.createOrder(order);
				}
			};
			longTimeAsyncCallService.handle(callable);
			return MessageBox.success(null,resultMap);
		}else {
			return MessageBox.fail("支付失败，请稍后再试");
		}
	
	}
	//再次签名
	@SuppressWarnings("unchecked")
	@RequestMapping("signAgin")
	public MessageBox signAgin(HttpServletRequest request) {
		String str =request.getParameter("str");
		JSONObject obj = JSON.parseObject(str);
		Map map =new HashMap();
		map.put("appId", obj.get("appid").toString());
		map.put("nonceStr", obj.get("nonce_str").toString());
		map.put("package","prepay_id="+obj.getString("prepay_id"));
		map.put("signType", "MD5");
		String timeStamp = System.currentTimeMillis()+"";
		map.put("timeStamp", timeStamp);
		String sign = null;
		try {
			 sign = WXPayUtil.generateSignature(map, appkey);
		} catch (Exception e) {
			e.printStackTrace();
			return	MessageBox.fail("支付失败，请稍后再试");
			 
		}
		Map result = new HashMap();
		result.put("sign", sign);
		result.put("time", timeStamp);
		return MessageBox.success(result);
	}
	
	@RequestMapping(value="buy")
	@ResponseBody
	public void Buy(HttpServletRequest request,HttpServletResponse response) throws Exception{
 
		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));  
		String line = null;  
		StringBuilder sb = new StringBuilder();  
		while((line = br.readLine()) != null){  
			sb.append(line);  
		}  
		br.close();  
		String notityXml = sb.toString();  
		String resXml = "";  
		// TODO 修改订单状态
	}
	
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	 public String getMCH_ID() {
		return MCH_ID;
	}
	public void setMCH_ID(String mCH_ID) {
		MCH_ID = mCH_ID;
	}
}
