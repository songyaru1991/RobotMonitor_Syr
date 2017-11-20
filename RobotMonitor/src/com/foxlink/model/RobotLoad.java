package com.foxlink.model;

import java.sql.Date;

public class RobotLoad {
	private String SN;
	private String Position;
	private Date DTTM;
	private double MaxLoad;
	private double AvgLoad;
	private double MinLoad;
	
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
	
	public Date getDTTM() {
		return DTTM;
	}
	public void setDTTM(Date dTTM) {
		DTTM = dTTM;
	}
	public double getMaxLoad() {
		return MaxLoad;
	}
	public void setMaxLoad(double maxLoad) {
		MaxLoad = maxLoad;
	}
	public double getAvgLoad() {
		return AvgLoad;
	}
	public void setAvgLoad(double avgLoad) {
		AvgLoad = avgLoad;
	}
	public double getMinLoad() {
		return MinLoad;
	}
	public void setMinLoad(double minLoad) {
		MinLoad = minLoad;
	}
}
