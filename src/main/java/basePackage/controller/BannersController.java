package basePackage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import basePackage.service.BannersService;
import basePackage.utils.MessageBox;

@RestController
@RequestMapping("banners")
public class BannersController {
	@Autowired
	private BannersService bs;
	@RequestMapping("getBanners")
	private MessageBox getBanners() {
		return MessageBox.success(bs.getBanners());
	}
}
