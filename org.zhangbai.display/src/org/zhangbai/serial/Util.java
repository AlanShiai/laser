package org.zhangbai.serial;

import java.io.UnsupportedEncodingException;

public class Util {
	
	private final static String encode = "UTF-8";
	
	public static byte[] stringToBytes(String string) {
		byte[] bytes = new byte[0];
		try {
			bytes = string.getBytes(encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return bytes;
	}
	
	public static String bytesToString(byte[] bytes) {
		String string = "";
		try {
			string = new String(bytes,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return string;
	}

	public static void main(String[] args) {
		System.out.println("xxx");
		System.out.println(stringToBytes("hello"));
		System.out.println(bytesToString(stringToBytes("hello")));

	}

}
