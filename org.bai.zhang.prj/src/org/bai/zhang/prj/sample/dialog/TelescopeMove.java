package org.bai.zhang.prj.sample.dialog;

import gnu.io.SerialPort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bai.zhang.prj.util.Log;
import org.bai.zhang.prj.util.MyDate;
import org.bai.zhang.prj.util.Serial;

public class TelescopeMove {
	
	private final static boolean isDebug = false;
	
	private final static File LOG_FILE = new File(Log.LOG_FOLDER, "serial_move_history.log");

	private final static boolean APPEND_TRUE = true;
	
	private static SerialPort serialPort;

	public static void noteSerialCommand(String serialCommand) {
		try {
			if ( ! Log.LOG_FOLDER.exists()) {
				Log.LOG_FOLDER.createNewFile();
			}
			FileWriter writer = new FileWriter(LOG_FILE, APPEND_TRUE);
			writer.append(MyDate.getLongDateString() + " : ");
			writer.append(serialCommand);
			writer.append("\n");

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void openSerialPort() {
		noteSerialCommand("Open serial port : " + Serial.getTelescopeSerialPort()); 
		noteSerialCommand("Open serial port baudrate : " + Serial.getTelescopeSerialPortBaudrate()); 
		if ( ! isDebug) {
			serialPort = SerialTool.openPort(Serial.getTelescopeSerialPort(), Serial.getTelescopeSerialPortBaudrate());
		}
	}
	private static void closeSerialPort() {
		noteSerialCommand("Close serial port : " + Serial.getLaserSerialPort()); 
		if ( ! isDebug) {
			SerialTool.closePort(serialPort);
		}
	}
	private static void delay() {
		try {
			Thread.sleep(Serial.getDefaultTeleMoveInterval() * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void leftRotate() {
		openSerialPort();
		
		noteSerialCommand("command_move_left_start : " + Serial.getCommand(Serial.COMMAND_MOVE_LEFT_START)); 
		if (isDebug) {
			System.out.println(MyDate.getMiddleDateString() + " : command_move_left_start"); 
		} else {
			SerialTool.sendToPort(serialPort, Serial.getCommand(Serial.COMMAND_MOVE_LEFT_START));
		}
		
		delay();

		noteSerialCommand("command_move_left_stop : " + Serial.getCommand(Serial.COMMAND_MOVE_LEFT_STOP)); 
		if (isDebug) {
			System.out.println(MyDate.getMiddleDateString() + " : command_move_left_stop"); 
		} else {
			SerialTool.sendToPort(serialPort, Serial.getCommand(Serial.COMMAND_MOVE_LEFT_STOP));
		}
		
		closeSerialPort();
	}
	
	public static void rightRotate() {

		openSerialPort();
		
		noteSerialCommand("command_move_right_start : " + Serial.getCommand(Serial.COMMAND_MOVE_RIGHT_START)); 
		if (isDebug) {
			System.out.println(MyDate.getMiddleDateString() + " : command_move_right_start"); 
		} else {
			SerialTool.sendToPort(serialPort, Serial.getCommand(Serial.COMMAND_MOVE_RIGHT_START));
		}
		
		delay();

		noteSerialCommand("command_move_right_stop : " + Serial.getCommand(Serial.COMMAND_MOVE_RIGHT_STOP)); 
		if (isDebug) {
			System.out.println(MyDate.getMiddleDateString() + " : command_move_right_stop"); 
		} else {
			SerialTool.sendToPort(serialPort, Serial.getCommand(Serial.COMMAND_MOVE_RIGHT_STOP));
		}
		
		closeSerialPort();
	}
	
	public static void clockwiseRotate() {

		openSerialPort();
		
		noteSerialCommand("command_move_down_start : " + Serial.getCommand(Serial.COMMAND_MOVE_DONW_START)); 
		if (isDebug) {
			System.out.println(MyDate.getMiddleDateString() + " : command_move_down_start"); 
		} else {
			SerialTool.sendToPort(serialPort, Serial.getCommand(Serial.COMMAND_MOVE_DONW_START));
		}
		
		delay();

		noteSerialCommand("command_move_down_stop : " + Serial.getCommand(Serial.COMMAND_MOVE_DOWN_STOP)); 
		if (isDebug) {
			System.out.println(MyDate.getMiddleDateString() + " : command_move_down_stop"); 
		} else {
			SerialTool.sendToPort(serialPort, Serial.getCommand(Serial.COMMAND_MOVE_DOWN_STOP));
		}
		
		closeSerialPort();
	}
	
	public static void anticlockwiseRotate() {

		openSerialPort();
		
		noteSerialCommand("command_move_up_start : " + Serial.getCommand(Serial.COMMAND_MOVE_UP_START)); 
		if (isDebug) {
			System.out.println(MyDate.getMiddleDateString() + " : command_move_up_start"); 
		} else {
			SerialTool.sendToPort(serialPort, Serial.getCommand(Serial.COMMAND_MOVE_UP_START));
		}
		
		delay();

		noteSerialCommand("command_move_up_stop : " + Serial.getCommand(Serial.COMMAND_MOVE_UP_STOP)); 
		if (isDebug) {
			System.out.println(MyDate.getMiddleDateString() + " : command_move_up_stop"); 
		} else {
			SerialTool.sendToPort(serialPort, Serial.getCommand(Serial.COMMAND_MOVE_UP_STOP));
		}
		
		closeSerialPort();
	}

}
