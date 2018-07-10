package org.zhangbai.display.index;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.zhangbai.display.readfile.ReadFile;

public class IndexImageDepth8_4_lidar2 {

	private static final String constVar = "IndexImageDepth8_4_lidar2";

	private static Image lidarImage = IndexImageDepth8_4_lidar.createIndexImage();

	private static Image legend = IndexImageDepth8_4_legend2.createIndexImage();
	
	private static Image lidar_text = new Image(Display.getDefault(),ReadFile.class.getResourceAsStream("lidar_text.png"));

	public static void main(String[] args) {
		Shell shell = new Shell (Display.getDefault());
		shell.setLayout (new FillLayout ());
		shell.setSize(990, 700);
		shell.setLocation(100,100);
		
		shell.setTextDirection(SWT.RIGHT_TO_LEFT);
		
		Canvas canvas = new Canvas (shell, SWT.NO_REDRAW_RESIZE);
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
		shell.setText(constVar);

		shell.open ();
		while (!shell.isDisposed ()) {
			if (!Display.getDefault().readAndDispatch())
				Display.getDefault().sleep ();
		}

		if (lidarImage != null && lidarImage.isDisposed()) {
			lidarImage.dispose();
		}
		if (legend != null && legend.isDisposed()) {
			legend.dispose();
		}
	}
}
