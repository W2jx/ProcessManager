package com.wjx.process.manager.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.wjx.process.manager.pojo.ProcessMsg;
import com.wjx.process.manager.table.util.ProcessManagerUtil;

public abstract class AbstractProcessManageService implements ProcessManageService {

	public  String[] getProcessScript;
	public  String[] killProcessScript;
	public  String[] getPortScript;

	/**
	 * 把输入数据转换成进程数据
	 * 
	 * @param processInput
	 * @return
	 */
	protected abstract ArrayList<ProcessMsg> input2ProcessMsgs(List<String> processInput, HashMap<String, String> pid2Ports);

	/**
	 * kill进程的过滤条件，返回true时表示可以kill
	 * 
	 * @return
	 */
	protected abstract boolean filter4KillProcess(ProcessMsg pMsg);

	/**
	 * 把输入的port数据转换成pid和port的map数据
	 * 
	 * @param portInput
	 * @return
	 */
	protected abstract HashMap<String, String> input2PidPortMap(List<String> portInput);

	
	@Override
	public ArrayList<ProcessMsg> getProcessMsg() {
		ArrayList<ProcessMsg> processMsgs = new ArrayList<ProcessMsg>();
		HashMap<String, String> pid2Ports = getPid2Ports();
		Process processinput = ProcessManagerUtil.execScript(getProcessScript);
		if (Objects.nonNull(processinput)) {
			try (BufferedReader out = new BufferedReader(new InputStreamReader(
					new BufferedInputStream(processinput.getInputStream()), Charset.forName("UTF-8")));
					BufferedReader br = new BufferedReader(
							new InputStreamReader(new BufferedInputStream(processinput.getErrorStream())))) {

				// 获取进程信息
				List<String> list = new ArrayList<>();
				String ostr;
				while ((ostr = out.readLine()) != null) {
					list.add(ostr);
				}
				processMsgs.addAll(input2ProcessMsgs(list, pid2Ports));

				// 错误信息
				String estr = br.readLine();
				if (estr != null)
					System.out.println("error:" + estr);

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("获取进程信息");
		}
		return processMsgs;
	}

	@Override
	public void killProcess(ProcessMsg pMsg) {
		if (Objects.isNull(pMsg))
			return;
		if (filter4KillProcess(pMsg)) {
			System.out.println("要kill进程号为：" + pMsg.getPid() + "的进程");
			String[] script = Arrays.copyOf(killProcessScript, killProcessScript.length + 1);
			script[script.length - 1] = pMsg.getPid();
			ProcessManagerUtil.execScript(script);
		}
	}

	/**
	 * 获取端口信息
	 * @return
	 */
	private HashMap<String, String> getPid2Ports() {
		HashMap<String, String> pid2ports = new HashMap<String, String>();
		Process execScript = ProcessManagerUtil.execScript(getPortScript);
		if (Objects.nonNull(execScript)) {
			try (BufferedReader out = new BufferedReader(new InputStreamReader(
					new BufferedInputStream(execScript.getInputStream()), Charset.forName("UTF-8")));
					BufferedReader br = new BufferedReader(
							new InputStreamReader(new BufferedInputStream(execScript.getErrorStream())))) {

				// 获取行信息
				List<String> list = new ArrayList<>();
				String portstr;
				while ((portstr = out.readLine()) != null) {
					list.add(portstr);
				}
				pid2ports.putAll(input2PidPortMap(list));
				
				// 错误信息
				String estr = br.readLine();
				if (estr != null) {
					System.out.println("error:" + estr);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("获取端口信息");
		}

		return pid2ports;
	}

	public AbstractProcessManageService(String[] getProcessScript, String[] killProcessScript, String[] getPortScript) {
		super();
		this.getProcessScript = getProcessScript;
		this.killProcessScript = killProcessScript;
		this.getPortScript = getPortScript;
	}
	
	
	

}
