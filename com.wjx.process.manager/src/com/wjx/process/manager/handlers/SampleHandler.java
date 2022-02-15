package com.wjx.process.manager.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.wjx.process.manager.table.MainTableViewer;

public class SampleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		System.out.println("点击表格");

		try {
			new MainTableViewer().open();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("退出表格");
		return null;
	}
}
