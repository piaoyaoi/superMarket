package basePackage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import basePackage.dao.GoodsDao;
import basePackage.entity.Goods;
import basePackage.service.GoodsService;
@Service
public class GoodsServiceImpl implements GoodsService{
	@Autowired
	private GoodsDao gd;

	@Override
	public PageInfo<Goods> getGoodsByclass(Goods goods) {
		PageHelper.startPage(goods.getCurrentPage(), goods.getPageSize());
		List<Goods> goodsList=gd.getGoodsByClass(goods);
		PageInfo<Goods>pageInfo = new PageInfo<>(goodsList);
		return pageInfo;
	}

	@Override
	public Goods getGoodsDetail(String id) {
		return gd.getGoodsDetail(id);
	}
}
