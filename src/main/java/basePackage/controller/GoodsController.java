package basePackage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import basePackage.entity.Goods;
import basePackage.service.GoodsService;
import basePackage.utils.MessageBox;

@RestController
@RequestMapping("goods")
public class GoodsController {
	@Autowired
	private GoodsService gs;
	@Autowired
	private MessageBox msgBox;
	@RequestMapping("getGoods")
	private MessageBox getGoods(Goods goods) {
		msgBox.setData(gs.getGoodsByclass(goods));
		return msgBox;
	}
	@RequestMapping("getGoodeDetail/{id}")
	private MessageBox getGoodsDetail(@PathVariable("id")String id) {
		msgBox.setData(gs.getGoodsDetail(id));
		return msgBox;
	}
}
