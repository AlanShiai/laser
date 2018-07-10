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

public class IndexImageDepth1_kelett_532p {
	
	private static int RED_INDEX = 1;
	
	private static void resetImageDataBasedOnSigma355(ImageData imageData) {
		int count = 0, validateNum = 0;
		double aDouble = 0.0, sum = 0.0;
		int row = imageData.height-1, value = 0, lastValue=-1;
		for (String line : ReadFile.readFile("Sigma532p.txt")) {
			count ++;
			aDouble = Double.parseDouble(line);
			if ( 20 == count) {
				if ( 0 != validateNum ) {
					value = (int) ( (sum / validateNum) * 1000 * imageData.width );
					if (value > imageData.width-1) {
						System.out.println(value);
						value = imageData.width-1;
					}
					imageData.setPixel(value, row, RED_INDEX);
					if (lastValue != -1) {
						if (lastValue < value) {
							for (int i = lastValue; i < value; i++ ) {
								imageData.setPixel(i, row, RED_INDEX);
							}
						} else {
							for (int i = value; i < lastValue; i++ ) {
								imageData.setPixel(i, row, RED_INDEX);
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

	public static Image createIndexImage() {
		PaletteData paletteData = new PaletteData(
				new RGB[] {
						MyRGB.BLACK,
						MyRGB.RED,
				});
		
		int width = 1024;
		int height = 500;
		ImageData imageData = new ImageData(width, height, 1, paletteData);
		
		resetImageDataBasedOnSigma355(imageData);
		
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
