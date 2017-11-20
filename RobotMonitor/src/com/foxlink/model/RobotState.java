package com.foxlink.model;
import java.sql.Date;

public class RobotState {
	private String SN;
	private int StatusCode;
	private int ErrorCode;
	private Date DTTM;
	
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	
	public int getStatusCode() {
		return StatusCode;
	}
	public void setStatusCode(int statusCode) {
		StatusCode = statusCode;
	}
	
	public int getErrorCode() {
		return ErrorCode;
	}
	public void setErrorCode(int errorCode) {
		ErrorCode = errorCode;
	}
	public Date getDTTM() {
		return DTTM;
	}
	public void setDTTM(Date dTTM) {
		DTTM = dTTM;
	}
}
