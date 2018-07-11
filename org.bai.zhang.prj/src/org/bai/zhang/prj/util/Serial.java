package org.bai.zhang.prj.util;

import java.io.File;
import java.util.HashMap;

import org.bai.zhang.prj.readfile.ReadFile;
import org.eclipse.core.runtime.Platform;

public class Serial {
	
	private static String laserSerialPort = "COM3";
	private static int laserSerialPortBaudrate = 19200;
	
	private static String telescopeSerialPort = "COM4";
	private static int telescopeSerialPortBaudrate = 9600;
	
	private static int defaultSerialCmdInterval = 1;
	
	private static int defaultTeleMoveCmdInterval = 3;
	
	public final static String COMMAND_01_ONLINE_ON			= "command_01_online_on";
	public final static String COMMAND_02_OFFLINE_OFF		= "command_02_offline_off";
	public final static String COMMAND_03_VOLTAGE			= "command_03_voltage";
	public final static String COMMAND_04_DIV_FREQ			= "command_04_dividing_freq";
	public final static String COMMAND_04_FREQ				= "command_04_freq";
	public final static String COMMAND_05_WARM_UP_ON		= "command_05_warm_up_on";
	public final static String COMMAND_06_WARM_UP_OFF		= "command_06_warm_up_off";
	public final static String COMMAND_07_START_ON			= "command_07_start_on";
	public final static String COMMAND_08_START_OFF			= "command_08_start_off";
	public final static String COMMAND_09_OUT_TIMER_ON		= "command_09_out_timer_on";
	public final static String COMMAND_10_IN_TIMER_ON		= "command_10_in_timer_on";
	public final static String COMMAND_11_Q_ON				= "command_11_Q_on";
	public final static String COMMAND_12_Q_OFF				= "command_12_Q_off";
	
	public final static String command_03_500v_voltage		= "command_03_500v_voltage";
	public final static String command_03_550v_voltage		= "command_03_550v_voltage";
	public final static String command_03_600v_voltage		= "command_03_600v_voltage";
	public final static String command_03_650v_voltage		= "command_03_650v_voltage";
	public final static String command_03_700v_voltage		= "command_03_700v_voltage";
	public final static String command_03_750v_voltage		= "command_03_750v_voltage";
	public final static String command_04_01Hz_freq			= "command_04_01Hz_freq";
	public final static String command_04_02Hz_freq			= "command_04_02Hz_freq";
	public final static String command_04_05Hz_freq			= "command_04_05Hz_freq";
	public final static String command_04_10Hz_freq			= "command_04_10Hz_freq";
	
	public final static String COMMAND_MOVE_RIGHT_START		= "command_move_right_start";
	public final static String COMMAND_MOVE_UP_START		= "command_move_up_start";
	public final static String COMMAND_MOVE_DONW_START		= "command_move_down_start";
	public final static String COMMAND_MOVE_LEFT_START		= "command_move_left_start";
	
	public final static String COMMAND_MOVE_RIGHT_STOP		= "command_move_right_stop";
	public final static String COMMAND_MOVE_UP_STOP			= "command_move_up_stop";
	public final static String COMMAND_MOVE_DOWN_STOP		= "command_move_down_stop";
	public final static String COMMAND_MOVE_LEFT_STOP		= "command_move_left_stop";
	
	private static HashMap<String, String> commands = new HashMap<String, String>();
	public static String getCommand(String key) {
		return commands.get(key);
	}
	public static int getDefaultInterval() {
		return defaultSerialCmdInterval;
	}
	
	public static int getDefaultTeleMoveInterval() {
		return defaultTeleMoveCmdInterval;
	}
	
	static {
		if (Platform.isRunning()) {
			int index;
			String commandKey, command;
			File serial = new File(Platform.getLocation().toFile().getParent(), "serial.txt");
			System.out.println(serial.getAbsolutePath());
			for (String line : ReadFile.readFile(serial)) {
				if (line.contains("laserSerialPortBaudrate")) {
					laserSerialPortBaudrate = Integer.parseInt(line.substring("laserSerialPortBaudrate".length()).trim());
				}
				else if (line.contains("telescopeSerialPortBaudrate")) {
					telescopeSerialPortBaudrate = Integer.parseInt(line.substring("telescopeSerialPortBaudrate".length()).trim());
				}
				else if (line.contains("laserSerialPort")) {
					laserSerialPort = line.substring("laserSerialPort".length()).trim();
				}
				else if (line.contains("defaultSerialCmdInterval")) {
					defaultSerialCmdInterval = Integer.parseInt(line.substring("defaultSerialCmdInterval".length()).trim());
				}
				else if (line.contains("defaultTeleMoveCmdInterval")) {
					defaultTeleMoveCmdInterval = Integer.parseInt(line.substring("defaultTeleMoveCmdInterval".length()).trim());
				}
				else if (line.contains("telescopeSerialPort")) {
					telescopeSerialPort = line.substring("telescopeSerialPort".length()).trim();
				}
				else if (line.contains("command") && line.contains("=")) {
					index = line.indexOf("=");
					commandKey = line.substring(0, index).trim();
					command = line.substring(index + 1).trim();
					commands.put(commandKey, command);
				}
				else {
					
				}
					
			}
		}
	}
	
	public static String getLaserSerialPort () {
		return laserSerialPort;
	}
	public static String getTelescopeSerialPort () {
		return telescopeSerialPort;
	}
	
	public static int getLaserSerialPortBaudrate () {
		return laserSerialPortBaudrate;
	}
	public static int getTelescopeSerialPortBaudrate () {
		return telescopeSerialPortBaudrate;
	}
	
	public static String getSetVoltageKey(String voltage) {
		String key = "command_03_750v_voltage";
		if (voltage.equals("500v")) {
			key = "command_03_500v_voltage";
		} else if (voltage.equals("550v")) {
			key = "command_03_550v_voltage";
		} else if (voltage.equals("600v")) {
			key = "command_03_600v_voltage";
		} else if (voltage.equals("650v")) {
			key = "command_03_650v_voltage";
		} else if (voltage.equals("700v")) {
			key = "command_03_700v_voltage";
		} else if (voltage.equals("7500v")) {
			key = "command_03_750v_voltage";
		}
		return key;
	}

	public static String getSetFrepKey(String freq) {
		String key = "10Hz";
		if (freq.equals("1Hz")) {
			key = "command_04_01Hz_freq";
		} else if (freq.equals("2Hz")) {
			key = "command_04_02Hz_freq";
		} else if (freq.equals("5Hz")) {
			key = "command_04_05Hz_freq";
		} else if (freq.equals("10Hz")) {
			key = "command_04_10Hz_freq";
		}
		return key;
	}

}
