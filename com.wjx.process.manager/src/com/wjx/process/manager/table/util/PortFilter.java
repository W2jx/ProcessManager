package com.wjx.process.manager.table.util;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.wjx.process.manager.pojo.ProcessMsg;

public class PortFilter extends ViewerFilter{
	
	private String fileterStr;

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		try {
			ProcessMsg pMsg = (ProcessMsg)element;
			String port = pMsg.getPort();
			if(port.contains(fileterStr)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getFileterStr() {
		return fileterStr;
	}

	public void setFileterStr(String fileterStr) {
		this.fileterStr = fileterStr;
	}
	
	
	
	

}
