package com.wjx.process.manager.pojo;

public class ProcessMsg {

	/**进程号*/
	private String pid;
	
	/**进程名称*/
	private String name;
	
	/**进程占用端口*/
	private String port;

	public ProcessMsg(String pid, String name, String port) {
		super();
		this.pid = pid;
		this.name = name;
		this.port = port;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
