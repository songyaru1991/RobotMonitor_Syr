package com.foxlink.model;

import java.sql.Date;

public class RobotMaster {
	private String SN;
	private String LineNO;
	private String ModelNO;
	private String FactoryCode;
	private String WorkShop;
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	public String getLineNO() {
		return LineNO;
	}
	public void setLineNO(String lineNO) {
		LineNO = lineNO;
	}
	public String getModelNO() {
		return ModelNO;
	}
	public void setModelNO(String modelNO) {
		ModelNO = modelNO;
	}
	public String getFactoryCode() {
		return FactoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		FactoryCode = factoryCode;
	}
	public String getWorkShop() {
		return WorkShop;
	}
	public void setWorkShop(String workShop) {
		WorkShop = workShop;
	}
}
