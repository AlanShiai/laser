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

public class IndexImageDepth4_display2 {

	
	private static void resetImageDataBasedOnFileData(ImageData imageData) {
		int volgetMax = 10000;
		double volgeMaxL = 10000.0;

		int GREEN_INDEX = 1;
		int RED_INDEX = 2;
		int YELLOW_INDEX = 3;
		int RED2_INDEX = 4;
		int BLUE_INDEX = 5;
		
		// draw x
		for (int i = 0; i < imageData.width; i++) {
			imageData.setPixel(i, imageData.height/4, GREEN_INDEX);
			imageData.setPixel(i, imageData.height/2, GREEN_INDEX);
			imageData.setPixel(i, imageData.height*3/4, GREEN_INDEX);
		}
		
		int step = 10000/imageData.width;
		
		String[] stringArray;
		int current355 = 0, sum355 = 0;
		int current355p = 0, sum355p = 0;
		int current355s = 0, sum355s = 0;
		int current1064 = 0, sum1064 = 0;
		
		int x = 0, count = 0;
		for (String line : ReadFile.readFile("1522487503895")) {
			if ( ! line.trim().equals("") ) {
				stringArray = line.split("\t");
				current355 = Integer.valueOf(stringArray[0].trim());
				current355p = Integer.valueOf(stringArray[1].trim());
				current355s = Integer.valueOf(stringArray[2].trim());
				current1064 = Integer.valueOf(stringArray[3].trim());
				count ++;
				if (step == count) {
					current355 = sum355 / step;
					current355p = sum355p / step;
					current355s = sum355s / step;
					current1064 = sum1064 / step;
					if ( current355 <= volgetMax ) {
						imageData.setPixel(x, (int) (imageData.height/2 - current355/volgeMaxL*imageData.height/2), RED_INDEX);
					}
					if ( current355p <= volgetMax ) {
						imageData.setPixel(x, (int) (imageData.height/2 - current355p/volgeMaxL*imageData.height/2), YELLOW_INDEX);
					}
					if ( current355s <= volgetMax ) {
						imageData.setPixel(x, (int) (imageData.height/2 - current355s/volgeMaxL*imageData.height/2), RED2_INDEX);
					}
					if ( current1064 <= volgetMax ) {
						imageData.setPixel(x, (int) (imageData.height/2 - current1064/volgeMaxL*imageData.height/2), BLUE_INDEX);
					}
					x++;
					count = 0;
					sum355 = 0;
					sum355p = 0;
					sum355s = 0;
					sum1064 = 0;
					if ( imageData.width == x) {
						break;
					}
				} else {
					sum355 += current355;
					sum355p += current355p;
					sum355s += current355s;
					sum1064 += current1064;
				}
			}
		}
	}

	public static Image createIndexImage() {
		PaletteData paletteData = new PaletteData(
				new RGB[] {
						MyRGB.BLACK, MyRGB.GREEN, MyRGB.RED,   MyRGB.YELLOW,
						MyRGB.RED2,  MyRGB.BLUE,  MyRGB.WHITE, MyRGB.WHITE,
						MyRGB.WHITE, MyRGB.WHITE, MyRGB.WHITE, MyRGB.WHITE,
						MyRGB.WHITE, MyRGB.WHITE, MyRGB.WHITE, MyRGB.WHITE,
				});
		
		int width = 1024;
		int height = 500;
		ImageData imageData = new ImageData(width, height, 4, paletteData);
		
		resetImageDataBasedOnFileData(imageData);
		
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
