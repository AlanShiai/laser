package org.bai.zhang.prj;

import java.util.Timer;
import java.util.TimerTask;

import org.bai.zhang.prj.nls.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class CopyOfView extends ViewPart {
	public static final String ID = "org.bai.zhang.prj.view";
	
	private Font newFont;
	
	private Button button1, button2, button2s, button3,  button3_1;
//	private Button button4, button5, button6, button7;
//	private Button button8;

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
		button1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if ( composite_1064 !=  stackLayout.topControl ) {
					stackLayout.topControl = composite_1064;
				}
				mainPage.layout();
			}
		});
		button2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if ( composite_532P !=  stackLayout.topControl ) {
					stackLayout.topControl = composite_532P;
				}
				mainPage.layout();
			}
		});
		button2s.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if ( composite_532S !=  stackLayout.topControl ) {
					stackLayout.topControl = composite_532S;
				}
				mainPage.layout();
			}
		});
		button3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if ( composite_355 !=  stackLayout.topControl ) {
					stackLayout.topControl = composite_355;
				}
				mainPage.layout();
			}
		});
		button3_1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if ( composite_5 !=  stackLayout.topControl ) {
					stackLayout.topControl = composite_5;
				}
				mainPage.layout();
			}
		});
//		button4.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				MessageDialog.openInformation(Display.getDefault().getActiveShell(), getClass() + " execute", Messages.slash2_menu2);
//			}
//		});
//		button5.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				MessageDialog.openInformation(Display.getDefault().getActiveShell(), getClass() + " execute", Messages.slash2_menu3);
//			}
//		});
//		button6.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				MessageDialog.openInformation(Display.getDefault().getActiveShell(), getClass() + " execute", Messages.slash2_menu4);
//			}
//		});
//		button7.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				MessageDialog.openInformation(Display.getDefault().getActiveShell(), getClass() + " execute", Messages.slash2_menu5);
//			}
//		});
//		button8.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				MessageDialog.openInformation(Display.getDefault().getActiveShell(), getClass() + " execute", Messages.slash3_menu2);
//			}
//		});
	}

	private void createTopPanel(Composite top) {
		SashForm form = new SashForm(top,SWT.HORIZONTAL);
		form.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		form.setLayout(new FillLayout());

		Composite expandBarTop = new Composite(form,SWT.NONE);
		expandBarTop.setLayout(new FillLayout());
		
		createExpandBar(expandBarTop);

		mainPage = new Composite(form,SWT.NONE);
//		mainPage.setLayout(new FillLayout(SWT.VERTICAL));
		createMainPage(mainPage);
		
		form.setWeights(new int[] {20,70});
	}
	
	private StackLayout stackLayout;
	private void createMainPage(Composite mainPage) {
		stackLayout = new StackLayout();
		mainPage.setLayout(stackLayout);
		
		create1064Area(mainPage);
//		532 355 5_other
		create532PArea(mainPage);
		create532SArea(mainPage);
		create355Area(mainPage);
		create5_otherArea(mainPage);
		
		stackLayout.topControl = composite_1064;
	}
	
	private Composite mainPage;
	
	private Composite composite_1064;
	private StackLayout stackLayout2;
	private Label label1_1, label1_2;
	private Composite composite_1064_child;
	private void create1064Area(Composite parent) {
		composite_1064 = new Composite(parent, SWT.NONE);
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = 10;
		composite_1064.setLayout(fillLayout);
		
		Label label1 = new Label(composite_1064, SWT.NONE);
//		label1.setImage(Activator.getImageDescriptor("icons/1064.png").createImage());
		fillLabelWithImage(label1, Activator.getImageDescriptor("icons/1064.png").createImage());
		
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				System.out.print("click");
				if ( label1_2 ==  stackLayout2.topControl ) {
					stackLayout2.topControl = label1_1;
				} else {
					stackLayout2.topControl = label1_2;
				}
				composite_1064_child.layout();
			}
		});
		
		composite_1064_child = new Composite(composite_1064, SWT.NONE);
		stackLayout2 = new StackLayout();
		composite_1064_child.setLayout(stackLayout2);
		
		label1_1 = new Label(composite_1064_child, SWT.NONE);
//		label1_1.setImage(Activator.getImageDescriptor("icons/intensity1.png").createImage());
		fillLabelWithImage(label1_1, Activator.getImageDescriptor("icons/intensity1.png").createImage());
		label1_2 = new Label(composite_1064_child, SWT.NONE);
//		label1_2.setImage(Activator.getImageDescriptor("icons/intensity2.png").createImage());
		fillLabelWithImage(label1_2, Activator.getImageDescriptor("icons/intensity2.png").createImage());
		stackLayout2.topControl = label1_2;
	}
	
	private void fillLabelWithImage(Label label1, final Image originalImage) {
		label1.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				Image tmpImage;

				ImageData data = originalImage.getImageData().scaledTo(e.width, e.height);
				tmpImage = new Image(e.display, data);
				e.gc.drawImage(tmpImage, 0, 0);

				if(null != tmpImage && !tmpImage.isDisposed()) {
					tmpImage.dispose();
				}
			}
		});
	}

	private Composite composite_532P;
	private StackLayout stackLayout3;
	private Label label2_1, label2_2;
	private Composite composite_532_child;
	private void create532PArea(Composite parent) {
		composite_532P = new Composite(parent, SWT.NONE);
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = 10;
		composite_532P.setLayout(fillLayout);
		
		Label label1 = new Label(composite_532P, SWT.NONE);
//		label1.setImage(Activator.getImageDescriptor("icons/532P.png").createImage());
		fillLabelWithImage(label1, Activator.getImageDescriptor("icons/532P.png").createImage());
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				System.out.print("click");
				if ( label2_2 ==  stackLayout3.topControl ) {
					stackLayout3.topControl = label2_1;
				} else {
					stackLayout3.topControl = label2_2;
				}
				composite_532_child.layout();
			}
		});
		
		composite_532_child = new Composite(composite_532P, SWT.NONE);
		stackLayout3 = new StackLayout();
		composite_532_child.setLayout(stackLayout3);
		
		label2_1 = new Label(composite_532_child, SWT.NONE);
//		label2_1.setImage(Activator.getImageDescriptor("icons/intensity1.png").createImage());
		fillLabelWithImage(label2_1, Activator.getImageDescriptor("icons/intensity1.png").createImage());
		label2_2 = new Label(composite_532_child, SWT.NONE);
//		label2_2.setImage(Activator.getImageDescriptor("icons/intensity2.png").createImage());
		fillLabelWithImage(label2_2, Activator.getImageDescriptor("icons/intensity2.png").createImage());
		stackLayout3.topControl = label2_1;
	}
	
	private Composite composite_532S;
	private StackLayout stackLayout3s;
	private Label label2_1s, label2_2s;
	private Composite composite_532s_child;
	private void create532SArea(Composite parent) {
		composite_532S = new Composite(parent, SWT.NONE);
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = 10;
		composite_532S.setLayout(fillLayout);
		
		Label label1 = new Label(composite_532S, SWT.NONE);
//		label1.setImage(Activator.getImageDescriptor("icons/532S.png").createImage());
		fillLabelWithImage(label1, Activator.getImageDescriptor("icons/532S.png").createImage());
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				System.out.print("click");
				if ( label2_2s ==  stackLayout3s.topControl ) {
					stackLayout3s.topControl = label2_1s;
				} else {
					stackLayout3s.topControl = label2_2s;
				}
				composite_532s_child.layout();
			}
		});
		
		composite_532s_child = new Composite(composite_532S, SWT.NONE);
		stackLayout3s = new StackLayout();
		composite_532s_child.setLayout(stackLayout3s);
		
		label2_1s = new Label(composite_532s_child, SWT.NONE);
//		label2_1s.setImage(Activator.getImageDescriptor("icons/intensity1.png").createImage());
		fillLabelWithImage(label2_1s, Activator.getImageDescriptor("icons/intensity1.png").createImage());
		label2_2s = new Label(composite_532s_child, SWT.NONE);
//		label2_2s.setImage(Activator.getImageDescriptor("icons/intensity2.png").createImage());
		fillLabelWithImage(label2_2s, Activator.getImageDescriptor("icons/intensity2.png").createImage());
		stackLayout3s.topControl = label2_1s;
	}
	
	private Composite composite_355;
	private StackLayout stackLayout4;
	private Label label3_1, label3_2;
	private Composite composite_355_child;
	private void create5_otherArea(Composite parent) {
		composite_355 = new Composite(parent, SWT.NONE);
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = 10;
		composite_355.setLayout(fillLayout);
		
		Label label1 = new Label(composite_355, SWT.NONE);
//		label1.setImage(Activator.getImageDescriptor("icons/355.png").createImage());
		fillLabelWithImage(label1, Activator.getImageDescriptor("icons/355.png").createImage());
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				System.out.print("click");
				if ( label3_2 ==  stackLayout4.topControl ) {
					stackLayout4.topControl = label3_1;
				} else {
					stackLayout4.topControl = label3_2;
				}
				composite_355_child.layout();
			}
		});
		
		composite_355_child = new Composite(composite_355, SWT.NONE);
		stackLayout4 = new StackLayout();
		composite_355_child.setLayout(stackLayout4);
		
		label3_1 = new Label(composite_355_child, SWT.NONE);
//		label3_1.setImage(Activator.getImageDescriptor("icons/intensity1.png").createImage());
		fillLabelWithImage(label3_1, Activator.getImageDescriptor("icons/intensity1.png").createImage());
		label3_2 = new Label(composite_355_child, SWT.NONE);
//		label3_2.setImage(Activator.getImageDescriptor("icons/intensity2.png").createImage());
		fillLabelWithImage(label3_2, Activator.getImageDescriptor("icons/intensity2.png").createImage());
		stackLayout4.topControl = label3_1;
	}
	
	private Composite composite_5;
	private StackLayout stackLayout5;
	private Label label4_1, label4_2;
	private Composite composite_5_child;
	private void create355Area(Composite parent) {
		composite_5 = new Composite(parent, SWT.NONE);
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = 10;
		composite_5.setLayout(fillLayout);
		
		Label label1 = new Label(composite_5, SWT.NONE);
//		label1.setImage(Activator.getImageDescriptor("icons/5_other.png").createImage());
		fillLabelWithImage(label1, Activator.getImageDescriptor("icons/5_other.png").createImage());
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				System.out.print("click");
				if ( label4_2 ==  stackLayout5.topControl ) {
					stackLayout5.topControl = label4_1;
				} else {
					stackLayout5.topControl = label4_2;
				}
				composite_5_child.layout();
			}
		});
		
		composite_5_child = new Composite(composite_5, SWT.NONE);
		stackLayout5 = new StackLayout();
		composite_5_child.setLayout(stackLayout5);
		
		label4_1 = new Label(composite_5_child, SWT.NONE);
//		label4_1.setImage(Activator.getImageDescriptor("icons/intensity1.png").createImage());
		fillLabelWithImage(label4_1, Activator.getImageDescriptor("icons/intensity1.png").createImage());
		label4_2 = new Label(composite_5_child, SWT.NONE);
//		label4_2.setImage(Activator.getImageDescriptor("icons/intensity2.png").createImage());
		fillLabelWithImage(label4_2, Activator.getImageDescriptor("icons/intensity2.png").createImage());
		stackLayout5.topControl = label4_2;
	}

	private void createExpandBar(Composite parent) {
		ExpandBar bar = new ExpandBar (parent, SWT.V_SCROLL);
		
		createExpandItem4(bar);
//		createExpandItem3(bar);
//		createExpandItem2(bar);
		createExpandItem1(bar);
	}

	private void createExpandItem1(ExpandBar bar) {
		Composite composite = new Composite (bar, SWT.NONE);
		composite.setLayout(new RowLayout());
		
		button1 = new Button (composite, SWT.PUSH);
		button1.setImage(Activator.getImageDescriptor("icons/slash1.png").createImage());
		button1.setToolTipText(Messages.slash1_menu2);
		
		button2 = new Button (composite, SWT.PUSH);
		button2.setImage(Activator.getImageDescriptor("icons/slash2.png").createImage());
		button2.setToolTipText(Messages.slash1_menu3);
		
		button2s = new Button (composite, SWT.PUSH);
		button2s.setImage(Activator.getImageDescriptor("icons/slash2.png").createImage());
		button2s.setToolTipText(Messages.slash1_menu3_s);

		button3 = new Button (composite, SWT.PUSH);
		button3.setImage(Activator.getImageDescriptor("icons/slash3.png").createImage());
		button3.setToolTipText(Messages.slash1_menu4);
		
		button3_1 = new Button (composite, SWT.PUSH);
		button3_1.setImage(Activator.getImageDescriptor("icons/slash4.png").createImage());
		button3_1.setToolTipText(Messages.slash1_menu5);
		
		ExpandItem item = new ExpandItem (bar, SWT.NONE, 0);
		item.setText(Messages.slash1_menu1);
		item.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item.setControl(composite);
		item.setExpanded(true);
	}
	
//	private void createExpandItem2(ExpandBar bar) {
//		Composite composite = new Composite (bar, SWT.NONE);
//		composite.setLayout(new RowLayout());
//		
//		button4 = new Button (composite, SWT.PUSH);
//		button4.setImage(Activator.getImageDescriptor("icons/slash4.png").createImage());
//		button4.setToolTipText(Messages.slash2_menu2);
//		
//		button5 = new Button (composite, SWT.PUSH);
//		button5.setImage(Activator.getImageDescriptor("icons/slash5.png").createImage());
//		button5.setToolTipText(Messages.slash2_menu3);
//
//		button6 = new Button (composite, SWT.PUSH);
//		button6.setImage(Activator.getImageDescriptor("icons/slash6.png").createImage());
//		button6.setToolTipText(Messages.slash2_menu4);
//
//		button7 = new Button (composite, SWT.PUSH);
//		button7.setImage(Activator.getImageDescriptor("icons/slash7.png").createImage());
//		button7.setToolTipText(Messages.slash2_menu5);
//		
//		ExpandItem item = new ExpandItem (bar, SWT.NONE, 0);
//		item.setText(Messages.slash2_menu1);
//		item.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
//		item.setControl(composite);
//		item.setExpanded(true);
//	}
	
//	private void createExpandItem3(ExpandBar bar) {
//		Composite composite = new Composite (bar, SWT.NONE);
//		composite.setLayout(new RowLayout());
//		
//		button8 = new Button (composite, SWT.PUSH);
//		button8.setImage(Activator.getImageDescriptor("icons/slash8.png").createImage());
//		button8.setToolTipText(Messages.slash3_menu2);
//		
//		ExpandItem item = new ExpandItem (bar, SWT.NONE, 0);
//		item.setText(Messages.slash3_menu1);
//		item.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
//		item.setControl(composite);
//		item.setExpanded(true);
//	}
	
	private Text text;
	private long time = 600;
	
	private void createExpandItem4(ExpandBar bar) {
		Composite composite = new Composite (bar, SWT.NONE);
		composite.setLayout(new RowLayout());
		
		text = new Text(composite, SWT.MULTI);
		text.setText(time/60 + ":" + time%60);
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			public void run() {
				if ( 0 == time) {
					time = 600;
				} else {
					time = time - 1;
				}
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						if(null != text && !text.isDisposed())
							text.setText(time/60 + ":" + time%60);
					}
				});
			}
		}, 1000, 1000);
		
		Font initialFont = text.getFont();
		FontData[] fontData = initialFont.getFontData();
		for (int i = 0; i < fontData.length; i++) {
			fontData[i].setHeight(50);
		}
		newFont = new Font(Display.getDefault(), fontData);
		text.setFont(newFont);
		
		ExpandItem item = new ExpandItem (bar, SWT.NONE, 0);
		item.setText(Messages.slash4_menu1);
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
		label.setText("0");

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
		// TODO Auto-generated method stub
		super.dispose();
		
		if (null != newFont && !newFont.isDisposed()) {
			newFont.dispose();
		}
	}
}