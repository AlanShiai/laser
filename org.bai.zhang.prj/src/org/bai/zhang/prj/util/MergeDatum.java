package org.bai.zhang.prj.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bai.zhang.prj.readfile.ReadFile;

public class MergeDatum {
	public static int[] data_355  = new int[10000];
	public static int[] data_532p = new int[10000];
	public static int[] data_532s = new int[10000];
	public static int[] data_1064 = new int[10000];

	private final static int MERGE_NUMBER = 60;

	public static void main(String[] args) {
//		File originalDataFolder = new File("E:/zhangbai/export/version20180330_91_real/eclipse/measureData/2018-03-30_1923_1522409039462");
		File originalDataFolder = new File("E:/zhangbai/export/version20180331_1_real/eclipse/measureData/2018-03-31_1710_1522487422085");
		
		Long start = System.currentTimeMillis();
		System.out.println(start);
		
		File mergeFolder = new File(originalDataFolder.getParentFile(), "2018-03-31_1710_1522487422085_m");
		if ( ! mergeFolder.exists() ) {
			mergeFolder.mkdir();
		}
		
		merge(originalDataFolder, mergeFolder);
		
		System.out.println(MergeDatum.class + " finished !");
		
		Long finished = System.currentTimeMillis();
		System.out.println(finished);
		System.out.println(finished - start);
	}

	public static void merge(File originalDataFolder, File mergeFolder) {
		File[] files = originalDataFolder.listFiles();

		if ( null != files && files.length != 1) {
			int mergeNumber = 0;
			File lastFile = null;
			
			for (File file : files) {
				lastFile = file;
//				System.out.println(file);
				List<String> lines = ReadFile.readFile(file);
				int i = 0; String[] stringArray;
				for (String line : lines) {
//					int i = 0; String[] stringArray;
					if ( ! line.trim().equals("") ) {
						stringArray = line.split("\t");
						data_355[i]  += Integer.valueOf(stringArray[0].trim());
						data_532p[i] += Integer.valueOf(stringArray[1].trim());
						data_532s[i] += Integer.valueOf(stringArray[2].trim());
						data_1064[i] += Integer.valueOf(stringArray[3].trim());
						i++;
					}
				}
				mergeNumber ++;
				if ( mergeNumber > MERGE_NUMBER) {
					saveMergedata(mergeNumber, mergeFolder + "/" + file.getName());
					mergeNumber = 0;
					Arrays.fill(data_355, 0);
					Arrays.fill(data_532p, 0);
					Arrays.fill(data_532s, 0);
					Arrays.fill(data_1064, 0);
				}
			}
			saveMergedata(mergeNumber, mergeFolder + "/" + lastFile.getName());
		}
		
		for (File file : files) {
			file.delete();
		}
	}

	private static void saveMergedata(int mergeNumber, String mergeFileName) {
		try {
			FileWriter writer = new FileWriter(mergeFileName);
			for (int i = 0; i < 10000; i++) {
				writer.append(Integer.toString(data_355[i] / mergeNumber));
				writer.append("\t");
				writer.append(Integer.toString(data_532p[i] / mergeNumber));
				writer.append("\t");
				writer.append(Integer.toString(data_532s[i] / mergeNumber));
				writer.append("\t");
				writer.append(Integer.toString(data_1064[i] / mergeNumber));
				writer.append("\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
