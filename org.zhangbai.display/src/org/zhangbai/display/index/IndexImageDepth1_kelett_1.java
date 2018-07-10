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

public class IndexImageDepth1_kelett_1 {
	
	private final static byte[] data = new byte[512*128];
	
	public static Image createIndexImage() {
		PaletteData paletteData = new PaletteData(
				new RGB[] {
						MyRGB.BLACK,
						MyRGB.RED,
				});

		int width = 1024;
		int height = 512;
		ImageData imageData = new ImageData(width, height, 1, paletteData, 2, data);
		
		return new Image(Display.getDefault(), imageData);
	}

	
	public static void main(String[] args) {

		Shell shell = new Shell (Display.getDefault());
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
			if (!Display.getDefault().readAndDispatch ())
				Display.getDefault().sleep ();
		}
	}

}
