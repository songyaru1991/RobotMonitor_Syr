package com.foxlink.service;

import com.foxlink.model.RobotLoad;
import com.foxlink.model.RobotMaster;
import com.foxlink.model.RobotRPM;
import com.foxlink.model.RobotState;
import com.foxlink.model.RobotTemp;
import com.foxlink.model.RobotTorque;

public interface IRobotService {
	public Object FindRPMInfos(RobotRPM robotRPM,boolean isFindHistory)throws Exception;
	public Object FindLoadInfos(RobotLoad robotLoad,boolean isFindHistory)throws Exception;
	public Object FindTempatureInfos(RobotTemp robotTemp,boolean isFindHistory)throws Exception;
	public Object FindTorqueInfos(RobotTorque robotTorque,boolean isFindHistory)throws Exception;
	public Object FindRobotMasterInfos(RobotMaster robotMaster)throws Exception;
	public Object FindRobotState(RobotState robotState,boolean isFindHistory)throws Exception;
	public Object FindRobotList()throws Exception;
	public Object FindRobotStateAndDetail(String robotSN)throws Exception;
	public Object ShowRobotList(int offset,int currentPage,int numOfRecords,String queryCritirea,String queryParam)throws Exception;
	public boolean SaveRobotInfos(RobotMaster robotMaster,String updateUser)throws Exception;
	public boolean UpdateRobotInfos(RobotMaster robotMaster,String updateUser)throws Exception;
	public boolean DisableRobotInfos(String SN,String updateUser)throws Exception;
	
}
