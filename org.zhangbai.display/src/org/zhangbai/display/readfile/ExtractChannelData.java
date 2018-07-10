package org.zhangbai.display.readfile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.zhangbai.display.writefile.MyDate;

public class ExtractChannelData {
	
	private static List<ArrayList<Float>> ch1Data = new ArrayList<ArrayList<Float>>();
//	private static List<Float> ch1Data = new ArrayList<Float>(5000);
	
	public static void extractCh1Data() {
		for (int i = 0; i < 24; i++) {
			String fileName = "20171202_19_56_45/tek00"+ String.format("%02d", i) + ".csv";
			ch1Data.add(extractCh1DataFromOneFile(fileName));
		}
	}
	
	public static ArrayList<Float> extractCh1DataFromOneFile(String fileName) {
		ArrayList<Float> ch1DataFromOneFile = new ArrayList<Float>(5000);
		
		int lineNumber = 0;
		for (String line : ReadFile.readFile(fileName)) {
			lineNumber ++;
			if (lineNumber < 5019) {
				continue;
			}
			if (lineNumber > 6518) {
				break;
			}
			if (line.trim().equals("")) {
				continue;
			}
			if ( ! line.contains(",")) {
				continue;
			}
//			System.out.print(lineNumber + " ");
//			System.out.println(line);
			
			String[] splitStringArray = line.split(",");
			if (splitStringArray != null && splitStringArray.length == 5) {
				ch1DataFromOneFile.add(new BigDecimal(splitStringArray[2]).floatValue());
			}
		}
		
		return ch1DataFromOneFile;
	}

	public static void main(String[] args) {
//		for (String line : extractData()) {
//			System.out.println(line);
//		}
//		String s = "4.9810e-05,0.130813,0.107906,0.00504687,10.7961";
//		String[] splitStringArray = s.split(",");
//		
//		if (splitStringArray != null && splitStringArray.length == 5) {
//			System.out.println(splitStringArray[0]);
//			System.out.println(splitStringArray[1]);
//			System.out.println(splitStringArray[2]);
//			System.out.println(splitStringArray[3]);
//			System.out.println(splitStringArray[4]);
//		}
		
		extractCh1Data();
		System.out.println(ch1Data.size());
		
		ArrayList<Float> data1 = ch1Data.get(0);
		for (int line = 0; line < data1.size(); line++) {
			for (int col = 0; col < ch1Data.size(); col ++) {
//				System.out.print(ch1Data.get(col).get(line));
				System.out.print(String.format("%-8f", ch1Data.get(col).get(line)));
				System.out.print("\t");
			}
			System.out.println();
		}
		
		saveData2File("night_ch2Data.txt", ch1Data);
		
//		"0.1305 0.129156 0.115688"
		
//		int i = 0;
//		String.format("%d", i);
//		System.out.println(String.format("%02d", i));
//		
//		float f1 = 0.1305f;
//		System.out.println(String.format("%-9f", f1));
//		
//		System.out.println(String.format("%-9f", f1).length());
//
//		float f2 = 0.129156f;
//		System.out.println(String.format("%-9f", f2));
//		System.out.println(String.format("%-9f", f2).length());

		
//		String s = "4.9810e-05,0.130813,0.107906,0.00504687,10.7961";
//		System.out.println(s.substring(s.indexOf(",")+1));
	}
	
	private static void saveData2File(String fileName, List<ArrayList<Float>> chData) {
		File DATA_FOLDER = new File("data");
		File file = new File(DATA_FOLDER, fileName);
		
		try {
			FileWriter writer = new FileWriter(file, false);
			
			ArrayList<Float> data1 = chData.get(0);
			for (int line = 0; line < data1.size(); line++) {
				for (int col = 0; col < chData.size(); col ++) {
					writer.append(String.format("%-8f", chData.get(col).get(line)));
					writer.append("\t");
				}
				writer.append("\n");
			}
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
