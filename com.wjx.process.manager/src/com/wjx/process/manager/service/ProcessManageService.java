package com.wjx.process.manager.service;

import java.util.ArrayList;

import com.wjx.process.manager.pojo.ProcessMsg;

public interface ProcessManageService {

	/**
	 * 获取processmsg信息
	 * 
	 * @param table
	 */
	ArrayList<ProcessMsg> getProcessMsg();

	/**
	 * 停止进程
	 * 
	 * @param tableItem
	 */
	void killProcess(ProcessMsg pMsg);

	

}
