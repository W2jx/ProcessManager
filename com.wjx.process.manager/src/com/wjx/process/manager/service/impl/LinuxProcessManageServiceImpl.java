package com.wjx.process.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

import com.wjx.process.manager.pojo.ProcessMsg;
import com.wjx.process.manager.service.AbstractProcessManageService;
import com.wjx.process.manager.table.util.ProcessManagerUtil;

public class LinuxProcessManageServiceImpl extends AbstractProcessManageService {

	public LinuxProcessManageServiceImpl(String[] getProcessScript, String[] killProcessScript,
			String[] getPortScript) {
		super(getProcessScript, killProcessScript, getPortScript);
	}

	@Override
	protected ArrayList<ProcessMsg> input2ProcessMsgs(List<String> processInput, HashMap<String, String> pid2Ports) {
		ArrayList<ProcessMsg> processMsgs = new ArrayList<ProcessMsg>();
		for (int i = 1; i < processInput.size(); i++) {
			String processInfo = processInput.get(i);
			if (Objects.nonNull(processInfo) && processInfo.trim().length() > 0) {
				String pid = "";
				String name = "";
				for (int j = 0; j < 8; j++) {
					if (j == 1) {
						pid = processInfo.substring(0, processInfo.indexOf(" "));
					} else if (j == 7) {
						name = processInfo;
						break;
					}
					processInfo = processInfo.substring(processInfo.indexOf(" ")).trim();
				}
				ProcessMsg processMsg = new ProcessMsg(pid, name, "");
				if (Objects.nonNull(pid2Ports.get(pid))) {
					processMsg.setPort(pid2Ports.get(pid));
				}
				processMsgs.add(processMsg);
			}
		}
		return processMsgs;
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
		HashMap<String, String> pid2ports = new HashMap<String, String>();
		HashMap<String, HashSet<String>> middlePortsMsg = new HashMap<String, HashSet<String>>();
		// 遍历所有端口信息
		for (int i = 3; i < portInput.size(); i++) {
			// 截取
			String port = "";
			String pid = "";
			String pString = portInput.get(i);
			for (int j = 0; j < 7; j++) {
				if(j == 3) {
					port = pString.substring(0, pString.indexOf(" "));
					String[] split = port.split(":");
					port = split[split.length - 1];
				}else if (j == 6) {
					String[] split = pString.split("/");
					if(split.length > 1) {
						pid = split[0];
					}
					break;
				}
				pString = pString.substring(0, pString.indexOf(" ")).trim();
			}
			if(!pid.equals("")) {
				if (Objects.isNull(middlePortsMsg.get(pid))) {
					HashSet<String> hashSet = new HashSet<String>();
					hashSet.add(port);
					middlePortsMsg.put(pid, hashSet);
				} else {
					middlePortsMsg.get(pid).add(port);
				}
			}
		}

		for (Entry<String, HashSet<String>> entry : middlePortsMsg.entrySet()) {
			String ports = entry.getValue().toString();
			pid2ports.put(entry.getKey(), ports.substring(1, ports.length() - 1));
		}

		return pid2ports;
	}

}
