package org.bai.zhang.prj.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.bai.zhang.prj.Activator;
import org.bai.zhang.prj.jni.NativeClass;
import org.bai.zhang.prj.readfile.ReadFile;
import org.bai.zhang.prj.sample.dialog.MyRGB;
import org.bai.zhang.prj.sample.dialog.TelescopeMove;
import org.bai.zhang.prj.sample.dialog.WorkTestObject;
import org.bai.zhang.prj.sample.dialog.WorkTestTask;
import org.bai.zhang.prj.sample.dialog.WorkTestObject.Status;
import org.bai.zhang.prj.util.MergeDatum;
import org.bai.zhang.prj.util.MyDate;
import org.bai.zhang.prj.util.MyFile;
import org.bai.zhang.prj.util.Serial;
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class SettingView extends ViewPart {

	public static final String ID = "org.bai.zhang.prj.view.SettingView";

	static Button startDisplayButton, stopDisplayButton;

	static Button startButton, stopButton;

	static Text startTime, interval, endTime;
	static int startInt, intervalInt, endInt;

	static Table testDataTable;

	static Timer workflowTimer;
	static Timer collectDataTimer;
	static Timer displayTimer;

	static Canvas dispalyCanvas;

	static Font largeFont;

	static List<WorkTestObject> workTestObjects;

	static boolean shouldStopCollectData = false;

	static File originalDataSaveDir;
	static File mergeDataSaveDir;
	
	static Text displayRedrawTime, displayFileModifyTime;


	@Override
	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		Group telescopeSettingGroup = new Group(composite, SWT.NONE);
		telescopeSettingGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		telescopeSettingGroup.setText("Telescope Setting");
		createTelescopeSettingArea(telescopeSettingGroup);

		Group workFlowTableGroup = new Group(composite, SWT.NONE);
		workFlowTableGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		workFlowTableGroup.setText("Work Flow Table");
		createWorkFlowTableArea(workFlowTableGroup);

		Group workFlowControlGroup = new Group(composite, SWT.NONE);
		workFlowControlGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		workFlowControlGroup.setText("Work Flow Control");
		createWorkFlowControlArea(workFlowControlGroup);

		Group laserSettingGroup = new Group(composite, SWT.NONE);
		laserSettingGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		laserSettingGroup.setText("Laser Setting");
		createLaserSettingGroupArea(laserSettingGroup);

		Group laserDisplayGroup = new Group(composite, SWT.NONE);
		laserDisplayGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		laserDisplayGroup.setText("Laser Display");
		createLaserDisplayGroupArea(laserDisplayGroup);

		Group laserSettingControlGroup= new Group(composite, SWT.NONE);
		laserSettingControlGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		laserSettingControlGroup.setText("Laser Display Control");
		createLaserSettingControlGroupArea(laserSettingControlGroup);

		/*
		 * disable display setting
		 * 
		Group group2 = new Group(composite, SWT.NONE);
		group2.setLayoutData(new GridData(GridData.FILL_BOTH));
		group2.setText("示波器设置");
		createGroup2Area(group2);
		 */
	}

	private static Image dispalyImage;
	private Image getDisplayImage() {
		if ( null == dispalyImage ) {
			dispalyImage = createDisplayImage();
		}
		return dispalyImage;
	}

	public static Image createDisplayImage() {
		PaletteData paletteData = new PaletteData(
				new RGB[] {
						MyRGB.BLACK, MyRGB.GREEN, MyRGB.RED,   MyRGB.YELLOW,
						MyRGB.RED2,  MyRGB.BLUE,  MyRGB.WHITE, MyRGB.WHITE,
						MyRGB.WHITE, MyRGB.WHITE, MyRGB.WHITE, MyRGB.WHITE,
						MyRGB.WHITE, MyRGB.WHITE, MyRGB.WHITE, MyRGB.WHITE,
				});

		int width = 1024;
		int height = 400;
		ImageData imageData = new ImageData(width, height, 4, paletteData);

		resetImageDataBasedOnFileData(imageData);

		return new Image(Display.getDefault(), imageData);
	}

	private static void resetImageDataBasedOnFileData(ImageData imageData) {
		int volgetMax = 10000;
		double volgeMaxL = 10000.0;

		int GREEN_INDEX = 1;
		int RED_INDEX = 2;
		int YELLOW_INDEX = 3;
		int RED2_INDEX = 4;
		int BLUE_INDEX = 5;

		// draw x
		for (int i = 0; i < imageData.width; i++) {
			imageData.setPixel(i, imageData.height/4, GREEN_INDEX);
			imageData.setPixel(i, imageData.height/2, GREEN_INDEX);
			imageData.setPixel(i, imageData.height*3/4, GREEN_INDEX);
		}

		int step = 10000/imageData.width;

		String[] stringArray;
		int current355 = 0, sum355 = 0;
		int current355p = 0, sum355p = 0;
		int current355s = 0, sum355s = 0;
		int current1064 = 0, sum1064 = 0;

		int x = 0, count = 0;
		if ( ! MyFile.MEASURE_DISPLAY_FILE.exists()) {
			return;
		}
		for (String line : ReadFile.readFile(MyFile.MEASURE_DISPLAY_FILE)) {
			if ( ! line.trim().equals("") ) {
				stringArray = line.split("\t");
				current355 = Integer.valueOf(stringArray[0].trim());
				current355p = Integer.valueOf(stringArray[1].trim());
				current355s = Integer.valueOf(stringArray[2].trim());
				current1064 = Integer.valueOf(stringArray[3].trim());
				count ++;
				if (step == count) {
					current355 = sum355 / step;
					current355p = sum355p / step;
					current355s = sum355s / step;
					current1064 = sum1064 / step;
					if ( current355 <= volgetMax ) {
						imageData.setPixel(x, (int) (imageData.height/2 - current355/volgeMaxL*imageData.height/2), RED_INDEX);
					}
					if ( current355p <= volgetMax ) {
						imageData.setPixel(x, (int) (imageData.height/2 - current355p/volgeMaxL*imageData.height/2), YELLOW_INDEX);
					}
					if ( current355s <= volgetMax ) {
						imageData.setPixel(x, (int) (imageData.height/2 - current355s/volgeMaxL*imageData.height/2), RED2_INDEX);
					}
					if ( current1064 <= volgetMax ) {
						imageData.setPixel(x, (int) (imageData.height/2 - current1064/volgeMaxL*imageData.height/2), BLUE_INDEX);
					}
					x++;
					count = 0;
					sum355 = 0;
					sum355p = 0;
					sum355s = 0;
					sum1064 = 0;
					if ( imageData.width == x) {
						break;
					}
				} else {
					sum355 += current355;
					sum355p += current355p;
					sum355s += current355s;
					sum1064 += current1064;
				}
			}
		}
	}


	private void createLaserSettingControlGroupArea(Composite parent) {
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		parent.setLayout(new GridLayout(1, false));
	
		startDisplayButton = new Button(parent, SWT.NONE);
		startDisplayButton.setText("Start Dispaly");
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.heightHint = 40;
		startDisplayButton.setLayoutData(gridData);
		startDisplayButton.addSelectionListener(new SelectionAdapter() {
	
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("display start 1.");
				System.out.println(MyFile.MEASURE_DISPLAY_FILE.getAbsolutePath());
				System.out.println("display start 2.");
				displayTimer = new Timer(true);
				displayTimer.schedule(new TimerTask() {
					@Override
					public void run() {
						if ( ! MyFile.MEASURE_DISPLAY_FILE.exists()) {
							return;
						}
						if ( MyFile.MEASURE_DISPLAY_FILE.lastModified() != MyFile.MEASURE_DISPLAY_FILE_MODIFY_TIME ) {
							dispalyImage = createDisplayImage();
							MyFile.MEASURE_DISPLAY_FILE_MODIFY_TIME = MyFile.MEASURE_DISPLAY_FILE.lastModified();
							Display.getDefault().asyncExec(new Runnable () {
								public void run() {
									dispalyCanvas.redraw();
									displayRedrawTime.setText(MyDate.getLongDateString());
									displayFileModifyTime.setText(MyDate.getLongDateString(new Date(MyFile.MEASURE_DISPLAY_FILE_MODIFY_TIME)));
								}
							});
						}
					}
	
				}, 1000, 2000);
			}
	
		});
	
	
		stopDisplayButton = new Button(parent, SWT.NONE);
		stopDisplayButton.setText("Stop Dispaly");
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.heightHint = 40;
		stopDisplayButton.setLayoutData(gridData);
		stopDisplayButton.addSelectionListener(new SelectionAdapter() {
	
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("display stop.");
				if (null != displayTimer) {
					displayTimer.cancel();
					displayTimer = null;
				}
			}
		});
		
		Group group = new Group(parent, SWT.NONE);
		group.setText("Redraw Time:");
		group.setLayout(new FillLayout());
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		group.setLayoutData(gridData);
		
		group.setLayout(new FillLayout());
		displayRedrawTime = new Text(group, SWT.NONE);
		displayRedrawTime.setText(MyDate.getLongDateString());
		
		group = new Group(parent, SWT.NONE);
		group.setText("Data file modification time:");
		group.setLayout(new FillLayout());
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		group.setLayoutData(gridData);
		
		group.setLayout(new FillLayout());
		displayFileModifyTime = new Text(group, SWT.NONE);
		displayFileModifyTime.setText(MyDate.getLongDateString(new Date(MyFile.MEASURE_DISPLAY_FILE_MODIFY_TIME)));
	}

	private void createLaserDisplayGroupArea(Composite parent) {
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		parent.setLayout(new GridLayout(1, false));

		Composite displayLeftTop = new Composite(parent, SWT.BORDER);
		//		displayLeftTop.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		displayLeftTop.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		displayLeftTop.setLayout(new GridLayout(1, false));
		dispalyCanvas = new Canvas (displayLeftTop, SWT.NONE);
		dispalyCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		dispalyCanvas.addPaintListener (new PaintListener () {

			@Override
			public void paintControl(PaintEvent e) {
				int canvasWidth = dispalyCanvas.getBounds().width;
				int canvasHeight = dispalyCanvas.getBounds().height;

				e.gc.drawImage (getDisplayImage(), 0, 0, getDisplayImage().getBounds().width, getDisplayImage().getBounds().height,
						0, 0, canvasWidth, canvasHeight);

			}

		});
	}

	private void createLaserSettingGroupArea(Composite parent) {
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		parent.setLayout(new GridLayout(2, false));

		new Label(parent, SWT.NONE).setImage(Activator.getImageDescriptor("icons/display/laser.png").createImage());
		Composite top = new Composite(parent, SWT.NONE);
		top.setLayout(new GridLayout(2, false));

		Button laserInitButton = new Button(top, SWT.NONE);
		laserInitButton.setText("Laser Initialization");
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		laserInitButton.setLayoutData(gridData);
		laserInitButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				WorkTestTask.sendConfigCommand(Serial.COMMAND_01_ONLINE_ON);
			}
		});

		Label voltageLabel = new Label(top, SWT.NONE);
		voltageLabel.setText("Voltage : ");
		final Combo voltageCombo = new Combo(top, SWT.BORDER);
		voltageCombo.setItems(new String[]{"500v", "550v", "600v", "650v", "700v", "750v"});
		voltageCombo.setText("750v");
		//		Text voltageText = new Text(top, SWT.BORDER);
		//		voltageText.setText("10");
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		voltageCombo.setLayoutData(gridData);

		Label freqLabel = new Label(top, SWT.NONE);
		freqLabel.setText("Frequency : ");
		final Combo freqCombo = new Combo(top, SWT.BORDER);
		freqCombo.setItems(new String[]{"1Hz", "2Hz", "5Hz", "10Hz"});
		freqCombo.setText("10Hz");
		//		Text freqText = new Text(top, SWT.BORDER);
		//		freqText.setText("50");
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		freqCombo.setLayoutData(gridData);

		Button laserVoltageFreqApplyButton = new Button(top, SWT.NONE);
		laserVoltageFreqApplyButton.setText("Voltage and Frequency Apply");
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		laserVoltageFreqApplyButton.setLayoutData(gridData);
		laserVoltageFreqApplyButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				WorkTestTask.sendConfigCommand(Serial.getSetVoltageKey(voltageCombo.getText()));
				WorkTestTask.sendConfigCommand(Serial.COMMAND_04_DIV_FREQ);
				WorkTestTask.sendConfigCommand(Serial.getSetFrepKey(freqCombo.getText()));
			}
		});

		Button laserWarmUpButton = new Button(top, SWT.NONE);
		laserWarmUpButton.setText("Laser Warm Up");
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		laserWarmUpButton.setLayoutData(gridData);
		laserWarmUpButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				WorkTestTask.sendConfigCommand(Serial.COMMAND_05_WARM_UP_ON);
			}
		});

		Button laserLaunchButton = new Button(top, SWT.NONE);
		laserLaunchButton.setText("Laser Launch");
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		laserLaunchButton.setLayoutData(gridData);
		laserLaunchButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				WorkTestTask.sendConfigCommand(Serial.COMMAND_07_START_ON);
			}
		});

		Button laserShutDownButton = new Button(top, SWT.NONE);
		laserShutDownButton.setText("Laser Shut Down");
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		laserShutDownButton.setLayoutData(gridData);
		laserShutDownButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				WorkTestTask.sendConfigCommand(Serial.COMMAND_08_START_OFF);
			}
		});

	}

	private void createWorkFlowControlArea(Composite parent) {
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		parent.setLayout(new GridLayout (1, false));

		Composite top = new Composite (parent, SWT.NONE);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		top.setLayoutData(gridData);

		top.setLayout (new GridLayout (2, false));

		Label label = new Label(top, SWT.NONE);
		label.setText("Start Time:");
		startTime = new Text(top, SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		startTime.setLayoutData(gridData);
		startTime.setText("1");

		label = new Label(top, SWT.NONE);
		label.setText("Interval:");
		interval = new Text(top, SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		interval.setLayoutData(gridData);
		interval.setText("5");

		label = new Label(top, SWT.NONE);
		label.setText("End Time:");
		endTime = new Text(top, SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		endTime.setLayoutData(gridData);
		endTime.setText("60");

		startButton = new Button(top, SWT.NONE);
		startButton.setText("Start");
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.heightHint = 40;
		gridData.horizontalSpan = 2;
		startButton.setLayoutData(gridData);
		startButton.setEnabled(true);

		stopButton = new Button(top, SWT.NONE);
		stopButton.setText("Stop");
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.heightHint = 40;
		gridData.horizontalSpan = 2;
		stopButton.setLayoutData(gridData);
		stopButton.setEnabled(false);

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
				String dateFolderName = MyDate.getDateFolderName();
				originalDataSaveDir = new File(MyFile.MEASURE_DATA_DIR, dateFolderName);
				if ( ! originalDataSaveDir.exists()) {
					originalDataSaveDir.mkdir();
				}
				mergeDataSaveDir = new File(MyFile.MEASURE_DATA_DIR, dateFolderName + "_m");
				if ( ! mergeDataSaveDir.exists()) {
					mergeDataSaveDir.mkdir();
				}
				System.out.println("Sava path: " + originalDataSaveDir.getAbsolutePath());
				System.out.println("Merge Sava path: " + mergeDataSaveDir.getAbsolutePath());
				while ( ! shouldStopCollectData ) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
						nativeCode.closeDevice();
					}
					nativeCode.collectBlockTriggered();
					if (MyFile.BLOCK.exists()) {
						File renameFile = new File(originalDataSaveDir, System.currentTimeMillis() + "");
						System.out.println("rename : " + renameFile.getAbsolutePath());
						MyFile.BLOCK.renameTo(renameFile);
//						try {
//							Files.copy(MyFile.BLOCK.toPath(), renameFile.toPath(), StandardCopyOption.ATOMIC_MOVE);
//							if (null != displayTimer) {
//								Files.copy(MyFile.BLOCK.toPath(), MyFile.MEASURE_DISPLAY_FILE.toPath(), StandardCopyOption.ATOMIC_MOVE);
//							}
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
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

					if (null != originalDataSaveDir) {
						MergeDatum.merge(originalDataSaveDir, mergeDataSaveDir);
					}
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

	private void createWorkFlowTableArea(Composite parent) {
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		parent.setLayout(new GridLayout(1, false));

		testDataTable = new Table (parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		if ( null != getFont() && testDataTable.getFont() != getFont()) {
			testDataTable.setFont(getFont());
		}
		testDataTable.setLinesVisible (true);
		testDataTable.setHeaderVisible (true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 300;
		//		data.widthHint = 400;
		testDataTable.setLayoutData(data);
		String[] titles = {"Index", "Start Time", "End Time", "Status"};
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (testDataTable, SWT.NONE);
			column.setText (titles [i]);
		}
		Integer[] width = {70, 140, 140, 8};
		for (int i=0; i<titles.length; i++) {
			testDataTable.getColumn(i).setWidth(width[i]);
		}
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

	private void createTelescopeSettingArea(Composite parent) {
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		parent.setLayout(new GridLayout(2, false));

		new Label(parent, SWT.NONE).setImage(Activator.getImageDescriptor("icons/display/telescope.png").createImage());
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));

		Button placeHolderButton = new Button(composite, SWT.NONE);
		placeHolderButton.setVisible(false);

		Button upButton = new Button(composite, SWT.NONE);
		upButton.setImage(Activator.getImageDescriptor("icons/arrow/up_arrow.png").createImage());
		upButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TelescopeMove.anticlockwiseRotate();
			}
		});

		placeHolderButton = new Button(composite, SWT.NONE);
		placeHolderButton.setVisible(false);

		Button leftlButton = new Button(composite, SWT.NONE);
		leftlButton.setImage(Activator.getImageDescriptor("icons/arrow/left_arrow.png").createImage());
		leftlButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TelescopeMove.leftRotate();
			}
		});

		placeHolderButton = new Button(composite, SWT.NONE);
		placeHolderButton.setVisible(false);

		Button rightButton = new Button(composite, SWT.NONE);
		rightButton.setImage(Activator.getImageDescriptor("icons/arrow/right_arrow.png").createImage());
		rightButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TelescopeMove.rightRotate();
			}
		});

		placeHolderButton = new Button(composite, SWT.NONE);
		placeHolderButton.setVisible(false);

		Button downButton = new Button(composite, SWT.NONE);
		downButton.setImage(Activator.getImageDescriptor("icons/arrow/down_arrow.png").createImage());
		downButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TelescopeMove.clockwiseRotate();;
			}
		});

		placeHolderButton = new Button(composite, SWT.NONE);
		placeHolderButton.setVisible(false);
	}

	protected void createGroup1Area(Composite parent, String image, String buttonText) {
		parent.setLayout(new GridLayout(2, false));
		new Label(parent, SWT.NONE).setImage(Activator.getImageDescriptor("icons/display/" + image).createImage());
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		createLabelButton(composite, buttonText);
	}

	private void createLabelButton(Composite parent, final String buttonText) {
		Button controlButton = new Button(parent, SWT.NONE);
		controlButton.setImage(Activator.getImageDescriptor("icons/arrow/down_arrow.png").createImage());
		//    	controlButton.setText(buttonText);
		GridData gridData = new GridData();
		//    	gridData.widthHint = 200;
		//       	gridData.heightHint = 50;
		controlButton.setLayoutData(gridData);

		controlButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(buttonText + " clicked !");

				if (buttonText.equals("Left Rotation")) {
					TelescopeMove.leftRotate();
				} else if (buttonText.equals("Right Rotation")) {
					TelescopeMove.rightRotate();
				} else if (buttonText.equals("Clockwise Rotation")) {
					TelescopeMove.clockwiseRotate();
				} else if (buttonText.equals("AntiClockwise Rotation")) {
					TelescopeMove.anticlockwiseRotate();
				} else {

				}
			}
		});

	}

	protected void createLabelText(Composite parent, String LabelText) {
		Label lable = new Label(parent, SWT.RIGHT);
		lable.setText(LabelText);
		GridData gridData = new GridData();
		gridData.widthHint = 200;
		lable.setLayoutData(gridData);

		Text text = new Text(parent, SWT.NONE);
		text.setText("");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	protected void createGroup2Area(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		//    	createLabelText(parent, "355波长纵轴设置：");
		//    	createLabelText(parent, "355波长横轴设置：");
		//    	createLabelText(parent, "532波长纵轴设置：");
		//    	createLabelText(parent, "532波长横轴设置：");
		//    	createLabelText(parent, "1064波长纵轴设置：");
		//    	createLabelText(parent, "1064波长横轴设置：");

		Composite displayLeftTop = new Composite(parent, SWT.BORDER);
		displayLeftTop.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		displayLeftTop.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		createLeftTopArea(displayLeftTop);

		Composite displayRightTop = new Composite(parent, SWT.NONE);
		//    	displayRightTop.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));
		displayRightTop.setLayoutData(new GridData());

		createRightTopArea(displayRightTop);
	}

	private Label topLabel1, topLabel2, topLabel3, bottomLabel;
	private void createLeftTopArea(Composite parent) {
		//		parent.setLayout(new GridLayout());
		//		
		//		Composite top = new Composite(parent, SWT.BORDER);
		//		top.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//		top.setLayout(new GridLayout(4, true));

		//		topLabel1 = new Label(top, SWT.NONE);
		//		topLabel1.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));
		//		topLabel1.setText("CH1: 5V/div");
		//
		//		topLabel2 = new Label(top, SWT.NONE);
		//		topLabel2.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));
		//		topLabel2.setText("CH1: 5V/div");
		//
		//		topLabel3 = new Label(top, SWT.NONE);
		//		topLabel3.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));
		//		topLabel3.setText("100ms/div");
		//
		//		Label topLabel4 = new Label(top, SWT.NONE);
		//		topLabel4.setImage(Activator.getImageDescriptor("icons/display/display.png").createImage());
		//		
		//		topLabel1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//		topLabel2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//		topLabel3.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//		topLabel4.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		//		bottomLabel = new Label(parent, SWT.NONE);
		//		bottomLabel.setImage(Activator.getImageDescriptor("icons/display/d11.png").createImage());
		//		
		//		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		//		gridData.widthHint = 300;
		//		bottomLabel.setLayoutData(gridData);
	}

	private void createRightTopArea(Composite parent) {
		parent.setLayout(new GridLayout());

		Group group1 = new Group(parent, SWT.NONE);
		group1.setLayoutData(new GridData());
		group1.setText("通道1（CH1电压/div）");
		createDisplayGroup1Area(group1);

		Group group2 = new Group(parent, SWT.NONE);
		group2.setLayoutData(new GridData());
		group2.setText("通道2（CH2电压/div）");
		createDisplayGroup2Area(group2);

		Group group3 = new Group(parent, SWT.NONE);
		group3.setLayoutData(new GridData());
		group3.setText("时间每格");
		createDisplayGroup3Area(group3);

		Group group4 = new Group(parent, SWT.NONE);
		group4.setLayoutData(new GridData());
		group4.setText("横轴/纵轴");
		createDisplayGroup4Area(group4);
	}

	private void createDisplayGroup4Area(Group group4) {
		group4.setLayout(new GridLayout(5, true));

		Label labely = new Label(group4, SWT.NONE);
		labely.setText("横轴基准");

		final Scale yScale = new Scale (group4, SWT.VERTICAL);
		yScale.setMinimum(0);
		yScale.setIncrement(1);
		yScale.setMaximum(2);
		yScale.setSelection(1);

		Label labelx = new Label(group4, SWT.NONE);
		labelx.setText("纵轴基准");

		final Scale xScale = new Scale (group4, SWT.VERTICAL);
		xScale.setMinimum(0);
		xScale.setIncrement(1);
		xScale.setMaximum(2);
		xScale.setSelection(1);

		yScale.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String iconPath = "icons/display/d" + yScale.getSelection() + xScale.getSelection() + ".png";
				bottomLabel.setImage(Activator.getImageDescriptor(iconPath).createImage());
			}
		});
		xScale.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String iconPath = "icons/display/d" + yScale.getSelection() + xScale.getSelection() + ".png";
				bottomLabel.setImage(Activator.getImageDescriptor(iconPath).createImage());
			}
		});

		Button button = new Button(group4, SWT.NONE);
		button.setText("基准复位");

		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				yScale.setSelection(1);
				xScale.setSelection(1);

				bottomLabel.setImage(Activator.getImageDescriptor("icons/display/d11.png").createImage());
			}
		});
	}

	private void createDisplayGroup1Area(Group group1) {
		group1.setLayout(new GridLayout(5, false));

		String[] arr = new String[] {
				"0.05V","0.25V","0.5V","1V","1.5V",
				"2.5V","5V",
		};

		Combo combo = new Combo(group1, SWT.NONE);
		combo.setItems(arr);

		combo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Combo c = (Combo) e.getSource();
				topLabel1.setText("CH1: " + c.getText() +"/div");
			}
		});
		combo.select(arr.length -1);

		//		for(String text : arr) {
		//			Button b1 = new Button(group1, SWT.NONE);
		//			GridData gridData = new GridData();
		//			gridData.widthHint = 150;
		//			b1.setLayoutData(gridData);
		//			b1.setText(text);
		//			
		//			b1.addSelectionListener(new SelectionAdapter() {
		//				public void widgetSelected(SelectionEvent e) {
		//					Button b = (Button) e.getSource();
		//					topLabel1.setText("CH1: " + b.getText() +"/div");
		//				}
		//			});
		//		}
	}

	private void createDisplayGroup2Area(Group group1) {
		group1.setLayout(new GridLayout(5, false));

		String[] arr = new String[] {
				"0.05V","0.25V","0.5V","1V","1.5V",
				"2.5V","5V",
		};

		Combo combo = new Combo(group1, SWT.NONE);
		combo.setItems(arr);

		combo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Combo c = (Combo) e.getSource();
				topLabel2.setText("CH2: " + c.getText() +"/div");
			}
		});
		combo.select(arr.length -1);

		//		for(String text : arr) {
		//			Button b1 = new Button(group1, SWT.NONE);
		//			GridData gridData = new GridData();
		//			gridData.widthHint = 150;
		//			b1.setLayoutData(gridData);
		//			b1.setText(text);
		//			
		//			b1.addSelectionListener(new SelectionAdapter() {
		//				public void widgetSelected(SelectionEvent e) {
		//					Button b = (Button) e.getSource();
		//					topLabel2.setText("CH2: " + b.getText() +"/div");
		//				}
		//			});
		//		}
	}

	private void createDisplayGroup3Area(Group group1) {
		group1.setLayout(new GridLayout(5, false));

		String[] arr = new String[] {
				"10us","50us","100us","0.2ms","0.5ms",
				"2ms","5ms","10ms","15ms","25ms",
				"50ms","100ms","200ms","500ms","1s",
		};

		for(String text : arr) {
			Button b1 = new Button(group1, SWT.NONE);
			GridData gridData = new GridData();
			gridData.widthHint = 150;
			b1.setLayoutData(gridData);
			b1.setText(text);

			b1.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					Button b = (Button) e.getSource();
					topLabel3.setText(b.getText() +"/div");
				}
			});
		}

	}

	@Override
	public void dispose() {
		super.dispose();
		if ( null != largeFont ) {
			largeFont.dispose();
			largeFont = null;
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
		// TODO Auto-generated method stub

	}
}
