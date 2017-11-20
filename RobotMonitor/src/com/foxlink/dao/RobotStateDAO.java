package com.foxlink.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.foxlink.model.RobotState;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class RobotStateDAO extends DAO implements INormalDAO {
	private static Logger logger=Logger.getLogger(RobotStateDAO.class);
	private boolean isOracleDB=false;
	public RobotStateDAO(String dbURL, String dbUser, String dbPassword,boolean isOracleDB) {
		super(dbURL, dbUser, dbPassword,isOracleDB);
		// TODO Auto-generated constructor stub
		this.isOracleDB=isOracleDB;
	}

	@Override
	public Object SetNewObject(Object obj, boolean isBatch, String updateUser) throws Exception {
		// TODO Auto-generated method stub
		String sSQL="";
		if(isOracleDB)
			sSQL="INSERT foxlinkrobot.ROBOT_STATE_INFOS(SN,UPDATE_DATE) "
				+ " VALUES(?,SYSDATE)";
		else
			sSQL="INSERT foxlinkrobot.ROBOT_STATE_INFOS(SN,UPDATE_DATE) "
					+ " VALUES(?,now())";
		PreparedStatement pstmt=null;
		int effectRow=-1;
		try{
			RobotState robotState=(RobotState)obj;
			super.getDBConnection();
			Conn.setAutoCommit(false);
			pstmt=Conn.prepareStatement(sSQL);
			pstmt.setString(1, robotState.getSN());
			effectRow=pstmt.executeUpdate();
			pstmt.close();
			Conn.commit();
		}
		catch(Exception ex){
			logger.error("Inserting the robot state is failed , due to: ",ex);
			Conn.rollback();
		}
		finally{
			if(!Conn.isClosed())
				Conn.close();
		}
		
		return effectRow;
	}

	@Override
	public Object GetObject(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean UpdateObject(Object obj, String updateUser) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object DeleteObject(Object obj, boolean isBatch, String updateUser) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object GetObject(Object obj, boolean isShowHistory) throws Exception {
		// TODO Auto-generated method stub
		String sSQL="";
		JsonArray robotStateInfos=new JsonArray();
		JsonObject robotStateInfo=new JsonObject();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		if(isShowHistory)
			sSQL="SELECT a.DTTM,b.ERROR_MESSAGE,C.STATUS_MESSAGE"
				+ " FROM foxlinkrobot.ROBOT_STATE_INFOS_HISTORY a join foxlinkrobot.robot_error_code b"
				+" on a.error_code=b.error_code "
				+" join robot_status_code c on a.status_code=c.status_code"
				+ " WHERE a.SN=? ";
		else
			sSQL="SELECT a.UPDATE_DATE,b.ERROR_MESSAGE,C.STATUS_MESSAGE"
					+ " FROM foxlinkrobot.ROBOT_STATE_INFOS a join foxlinkrobot.robot_error_code b"
					+" on a.error_code=b.error_code "
					+" join robot_status_code c on a.status_code=c.status_code"
					+ " WHERE a.SN=? ";
		
		try{
			RobotState RobotState=(RobotState)obj;
			super.getDBConnection();
			pstmt=Conn.prepareStatement(sSQL);
			pstmt.setString(1, RobotState.getSN());
			rs=pstmt.executeQuery();
			while(rs.next()){
				if(isShowHistory){
					JsonObject robotState=new JsonObject();
					robotState.addProperty("TIME", rs.getString("DTTM"));
					robotState.addProperty("STATUSCODE", rs.getString("STATUS_MESSAGE"));
					robotState.addProperty("ERRORCODE", rs.getString("ERROR_MESSAGE"));
					robotStateInfos.add(robotState);
				}
				else{
					robotStateInfo.addProperty("TIME", rs.getString("UPDATE_DATE"));
					robotStateInfo.addProperty("STATUSCODE", rs.getString("STATUS_MESSAGE"));
					robotStateInfo.addProperty("ERRORCODE", rs.getString("ERROR_MESSAGE"));
					robotStateInfos.add(robotStateInfo);
				}
			}
			rs.close();
			pstmt.close();
		}
		catch(Exception ex){
			logger.error("",ex);
			throw ex;
		}
		finally{
			if(!Conn.isClosed())
				Conn.close();
		}
		
		return robotStateInfos.toString();
	}

	@Override
	public Object GetObject() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object GetObject(int offset, int numOfRecords, int currentPage, String queryCritirea, String queryParam)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object GetRobotStateAndDetailInfos(String robotSN) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
