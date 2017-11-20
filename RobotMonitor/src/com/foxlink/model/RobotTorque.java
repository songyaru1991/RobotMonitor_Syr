package com.foxlink.model;

import java.sql.Date;

public class RobotTorque {
	private String SN;
	private Date DTTM;
	private String MAXTorque;
	private String AVGTorque;
	private String MINTorque;
	private String Position;
	
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
	
	public String getMAXTorque() {
		return MAXTorque;
	}
	public void setMAXTorque(String mAXTorque) {
		MAXTorque = mAXTorque;
	}
	public String getAVGTorque() {
		return AVGTorque;
	}
	public void setAVGTorque(String aVGTorque) {
		AVGTorque = aVGTorque;
	}
	public String getMINTorque() {
		return MINTorque;
	}
	public void setMINTorque(String mINTorque) {
		MINTorque = mINTorque;
	}
	public String getPosition() {
		return Position;
	}
	public void setPosition(String position) {
		Position = position;
	}
	
	
}
