package com.wjx.process.manager.table.util;

import java.util.Objects;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.actions.ActionGroup;

import com.wjx.process.manager.pojo.ProcessMsg;
import com.wjx.process.manager.service.ProcessManageService;

public class MyActionGroup extends ActionGroup {

	private TableViewer tViewer;
	private ProcessManageService pManageService;

	public MyActionGroup(TableViewer tViewer, ProcessManageService pManageService) {
		super();
		this.pManageService = pManageService;
		this.tViewer = tViewer;
	}

	@Override
	public void fillContextMenu(IMenuManager mgr) {
		MenuManager menuManager = (MenuManager) mgr;
		menuManager.add(new KillAction(tViewer, pManageService));

		Table table = tViewer.getTable();
		Menu createContextMenu = menuManager.createContextMenu(table);
		table.setMenu(createContextMenu);
	}
	
	
	public void fillActionToolBars(ToolBarManager toolBarManager) {
		KillAction killAction = new KillAction(tViewer, pManageService);
		RefreshAction refreshAction = new RefreshAction(tViewer, pManageService);
		FindAction findAction = new FindAction(tViewer, pManageService);
		toolBarManager.add(createContributionItem(killAction));
		toolBarManager.add(createContributionItem(refreshAction));
		toolBarManager.add(findAction);
		
		toolBarManager.update(true);
	}
	
	private IContributionItem createContributionItem(Action action) {
		ActionContributionItem actionContributionItem = new ActionContributionItem(action);
		actionContributionItem.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		return actionContributionItem;
	}
	
	
	/**
	 * kill进程
	 * @author liferay
	 *
	 */
	private class KillAction extends Action {

		private TableViewer tViewer;
		private ProcessManageService pManageService;

		public KillAction(TableViewer tViewer, ProcessManageService pManageService) {
			this.tViewer = tViewer;
			this.pManageService = pManageService;
			setText("Kill");
		}

		@Override
		public void run() {
			IStructuredSelection selection = (IStructuredSelection)tViewer.getSelection();
			ProcessMsg pMsg = (ProcessMsg)selection.getFirstElement();
			if(Objects.isNull(pMsg)) {
				MessageDialog.openInformation(null, null, "Please select the target first!");
			}else {
				if(MessageDialog.openConfirm(null, "CONFIRM", "Do you want to kill the process? PID:"+pMsg.getPid())) {
					pManageService.killProcess(pMsg);
//					new RefreshAction(tViewer, pManageService).run();
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tViewer.getTable().removeAll();
					tViewer.refresh();
					tViewer.setInput(pManageService.getProcessMsg());
				}
			}
			
		}
	}
	
	/**
	 * 刷新
	 * @author liferay
	 *
	 */
	private class RefreshAction extends Action{
		private TableViewer tViewer;
		private ProcessManageService pManageService;

		public RefreshAction(TableViewer tViewer, ProcessManageService pManageService) {
			this.tViewer = tViewer;
			this.pManageService = pManageService;
			setText("Refresh");
		}
		
		@Override
		public void run() {
				tViewer.getTable().removeAll();
				tViewer.resetFilters();
				tViewer.refresh();
				tViewer.setInput(pManageService.getProcessMsg());
		}
	}
	
	
	/**
	 * 过滤，查找
	 * @author liferay
	 *
	 */
	private class FindAction extends Action{
		private TableViewer tViewer;
		private String filterStr;
		private ProcessManageService pManageService;
		
		public FindAction(TableViewer tViewer, ProcessManageService pManageService) {
			this.tViewer = tViewer;
			this.pManageService = pManageService;
			setText("Find");
		}
		
		@Override
		public void run() {
				InputDialog inputDialog = new InputDialog(null, "FIND", "Please enter a port number.", "", null);
				inputDialog.open();
				filterStr = inputDialog.getValue();
				PortFilter portFilter = new PortFilter();
				portFilter.setFileterStr(filterStr);
				new RefreshAction(tViewer, pManageService).run();
				tViewer.setFilters(portFilter);
		}
	}
	
}

