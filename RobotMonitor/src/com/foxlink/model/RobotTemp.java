package com.foxlink.model;

import java.sql.Date;

public class RobotTemp {
	private String SN;
	private Date DTTM;
	private double MaxTemp;
	private double MinTemp;
	private double AvgTemp;
	
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	public Date getDTTM() {
		return DTTM;
	}
	public void setDTTM(Date dTTM) {
		DTTM = dTTM;
	}
	public double getMaxTemp() {
		return MaxTemp;
	}
	public void setMaxTemp(double maxTemp) {
		MaxTemp = maxTemp;
	}
	public double getMinTemp() {
		return MinTemp;
	}
	public void setMinTemp(double minTemp) {
		MinTemp = minTemp;
	}
	public double getAvgTemp() {
		return AvgTemp;
	}
	public void setAvgTemp(double avgTemp) {
		AvgTemp = avgTemp;
	}
}
