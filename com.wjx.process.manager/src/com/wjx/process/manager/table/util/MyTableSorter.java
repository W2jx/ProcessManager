package com.wjx.process.manager.table.util;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.wjx.process.manager.pojo.ProcessMsg;

public class MyTableSorter extends ViewerSorter {

	// 正数表示升序
	private static final int PID = 1;
	private static final int NAME = 2;

	// 排序器对象
	public static final MyTableSorter PID_ASC = new MyTableSorter(PID);
	public static final MyTableSorter PID_DESC = new MyTableSorter(-PID);
	public static final MyTableSorter NAME_ASC = new MyTableSorter(NAME);
	public static final MyTableSorter NAME_DESC = new MyTableSorter(-NAME);

	private int sortType;

	public MyTableSorter(int sortType) {
		super();
		this.sortType = sortType;
	}

	@Override
	public int compare(Viewer viewer, Object first, Object second) {
		ProcessMsg fiMsg = (ProcessMsg) first;
		ProcessMsg seMsg = (ProcessMsg) second;

		switch (sortType) {
		case PID:
			try {
				return Integer.parseInt(fiMsg.getPid()) - Integer.parseInt(seMsg.getPid());
			} catch (Exception e) {
				return fiMsg.getPid().compareTo(seMsg.getPid());
			}
			
		case -PID:
			try {
				return Integer.parseInt(seMsg.getPid()) - Integer.parseInt(fiMsg.getPid());
			} catch (Exception e) {
				return -(fiMsg.getPid().compareTo(seMsg.getPid()));
			}
			
		case NAME:
			return fiMsg.getName().toLowerCase().compareTo(seMsg.getName().toLowerCase());
		case -NAME:
			return -(fiMsg.getName().toLowerCase().compareTo(seMsg.getName().toLowerCase()));
		}
		return 0;
	}

}
