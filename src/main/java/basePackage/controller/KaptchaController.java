package basePackage.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.kaptcha.impl.DefaultKaptcha;

@Controller
public class KaptchaController {
	@Autowired
	private DefaultKaptcha defaultKaptcha;
	@RequestMapping("/generateKaptcha")
	public void kaptcha(HttpServletRequest request,HttpServletResponse response) {
		byte[]jpeg=null;
		ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream();
		//生成验证码字符串保存到session
		String code=defaultKaptcha.createText();
		request.getSession().setAttribute("code", code);
		  // 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
		 BufferedImage challenge = defaultKaptcha.createImage(code);
         try {
			ImageIO.write(challenge, "jpg", arrayOutputStream);
			jpeg = arrayOutputStream.toByteArray();
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");
			ServletOutputStream out =response.getOutputStream();
			out.write(jpeg);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Boolean check(HttpServletRequest request, HttpServletResponse response,String inputcode) {
		String rightCode = (String) request.getSession().getAttribute("code");
		if(inputcode == null) {
			return true;
		}
		return rightCode.equals(inputcode); 
	} 
}
