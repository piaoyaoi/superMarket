package basePackage.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import basePackage.entity.Weachat;
import basePackage.utils.HttpUtil;
import basePackage.utils.MessageBox;

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
//	private String requestUrl;
	
	 @Autowired
	   private RedisTemplate<String, String> redisTemplate;
	
	 @Autowired
	 private MessageBox msgBox;
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
			msgBox.setSuccess(false);
			msgBox.setData(msg);
			return msgBox;
		}
		String  key = UUID.randomUUID().toString();
		redisTemplate.opsForValue().set(key, weachat.getSession_key());
		msgBox.setData(key);
		return msgBox;
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
