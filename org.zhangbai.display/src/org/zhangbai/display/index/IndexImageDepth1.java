package org.zhangbai.display.index;

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

public class IndexImageDepth1 {

	static Display display;

	public static Image createIndexImage() {
		PaletteData paletteData = new PaletteData(
				new RGB[] {
						MyRGB.RED,
						MyRGB.GREEN,
				});

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

		Shell shell = new Shell (display);
		shell.setLayout (new FillLayout ());
		shell.setSize(400, 300);
		shell.setLocation(600,300);
		Canvas canvas = new Canvas (shell, SWT.NONE);
		canvas.addPaintListener (new PaintListener () {
			public void paintControl (PaintEvent e) {
				e.gc.drawImage (createIndexImage(), 20, 20);
			}
		});

		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ())
				display.sleep ();
		}
	}

}
