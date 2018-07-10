package org.zhangbai.display.index;

import java.io.File;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.zhangbai.display.readfile.ReadFile;

public class IndexImageDepth8_4_real_1000x244_532 {

	static int height = 1000;
	static int width = 0;
	static int MAX = 5000;

	public static void getHeight() {
		File dataFolder = new File("E:/zhangbai/export/version20180330_91_real/eclipse/measureData/2018-03-30_1923_1522409039462_m");

		File[] files = dataFolder.listFiles();

		if ( null != files && files.length > 0) {
			width = files.length;
			width = (width/4) * 4; // Make sure height is *4
		}		
	}

	private static void fillData(byte[] data) {
		File dataFolder = new File("E:/zhangbai/export/version20180330_91_real/eclipse/measureData/2018-03-30_1923_1522409039462_m");

		File[] files = dataFolder.listFiles();

		int col = 0;
		for (File file : files) {
			List<String> lines = ReadFile.readFile(file);
			int i = 0; String[] stringArray;
			int value = 0;
			int row = height - 1;
			for (String line : lines) {
				if ( ! line.trim().equals("") ) {
					stringArray = line.split("\t");
					value += Integer.valueOf(stringArray[2].trim());
					i++;
					if ( i == 10) {
						value = value / 10;
						data[row * width + col] = (byte)((value * 255.0)/MAX);
						i = 0; value = 0;
						row --;
					}
				}
			}
			col ++;
			if (col == width ) {
				break;
			}
		}

	}

	private static Image image = createIndexImage();

	public static Image createIndexImage() {

		getHeight();

		byte[] data = new byte[width * height];

		fillData(data);

		ImageData imageData = new ImageData(width, height, 8, MyPaletteData.createPaletteAsLidar256(), 4, data);

		imageData = imageData.scaledTo(686, 402);

		return new Image(Display.getDefault(), imageData);
	}

	public static void main(String[] args) {
		Shell shell = new Shell (Display.getDefault());
		shell.setLayout (new FillLayout ());
		shell.setSize(800, 600);
		shell.setLocation(100, 100);
		Canvas canvas = new Canvas (shell, SWT.NONE);
		canvas.addPaintListener (new PaintListener () {
			public void paintControl (PaintEvent e) {
				e.gc.drawImage (image, 20, 20);
			}
		});
		shell.setText("532");

		shell.open ();
		while (!shell.isDisposed ()) {
			if (!Display.getDefault().readAndDispatch ())
				Display.getDefault().sleep ();
		}

		if (image != null && image.isDisposed()) {
			image.dispose();
		}

	}

}
