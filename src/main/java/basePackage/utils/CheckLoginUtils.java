package basePackage.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class CheckLoginUtils {
	private static RedisTemplate<String, String> redisTemplate;
	public static final boolean checkLogin(String token) {
		String result= redisTemplate.opsForValue().get(token);
		if(result == null) {
			return false;
		}
		return true;
	}
	public static RedisTemplate<String, String> getRedisTemplate() {
		return redisTemplate;
	}
	@Autowired
	public static void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		CheckLoginUtils.redisTemplate = redisTemplate;
	}
	
}
