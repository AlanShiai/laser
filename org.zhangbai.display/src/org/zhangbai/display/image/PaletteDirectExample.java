package org.zhangbai.display.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class PaletteDirectExample {

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
		// ***                    PaletteData(redMask, greenMask, blueMask)
		PaletteData palette = new PaletteData(0xFF0000   , 0xFF00   , 0xFF);

		ImageData imageData = new ImageData(48,48,24,palette);
		for (int x=0;x<48;x++) {
			for(int y=0;y<48;y++) {
				if(y > 11 && y < 35 && x > 11 && x < 35) {
					imageData.setPixel(x,y,0xFFFFFF);   // Set the center to green
				} else {
					imageData.setPixel(x,y,0xFF);   // and everything else to red
				}
			}
		}
		return new Image(display,imageData);
	}

	public static void main(String[] args) {
		display = new Display();
		PaletteDirectExample e = new PaletteDirectExample();
		e.drawImage(e.createIndexImage());
	}

}