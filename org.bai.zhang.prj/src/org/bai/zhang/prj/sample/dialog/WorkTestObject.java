package org.bai.zhang.prj.sample.dialog;

import java.util.Calendar;
import java.util.Date;

import org.bai.zhang.prj.util.MyDate;

public class WorkTestObject {
	
	public enum Status {NotStart, InProgress, Done};
	
	private int times;
	private Date startTime;


	private Date endTime;
	private Status status;
	
	public WorkTestObject(int times, Date startTime, Date endTime, Status status) {
		this.times = times;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
	}
	
	public WorkTestObject(int times, Date startTime, Status status) {
		this(times, startTime, addMinutes(startTime, 1), status);
	}
	
	public static Date addMinutes(Date date, int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minutes);
		return cal.getTime();
	}

	
	public static void main(String[] args) {
		System.out.println(WorkTestObject.class);
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, 1);
		cal.getTime();
		System.out.println(MyDate.getShortDateString(date));
		System.out.println(MyDate.getShortDateString(addMinutes(date, 1)));
		System.out.println(MyDate.getShortDateString(addMinutes(date, 5)));
//		WorkTestObject testObject = new WorkTestObject();
	}

	public int getTimes() {
		return times;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Status getStatus() {
		return status;
	}
	
	public Status setStatus(Status status) {
		return this.status = status;
	}
}
