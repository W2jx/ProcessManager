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

public class WindowsProcessManageServiceImpl extends AbstractProcessManageService {

	public WindowsProcessManageServiceImpl(String[] getProcessScript, String[] killProcessScript,
			String[] getPortScript) {
		super(getProcessScript, killProcessScript, getPortScript);
	}

	@Override
	public ArrayList<ProcessMsg> input2ProcessMsgs(List<String> processInput, HashMap<String, String> pid2Ports) {
		ArrayList<ProcessMsg> processMsgs = new ArrayList<ProcessMsg>();
		// 遍历所有进程
		for (int i = 3; i < processInput.size(); i++) {
			// 截取固定长度
			if (Objects.nonNull(processInput.get(i)) && processInput.get(i).length() > 34) {
				String name = processInput.get(i).substring(0, 25).trim();
				String pid = processInput.get(i).substring(25, 35).trim();
				ProcessMsg msg = new ProcessMsg(pid, name, "");
				if (Objects.nonNull(pid2Ports.get(pid))) {
					msg.setPort(pid2Ports.get(pid));
				}
				processMsgs.add(msg);
			}
		}
		return processMsgs;
	}

	@Override
	public boolean filter4KillProcess(ProcessMsg pMsg) {
		String runTimePid = ProcessManagerUtil.getRuntimePid();
		if (Objects.nonNull(runTimePid) && runTimePid.equals(pMsg.getPid())) {
			return false;
		} else if (pMsg.getName().toLowerCase().equals("eclipse.exe")) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public HashMap<String, String> input2PidPortMap(List<String> portInput) {
		HashMap<String, String> pid2ports = new HashMap<String, String>();
		HashMap<String, HashSet<String>> middlePortsMsg = new HashMap<String, HashSet<String>>();
		// 遍历所有端口信息
		for (int i = 4; i < portInput.size(); i++) {
			// 截取固定长度
			String proto = portInput.get(i).substring(2, 5).trim().toUpperCase();
			if (proto.equals("TCP")) {
				String ipAndPort = portInput.get(i).substring(9, 31).trim();
				String pid = portInput.get(i).substring(71).trim();
				String[] split = ipAndPort.split(":");
				String port = split[split.length - 1];
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
