package org.bai.zhang.prj.sample.dialog;

import org.bai.zhang.prj.readfile.ReadFile;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class LidarDialog  extends Dialog {
	
	private static Image legend = IndexImageDepth8_4_legend2.createIndexImage();
	private static Image lidarImage;
	private static Image lidar_text = new Image(Display.getDefault(),ReadFile.class.getResourceAsStream("lidar_text.png"));
	
	public LidarDialog(Shell parentShell) {
		super(parentShell);
	}
	
	protected Image getLidarImage() {
		return IndexImageDepth8_4_lidar.createIndexImage();
	}
	
	public int open() {
		lidarImage = getLidarImage();
		return super.open();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
//		lidarImage = getLidarImage();
		
		Canvas canvas = new Canvas (parent, SWT.NO_REDRAW_RESIZE);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.widthHint = 990;
		gridData.heightHint = 600;
		canvas.setLayoutData(gridData);

		canvas.addPaintListener (new PaintListener () {
			public void paintControl (PaintEvent e) {
				e.gc.drawImage (lidarImage, 100, 20);
				e.gc.drawImage(legend, 800, 20);
				
				for (int i = 0; i < 9; i++) {
					int y = 421 - i * 44;
					e.gc.drawLine(94, y, 99, y);
					e.gc.drawText(i+"", 80, y-8);
				}
				
				e.gc.drawText("Times(CST)", 400,  462);
				for (int i = 0; i < 24; i++) {
					int x = 100 + i * 29;
					e.gc.drawLine(x, 422, x, 427);
					e.gc.drawText(i+1+"", x, 435);
				}
				
				e.gc.drawLine(836,  20, 841,  20);
				e.gc.drawText("6E-2", 846,  20);
				e.gc.drawLine(836, 221, 841, 221);
				e.gc.drawText("6E-2", 846, 221);
				e.gc.drawLine(836, 421, 841, 421);
				e.gc.drawText("0",841, 421);
				
				e.gc.drawImage(lidar_text, 0, 100, lidar_text.getBounds().width/2, lidar_text.getBounds().height-100, 
						20, 50, lidar_text.getBounds().width/2, lidar_text.getBounds().height-100);
				e.gc.drawImage(lidar_text, 0+lidar_text.getBounds().width/2, 0, lidar_text.getBounds().width/2, lidar_text.getBounds().height, 
						900, 50, lidar_text.getBounds().width/2, lidar_text.getBounds().height);
			}
		});
		
		return parent;
	}
	
	@Override
	public boolean close() {
		if (lidarImage != null && lidarImage.isDisposed()) {
			lidarImage.dispose();
		}
		if (legend != null && legend.isDisposed()) {
			legend.dispose();
		}
		return super.close();
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Lidar Sample");
	}

	@Override
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE;
	}

	public static void main(String[] args) {
		new LidarDialog(Display.getDefault().getActiveShell()).open();
	}


}
