package basePackage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basePackage.dao.BannersDao;
import basePackage.entity.Banner;
import basePackage.service.BannersService;
@Service
public class BannersServiceImpl implements BannersService{

	@Autowired
	private BannersDao bd;
		
	@Override
	public List<Banner> getBanners() {
		return bd.list();
	}

}
