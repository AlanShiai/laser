package org.bai.zhang.prj;


import java.io.File;
import java.io.FilenameFilter;
import java.util.Date;
import java.util.List;

import org.bai.zhang.prj.jni.algorithm_for_file;
import org.bai.zhang.prj.nls.Messages;
import org.bai.zhang.prj.readfile.ReadFile;
import org.bai.zhang.prj.sample.dialog.IndexImageDepth8_4_legend2;
import org.bai.zhang.prj.sample.dialog.MyPaletteData;
import org.bai.zhang.prj.sample.dialog.MyRGB;
import org.bai.zhang.prj.util.MyDate;
import org.bai.zhang.prj.util.MyFile;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;

public class View extends ViewPart {
	public static final String ID = "org.bai.zhang.prj.view";

	private static CHANNEL channel = CHANNEL.CHANNEL_355;

	enum CHANNEL {
		CHANNEL_355,
		CHANNEL_532p,
		CHANNEL_532s,
		CHANNEL_1064,
	}

	private Font newFont;

	private Button button1064, button532p, button532s, button355,  button3_1;

	static int height = 1000;
	static int THI_WIDTH = 0;
	private final static double[] distance_data_355 = new double[10000];

	private static double default_Max10, default_Min10;

	static {
		for (int i =  0 ; i < 10000; i++) {
			distance_data_355[i] =  (i+1) * 1.2 ;
		}
	}

	private static File fTHI_file = new File("E:/zhangbai/export/version20180331_1_real/eclipse/measureData/2018-03-31_1710_1522487422084_m");
	private static File kelleFile = new File("E:/zhangbai/export/version20180330_91_real/eclipse/measureData/2018-03-30_1923_1522409039462_m/1522411632096");;

	private static Image legend = IndexImageDepth8_4_legend2.createIndexImage();
	//	private static Image lidar_text = new Image(Display.getDefault(),ReadFile.class.getResourceAsStream("lidar_text.png"));


	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		Composite top = new Composite(parent, SWT.BORDER);
		top.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		top.setLayout(new GridLayout());

		createTopPanel(top);

		createStatusLine(top);

		addListenings();
	}

	private void addListenings() {
		SelectionAdapter channelButtonListening = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (e.widget instanceof Button) {
					CHANNEL newChannel = (CHANNEL) e.widget.getData(CHANNEL.class.getName());
					if (newChannel != channel) {
						channel = newChannel;
						vTHI_Image = createTHI_Image();
						vTHI_Canvas.redraw();
					}
				}
			}
		};
		button1064.addSelectionListener(channelButtonListening);
		button532p.addSelectionListener(channelButtonListening);
		button532s.addSelectionListener(channelButtonListening);
		button355.addSelectionListener(channelButtonListening);

		button3_1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			}
		});
	}

	private void createTopPanel(Composite top) {
		SashForm form = new SashForm(top,SWT.HORIZONTAL);
		form.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		form.setLayout(new FillLayout());

		Composite expandBarTop = new Composite(form,SWT.NONE);
		expandBarTop.setLayout(new FillLayout());

		createExpandBar(expandBarTop);

		Composite mainPage = new Composite(form, SWT.NONE);
		mainPage.setLayout(new FillLayout(SWT.VERTICAL));
		createMainPage(mainPage);

		form.setWeights(new int[] {20,70});
	}

	public static void calculateTHI_Width() {
		File[] files = fTHI_file.listFiles();

		if ( null != files && files.length > 0) {
			THI_WIDTH = files.length;
			THI_WIDTH = (THI_WIDTH/4) * 4; // Make sure height is *4
		}
	}
	
	private static void calculateMaxAndMin(File[] files) {
		int [] original_data_355 = new int [10000];
		double [] filter_data_355 = new double [10000];
		double [] R_355 = new double [10000];
		double [] S_355 = new double [10000];
		double [] P_1_355 = new double [10000];
		double c1=0.35;
		
		default_Max10 = 0.0;
		default_Min10 = 0.0;
		
		for (File file : files) {
			List<String> lines = ReadFile.readFile(file);
			int i = 0; String[] stringArray;
			int current = 0;

			for (String line : lines) {
				if ( ! line.trim().equals("") ) {
					stringArray = line.split("\t");
					current = Integer.valueOf(stringArray[channel.ordinal()].trim());
					original_data_355[i] = current;
					i++;
					if ( i >= 10000) {
						break;
					}
				}
			}
			double sum=0, sum1 = 0;
			for(i=8000;i<=9999;i++)
			{
				sum+=original_data_355[i];
			}
			double back_ground_signal=sum/(9999-8000+1);
			for(i=20;i<3000-20;i++)
			{
				sum=0;
				sum1=0;
				for(int j=-20;j<21;j++)
				{
					sum+=(original_data_355[i+j]-back_ground_signal);
					sum1+=(distance_data_355[i+j]);
				}
				filter_data_355[i]=sum/41.0;
				R_355[i]=sum1/41.0;
				S_355[i]=filter_data_355[i]/c1;
				P_1_355[i]=S_355[i]*R_355[i]*R_355[i] / 1000;
			}

			double current_MAX = 0.0;
			double current_MIN = 0.0;
			for (int k = 0; k < 3000; k++) {
				if (current_MAX < P_1_355[k]) {
					current_MAX = P_1_355[k];
				} else if (current_MIN > P_1_355[k]) {
					current_MIN = P_1_355[k];
				}
			}
			double current_MAX10 = current_MAX * 3;
			double current_MIN10 = current_MIN * 3;
			
			if (current_MAX10 > default_Max10) {
				default_Max10 = current_MAX10;
			}
			if (current_MIN10 < default_Min10) {
				default_Min10 = current_MIN10;
			}

		}
	}

	private static void fillData(byte[] data) {
		File[] files = fTHI_file.listFiles();

		int [] original_data_355 = new int [10000];
		double [] filter_data_355 = new double [10000];
		double [] R_355 = new double [10000];
		double [] S_355 = new double [10000];
		double [] P_1_355 = new double [10000];
		double c1=0.35;

		int col = 0;
		if ( null != files ) {
			
			calculateMaxAndMin(files);
			
			System.out.println("default_Max10 + " + default_Max10);
			
			long start = System.currentTimeMillis();
			System.out.println("aijun 1" + files.length);
			for (File file : files) {
				List<String> lines = ReadFile.readFile(file);
				int i = 0; String[] stringArray;
				double value = 0;
				int current = 0;
				int row = height - 1;

				for (String line : lines) {
					if ( ! line.trim().equals("") ) {
						stringArray = line.split("\t");
						current = Integer.valueOf(stringArray[channel.ordinal()].trim());
						original_data_355[i] = current;
						i++;
						if ( i >= 10000) {
							break;
						}
					}
				}
				double sum=0, sum1 = 0;
				for(i=8000;i<=9999;i++)
				{
					sum+=original_data_355[i];
				}
				double back_ground_signal=sum/(9999-8000+1);
				for(i=20;i<3000-20;i++)
				{
					sum=0;
					sum1=0;
					for(int j=-20;j<21;j++)
					{
						sum+=(original_data_355[i+j]-back_ground_signal);
						sum1+=(distance_data_355[i+j]);
					}
					filter_data_355[i]=sum/41.0;
					R_355[i]=sum1/41.0;
					S_355[i]=filter_data_355[i]/c1;
					P_1_355[i]=S_355[i]*R_355[i]*R_355[i] / 1000;
				}

				i = 0;
				for (int k = 0; k < 3000; k++) {
					value += P_1_355[k];
					i++;
					if ( i == 3) {
						value = value / 3;
						data[row * THI_WIDTH + col] = (byte)(((value-0) * 255.0)/default_Max10);
						i = 0; value = 0;
						row --;
					}
				}
				col ++;
				if (col == THI_WIDTH ) {
					break;
				}
			}
			long end = System.currentTimeMillis();
			System.out.println("aijun 1" + (end - start));
		}

	}

	public static Image createTHI_Image() {

		calculateTHI_Width();

		byte[] data = new byte[THI_WIDTH * height];
		//		Arrays.fill(data, (byte) 1);
		fillData(data);

		ImageData imageData = new ImageData(THI_WIDTH, height, 8, MyPaletteData.createPaletteAsLidar256(), 4, data);

		imageData = imageData.scaledTo(686, 402);

		return new Image(Display.getDefault(), imageData);
	}

	private static int RED_INDEX = 1;
	private static void resetImageDataBasedOnSigmaFile(ImageData imageData) {
		int count = 0, validateNum = 0;
		double aDouble = 0.0, sum = 0.0;
		int row = imageData.height-1, value = 0, lastValue=-1;

		File SigmaFile;
		if (CHANNEL.CHANNEL_355 == channel) {
			SigmaFile = MyFile.SIGMA_355;
		} else if (CHANNEL.CHANNEL_532p == channel) {
			SigmaFile = MyFile.SIGMA_532p;
		} else if (CHANNEL.CHANNEL_532s == channel) {
			SigmaFile = MyFile.SIGMA_532s;
		} else {
			SigmaFile = MyFile.SIGMA_1064;
		}
		
		int max = 0;
		// calculate max;
		for (String line : ReadFile.readFile(SigmaFile)) {
			count ++;
			aDouble = Double.parseDouble(line);
			if ( 20 == count) {
				if ( 0 != validateNum ) {
					value = (int) ( (sum / validateNum) * 1000 * imageData.width );
					if (value > imageData.width-1) {
						value = imageData.width-1;
					}
					if (value > max) {
						max = value;
					}
				}
				count = 0;
				validateNum = 0;
				sum = 0.0;
			}
			if(aDouble < 0.0) {
				aDouble = -aDouble;
			}
			validateNum++;
			sum += aDouble;
		}
		
		count = 0; validateNum = 0;
		aDouble = 0.0; sum = 0.0;
		row = imageData.height-1; value = 0; lastValue=-1;

		//		for (String line : ReadFile.readFile(new File("E:/soft/eclipse-modeling-luna-SR1-win32/eclipse/./Sigma355.txt"))) {
		for (String line : ReadFile.readFile(SigmaFile)) {
			count ++;
			aDouble = Double.parseDouble(line);
			if ( 20 == count) {
				if ( 0 != validateNum ) {
					value = (int) ( (sum / validateNum) * 1000 * imageData.width * (imageData.width / max) );
					if (value > imageData.width-1) {
						value = imageData.width-1;
					}
					imageData.setPixel(value, row, RED_INDEX);
					if (lastValue != -1) {
						if (lastValue < value) {
							for (int i = lastValue; i < value; i++ ) {
								imageData.setPixel(i, row, RED_INDEX);
								if (row - 1 > 0) {
									imageData.setPixel(i, row-1, RED_INDEX);
								}
							}
						} else {
							for (int i = value; i < lastValue; i++ ) {
								imageData.setPixel(i, row, RED_INDEX);
								if (row - 1 > 0) {
									imageData.setPixel(i, row-1, RED_INDEX);
								}
							}
						}
					}
					lastValue = value;
				}
				row --;
				count = 0;
				validateNum = 0;
				sum = 0.0;
			}
			if(aDouble < 0.0) {
				aDouble = -aDouble;
				//				continue;
			}
			validateNum++;
			sum += aDouble;
		}
	}

	public static Image createKelleImage() {
		PaletteData paletteData = new PaletteData(
				new RGB[] {
						MyRGB.BLACK,
						MyRGB.RED,
				});

		ImageData imageData = new ImageData(1024, 500, 1, paletteData);

		resetImageDataBasedOnSigmaFile(imageData);

		return new Image(Display.getDefault(), imageData);
	}

	private static Image vTHI_Image;
	private Image getTHI() {
		if ( null == vTHI_Image ) {
			vTHI_Image = createTHI_Image();
		}
		return vTHI_Image;
	}


	private static Image kelleImage;
	private Image getKelleImage() {
		if ( null == kelleImage ) {
			kelleImage = createKelleImage();
		}
		return kelleImage;
	}

	private static Canvas vTHI_Canvas;

	private static Canvas kelleCanvas;
	private void createMainPage(Composite mainPage) {
		vTHI_Canvas = new Canvas (mainPage, SWT.NO_REDRAW_RESIZE);
		kelleCanvas = new Canvas (mainPage, SWT.NO_REDRAW_RESIZE);
		vTHI_Canvas.addPaintListener (new PaintListener () {

			public void paintControl (PaintEvent e) {
				int canvasX = vTHI_Canvas.getBounds().x;
				int canvasY = vTHI_Canvas.getBounds().y;
				int canvasWidth = vTHI_Canvas.getBounds().width;
				int canvasHeight = vTHI_Canvas.getBounds().height;

				if ( canvasWidth - 200 > 0 && canvasHeight - 100 > 0) {
					e.gc.drawImage (getTHI(), 0, 0, getTHI().getBounds().width, getTHI().getBounds().height,
							40, 20, canvasWidth - 100, canvasHeight - 64);
					e.gc.drawImage(legend, 0, 0, legend.getBounds().width, legend.getBounds().height,
							canvasWidth - 40, 20, legend.getBounds().width, canvasHeight - 64);
				}

				int step = (canvasHeight - 64) / 12;
				for (int i = 0; i < 13; i++) {
					int y = canvasHeight - i * step - 45;
					e.gc.drawLine(34, y, 39, y);
					e.gc.drawText(i+" km", 2, y-8);
				}

				if (fTHI_file.isDirectory()) {
					String[] subFiles = fTHI_file.list();
					String start = MyDate.getShortDateString(subFiles[0]);
					String end = MyDate.getShortDateString(subFiles[subFiles.length - 1]);
					e.gc.drawText(channel.name() + "   Time: " + start + " - " + end , canvasX + canvasWidth/2 - 100 ,  canvasY + canvasHeight - 18);

					long startLong = Long.parseLong(subFiles[0]);
					long endLong = Long.parseLong(subFiles[subFiles.length - 1]);
					long duration = endLong - startLong;
					int interal = (int) duration / 1000 / 12;

					Date startDate = new Date(startLong);

					System.out.println(MyDate.hourMintues.format(startDate));

					for (int i = 0; i < 12; i++) {
						int x = 40 + i * ((canvasWidth - 100)/12);
						e.gc.drawLine(x, canvasY + canvasHeight - 38, x, canvasY + canvasHeight - 44);
						String hourMintues = MyDate.hourMintues.format(MyDate.addSeconds(startDate, interal * i));
						e.gc.drawText(hourMintues, x - 8, canvasY + canvasHeight - 34);
					}

				}

			}
		});

		kelleCanvas.addPaintListener (new PaintListener () {
			public void paintControl (PaintEvent e) {
				int canvasX = vTHI_Canvas.getBounds().x;
				int canvasY = vTHI_Canvas.getBounds().y;
				int canvasWidth = kelleCanvas.getBounds().width;
				int canvasHeight = kelleCanvas.getBounds().height;

				if ( canvasWidth - 200 > 0 && canvasHeight - 100 > 0) {
					e.gc.drawImage (getKelleImage(), 0, 0, getKelleImage().getBounds().width, getKelleImage().getBounds().height,
							40, 20, canvasWidth - 100, canvasHeight - 64);
				}

				String timeStemp = MyDate.getMiddleDateString(new Date(Long.parseLong(kelleFile.getName())));
				e.gc.drawText(timeStemp, canvasX + canvasWidth/2 - 100 ,  canvasY + canvasHeight - 18);

				int step = (canvasHeight - 64) / 12;
				for (int i = 0; i < 13; i++) {
					int y = canvasHeight - i * step - 45;
					e.gc.drawLine(34, y, 39, y);
					e.gc.drawText(i+" km", 2, y-8);
				}

				long start = (long) 0.0;
				for (int i = 0; i < 12; i++) {
					int x = 40 + i * ((canvasWidth - 100)/12);
					e.gc.drawLine(x, canvasY + canvasHeight - 38, x, canvasY + canvasHeight - 44);
					e.gc.drawText((start + 0.1*i + "").substring(0,3), x - 7, canvasY + canvasHeight - 34);
				}
			}
		});


		vTHI_Canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				int canvasX = vTHI_Canvas.getBounds().x;
				int canvasWidth = vTHI_Canvas.getBounds().width;
				System.out.println(e.x);

				File[] files = fTHI_file.listFiles();

				File file = null;
				if ( null != files && files.length > 0) {
					file = files[(e.x - canvasX) * files.length / (canvasWidth)];
				}

				if ( null != file ) {
					System.out.println(file);
					kelleFile = file;
					algorithm_for_file.calculate_kelett(file.getAbsolutePath());
					kelleImage = createKelleImage();
					kelleCanvas.redraw();
				}
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}

		});

	}

	private void createExpandBar(Composite parent) {
		ExpandBar bar = new ExpandBar (parent, SWT.V_SCROLL);

		createHistoryExpandItem(bar);

		createChannelExpandItem(bar);
	}

	private void createHistoryExpandItem(ExpandBar bar) {
		Composite composite = new Composite (bar, SWT.NONE);
		composite.setLayout(new FillLayout());

		final Table table = new Table (composite, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		table.setToolTipText(MyFile.MEASURE_DATA_DIR.getAbsolutePath());

		TableColumn column = new TableColumn (table, SWT.NONE);
		column.setText ("Index");
		column.setWidth(40);
		column = new TableColumn (table, SWT.NONE);
		column.setText ("Start Time");
		column.setWidth(120);
		column = new TableColumn (table, SWT.NONE);
		column.setText ("End Time");
		column.setWidth(120);

		//		TableItem tableItem = new TableItem (table, SWT.NONE);
		//		tableItem.setText (0, "1");
		//		tableItem.setText (1, "2018-03-31_1710");
		//		tableItem.setText (2, "2018-03-31_1710");
		//		
		//		tableItem = new TableItem (table, SWT.NONE);
		//		tableItem.setText (0, "2");		
		//		tableItem.setText (1, "2018-03-31_1710");
		//		tableItem.setText (2, "2018-03-31_1710");

		System.out.println(MyFile.MEASURE_DATA_DIR.getAbsolutePath());

		FilenameFilter historyFolderFilter = new FilenameFilter() {

			public boolean accept(File dir, String name) {
				if (name.endsWith("_m"))
					return true;
				return false;
			}
		};

		int index = 1;
		if (MyFile.MEASURE_DATA_DIR.exists() && MyFile.MEASURE_DATA_DIR.isDirectory()) {
			File[] files = MyFile.MEASURE_DATA_DIR.listFiles(historyFolderFilter);
			for (File file : files) {
				if (file.isDirectory()) {
					TableItem tableItem = new TableItem (table, SWT.NONE);
					String[] subFiles = file.list();
					if (subFiles != null && subFiles.length > 2) {
						tableItem.setText (0, index+""); index++;
						tableItem.setText (1, MyDate.getShortDateString(subFiles[0]));
						tableItem.setText (2, MyDate.getShortDateString(subFiles[subFiles.length - 1]));
						tableItem.setData(file);
					}
				}
			}
		}

		table.addSelectionListener(new SelectionAdapter () {
			public void widgetDefaultSelected(SelectionEvent e) {
				if (table.getSelectionCount() > 0) {
					for (TableItem tableItem : table.getSelection()) {
						if (tableItem.getData() instanceof File) {
							File newFile = (File) tableItem.getData();
							if (newFile != fTHI_file) {
								fTHI_file = newFile;
								vTHI_Image = createTHI_Image();
								vTHI_Canvas.redraw();
							}
						}
						break;
					}
				}
			}
		});

		ExpandItem expandItem = new ExpandItem (bar, SWT.NONE, 0);
		expandItem.setText("History Data");
		expandItem.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		expandItem.setControl(composite);
		expandItem.setExpanded(true);
	}

	private void createChannelExpandItem(ExpandBar bar) {
		Composite composite = new Composite (bar, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));

		button1064 = new Button (composite, SWT.PUSH);
		button1064.setImage(Activator.getImageDescriptor("icons/slash1.png").createImage());
		button1064.setToolTipText(Messages.slash1_menu2); // 1064
		button1064.setData(CHANNEL.class.getName(), CHANNEL.CHANNEL_1064);

		button532p = new Button (composite, SWT.PUSH);
		button532p.setImage(Activator.getImageDescriptor("icons/slash2.png").createImage());
		button532p.setToolTipText(Messages.slash1_menu3); // 532p
		button532p.setData(CHANNEL.class.getName(), CHANNEL.CHANNEL_532p);

		button532s = new Button (composite, SWT.PUSH);
		button532s.setImage(Activator.getImageDescriptor("icons/slash2.png").createImage());
		button532s.setToolTipText(Messages.slash1_menu3_s); // 532s
		button532s.setData(CHANNEL.class.getName(), CHANNEL.CHANNEL_532s);

		button355 = new Button (composite, SWT.PUSH);
		button355.setImage(Activator.getImageDescriptor("icons/slash3.png").createImage());
		button355.setToolTipText(Messages.slash1_menu4); // 355
		button355.setData(CHANNEL.class.getName(), CHANNEL.CHANNEL_355);

		button3_1 = new Button (composite, SWT.PUSH);
		button3_1.setImage(Activator.getImageDescriptor("icons/slash4.png").createImage());
		button3_1.setToolTipText(Messages.slash1_menu5);

		ExpandItem item = new ExpandItem (bar, SWT.NONE, 0);
		item.setText(Messages.slash1_menu1);
		item.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item.setControl(composite);
		item.setExpanded(true);
	}

	private void createStatusLine(Composite parent) {
		Composite statusLine = new Composite(parent, SWT.BORDER);
		statusLine.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		statusLine.setLayout(new GridLayout(4, false));

		CLabel label = new CLabel(statusLine, SWT.NONE);
		label.setLayoutData(new GridData());
		label.setText(Messages.others_high);

		label = new CLabel(statusLine, SWT.BORDER | SWT.CENTER);
		GridData gridData = new GridData();
		gridData.widthHint = 60;
		label.setLayoutData(gridData);
		label.setText("3");

		label = new CLabel(statusLine, SWT.NONE);
		label.setLayoutData(new GridData (SWT.RIGHT, SWT.RIGHT, true, false));
		label.setText(Messages.others_data_ananlyse_done);

		label = new CLabel(statusLine, SWT.BORDER | SWT.CENTER);
		gridData = new GridData (SWT.RIGHT, SWT.RIGHT, false, false);
		gridData.widthHint = 120;
		label.setLayoutData(gridData);
		label.setText("100%");
		label.setBackground(new Color[]{Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN), 
				Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN),
				Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN), 
				Display.getDefault().getSystemColor(SWT.COLOR_GREEN)},
				new int[] {25, 50, 100});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}

	@Override
	public void dispose() {
		super.dispose();

		if (null != newFont && !newFont.isDisposed()) {
			newFont.dispose();
		}
	}
}