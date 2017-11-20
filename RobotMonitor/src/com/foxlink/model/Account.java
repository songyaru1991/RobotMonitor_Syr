package com.foxlink.model;

public class Account {
	private String EmpID;
	private String EmpName;
	private String EmpDeptID;
	private String EmpPassword;
	private int AccountPriority;
	private int AccountEnable;
	
	public String getEmpID() {
		return EmpID;
	}
	public void setEmpID(String empID) {
		EmpID = empID;
	}
	public String getEmpName() {
		return EmpName;
	}
	public void setEmpName(String empName) {
		EmpName = empName;
	}
	public String getEmpDeptID() {
		return EmpDeptID;
	}
	public void setEmpDeptID(String empDeptID) {
		EmpDeptID = empDeptID;
	}
	public int getAccountPriority() {
		return AccountPriority;
	}
	public void setAccountPriority(int accountPriority) {
		AccountPriority = accountPriority;
	}
	public int getAccountEnable() {
		return AccountEnable;
	}
	public void setAccountEnable(int accountEnable) {
		AccountEnable = accountEnable;
	}
	public String getEmpPassword() {
		return EmpPassword;
	}
	public void setEmpPassword(String empPassword) {
		EmpPassword = empPassword;
	}
}
