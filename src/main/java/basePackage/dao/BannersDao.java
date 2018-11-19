package basePackage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import basePackage.entity.Banner;

@Mapper
public interface BannersDao {
	@Select("select id,goodsId,imgUrl from banners")
	List<Banner>list();
}
