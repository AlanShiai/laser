package org.zhangbai.display.writefile;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {
	
	private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("[yyyy-MM-dd HH:mm:ss:SSS] "); 
	
	public static String getDateString() {
		return simpleDateFormat.format(new Date()); 
	}

	public static void main(String[] args) {
		System.out.println(getDateString());
	}
}
