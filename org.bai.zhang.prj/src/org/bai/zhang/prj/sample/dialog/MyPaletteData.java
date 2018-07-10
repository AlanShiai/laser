package org.bai.zhang.prj.sample.dialog;

import java.util.ArrayList;
import java.util.List;

import org.bai.zhang.prj.readfile.ReadFile;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

public class MyPaletteData {

	public static final PaletteData getPalette() {
		return new PaletteData(
				new RGB[] {
						MyRGB.RED,	 MyRGB.GREEN, MyRGB.BLACK, MyRGB.WHITE,
						MyRGB.WHITE, MyRGB.WHITE,MyRGB.WHITE, MyRGB.WHITE,
						MyRGB.WHITE, MyRGB.WHITE,MyRGB.WHITE, MyRGB.WHITE,
						MyRGB.WHITE, MyRGB.WHITE,MyRGB.WHITE, MyRGB.WHITE,
				});
	}
	
	public static final PaletteData createPaletteAsLidar256() {
		PaletteData paletteData = createPaletteAsLidar();
		
		if (paletteData.colors.length > 256) {
			List<RGB> rgbs = new ArrayList<RGB>();
			
			double step = paletteData.colors.length / (double) 256;
			
			for (int i = 0; i < 256 ; i++ ) {
				rgbs.add(paletteData.colors[(int) (i * step)]);
			}
			paletteData = new PaletteData(rgbs.toArray(new RGB[0]));
		}
		
		return paletteData;
	}
	
	public static final PaletteData createPaletteAsLidar() {
		List<RGB> rgbs = new ArrayList<RGB>();
		
		for (int i = 200; i > 0; i--) {
			/* 
			 * [ (200, 0, 255) -> (0, 0, 255) )
			 * purple -> dark blue.
			 */
			rgbs.add(new RGB(i, 0, 255));
		}
		for (int i = 0; i < 255; i++) {
			/*
			 * [ (0, 0, 255) -> (0, 255, 255) )
			 * dark blue -> light blue.
			 */
			rgbs.add(new RGB(0, i, 255));
		}
		for (int i = 255; i > 0; i--) {
			/*
			 * [ (0, 255, 255) -> (0, 255, 0) )
			 * light blue -> green.
			 */
			rgbs.add(new RGB(0, 255, i));
		}
		for (int i = 0; i < 255; i++) {
			/*
			 * [ (0, 255, 0) -> (255, 255, 0) )
			 * green -> yellow
			 */
			rgbs.add(new RGB(i, 255, 0));
		}
		for (int i = 255; i > -1; i--) {
			/*
			 * [ (255, 255, 0) -> (255, 0, 0) ]
			 * yellow -> red
			 */
			rgbs.add(new RGB(255, i, 0));
		}
		
		
		return new PaletteData(rgbs.toArray(new RGB[0]));
	}

	public static final PaletteData createPaletteMyRGBHtml() {
		List<RGB> rgbs = new ArrayList<RGB>();

		String[] strings;

		for (String line : ReadFile.readFile("MyRGB.html")) {
			line = line.trim();
			if (line.equals("")) {
				continue;
			}
			if ( ! line.contains("bgcolor") || ! line.contains("\"")) {
				continue;
			}
			strings = line.split("\"");
			if (strings.length == 3 && strings[1].length() == 7) {
				rgbs.add(new RGB(
						Integer.parseInt(strings[1].substring(1,3), 16),
						Integer.parseInt(strings[1].substring(3,5), 16),
						Integer.parseInt(strings[1].substring(5), 16)
						));
			}
		}

		return new PaletteData(rgbs.toArray(new RGB[0]));
	}
	public static final PaletteData createPaletteMyRGBTxt() {
		List<RGB> rgbs = new ArrayList<RGB>();

		//		String colorName = "";
		String rgbString = "";
		String[] rgbArray;

		for (String line : getValidateStrings()) {
			if (line.contains("=")) {
				//				colorName = line.substring(0, line.indexOf("="));
				rgbString = line.substring(line.indexOf("=") + 1);

				//				System.out.println("colorName : " + colorName);
				//				System.out.println("rgbString : " + rgbString);
				rgbArray = rgbString.split(",");
				if ( null != rgbArray && 3 == rgbArray.length) {
					rgbs.add(new RGB(
							Integer.parseInt(rgbArray[0].trim(), 16),
							Integer.parseInt(rgbArray[1].trim(), 16),
							Integer.parseInt(rgbArray[2].trim(), 16)
							));
				}
			}
		}

		return new PaletteData(rgbs.toArray(new RGB[0]));
	}

	public static List<String> getValidateStrings() {
		List<String> validateStrings = new ArrayList<String>();
		for (String line : ReadFile.readFile("MyRGB.txt")) {
			line = line.trim();
			if (line.equals("") || line.startsWith("//")) {
				continue;
			}
			if ( ! line.contains(",") || ! line.contains("=")) {
				System.out.println("Not valid string : " + line);
				continue;
			}
			if (line.contains("//")) {
				line = line.substring(0, line.indexOf("//"));
			}
			validateStrings.add(line);
			//			System.out.println(line);
		}
		return validateStrings;
	}


	public static void main(String[] args) {
		//		int i = Integer.parseInt("FF", 16);
		//		System.out.println(i);
		//		createPaletteMyRGBTxt();
//		createPaletteMyRGBHtml();
		double step = 1221 / (double) 256;
		System.out.println(step);
		
		for (int i = 0; i < 256 ; i++ ) {
			System.out.println(i);
			System.out.println(i * step);
			System.out.println((int) (i * step));
		}
		
		
	}
}
