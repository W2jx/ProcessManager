package com.wjx.process.manager.table;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;

import com.wjx.process.manager.service.ProcessManageFactory;
import com.wjx.process.manager.service.ProcessManageService;
import com.wjx.process.manager.table.util.MyActionGroup;
import com.wjx.process.manager.table.util.MyTableSorter;
import com.wjx.process.manager.table.util.TableViewerContentProvider;
import com.wjx.process.manager.table.util.TableViewerLabelProvider;

public class MainTableViewer {

	protected Shell shell;
	private Table table;
	private TableItem item;
	private final ProcessManageService pManageService = ProcessManageFactory.getProcessManageService();

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainTableViewer window = new MainTableViewer();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(600, 600);
		shell.setText("Process Manager");
		shell.setLayout(new FillLayout());
		ViewForm viewForm = new ViewForm(shell, SWT.NONE);
		viewForm.setLayout(new FillLayout());

		final TableViewer tableViewer = new TableViewer(viewForm,
				SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);

		TableViewerColumn tViewerColumnPid = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnPid = tViewerColumnPid.getColumn();
		tblclmnPid.setWidth(200);
		tblclmnPid.setText("pid");
		tblclmnPid.addSelectionListener(new SelectionAdapter() {
			boolean asc = true;

			@Override
			public void widgetSelected(SelectionEvent e) {
				tableViewer.setSorter(asc ? MyTableSorter.PID_ASC : MyTableSorter.PID_DESC);
				tableViewer.refresh();
				asc = !asc;

			}
		});

		TableViewerColumn tViewerColumnName = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnName = tViewerColumnName.getColumn();
		tblclmnName.setWidth(200);
		tblclmnName.setText("name");
		tblclmnName.addSelectionListener(new SelectionAdapter() {
			boolean asc = true;

			@Override
			public void widgetSelected(SelectionEvent e) {
				tableViewer.setSorter(asc ? MyTableSorter.NAME_ASC : MyTableSorter.NAME_DESC);
				tableViewer.refresh();
				asc = !asc;

			}
		});

		TableViewerColumn tViewerColumnPort = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnPort = tViewerColumnPort.getColumn();
		tblclmnPort.setWidth(200);
		tblclmnPort.setText("port");

		tableViewer.setContentProvider(new TableViewerContentProvider());
		tableViewer.setLabelProvider(new TableViewerLabelProvider());
		tableViewer.setUseHashlookup(true);
		tableViewer.setInput(pManageService.getProcessMsg());

		/** menu */
		MyActionGroup myActionGroup = new MyActionGroup(tableViewer, pManageService);
		myActionGroup.fillContextMenu(new MenuManager());

		/** toolbar */
		ToolBar toolBar = new ToolBar(viewForm, SWT.NONE);
		ToolBarManager toolBarManager = new ToolBarManager(toolBar);
		myActionGroup.fillActionToolBars(toolBarManager);
		viewForm.setContent(tableViewer.getControl());
		viewForm.setTopLeft(toolBar);

	}

}
