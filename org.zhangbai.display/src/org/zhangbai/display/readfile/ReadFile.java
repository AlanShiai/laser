package org.zhangbai.display.readfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReadFile {

	public static List<String> readFile(String fileName) {
		try {
			File file = new File(ReadFile.class.getResource(fileName).toURI());
			return readFile(file);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
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

	public static void main(String[] args) {
		for (String line : readFile("serial.txt")) {
			System.out.println(line);
		}
	}
	
}
