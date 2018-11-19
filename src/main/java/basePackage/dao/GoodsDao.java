package basePackage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import basePackage.entity.Goods;

@Mapper
public interface GoodsDao {
	List<Goods>getGoodsByClass(Goods goods);
	Goods getGoodsDetail(String id);
}
