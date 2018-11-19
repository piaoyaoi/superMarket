package basePackage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import basePackage.entity.Notice;

@Mapper
public interface NoticeDao {
	@Select("select * from notice order by weight")
	List<Notice>getNotice();
}
