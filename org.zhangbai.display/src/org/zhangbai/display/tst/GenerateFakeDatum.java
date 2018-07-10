package org.zhangbai.display.tst;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerateFakeDatum {
	
	private final String original_file = "E:/soft/eclipse-modeling-luna-SR1-win32/workspace/org.zhangbai.display/block.txt";
	
	public static void main(String[] args) {
		Date date = new Date(Long.parseLong("1522301262666"));

		System.out.println(date.getTime());
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS");

		long currentTime = Long.parseLong("1522301262576");
		date = new Date(currentTime);
		System.out.println(formatter.format(date));

	}

}
