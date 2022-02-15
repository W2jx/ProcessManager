package com.wjx.process.manager.service;

import org.eclipse.core.runtime.ILog;

import com.wjx.process.manager.Activator;
import com.wjx.process.manager.service.impl.LinuxProcessManageServiceImpl;
import com.wjx.process.manager.service.impl.MacProcessManageServiceImpl;
import com.wjx.process.manager.service.impl.WindowsProcessManageServiceImpl;

public class ProcessManageFactory {
	private static final ILog log = Activator.getDefault().getLog();

	public static ProcessManageService getProcessManageService() {
		String property = System.getProperty("os.name").toLowerCase();

		if (property.contains("windows")) {
			String[] getProcessScript = new String[]{"tasklist"};
			String[] killProcessScript = new String[]{"taskkill", "/F", "/T", "/PID"};
			String[] getPortScript = new String[]{"netstat", "-ano"};
			return new WindowsProcessManageServiceImpl(getProcessScript, killProcessScript, getPortScript);

		} else if (property.contains("linux")) {
			String[] getProcessScript = new String[]{"ps", "-ef"};
			String[] killProcessScript = new String[]{"kill", "-9"};
			String[] getPortScript = new String[]{"netstat", "-ntlp"};
			return new LinuxProcessManageServiceImpl(getProcessScript, killProcessScript, getPortScript);

		} else if (property.contains("mac")) {
			String[] getProcessScript = new String[]{"ps", "-ef"};
			String[] killProcessScript = new String[]{"kill", "-9"};
			String[] getPortScript = new String[]{"netstat", "-ntlp"};
			return new MacProcessManageServiceImpl(getProcessScript, killProcessScript, getPortScript);

		} else {
			log.warn(property + " is an unrecognized os!");
			String[] getProcessScript = new String[]{"ps", "-ef"};
			String[] killProcessScript = new String[]{"kill", "-9"};
			String[] getPortScript = new String[]{"netstat", "-ntlp"};
			return new LinuxProcessManageServiceImpl(getProcessScript, killProcessScript, getPortScript);
		}

	}

}
