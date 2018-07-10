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

public class IndexImageDepth2 {
	
	public static Image createIndexImage() {
		PaletteData paletteData = new PaletteData(
				new RGB[] {
						MyRGB.RED,
						MyRGB.GREEN,
						MyRGB.BLACK,
						MyRGB.WHITE,
				});

		ImageData imageData = new ImageData(48,48,2,paletteData);
		
		for (int x = 0; x < 16; x++ ) {
			for (int y = 8; y < 16; y++) {
				imageData.setPixel(x,y,3);
			}
		}
		
		System.out.println(imageData.scanlinePad);
		
		System.out.println(24 * 24);
		System.out.println(imageData.data.length);
		
		for (int x = 0; x < 48; x++ ) {
			for (int y = 0; y < 12; y++) {
				System.out.printf("%x,",imageData.data[x*12 + y]);
			}
			System.out.println();
		}
		
		
//		for(int x=11;x<35;x++) {
//			for(int y=11;y<35;y++) {
//				if (x % 2 == 0 && y % 2 == 0 ) {
//					imageData.setPixel(x,y,1);
//				}
//				else {
//					imageData.setPixel(x,y,2);
//				}
//			}
//		}
		return new Image(Display.getDefault(),imageData);
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
