package com.wjx.process.manager.table.util;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.wjx.process.manager.pojo.ProcessMsg;

public class TableViewerLabelProvider implements ITableLabelProvider {

	@Override
	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image getColumnImage(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		ProcessMsg pMsg = (ProcessMsg) arg0;
		switch (arg1) {
		case 0:
			return pMsg.getPid();
		case 1:
			return pMsg.getName();
		case 2:
			return pMsg.getPort();
		}

		return null;
	}

}
