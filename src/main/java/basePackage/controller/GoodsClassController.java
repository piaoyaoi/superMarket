package basePackage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import basePackage.service.GoodsClassService;
import basePackage.utils.MessageBox;
@RestController
@RequestMapping("goodsClass")
public class GoodsClassController {
	@Autowired
	private GoodsClassService gcs;
	@Autowired
	private MessageBox msgBox;
	@RequestMapping("getGoodsClass")
	public MessageBox getGoodsClass() {
		msgBox.setData(gcs.getGoodsClass());
		return msgBox;
	}
}
