package org.bai.zhang.prj.readfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReadFile {

	public static List<String> readFile(String fileName) {
		try {
			return readFile(ReadFile.class.getResource(fileName).openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
	
	public static List<String> readFile(File file) {
		List<String> lines = new ArrayList<String>();
		
		BufferedReader bufferedReader = null;
		try {
			bufferedReader= new BufferedReader(new FileReader(file));
			
			String s = null;
			while ( (s = bufferedReader.readLine()) != null) {
				lines.add(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return lines;
	}

	public static List<String> readFile(InputStream inputStream) {
		List<String> lines = new ArrayList<String>();
		
		BufferedReader bufferedReader = null;
		try {
			bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
			
			String s = null;
			while ( (s = bufferedReader.readLine()) != null) {
				lines.add(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return lines;
	}

	public static void main(String[] args) {
		for (String line : readFile("serial.txt")) {
			System.out.println(line);
		}
	}
	
}
