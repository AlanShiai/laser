package org.bai.zhang.prj.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.bai.zhang.prj.jni.NativeClass;
import org.bai.zhang.prj.sample.dialog.WorkTestObject;
import org.bai.zhang.prj.sample.dialog.WorkTestTask;
import org.bai.zhang.prj.sample.dialog.WorkTestObject.Status;
import org.bai.zhang.prj.util.MyDate;
import org.bai.zhang.prj.util.MyFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class TimingMeasureView  extends ViewPart {
	public static final String ID = "org.bai.zhang.prj.view.TimingMeasureView";

	static Button startButton, stopButton;
	//	static Combo serialPortCombo;
	static Table testDataTable;

	static Text startTime, interval, endTime;
	static int startInt, intervalInt, endInt;

	static Timer workflowTimer;
	static Timer collectDataTimer;

	static Font largeFont;

	static List<WorkTestObject> workTestObjects;

	static boolean shouldStopCollectData = false;

	@Override
	public void createPartControl(Composite parent) {

		parent.setLayout (new GridLayout (2, false));

		testDataTable = new Table (parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		if ( null != getFont() && testDataTable.getFont() != getFont()) {
			testDataTable.setFont(getFont());
		}
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

		//		return parent;
	}

	private Font getFont() {
		if ( null == largeFont) {
			Font systemFont = Display.getDefault().getSystemFont();
			FontData[] fontData = systemFont.getFontData();
			if ( null != fontData && fontData.length > 0) {
				largeFont = new Font(Display.getDefault(), fontData[0].getName(), (int)(fontData[0].getHeight()*1.5), fontData[0].getStyle());
			}
		}
		return largeFont;
	}

	@Override
	public void dispose() {
		super.dispose();
		if ( null != largeFont ) {
			largeFont.dispose();
			largeFont = null;
		}
	}

	private static void createButtons(Composite parent) {
		Composite top = new Composite (parent, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		top.setLayoutData(data);

		top.setLayout (new GridLayout (2, false));

		Label l = new Label(top, SWT.NONE);
		l.setText("Start Time:");
		startTime = new Text(top, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		startTime.setLayoutData(data);
		startTime.setText("1");

		l = new Label(top, SWT.NONE);
		l.setText("Interval:");
		interval = new Text(top, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		interval.setLayoutData(data);
		interval.setText("5");

		l = new Label(top, SWT.NONE);
		l.setText("End Time:");
		endTime = new Text(top, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		endTime.setLayoutData(data);
		endTime.setText("60");

		startButton = new Button(top, SWT.NONE);
		startButton.setText("Start");
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.heightHint = 40;
		startButton.setLayoutData(data);
		startButton.setEnabled(true);

		stopButton = new Button(top, SWT.NONE);
		stopButton.setText("Stop");
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.heightHint = 40;
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

		addStartIntervalEndTimeValidateAndModify();

		createButtonListen();
	}

	private static void addStartIntervalEndTimeValidateAndModify() {
		VerifyListener verifyListener = new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				boolean isDigit = ("0123456789".indexOf(e.text) >= 0 );
				if ( ! isDigit) {
					((Text) e.getSource()).setToolTipText("Please input digit.");
				} else {
					((Text) e.getSource()).setToolTipText(null);
				}
				e.doit = isDigit;
			}

		};
		startTime.addVerifyListener(verifyListener);
		interval.addVerifyListener(verifyListener);
		endTime.addVerifyListener(verifyListener);
	}

	private static void createButtonListen() {
		startButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("click start Button start!");

				if (startTime.getText().trim().equals("")) {
					MessageDialog.openError(null, "Start Time Error", "Please provide big than 1 integer as start time!");
					return;
				}
				startInt = Integer.parseInt(startTime.getText());
				if (startInt < 1) {
					MessageDialog.openError(null, "Start Time Error", "Please provide big than 1 integer as start time!");
					return;
				}

				if (interval.getText().trim().equals("")) {
					MessageDialog.openError(null, "interval Error", "Please provide big than 4 integer as interval time!");
					return;
				}
				intervalInt = Integer.parseInt(interval.getText());
				if (intervalInt < 5) {
					MessageDialog.openError(null, "interval Error", "Please provide big than 4 integer as interval time!");
					return;
				}

				if (endTime.getText().trim().equals("")) {
					MessageDialog.openError(null, "End Time Error", "Please provide big than 10 integer as end time!");
					return;
				}
				endInt = Integer.parseInt(endTime.getText());
				if (endInt < 10) {
					MessageDialog.openError(null, "End Time Error", "Please provide big than 10 integer as end time!");
					return;
				}

				startButton.setEnabled(false);
				stopButton.setEnabled(true);
				workTestObjects = generateTableData();

				fillTableData();

				startWorkflowTasks();
				startCollectDataTimer();
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
				stopCollectDataTimer();
				System.out.println("click stop Button end!");
			}

		});
	}

	protected static void startCollectDataTimer() {
		collectDataTimer = new Timer(true);

		shouldStopCollectData = false;

		collectDataTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				System.loadLibrary("DataCollectLib");  
				NativeClass nativeCode = new NativeClass();  
				nativeCode.openDevice(); 

				if ( ! MyFile.MEASURE_DATA_DIR.exists()) {
					MyFile.MEASURE_DATA_DIR.mkdir();
				}
				File dataDir = new File(MyFile.MEASURE_DATA_DIR, MyDate.getDateFolderName());
				if ( ! MyFile.BLOCK.exists()) {
					dataDir.mkdir();
				}
				System.out.println("Sava path: " + dataDir.getAbsolutePath());
				while ( ! shouldStopCollectData ) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
						nativeCode.closeDevice();
					}
					nativeCode.collectBlockTriggered();
					if (MyFile.BLOCK.exists()) {
						File renameFile = new File(dataDir, System.currentTimeMillis() + "");
						System.out.println("rename : " + renameFile.getAbsolutePath());
						MyFile.BLOCK.renameTo(renameFile);
					}
				}
				nativeCode.closeDevice();

				System.out.println("finish");
			}

		}, 100);
	}

	protected static void startWorkflowTasks() {
		workflowTimer = new Timer(true);

		//		int start = 1, interval = 5, end = 60; 
		int testDuration = 1, warmup = 1;

		long delay = 0;

		for (int i = 0; i < endInt / intervalInt; i++ ) {
			for(WorkTestTask laserTask : WorkTestTask.generateOneTestLaserTasks(startInt*1000, testDuration*60*1000)) {
				delay = delay + laserTask.getDelay();
				workflowTimer.schedule(laserTask, delay);
			}
			delay = delay + (5 - testDuration - warmup)*60*1000;

			final int index = i;
			workflowTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					Display.getDefault().asyncExec(new Runnable () {
						public void run() {
							updateStatus(index);
						}
					});
				}
			}, delay);
		}
		
		workflowTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				Display.getDefault().asyncExec(new Runnable () {
					public void run() {
						startButton.setEnabled(true);
						stopButton.setEnabled(false);

						stopWorkflowTasks();
						stopCollectDataTimer();
					}
				});
			}
		}, delay);
	}

	protected static void stopCollectDataTimer() {
		shouldStopCollectData = true;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if ( null != collectDataTimer ) {
			collectDataTimer.cancel();
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
			item.setData(testObject);
		}

		/*
		 * Test for update color when testObject update
		 */
//		int tstCol = 0;
		//		for (TableItem item : testDataTable.getItems()) {
		//			tstCol ++;
		//			if (tstCol < 5) {
		//				updateStatus((WorkTestObject) item.getData(), WorkTestObject.Status.Done);
		//			}
		//			if (tstCol == 5) {
		//				updateStatus((WorkTestObject) item.getData(), WorkTestObject.Status.InProgress);
		//			}
		//		}
		for (TableItem item : testDataTable.getItems()) {
			updateStatus((WorkTestObject) item.getData(), WorkTestObject.Status.InProgress);
			break;
		}
	}

	protected static void updateStatus(int i) {
		int row = 0;
		for (TableItem item : testDataTable.getItems()) {
			if (row == i) {
				updateStatus((WorkTestObject) item.getData(), WorkTestObject.Status.Done);
			}
			if (row == (i + 1)) {
				updateStatus((WorkTestObject) item.getData(), WorkTestObject.Status.InProgress);
				break;
			}
			row ++;
		}
	}

	protected static void updateStatus(WorkTestObject testObject, WorkTestObject.Status status) {
		testObject.setStatus(status);

		for (TableItem item : testDataTable.getItems()) {
			if (item.getData() == testObject) {
				item.setText (3, status.name());
				if (WorkTestObject.Status.InProgress == status) {
					item.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_YELLOW));
				} else if (WorkTestObject.Status.Done == status) {
					item.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));
				}
			}
		}
	}

	private static List<WorkTestObject> generateTableData() {
		List<WorkTestObject> testObjects = new ArrayList<WorkTestObject>();

		//		int start = 1, interval = 5, end = 60;
		Date date = new Date();
		date = WorkTestObject.addMinutes(date, startInt);

		for (int i = 0; i < endInt / intervalInt; i++ ) {
			testObjects.add(new WorkTestObject(i+1, date, Status.NotStart));
			date = WorkTestObject.addMinutes(date, intervalInt);
		}

		return testObjects;
	}


	@Override
	public void setFocus() {

	}

}
