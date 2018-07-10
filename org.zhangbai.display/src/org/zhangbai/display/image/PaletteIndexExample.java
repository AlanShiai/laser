package org.zhangbai.display.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class PaletteIndexExample {
	static Display display;

	public void drawImage(final Image image) {
		Shell shell = new Shell (display);
		shell.setLayout (new FillLayout ());
		shell.setSize(400, 300);
		shell.setLocation(600,300);
		Canvas canvas = new Canvas (shell, SWT.NONE);
		canvas.addPaintListener (new PaintListener () {
			public void paintControl (PaintEvent e) {
				e.gc.drawImage (image, 20, 20);
			}
		});

		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ())
				display.sleep ();
		}
	}
	

	public Image createIndexImage() {
		RGB redRGB = new RGB(0xFF,0,0);
		RGB greenRGB = new RGB(0,0xFF,0);
		PaletteData paletteData = new PaletteData(new RGB[]{redRGB,greenRGB});

		ImageData imageData = new ImageData(48,48,1,paletteData);
		for(int x=11;x<35;x++){
			for(int y=11;y<35;y++){
				imageData.setPixel(x,y,1);
			}
		}
		return new Image(display,imageData);
	}

	public static void main(String[] args) {
		display = new Display();
		PaletteIndexExample e = new PaletteIndexExample();
		e.drawImage(e.createIndexImage());
	}
}