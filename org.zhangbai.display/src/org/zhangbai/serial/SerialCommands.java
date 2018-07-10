package org.zhangbai.serial;

import java.util.HashMap;

import org.zhangbai.display.readfile.ReadFile;

public class SerialCommands {
	
	public final static String COMMAND_01_ONLINE_ON			= "command_01_online_on";
	public final static String COMMAND_02_OFFLINE_OFF		= "command_02_offline_off";
	public final static String COMMAND_03_VOLTAGE			= "command_03_voltage";
	public final static String COMMAND_04_FREQ				= "command_04_freq";
	public final static String COMMAND_05_WARM_UP_ON		= "command_05_warm_up_on";
	public final static String COMMAND_06_WARM_UP_OFF		= "command_06_warm_up_off";
	public final static String COMMAND_07_START_ON			= "command_07_start_on";
	public final static String COMMAND_08_START_OFF			= "command_08_start_off";
	public final static String COMMAND_09_OUT_TIMER_ON		= "command_09_out_timer_on";
	public final static String COMMAND_10_IN_TIMER_ON		= "command_10_in_timer_on";
	public final static String COMMAND_11_WATER_PROBLEM		= "command_11_water_problem";
	public final static String COMMAND_12_SWTICH_PROBLEM	= "command_12_swtich_problem";
	public final static String COMMAND_13_SWTICH_IS_OK		= "command_13_swtich_is_ok";
	public final static String COMMAND_14_WATER_IS_OK		= "command_14_water_is_ok";

	public final static String COMMAND_MOVE_RIGHT_START		= "command_move_right_start";
	public final static String COMMAND_MOVE_UP_START		= "command_move_up_start";
	public final static String COMMAND_MOVE_DONW_START		= "command_move_down_start";
	public final static String COMMAND_MOVE_LEFT_START		= "command_move_left_start";
	
	public final static String COMMAND_MOVE_RIGHT_STOP		= "command_move_right_stop";
	public final static String COMMAND_MOVE_UP_STOP			= "command_move_up_stop";
	public final static String COMMAND_MOVE_DOWN_STOP		= "command_move_down_stop";
	public final static String COMMAND_MOVE_LEFT_STOP		= "command_move_left_stop";

	private static HashMap<String, String> commands = new HashMap<String, String>();
	static {
		int index;
		String commandKey, command;
		for (String line : ReadFile.readFile("serial.txt")) {
			if (line.contains("command") && line.contains("=")) {
				index = line.indexOf("=");
				commandKey = line.substring(0, index).trim();
				command = line.substring(index + 1).trim();
				commands.put(commandKey, command);
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println(commands);
	}

}
