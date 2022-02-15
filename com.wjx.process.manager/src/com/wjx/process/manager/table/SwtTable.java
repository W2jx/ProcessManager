package com.wjx.process.manager.table;

import java.util.ArrayList;
import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.wjx.process.manager.pojo.ProcessMsg;
import com.wjx.process.manager.service.ProcessManageFactory;
import com.wjx.process.manager.service.ProcessManageService;

public class SwtTable {

	protected Shell shell;
	private Table table;
	private TableItem item;
	private final ProcessManageService pManageService = ProcessManageFactory.getProcessManageService();

	public static void main(String[] args) {
		SwtTable swtTable = new SwtTable();
		swtTable.open();
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		shell.dispose();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		FormLayout formLayout = new FormLayout();
		shell.setSize(600, 600);
		shell.setText("Process Manager");
		shell.setLayout(formLayout);

		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				int selectionIndex = table.getSelectionIndex();
				item = table.getItem(selectionIndex);
			
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		TableColumn tblclmnPid = new TableColumn(table, SWT.NONE);
		tblclmnPid.setWidth(200);
		tblclmnPid.setResizable(true);
		tblclmnPid.setText("pid");

		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(200);
		tblclmnName.setResizable(true);
		tblclmnName.setText("name");

		TableColumn tblclmnPort = new TableColumn(table, SWT.NONE);
		tblclmnPort.setWidth(200);
		tblclmnPort.setResizable(true);
		tblclmnPort.setText("port");

		ArrayList<ProcessMsg> processMsgs = pManageService.getProcessMsg();
		for (ProcessMsg pMsg : processMsgs) {
			TableItem tableItem = new TableItem(table, SWT.NONE);
			tableItem.setText(new String[] { pMsg.getPid(), pMsg.getName(), pMsg.getPort() });
		}

		/** 按钮 */
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(Objects.isNull(item)) {
					return;
				}
				pManageService.killProcess(new ProcessMsg(item.getText(0), item.getText(1), item.getText(2)));
				ArrayList<ProcessMsg> newProcessMsgs = pManageService.getProcessMsg();
				table.removeAll();
				for (ProcessMsg pMsg : newProcessMsgs) {
					TableItem tableItem = new TableItem(table, SWT.NONE);
					tableItem.setText(new String[] { pMsg.getPid(), pMsg.getName(), pMsg.getPort() });
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		FormData buFormData = new FormData();
		buFormData.right = new FormAttachment(100, -5);
		buFormData.bottom = new FormAttachment(100, -5);
		buFormData.left = new FormAttachment(90);
		buFormData.top = new FormAttachment(95);
		btnNewButton.setLayoutData(buFormData);
		btnNewButton.setText("kill");

		FormData tFormData = new FormData();
		tFormData.bottom = new FormAttachment(btnNewButton, -10);
		tFormData.top = new FormAttachment(0, 4);
		tFormData.left = new FormAttachment(0, 5);
		tFormData.right = new FormAttachment(100, -5);
		table.setLayoutData(tFormData);

	}

}
