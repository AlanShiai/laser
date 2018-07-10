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

public class IndexImageDepth4_1 {
	
	private final static byte B_11= (byte)0x11;
	private final static byte B_22= (byte)0x22;
	private final static byte B_33= (byte)0x33;
	private final static byte B_55= (byte)0x55;
	private final static byte B_AA= (byte)0xAA;
	private final static byte B_FF= (byte)0xFF;

	private final static byte[] data = {
//		0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,
//		1,1,1,1,1,1,1,1, 1,1,1,1,1,1,1,1,
//		2,2,2,2,2,2,2,2, 2,2,2,2,2,2,2,2,
//		3,3,3,3,3,3,3,3, 3,3,3,3,3,3,3,3,

		0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00,// 00
		0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00,// 01
		0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00,// 02
		0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00,// 03
		B_FF,B_FF,0x00,0x00, B_AA,B_AA,0x00,0x00, B_55,B_55,0x00,0x00, 0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00,// 04
		B_FF,B_FF,0x00,0x00, B_AA,B_AA,0x00,0x00, B_55,B_55,0x00,0x00, 0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00,// 05
		B_FF,B_FF,0x00,0x00, 0x00,0x00,0x00,0x00, B_55,B_55,0x00,0x00, 0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00,// 06
		B_FF,B_FF,0x00,0x00, 0x00,0x00,0x00,0x00, B_55,B_55,0x00,0x00, 0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00,// 07
		B_FF,B_FF,0x00,0x00, 0x00,0x00,0x00,0x00, B_55,B_55,0x00,0x00, 0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00,// 08
		B_FF,B_FF,0x00,0x00, 0x00,0x00,0x00,0x00, B_55,B_55,0x00,0x00, 0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00,// 09
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00,// 10
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00,// 11
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00,// 12
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00,// 13
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00,// 14
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, B_AA,B_AA,0x00,0x00, 0x00,0x00,0x00,0x00,// 15

		B_11,B_11,0x00,0x00, B_22,B_22,0x00,0x00, 0x00,0x00,0x00,0x00, B_33,B_33,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 16
		B_11,B_11,0x00,0x00, B_22,B_22,0x00,0x00, 0x00,0x00,0x00,0x00, B_33,B_33,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 17
		B_11,B_11,0x00,0x00, B_22,B_22,0x00,0x00, 0x00,0x00,0x00,0x00, B_33,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 18
		B_11,B_11,0x00,0x00, B_22,B_22,0x00,0x00, 0x00,0x00,0x00,0x00, B_33,B_33,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 19
		B_11,B_11,0x00,0x00, B_22,B_22,0x00,0x00, 0x00,0x00,0x00,0x00, B_33,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 20
		B_11,B_11,0x00,0x00, B_22,B_22,0x00,0x00, 0x00,0x00,0x00,0x00, B_33,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 21
		0x00,0x00,0x00,0x00, B_22,B_22,0x00,0x00, 0x00,0x00,0x00,0x00, B_33,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 22
		0x00,0x00,0x00,0x00, B_22,B_22,0x00,0x00, 0x00,0x00,0x00,0x00, B_33,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 23
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 24
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 25
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 26
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 27
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 28
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 29
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 30
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 31

		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 32
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 33
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 34
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 35
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 36
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 37
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 38
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 39
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 40
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 41
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 42
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 43
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 44
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 45
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 46
		0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, 0x00,0x00,0x00,0x00, // 47
	};


	public static Image createIndexImage() {
		PaletteData paletteData = new PaletteData(
				new RGB[] {
						MyRGB.RED,	 MyRGB.GREEN, MyRGB.BLACK, MyRGB.WHITE,
						MyRGB.WHITE, MyRGB.WHITE,MyRGB.WHITE, MyRGB.WHITE,
						MyRGB.WHITE, MyRGB.WHITE,MyRGB.WHITE, MyRGB.WHITE,
						MyRGB.WHITE, MyRGB.WHITE,MyRGB.WHITE, MyRGB.WHITE,
				});

//		ImageData imageData = new ImageData(48, 48, 4, paletteData);

		ImageData imageData = new ImageData(48, 48, 4, paletteData, 4, data);
		
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