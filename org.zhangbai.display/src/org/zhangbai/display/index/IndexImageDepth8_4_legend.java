package org.zhangbai.display.index;

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

public class IndexImageDepth8_4_legend {

	
	private static final String constVar = "IndexImageDepth8_4_legend";
	
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
		PaletteData paletteData = MyPaletteData.createPaletteMyRGBHtml();
		
		int height = paletteData.getRGBs().length;
		
		byte[] data = createData(48, height);
		
		ImageData imageData = new ImageData(48, height, 8, paletteData, 4, data);
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
				
				e.gc.drawString(constVar, 20, 100);
			}
		});
		shell.setText(constVar);

		shell.open ();
		while (!shell.isDisposed ()) {
			if (!Display.getDefault().readAndDispatch ())
				Display.getDefault().sleep ();
		}
	}






}
