package basePackage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import basePackage.entity.GoodsClass;

@Mapper
public interface GoodsClassDao {
	@Select("select className,id from goodsClass order by  weight")
	List<GoodsClass>getGoodsClass();
}
