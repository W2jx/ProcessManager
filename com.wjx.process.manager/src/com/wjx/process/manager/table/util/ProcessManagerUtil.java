package com.wjx.process.manager.table.util;

import java.lang.management.ManagementFactory;
import java.util.Objects;

public class ProcessManagerUtil {

	/**
	 * 获取当前进程的pid
	 * 
	 * @return
	 */
	public static String getRuntimePid() {
		try {
			String name = ManagementFactory.getRuntimeMXBean().getName();
			if (Objects.nonNull(name)) {
				String[] strings = name.split("@");
				if (strings.length > 0) {
					return strings[0];
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 执行脚本
	 * 
	 * @param command
	 * @return
	 */
	public static Process execScript(String... command) {
		try {
			ProcessBuilder processBuilder = new ProcessBuilder(command);
			Process start = processBuilder.start();
			return start;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
