package com.foxlink.service;
import com.foxlink.dao.INormalDAO;
import com.foxlink.dao.RobotDAO;
import com.foxlink.dao.RobotLoadDAO;
import com.foxlink.dao.RobotRPMDAO;
import com.foxlink.dao.RobotStateDAO;
import com.foxlink.dao.RobotTempDAO;
import com.foxlink.dao.RobotTorqueDAO;
import com.foxlink.model.RobotLoad;
import com.foxlink.model.RobotMaster;
import com.foxlink.model.RobotRPM;
import com.foxlink.model.RobotState;
import com.foxlink.model.RobotTemp;
import com.foxlink.model.RobotTorque;

public class ImplRobotService implements IRobotService {
	private String dbURL,dbUser,dbPassword;
	private boolean isOracleDB=false;
	private INormalDAO IDAO;
	public ImplRobotService(String dbURL,String dbUser,String dbPassword,boolean isOracleDB){
		this.dbURL=dbURL;
		this.dbUser=dbUser;
		this.dbPassword=dbPassword;
		this.isOracleDB=isOracleDB;
	}
	
	@Override
	public Object FindRPMInfos(RobotRPM robotRPM, boolean isFindHistory)throws Exception {
		// TODO Auto-generated method stub
		IDAO=new RobotRPMDAO(dbURL,dbUser,dbPassword,isOracleDB);
		return IDAO.GetObject(robotRPM, isFindHistory);
	}

	@Override
	public Object FindLoadInfos(RobotLoad robotLoad, boolean isFindHistory)throws Exception {
		// TODO Auto-generated method stub
		IDAO=new RobotLoadDAO(dbURL,dbUser,dbPassword,isOracleDB);
		return IDAO.GetObject(robotLoad, isFindHistory);
	}

	@Override
	public Object FindTempatureInfos(RobotTemp robotTemp, boolean isFindHistory)throws Exception {
		// TODO Auto-generated method stub
		IDAO=new RobotTempDAO(dbURL,dbUser,dbPassword,isOracleDB);
		return IDAO.GetObject(robotTemp, isFindHistory);
	}

	@Override
	public Object FindTorqueInfos(RobotTorque robotTorque, boolean isFindHistory)throws Exception {
		// TODO Auto-generated method stub
		INormalDAO IDAO=new RobotTorqueDAO(dbURL,dbUser,dbPassword,isOracleDB);
		return IDAO.GetObject(robotTorque, isFindHistory);
	}

	@Override
	public Object FindRobotMasterInfos(RobotMaster robotMaster)throws Exception {
		// TODO Auto-generated method stub
		IDAO=new RobotDAO(dbURL,dbUser,dbPassword,isOracleDB);
		return IDAO.GetObject(robotMaster);
	}

	@Override
	public boolean SaveRobotInfos(RobotMaster robotMaster,String updateUser)throws Exception {
		// TODO Auto-generated method stub
		IDAO=new RobotDAO(dbURL,dbUser,dbPassword,isOracleDB);
		IDAO.SetNewObject(robotMaster, false, updateUser);
		//新增Load
		RobotLoad robotLoad=new RobotLoad();
		robotLoad.setSN(robotMaster.getSN());
		IDAO=new RobotLoadDAO(dbURL,dbUser,dbPassword,isOracleDB);
		IDAO.SetNewObject(robotLoad, false, updateUser);
		//新增RPM
		RobotRPM robotRPM=new RobotRPM();
		robotRPM.setSN(robotMaster.getSN());
		IDAO=new RobotRPMDAO(dbURL,dbUser,dbPassword,isOracleDB);
		IDAO.SetNewObject(robotRPM, false, updateUser);
		//新增Torque(轉矩)
		RobotTorque robotTorque=new RobotTorque();
		robotTorque.setSN(robotTorque.getSN());
		IDAO=new RobotTorqueDAO(dbURL,dbUser,dbPassword,isOracleDB);
		IDAO.SetNewObject(robotTorque, false, updateUser);
		//新增State
		RobotState robotState=new RobotState();
		IDAO=new RobotStateDAO(dbURL,dbUser,dbPassword,isOracleDB);
		IDAO.SetNewObject(robotState, false, updateUser);
		
		return true;
	}

	@Override
	public boolean UpdateRobotInfos(RobotMaster robotMaster,String updateUser) throws Exception{
		// TODO Auto-generated method stub
		IDAO=new RobotDAO(dbURL,dbUser,dbPassword,isOracleDB);
		return IDAO.UpdateObject(robotMaster, updateUser);
	}

	@Override
	public Object FindRobotState(RobotState robotState, boolean isFindHistory) throws Exception {
		// TODO Auto-generated method stub
		IDAO=new RobotStateDAO(dbURL,dbUser,dbPassword,isOracleDB);
		return IDAO.GetObject(robotState, isFindHistory);
	}

	@Override
	public Object FindRobotList() throws Exception {
		// TODO Auto-generated method stub
		IDAO=new RobotDAO(dbURL,dbUser,dbPassword,isOracleDB);
		return IDAO.GetObject();
	}

	@Override
	public Object ShowRobotList(int offset, int currentPage, int numOfRecords, String queryCritirea, String queryParam)
			throws Exception {
		// TODO Auto-generated method stub
		IDAO=new RobotDAO(dbURL,dbUser,dbPassword,isOracleDB);
		return IDAO.GetObject(offset, numOfRecords, currentPage, queryCritirea, queryParam);
	}

	@Override
	public boolean DisableRobotInfos(String SN, String updateUser)throws Exception {
		// TODO Auto-generated method stub
		IDAO=new RobotDAO(dbURL,dbUser,dbPassword,isOracleDB);
		return (boolean) IDAO.DeleteObject(SN, false, updateUser);
	}

	@Override
	public Object FindRobotStateAndDetail(String robotSN) throws Exception {
		// TODO Auto-generated method stub
		IDAO=new RobotDAO(dbURL,dbUser,dbPassword,isOracleDB);
		return IDAO.GetRobotStateAndDetailInfos(robotSN);
	}
}
