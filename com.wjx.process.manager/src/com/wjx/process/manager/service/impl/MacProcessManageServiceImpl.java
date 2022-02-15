package com.wjx.process.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.wjx.process.manager.pojo.ProcessMsg;
import com.wjx.process.manager.service.AbstractProcessManageService;
import com.wjx.process.manager.table.util.ProcessManagerUtil;

public class MacProcessManageServiceImpl extends AbstractProcessManageService{

	public MacProcessManageServiceImpl(String[] getProcessScript, String[] killProcessScript, String[] getPortScript) {
		super(getProcessScript, killProcessScript, getPortScript);
	}

	@Override
	protected ArrayList<ProcessMsg> input2ProcessMsgs(List<String> processInput, HashMap<String, String> pid2Ports) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean filter4KillProcess(ProcessMsg pMsg) {
		String runTimePid = ProcessManagerUtil.getRuntimePid();
		if (Objects.nonNull(runTimePid) && runTimePid.equals(pMsg.getPid())) {
			return false;
		} else if (pMsg.getName().toLowerCase().contains("eclipse.exe")) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected HashMap<String, String> input2PidPortMap(List<String> portInput) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
