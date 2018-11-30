package basePackage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	@PostMapping("getGoods")
	private MessageBox getGoods(Goods goods) {
		return MessageBox.success(gs.getGoodsByclass(goods));
	}
	@RequestMapping("getGoodeDetail/{id}")
	private MessageBox getGoodsDetail(@PathVariable("id")String id) {
		return MessageBox.success(gs.getGoodsDetail(id));
	}
}
