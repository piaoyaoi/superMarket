package basePackage.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

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

import basePackage.entity.Goods;
import basePackage.entity.Weachat;
import basePackage.utils.HttpUtil;
import basePackage.utils.MessageBox;
import basePackage.utils.PayCommonUtil;
import basePackage.utils.XMLUtil;

/**
 * 微信controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/weachat")
@ConfigurationProperties(prefix="wx")
public class WeachatController {
	private String appId;
	private String appSecret;
	private String grantType;
	private String MCH_ID ; //商户号
//	private String requestUrl;
	
	 public String getMCH_ID() {
		return MCH_ID;
	}
	public void setMCH_ID(String mCH_ID) {
		MCH_ID = mCH_ID;
	}
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
			return MessageBox.HasMessageAndFail(msg, null);
		}
		String  key = UUID.randomUUID().toString();
		redisTemplate.opsForValue().set(key, weachat.getSession_key());
		return MessageBox.noMessageAndSuccess(key);
	}
	/**
	 * 微信预支付
	 * @return 
	 * @return
	 */
	@RequestMapping("prePayment")
	private MessageBox payment(String str) {
		JSONObject obj = JSON.parseObject(str);
	String openid=	redisTemplate.opsForValue().get(obj.get("token"));
	Goods goods=JSON.parseObject(str, Goods.class);
	if(openid == null) { //登录失效
		return MessageBox.HasMessageAndFail("登录失效请重新登录", null);
	}
		String time = System.currentTimeMillis()+"";
		//订单编号（自定义 这里以时间戳+随机数）
				Random random = new Random();
				String did = time+random.nextInt(1000);
				SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
				packageParams.put("appid", appId);//微信小程序ID
				packageParams.put("mch_id", MCH_ID);//商户ID
				packageParams.put("nonce_str", time);//随机字符串（32位以内） 这里使用时间戳
				packageParams.put("body", "情趣商城");//支付主体名称 自定义
				packageParams.put("out_trade_no", did+"");//编号 自定义以时间戳+随机数+商品ID
				packageParams.put("total_fee", goods.getPrice());//价格 自定义
				//packageParams.put("spbill_create_ip", remoteAddr);
				packageParams.put("notify_url", "http://localhost/order/buy.action");//支付返回地址要外网访问的到， localhost不行，调用下面buy方法。（订单存入数据库）
				packageParams.put("trade_type", "JSAPI");//这个api有，固定的
//				packageParams.put("openid", openid);//用户的openid 可以要 可以不要
				String sign = PayCommonUtil.createSign("UTF-8", packageParams, "14257615475846987412365874562136");//最后这个是自己在微信商户设置的32位密钥
				packageParams.put("sign", sign);
				//转成XML
				String requestXML = PayCommonUtil.getRequestXml(packageParams);
				//得到含有prepay_id的XML
				String resXml = HttpUtil.postData("https://api.mch.weixin.qq.com/pay/unifiedorder", requestXML);
				//解析XML存入Map
				Map map = null;
				try {
					map = XMLUtil.doXMLParse(resXml);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(map.get("return_code").equals("FAIL")) {
					return MessageBox.HasMessageAndFail(map.get("return_msg").toString(), null);
				}
				// String return_code = (String) map.get("return_code");
				//得到prepay_id
				String prepay_id = (String) map.get("prepay_id");
				SortedMap<Object, Object> packageP = new TreeMap<Object, Object>();
				packageP.put("appId", "微信小程序ID");//！！！注意，这里是appId,上面是appid
				packageP.put("nonceStr", time);//时间戳
				packageP.put("package", "prepay_id=" + prepay_id);//必须把package写成 "prepay_id="+prepay_id这种形式
				packageP.put("signType", "MD5");//paySign加密
				packageP.put("timeStamp", (System.currentTimeMillis() / 1000) + "");
				//得到paySign
				String paySign = PayCommonUtil.createSign("UTF-8", packageP, "32位秘钥");
				packageP.put("paySign", paySign);
				//将packageP数据返回给小程序

				String json = JSON.toJSONString(packageP);
				return MessageBox.noMessageAndSuccess(json);
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
		//sb为微信返回的xml  
		String notityXml = sb.toString();  
		String resXml = "";  
		Map map = XMLUtil.doXMLParse(notityXml);
		String returnCode = (String) map.get("return_code");  
 
		if("SUCCESS".equals(returnCode)){  
			String out_trade_no=(String) map.get("out_trade_no");
			String timestamp=(String) map.get("nonce_str");
			String goodsid=out_trade_no.substring(out_trade_no.length()-3, out_trade_no.length());
			String openid=(String) map.get("openid");
			/*
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 自己写存入数据库的逻辑
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * */
			resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"  
					+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";  
		}else {
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
					+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";  
		}
		BufferedOutputStream out = new BufferedOutputStream(  
				response.getOutputStream());  
		out.write(resXml.getBytes());  
		out.flush();  
		out.close();  
 
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
}
