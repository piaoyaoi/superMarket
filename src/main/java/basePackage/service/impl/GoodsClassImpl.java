package basePackage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import basePackage.dao.GoodsClassDao;
import basePackage.entity.GoodsClass;
import basePackage.service.GoodsClassService;

@Service
@Transactional
public class GoodsClassImpl  implements GoodsClassService{
	@Autowired
	private GoodsClassDao gcd;
	@Override
	public List<GoodsClass> getGoodsClass() {
		return gcd.getGoodsClass();
	}
}
