package org.bai.zhang.prj.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MyDate {

	public final static SimpleDateFormat hourMintues = new SimpleDateFormat ("HH:mm"); 

	private final static SimpleDateFormat folderSimpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd_HHmm_"); 
	
	private final static SimpleDateFormat shortSimpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm"); 
	private final static SimpleDateFormat middleSimpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
	private final static SimpleDateFormat longSimpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS"); 
	
	public static String getDateFolderName() {
		long currentTime = System.currentTimeMillis();
		return folderSimpleDateFormat.format(new Date(currentTime)) + currentTime; 
	}

	public static String getShortDateString() {
		return shortSimpleDateFormat.format(new Date()); 
	}

	public static String getMiddleDateString() {
		return middleSimpleDateFormat.format(new Date()); 
	}

	public static String getLongDateString() {
		return longSimpleDateFormat.format(new Date()); 
	}

	public static String getShortDateString(Date date) {
		return shortSimpleDateFormat.format(date); 
	}

	public static String getMiddleDateString(Date date) {
		return middleSimpleDateFormat.format(date); 
	}

	public static String getLongDateString(Date date) {
		return longSimpleDateFormat.format(date); 
	}

	public static String getShortDateString(String string) {
		Date date = new Date(Long.parseLong(string));
		return shortSimpleDateFormat.format(date); 
	}

	public static String getMiddleDateString(String string) {
		Date date = new Date(Long.parseLong(string));
		return middleSimpleDateFormat.format(date); 
	}
	
//	public static Date addMillsecond(Date date, long millseconds) {
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(date);
//		cal.add(Calendar.MILLISECOND, millseconds);
//		return cal.getTime();
//	}

	public static Date addMinutes(Date date, int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minutes);
		return cal.getTime();
	}
	public static Date addSeconds(Date date, int seconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, seconds);
		return cal.getTime();
	}
	
	public static void main(String[] args) {
		System.out.println(getLongDateString());
		System.out.println(getMiddleDateString());
		System.out.println(getShortDateString());
		
//		Start Laser:
//			command_01_online_on£¬command_03_voltage£¬command_04_dividing_freq£¬command_04_freq£¬
//		command_05_warm_up_on£¬command_07_start_on£¬command_11_Q_on£»
//
//			Stop Laser:
//			command_11_Q_off£¬command_08_start_off£¬command_06_warm_up_off£¬command_02_offline_off
		
		int serialCommandInverval = 1;
		int testDuration = 1;

		printStringForDemo(serialCommandInverval, testDuration);
		
		TimerTask[] startLaserTasks = {
				new TimerTask() {
					public void run() { System.out.println(getMiddleDateString() + " : command_01_online_on"); }
				},
				new TimerTask() {
					public void run() { System.out.println(getMiddleDateString() + " : command_03_voltage"); }
				},
				new TimerTask() {
					public void run() { System.out.println(getMiddleDateString() + " : command_04_dividing_freq"); }
				},
				new TimerTask() {
					public void run() { System.out.println(getMiddleDateString() + " : command_04_freq"); }
				},
				new TimerTask() {
					public void run() { System.out.println(getMiddleDateString() + " : command_05_warm_up_on"); }
				},
				new TimerTask() {
					public void run() { System.out.println(getMiddleDateString() + " : command_07_start_on"); }
				},
				new TimerTask() {
					public void run() { System.out.println(getMiddleDateString() + " : command_11_Q_on"); }
				},
		};
		TimerTask[] stopLaserTasks = {
				new TimerTask() {
					public void run() { System.out.println(getMiddleDateString() + " : command_11_Q_off"); }
				},
				new TimerTask() {
					public void run() { System.out.println(getMiddleDateString() + " : command_08_start_off"); }
				},
				new TimerTask() {
					public void run() { System.out.println(getMiddleDateString() + " : command_06_warm_up_off"); }
				},
				new TimerTask() {
					public void run() { System.out.println(getMiddleDateString() + " : command_02_offline_off"); }
				},
		};
		
		boolean isDaemon = false;
		Timer timer = new Timer(isDaemon);
		
		long delay = 0;
		for(TimerTask task : startLaserTasks) {
			timer.schedule(task, delay);
			delay = delay + serialCommandInverval * 1000;
		}
		
		delay = delay + 30 * testDuration * 1000;
		
		for(TimerTask task : stopLaserTasks) {
			timer.schedule(task, delay);
			delay = delay + serialCommandInverval * 1000;
		}
	}

	private static void printStringForDemo(int serialCommandInverval, int testDuration) {
		System.out.println("::::::: :::::::: printStringForDemo start :::::::: ::::::::");
		
		Date start = new Date();
		System.out.println("::::::: :::::::: Start Laser :::::::: ::::::::");
		System.out.print(getMiddleDateString(start));
		System.out.println(" : command_01_online_on");

		start = addSeconds(start, serialCommandInverval);
		System.out.print(getMiddleDateString(start));
		System.out.println(" : command_03_voltage");
		
		start = addSeconds(start, serialCommandInverval);
		System.out.print(getMiddleDateString(start));
		System.out.println(" : command_04_dividing_freq");

		start = addSeconds(start, serialCommandInverval);
		System.out.print(getMiddleDateString(start));
		System.out.println(" : command_04_freq");

		start = addSeconds(start, serialCommandInverval);
		System.out.print(getMiddleDateString(start));
		System.out.println(" : command_05_warm_up_on");

		start = addSeconds(start, serialCommandInverval);
		System.out.print(getMiddleDateString(start));
		System.out.println(" : command_07_start_on");

		start = addSeconds(start, serialCommandInverval);
		System.out.print(getMiddleDateString(start));
		System.out.println(" : command_11_Q_on");
		
		
		start = addMinutes(start, testDuration);
		System.out.println("::::::: :::::::: Stop Laser :::::::: ::::::::");
		System.out.print(getMiddleDateString(start));
		System.out.println(" : command_11_Q_off");
		
		start = addSeconds(start, serialCommandInverval);
		System.out.print(getMiddleDateString(start));
		System.out.println(" : command_08_start_off");
		
		start = addSeconds(start, serialCommandInverval);
		System.out.print(getMiddleDateString(start));
		System.out.println(" : command_06_warm_up_off");
		
		start = addSeconds(start, serialCommandInverval);
		System.out.print(getMiddleDateString(start));
		System.out.println(" : command_02_offline_off");
		
//		command_08_start_off£¬command_06_warm_up_off£¬command_02_offline_off
		System.out.println("::::::: ::::::::printStringForDemo end :::::::: ::::::::");
	}
}
