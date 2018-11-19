package basePackage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basePackage.dao.NoticeDao;
import basePackage.entity.Notice;
import basePackage.service.NoticeService;
@Service
public class NoticeServiceImpl implements NoticeService{
	@Autowired
	private NoticeDao nd;
	@Override
	public List<Notice> getNotice() {
		return nd.getNotice();
	}
}


