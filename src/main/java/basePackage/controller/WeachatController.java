package basePackage.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import basePackage.utils.IpUtils;
import basePackage.utils.MessageBox;
import cn.javaer.wechat.pay.model.UnifiedOrderRequest;
import cn.javaer.wechat.pay.model.UnifiedOrderResponse;
import cn.javaer.wechat.pay.model.base.TradeType;
import cn.javaer.wechat.pay.util.CodecUtils;
import cn.javaer.wechat.pay.util.ObjectUtils;
import cn.javaer.wechat.pay.util.SignUtils;

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
			return MessageBox.fail(msg);
		}
		String  key = UUID.randomUUID().toString();
		redisTemplate.opsForValue().set(key, weachat.getSession_key());
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
		String openid=	redisTemplate.opsForValue().get(obj.get("token"));
		Goods goods=JSON.parseObject(str, Goods.class);
		if(openid == null) { //登录失效
			return MessageBox.fail("登录失效请重新登录");
		}
		UnifiedOrderRequest params =new UnifiedOrderRequest();
		params.setMchId(MCH_ID);
		params.setAppId(appId);
		params.setNonceStr(ObjectUtils.uuid32());
		params.setBody("腾讯充值中心-QQ会员充值");
		params.setDetail("充值详情");
		params.setOutTradeNo(ObjectUtils.uuid32()); //商户订单号
		Double price = goods.getPrice()*100;
		if(price.toString().indexOf(".")!=-1) {
			String totalPrice = price.toString().split("\\.")[0];
			params.setTotalFee(Integer.parseInt(totalPrice));  //总价格
		}else {
			params.setTotalFee(Integer.parseInt(price.toString()));  //总价格
		}
		try {
			params.setSpbillCreateIp(IpUtils.getIpAddress(request));//终端ip
		} catch (IOException e) {
			e.printStackTrace();
		}  
		params.setProductId(goods.getId());
		params.setOpenId(openid);
		params.setTradeType(TradeType.JSAPI); //交易类型
		//签名，最后一位是32为商户密钥
		String sign = SignUtils.generateSign(params, ObjectUtils.uuid32());
		params.setSign(sign);  //签名
	/**
	 * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。 // TODO 
	 */
		params.setNotifyUrl("http://localhost:8080/buy");
		String reqXml = CodecUtils.marshal(params);
		String result = HttpUtil.postData("https://api.mch.weixin.qq.com/pay/unifiedorder", reqXml);
		System.out.println(result);
		UnifiedOrderResponse unifiedOrderResponse = CodecUtils.unmarshal(reqXml, UnifiedOrderResponse.class);
		System.out.println(unifiedOrderResponse);
//		XMLUtils.getAttributeValue(, name);
		return MessageBox.success(null);
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
//		//sb为微信返回的xml  
		String notityXml = sb.toString();  
		String resXml = "";  
//		Map map = XMLUtil.doXMLParse(notityXml);
//		String returnCode = (String) map.get("return_code");  
// 
//		if("SUCCESS".equals(returnCode)){  
//			String out_trade_no=(String) map.get("out_trade_no");
//			String timestamp=(String) map.get("nonce_str");
//			String goodsid=out_trade_no.substring(out_trade_no.length()-3, out_trade_no.length());
//			String openid=(String) map.get("openid");
//			/*
//			 * 
//			 * 
//			 * 
//			 * 
//			 * 
//			 * TODO 存入数据库的逻辑
//			 * 
//			 * 
//			 * 
//			 * 
//			 * 
//			 * 
//			 * 
//			 * */
//			resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"  
//					+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";  
//		}else {
//			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
//					+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";  
//		}
//		BufferedOutputStream out = new BufferedOutputStream(  
//				response.getOutputStream());  
//		out.write(resXml.getBytes());  
//		out.flush();  
//		out.close();  
// 
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
