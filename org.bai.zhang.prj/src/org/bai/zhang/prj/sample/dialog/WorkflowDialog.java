package org.bai.zhang.prj.sample.dialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import org.bai.zhang.prj.sample.dialog.WorkTestObject.Status;
import org.bai.zhang.prj.util.MyDate;
import org.bai.zhang.prj.util.Serial;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class WorkflowDialog extends Dialog {

	static Button startButton, stopButton;
//	static Combo serialPortCombo;
	static Table testDataTable;
	
	static Timer workflowTimer;
	
	static List<WorkTestObject> workTestObjects;

	public WorkflowDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		parent.setLayout (new GridLayout (2, false));

		testDataTable = new Table (parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		testDataTable.setLinesVisible (true);
		testDataTable.setHeaderVisible (true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 400;
		data.widthHint = 600;
		testDataTable.setLayoutData(data);
		String[] titles = {"Index", "Start Time", "End Time", "Status"};
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (testDataTable, SWT.NONE);
			column.setText (titles [i]);
		}
		//		int count = 58;
		//		for (int i=0; i<count; i++) {
		//			TableItem item = new TableItem (table, SWT.NONE);
		//			item.setText (0, i+1+"");
		//			item.setText (1, "2018-01-13 10:20");
		//			item.setText (2, "2018-01-13 10:21");
		//			item.setText (3, "Not Start");
		//		}
		Integer[] width = {80, 160, 160, 100};
		for (int i=0; i<titles.length; i++) {
			testDataTable.getColumn(i).setWidth(width[i]);
		}

		createButtons(parent);

		Canvas canvas = new Canvas (parent, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 400;
		data.widthHint = 600;
		canvas.setLayoutData(data);
		canvas.addPaintListener (new PaintListener () {
			public void paintControl (PaintEvent e) {
				e.gc.drawOval(20, 20, 100, 100);
			}
		});

		return parent;
	}

	private static void createButtons(Composite parent) {
		Composite top = new Composite (parent, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		top.setLayoutData(data);

		top.setLayout (new GridLayout (2, false));

		Label l = new Label(top, SWT.NONE);
		l.setText("Start Time:");
		Text t = new Text(top, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		t.setLayoutData(data);
		t.setText("1");

		l = new Label(top, SWT.NONE);
		l.setText("Interval:");
		t = new Text(top, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		t.setLayoutData(data);
		t.setText("5");

		l = new Label(top, SWT.NONE);
		l.setText("End Time:");
		t = new Text(top, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		t.setLayoutData(data);
		t.setText("60");

		startButton = new Button(top, SWT.NONE);
		startButton.setText("Start");
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		startButton.setLayoutData(data);
		startButton.setEnabled(true);

		stopButton = new Button(top, SWT.NONE);
		stopButton.setText("Stop");
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		stopButton.setLayoutData(data);
		stopButton.setEnabled(false);
		
//		serialPortCombo = new Combo(top, SWT.READ_ONLY);
//		data = new GridData(SWT.FILL, SWT.FILL, true, false);
//		serialPortCombo.setLayoutData(data);
//		serialPortCombo.setItems(new String[] {
//				"COM0", "COM1", "COM2", "COM3", "COM4",
//				"COM5", "COM6", "COM7", "COM8", "COM9",
//		});
//		serialPortCombo.setText("COM0");

		createButtonListen();
	}

	private static void createButtonListen() {
		startButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("click start Button start!");
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
				
				workTestObjects = generateTableData();
				
				fillTableData();
				
				startWorkflowTasks();
				System.out.println("click start Button end!");
			}

		});

		stopButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("click stop Button start!");
				startButton.setEnabled(true);
				stopButton.setEnabled(false);
				
				stopWorkflowTasks();
				System.out.println("click stop Button end!");
			}

		});

	}

	protected static void startWorkflowTasks() {
		workflowTimer = new Timer(true);
		
		int start = 1, interval = 5, end = 60, testDuration = 1, warmup = 1;

		long delay = 0;
//		for(WorkTestTask laserTask : WorkTestTask.generateOneTestLaserTasks(1*1000, 1*60*1000)) {
//			delay = delay + laserTask.getDelay();
//			workflowTimer.schedule(laserTask, delay);
//		}
		
		for (int i = 0; i < end / interval; i++ ) {
			for(WorkTestTask laserTask : WorkTestTask.generateOneTestLaserTasks(start*1000, testDuration*60*1000)) {
				delay = delay + laserTask.getDelay();
				workflowTimer.schedule(laserTask, delay);
			}
			delay = delay + (5 - testDuration - warmup)*60*1000;
		}

	}

	protected static void stopWorkflowTasks() {
		if ( null != workflowTimer ) {
			workflowTimer.cancel();
		}
	}

	protected static void fillTableData() {
		testDataTable.removeAll();

		for (WorkTestObject testObject : workTestObjects) {
			TableItem item = new TableItem (testDataTable, SWT.NONE);
			item.setText (0, testObject.getTimes()+"");
			item.setText (1, MyDate.getShortDateString(testObject.getStartTime()));
			item.setText (2, MyDate.getShortDateString(testObject.getEndTime()));
			item.setText (3, testObject.getStatus().name());
		}
		
	}

	private static List<WorkTestObject> generateTableData() {
		List<WorkTestObject> testObjects = new ArrayList<WorkTestObject>();

		int start = 1, interval = 5, end = 60;
		Date date = new Date();
		date = WorkTestObject.addMinutes(date, start);

		for (int i = 0; i < end / interval; i++ ) {
			testObjects.add(new WorkTestObject(i+1, date, Status.NotStart));
			date = WorkTestObject.addMinutes(date, interval);
		}
		
		return testObjects;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("work flow dialog " + Serial.getLaserSerialPort() +" "+Serial.getLaserSerialPortBaudrate());
	}

	@Override
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE;
	}

	public static void main(String[] args) {
		new WorkflowDialog(Display.getDefault().getActiveShell()).open();
	}


}
