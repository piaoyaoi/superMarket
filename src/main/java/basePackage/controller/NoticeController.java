package basePackage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import basePackage.service.NoticeService;
import basePackage.utils.MessageBox;

@RestController
@RequestMapping("notice")
public class NoticeController {
	
	@Autowired
	private NoticeService ns;
	@Autowired
	private MessageBox msgBox;
	@RequestMapping("getNotice")
	private MessageBox getNotice() {
		msgBox.setData(ns.getNotice());
		return msgBox;
	}
}
