package com.foxlink.model;

import java.sql.Date;

public class RobotRPM {
	private String SN;
	private String Position;
	private double MaxRPM;
	private double AvgRPM;
	private double MinRpm;
	private Date DTTM;
	
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	public String getPosition() {
		return Position;
	}
	public void setPosition(String position) {
		Position = position;
	}
	public double getMaxRPM() {
		return MaxRPM;
	}
	public void setMaxRPM(double maxRPM) {
		MaxRPM = maxRPM;
	}
	public double getAvgRPM() {
		return AvgRPM;
	}
	public void setAvgRPM(double avgRPM) {
		AvgRPM = avgRPM;
	}
	public double getMinRpm() {
		return MinRpm;
	}
	public void setMinRpm(double minRpm) {
		MinRpm = minRpm;
	}
	public Date getDTTM() {
		return DTTM;
	}
	public void setDTTM(Date dTTM) {
		DTTM = dTTM;
	}
}
