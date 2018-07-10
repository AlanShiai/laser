package org.bai.zhang.prj.sample.dialog;

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

public class IndexImageDepth8_4_legend2 {

	private static final String constVar = "IndexImageDepth8_4_legend2";
	
	private static Image image = createIndexImage();
	
	public static byte[] createData(int width, int height) {
		byte[] data = new byte[width * height];
		for(int i = 0; i < height; i++) {
			for (int j = 0; j< width; j++) {
				data[i * width + j] = (byte) (height - i - 1);
			}
		}
		
		return data;
	}

	public static Image createIndexImage() {
		PaletteData paletteData = MyPaletteData.createPaletteAsLidar256();
		
		int height = paletteData.getRGBs().length;
		
		System.out.println(height);
		
		byte[] data = createData(48, height);
		
		ImageData imageData = new ImageData(48, height, 8, paletteData, 4, data);
		
		imageData = imageData.scaledTo(36, 402);
		
		return new Image(Display.getDefault(), imageData);
	}
	
	public static void main(String[] args) {
		Shell shell = new Shell (Display.getDefault());
		shell.setLayout (new FillLayout ());
		shell.setSize(800, 600);
		shell.setLocation(100,100);
		Canvas canvas = new Canvas (shell, SWT.NONE);
		canvas.addPaintListener (new PaintListener () {
			public void paintControl (PaintEvent e) {
				e.gc.drawImage (image, 500, 20);
			}
		});
		shell.setText(constVar);

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
