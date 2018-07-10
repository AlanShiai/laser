package org.bai.zhang.prj.util;

import java.io.File;

public class MyFile {
	
	public final static File BLOCK = new File(".\\block.txt");

	public final static File SIGMA_355  = new File(".\\Sigma355.txt");
	public final static File SIGMA_532p = new File(".\\Sigma532p.txt");
	public final static File SIGMA_532s = new File(".\\Sigma532s.txt");
	public final static File SIGMA_1064 = new File(".\\Sigma1064.txt");

	public final static File MEASURE_DATA_DIR = new File(BLOCK.getParent(), "measureData");

	public final static File MEASURE_DISPLAY_DIR = new File(MEASURE_DATA_DIR, "display");

	public final static File MEASURE_DISPLAY_FILE = new File(MEASURE_DISPLAY_DIR, "block.txt");
	public static long MEASURE_DISPLAY_FILE_MODIFY_TIME = MEASURE_DISPLAY_FILE.lastModified();

}
