package org.bai.zhang.prj.sample.dialog;

import gnu.io.SerialPort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.bai.zhang.prj.util.Log;
import org.bai.zhang.prj.util.MyDate;
import org.bai.zhang.prj.util.Serial;

public abstract class WorkTestTask extends TimerTask {

	private final static boolean isDebug = false;

	private final static File LOG_FILE = new File(Log.LOG_FOLDER, "serial_command_history.log");

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

	private long delay = Serial.getDefaultInterval() * 1000; // default delay 1s.

	private WorkTestTask() {
	}

	private WorkTestTask(long delay) {
		this.delay = delay;
	}

	public long getDelay() {
		return delay;
	}

	private static List<WorkTestTask> generateStartLaserTasks(long start) {
		List<WorkTestTask> laserTasks = new ArrayList<WorkTestTask>();
		laserTasks.add(new WorkTestTask() {
			public void run() {
				noteSerialCommand("command_01_online_on : " + Serial.getCommand(Serial.COMMAND_01_ONLINE_ON)); 
				if (isDebug) {
					System.out.println(MyDate.getMiddleDateString() + " : command_01_online_on"); 
				} else {
					SerialTool.sendHexToPort(serialPort, Serial.getCommand(Serial.COMMAND_01_ONLINE_ON));
				}
			}
		});
		laserTasks.add(new WorkTestTask() {
			public void run() {
				noteSerialCommand("command_03_voltage : " + Serial.getCommand(Serial.COMMAND_03_VOLTAGE)); 
				if (isDebug) {
					System.out.println(MyDate.getMiddleDateString() + " : command_03_voltage"); 
				} else {
					SerialTool.sendHexToPort(serialPort, Serial.getCommand(Serial.COMMAND_03_VOLTAGE));
				}
			}
		});
		laserTasks.add(new WorkTestTask() {
			public void run() {
				noteSerialCommand("command_04_dividing_freq : " + Serial.getCommand(Serial.COMMAND_04_DIV_FREQ)); 
				if (isDebug) {
					System.out.println(MyDate.getMiddleDateString() + " : command_04_dividing_freq"); 
				} else {
					SerialTool.sendHexToPort(serialPort, Serial.getCommand(Serial.COMMAND_04_DIV_FREQ));
				}
			}
		});
		laserTasks.add(new WorkTestTask() {
			public void run() {
				noteSerialCommand("command_04_freq : " + Serial.getCommand(Serial.COMMAND_04_FREQ)); 
				if (isDebug) {
					System.out.println(MyDate.getMiddleDateString() + " : command_04_freq"); 
				} else {
					SerialTool.sendHexToPort(serialPort, Serial.getCommand(Serial.COMMAND_04_FREQ));
				}
			}
		});
		laserTasks.add(new WorkTestTask() {
			public void run() {
				noteSerialCommand("command_05_warm_up_on : " + Serial.getCommand(Serial.COMMAND_05_WARM_UP_ON)); 
				if (isDebug) {
					System.out.println(MyDate.getMiddleDateString() + " : command_05_warm_up_on"); 
				} else {
					SerialTool.sendHexToPort(serialPort, Serial.getCommand(Serial.COMMAND_05_WARM_UP_ON));
				}
			}
		});
		laserTasks.add(new WorkTestTask(60 * 1000) {
			public void run() {
				noteSerialCommand("command_07_start_on : " + Serial.getCommand(Serial.COMMAND_07_START_ON)); 
				if (isDebug) {
					System.out.println(MyDate.getMiddleDateString() + " : command_07_start_on"); 
				} else {
					SerialTool.sendHexToPort(serialPort, Serial.getCommand(Serial.COMMAND_07_START_ON));
				}
			}
		});
		laserTasks.add(new WorkTestTask() {
			public void run() {
				noteSerialCommand("command_11_Q_on : " + Serial.getCommand(Serial.COMMAND_11_Q_ON)); 
				if (isDebug) {
					System.out.println(MyDate.getMiddleDateString() + " : command_11_Q_on"); 
				} else {
					SerialTool.sendHexToPort(serialPort, Serial.getCommand(Serial.COMMAND_11_Q_ON));
				}
			}
		});
		return laserTasks;
	}

	private static List<WorkTestTask> generateStopLaserTasks(long duration) {
		List<WorkTestTask> laserTasks = new ArrayList<WorkTestTask>();
		laserTasks.add(new WorkTestTask(duration) {
			public void run() {
				noteSerialCommand("command_12_Q_off : " + Serial.getCommand(Serial.COMMAND_12_Q_OFF)); 
				if (isDebug) {
					System.out.println(MyDate.getMiddleDateString() + " : command_12_Q_off"); 
				} else {
					SerialTool.sendHexToPort(serialPort, Serial.getCommand(Serial.COMMAND_12_Q_OFF));
				}
			}
		});
		laserTasks.add(new WorkTestTask() {
			public void run() {
				noteSerialCommand("command_08_start_off : " + Serial.getCommand(Serial.COMMAND_08_START_OFF)); 
				if (isDebug) {
					System.out.println(MyDate.getMiddleDateString() + " : command_08_start_off"); 
				} else {
					SerialTool.sendHexToPort(serialPort, Serial.getCommand(Serial.COMMAND_08_START_OFF));
				}
			}
		});
		laserTasks.add(new WorkTestTask() {
			public void run() {
				noteSerialCommand("command_06_warm_up_off : " + Serial.getCommand(Serial.COMMAND_06_WARM_UP_OFF)); 
				if (isDebug) {
					System.out.println(MyDate.getMiddleDateString() + " : command_06_warm_up_off"); 
				} else {
					SerialTool.sendHexToPort(serialPort, Serial.getCommand(Serial.COMMAND_06_WARM_UP_OFF));
				}
			}
		});
		laserTasks.add(new WorkTestTask() {
			public void run() {
				noteSerialCommand("command_02_offline_off : " + Serial.getCommand(Serial.COMMAND_02_OFFLINE_OFF)); 
				if (isDebug) {
					System.out.println(MyDate.getMiddleDateString() + " : command_02_offline_off"); 
				} else {
					SerialTool.sendHexToPort(serialPort, Serial.getCommand(Serial.COMMAND_02_OFFLINE_OFF));
				}
			}
		});
		return laserTasks;
	}

	public static List<WorkTestTask> generateOneTestLaserTasks(long start, long duration) {
		List<WorkTestTask> laserTasks = new ArrayList<WorkTestTask>();
		laserTasks.add(new WorkTestTask(10) {
			public void run() {
				noteSerialCommand("Open serial port : " + Serial.getLaserSerialPort()); 
				noteSerialCommand("Open serial port baudrate : " + Serial.getLaserSerialPortBaudrate()); 
				if ( ! isDebug) {
					serialPort = SerialTool.openPort(Serial.getLaserSerialPort(), Serial.getLaserSerialPortBaudrate());
				}
			}
		});
		laserTasks.addAll(generateStartLaserTasks(start));
		laserTasks.addAll(generateStopLaserTasks(duration));
		laserTasks.add(new WorkTestTask() {
			public void run() {
				noteSerialCommand("Close serial port : " + Serial.getLaserSerialPort()); 
				if ( ! isDebug) {
					SerialTool.closePort(serialPort);
				}
			}
		});
		return laserTasks;
	}
	
	private static List<WorkTestTask> executeOneCommand(final String command) {
		List<WorkTestTask> laserTasks = new ArrayList<WorkTestTask>();
		laserTasks.add(new WorkTestTask(10) {
			public void run() {
				noteSerialCommand("Open serial port : " + Serial.getLaserSerialPort()); 
				noteSerialCommand("Open serial port baudrate : " + Serial.getLaserSerialPortBaudrate()); 
				if ( ! isDebug) {
					serialPort = SerialTool.openPort(Serial.getLaserSerialPort(), Serial.getLaserSerialPortBaudrate());
				}
			}
		});
		laserTasks.add(new WorkTestTask() {
			public void run() {
				noteSerialCommand("exec command : " + Serial.getCommand(command)); 
				if (isDebug) {
					System.out.println(MyDate.getMiddleDateString() + " : " +  command); 
				} else {
					SerialTool.sendHexToPort(serialPort, Serial.getCommand(command));
				}
			}
		});
		laserTasks.add(new WorkTestTask() {
			public void run() {
				noteSerialCommand("Close serial port : " + Serial.getLaserSerialPort()); 
				if ( ! isDebug) {
					SerialTool.closePort(serialPort);
				}
			}
		});
		return laserTasks;
	}
	
	public static void sendCommand(String command) {
		long delay = 0;
		Timer timer = new Timer(true);
		for (WorkTestTask laserTask : executeOneCommand(command)) {
			delay = delay + laserTask.getDelay();
			timer.schedule(laserTask, delay);
		}
		
	}


}
