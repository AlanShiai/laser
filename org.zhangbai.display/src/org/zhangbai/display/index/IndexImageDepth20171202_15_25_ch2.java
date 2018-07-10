package org.zhangbai.display.index;

import java.math.BigDecimal;
import java.util.ArrayList;
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

public class IndexImageDepth20171202_15_25_ch2 {


	private static final String constVar = "20171202_15_25_ch2Data";

	private static Image image = createIndexImage();

	private static int max = 0;
	private static int height = 48;

	public static List<String> getValidateStrings() {
		List<String> validateStrings = new ArrayList<String>();
		int imageWidth = 0;
		int lineNum = 0;
		for (String line : ReadFile.readFile(constVar + ".txt")) {
			line = line.trim();
			if (line.equals("")) {
				continue;
			}
			if (line.startsWith("//")) {
				continue;
			}
			if (line.contains("//")) {
				line = line.substring(0, line.indexOf("//"));
			}
			if (imageWidth == 0) {
				imageWidth = line.split("\\s").length;
			} else {
				if (imageWidth != line.split("\\s").length) {
					try {
						System.out.println("lineNum: " + lineNum);
						System.out.println("imageWidth: " + imageWidth);
						System.out.println("line length: " + line.split("\\s").length);
						throw new Exception("Find difference line width!");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			validateStrings.add(line);
			lineNum ++;
		}
		System.out.println("imageWidth: " + imageWidth);
		height = lineNum;
		return validateStrings;
	}

	public static List<Integer> getIntegers() {
		/*
		 * 
		 * 1, string -> Integer
		 * 2, negative use 0 replace.
		 * 3, find max.
		 */
		List<Integer> dataList = new ArrayList<Integer>();
		String[] lineDatas;
		Integer i;
		String line;
		
		List<String> lines = getValidateStrings();
		for (int j = lines.size() - 1; j > -1; j--) {
			line = lines.get(j);
			lineDatas = line.split("\\s");
			if (lineDatas != null) {
				for (String s : lineDatas) {
					s = s.trim();
					if (!s.equals("")) {
						i = (int) (new BigDecimal(s).floatValue() * 10000);
						if (i > max) {
							max = i;
						} else if (i < 0) {
							i = 0;
						}
						dataList.add(i);
					}
				}
			}
		}
		
//		for (String line : getValidateStrings()) {
//			lineDatas = line.split("\\s");
//			if (lineDatas != null) {
//				for (String s : lineDatas) {
//					s = s.trim();
//					if (!s.equals("")) {
//						i = new BigDecimal(s).intValue();
//						if (i > max) {
//							max = i;
//						} else if (i < 0) {
//							i = 0;
//						}
//						dataList.add(i);
//					}
//				}
//			}
//		}

		return dataList;
	}

	public static List<Byte> getBytes() {
		List<Byte> dataList = new ArrayList<Byte>();

		for (Integer i : getIntegers()) {
			dataList.add((byte)((i * 255.0)/max));
		}

		return dataList;
	}

	public static byte[] listByte2byteArray(List<Byte> listByte) {
		byte[] data = new byte[listByte.size()];
		for (int i = 0; i < listByte.size(); i++) {
			data[i] = listByte.get(i);
		}
		return data;
	}

	public static Image createIndexImage() {

		byte[] data = listByte2byteArray(getBytes());

		ImageData imageData = new ImageData(24, height, 8, MyPaletteData.createPaletteAsLidar256(), 4, data);

		imageData = imageData.scaledTo(686, 402);

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
				e.gc.drawImage (image, 20, 20);
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
