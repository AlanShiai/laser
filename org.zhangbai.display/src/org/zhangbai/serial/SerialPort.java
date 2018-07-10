package org.zhangbai.serial;

import org.zhangbai.display.readfile.ReadFile;

public class SerialPort {
	
	private static String serial_port1 = "COM0";
	
	private static String serial_port2 = "COM3";
	
	static {
		for (String line : ReadFile.readFile("serial.txt")) {
			if (line.contains("serial_port1")) {
				serial_port1 = line.substring("serial_port1".length()).trim();
			}
			if (line.contains("serial_port2")) {
				serial_port2 = line.substring("serial_port2".length()).trim();
			}
		}
	}
	
	public static String getSerialPort1 () {
		return serial_port1;
	}
	
	public static String getSerialPort2 () {
		return serial_port2;
	}

	public static void main(String[] args) {
		System.out.println("use serial port 1 : " + serial_port1);
		System.out.println("use serial port 2 : " + serial_port2);
	}

}
