package org.bai.zhang.prj.sample.dialog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Enumeration;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class SerialTool {

	public static void main(String[] args) {
		getSerialTool();
		System.out.println(SerialTool.findPort());

		SerialPort serialPort = openPort("COM3", 9600);


		sendToPort(serialPort, new byte[] {1, 2, 3, 3, 2, 1});

		sendToPort(serialPort, "234");

		//		readFromPort(serialPort);

		closePort(serialPort);

	}

	private static SerialTool serialTool = null;
	static {
		if (serialTool == null) {
			serialTool = new SerialTool();
		}
	}
	private SerialTool() {}    

	public static SerialTool getSerialTool() {
		if (serialTool == null) {
			serialTool = new SerialTool();
		}
		return serialTool;
	}

	@SuppressWarnings("unchecked")
	public static final ArrayList<String> findPort() {
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();    
		ArrayList<String> portNameList = new ArrayList<>();
		while (portList.hasMoreElements()) {
			String portName = portList.nextElement().getName();
			portNameList.add(portName);
		}
		return portNameList;
	}

	public static final SerialPort openPort(String portName, int baudrate) {
		SerialPort serialPort = null;
		try {
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);

			CommPort commPort = portIdentifier.open(portName, 2000);
			if (commPort instanceof SerialPort) {
				serialPort = (SerialPort) commPort;
				try {
					//设置一下串口的波特率等参数
					serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);                              
				} catch (UnsupportedCommOperationException e) {
					e.printStackTrace();
				}
				System.out.println("Open " + portName + " sucessfully !");
			}        
		} catch (NoSuchPortException e1) {
			e1.printStackTrace();
		} catch (PortInUseException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serialPort;
	}

	public static void closePort(SerialPort serialPort) {
		System.out.println("Closing " + serialPort.getName() + " sucessfully !");
		if (serialPort != null) {
			serialPort.close();
			serialPort = null;
		}
	}

	public static byte[] hexStr2Byte(String hex) {
		ByteBuffer bf = ByteBuffer.allocate(hex.length() / 2);
		for (int i = 0; i < hex.length(); i++) {
			String hexStr = hex.charAt(i) + "";
			i++;
			hexStr += hex.charAt(i);
			byte b = (byte) Integer.parseInt(hexStr, 16);
			bf.put(b);
		}
		return bf.array();
	}
	
	public static void sendHexToPort(SerialPort serialPort, String message) {
		sendToPort(serialPort, hexStr2Byte(message));
	}

	public static void sendToPort(SerialPort serialPort, String message) {
		OutputStream out = null;
		try {
			out = serialPort.getOutputStream();
			for (int i = 0; i < message.length(); i++) {  
				out.write(message.charAt(i));  
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
					out = null;
				}                
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void sendToPort(SerialPort serialPort, byte[] order) {
		OutputStream out = null;
		try {
			out = serialPort.getOutputStream();
			out.write(order);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
					out = null;
				}                
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static byte[] readFromPort(SerialPort serialPort) {
		InputStream in = null;
		byte[] bytes = null;

		try {
			in = serialPort.getInputStream();
			int bufflenth = in.available();        //获取buffer里的数据长度
			while (bufflenth != 0) {                             
				bytes = new byte[bufflenth];    //初始化byte数组为buffer中数据的长度
				in.read(bytes);
				bufflenth = in.available();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
					in = null;
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}


}