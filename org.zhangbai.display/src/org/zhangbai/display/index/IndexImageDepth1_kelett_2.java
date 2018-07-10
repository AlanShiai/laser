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

public class IndexImageDepth1_kelett_2 {
	
//	private final static byte[] subData = {(byte)0x80, 0x40, 0x20, 0x10, 0x08, 0x04, 0x02, 0x01,};
	private final static byte[] subData = {(byte)0x80, 0x0B, 0x06, 0x03, 0x18, 0x0B, 0x06, 0x03,};
//	private final static byte[] subData = {(byte)0xE0, (byte)0xE0, (byte)0xE0, 0x70, 0x38, 0x1B, 0x0E, 0x07,};
	
	private final static byte[] data = new byte[500*128];

	private static void resetDataBasedOnSigma355() {
		int count = 0, validateNum = 0;
		double aDouble = 0.0, sum = 0.0;
		int row = 499, value = 0;
		for (String line : ReadFile.readFile("Sigma355.txt")) {
			count ++;
			aDouble = Double.parseDouble(line);
			if ( 20 == count) {
				if ( 0 != validateNum ) {
					value = (int) ( (sum / validateNum) * 1000 * 1024 );
					if (value > 1024) {
						data[row*128 + 127] = subData[7];
					} else {
						data[row*128 + value/8] = subData[value%8];
					}
				}
				row --;
				count = 0;
				validateNum = 0;
				sum = 0.0;
			}
			if(aDouble < 0.00000001) {
				continue;
			}
			validateNum++;
			sum += aDouble;
		}
	}

	public static Image createIndexImage() {
		PaletteData paletteData = new PaletteData(
				new RGB[] {
						MyRGB.BLACK,
						MyRGB.RED,
				});
		
		resetDataBasedOnSigma355();

		int width = 1024;
		int height = 500;
		ImageData imageData = new ImageData(width, height, 1, paletteData, 2, data);
		
		return new Image(Display.getDefault(), imageData);
	}



	public static void main(String[] args) {

		Shell shell = new Shell (Display.getDefault());
		shell.setLayout (new FillLayout ());
		shell.setSize(1200, 700);
		shell.setLocation(300,300);
		Canvas canvas = new Canvas (shell, SWT.NONE);
		canvas.addPaintListener (new PaintListener () {
			public void paintControl (PaintEvent e) {
				e.gc.drawImage (createIndexImage(), 50, 50);
			}
		});

		shell.open ();
		while (!shell.isDisposed ()) {
			if (!Display.getDefault().readAndDispatch ())
				Display.getDefault().sleep ();
		}
	}

}
