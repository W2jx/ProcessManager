package com.wjx.process.manager.table.util;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;

public class TableViewerContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 instanceof List) {
			return((List)arg0).toArray();
		}
		return new Object[0];
	}

}
