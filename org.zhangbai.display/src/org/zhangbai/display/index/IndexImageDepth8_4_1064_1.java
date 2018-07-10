package org.zhangbai.display.index;

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

public class IndexImageDepth8_4_1064_1 {
	
	private static final String constVar = "IndexImageDepth8_4_1064_1";
	
	public static List<String> getValidateStrings() {
		List<String> validateStrings = new ArrayList<String>();
		int imageWidth = 0;
		for (String line : ReadFile.readFile(constVar + ".txt")) {
			line = line.trim();
			if (line.equals("")) {
				continue;
			}
			if (line.startsWith("//")) {
				continue;
			}
			if ( ! line.contains(",")) {
				continue;
			}
			if (line.contains("//")) {
				line = line.substring(0, line.indexOf("//"));
			}
			if (imageWidth == 0) {
				imageWidth = line.split(",").length;
			} else {
				if (imageWidth != line.split(",").length) {
					try {
						throw new Exception("Find difference line width!");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			validateStrings.add(line);
//			System.out.println(line);
		}
//		System.out.println("The image width is " + imageWidth);
//		System.out.println("The image heigth is " + validateStrings.size());
		return validateStrings;
	}
	
	public static List<Byte> getBytes() {
		List<Byte> dataList = new ArrayList<Byte>();
		
		String[] lineDatas;
		
		List<String> validateStrings = getValidateStrings();
		for (String line : validateStrings) {
			lineDatas = line.split(",");
			if (lineDatas != null) {
				for (String s : lineDatas) {
					s = s.trim();
					if (!s.equals("")) {
						dataList.add(Byte.parseByte(s));
					}
				}
			}
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
		ImageData imageData = new ImageData(48, 48, 8, MyPaletteData.createPaletteMyRGBHtml(), 4, listByte2byteArray(getBytes()));
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
