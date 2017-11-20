package com.foxlink.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.foxlink.model.RobotTemp;

public class RobotTempDAO extends DAO implements INormalDAO {
	private static Logger logger=Logger.getLogger(RobotTempDAO.class);
	private boolean isOracleDB=false;
	public RobotTempDAO(String dbURL, String dbUser, String dbPassword,boolean isOracleDB) {
		super(dbURL, dbUser, dbPassword,isOracleDB);
		// TODO Auto-generated constructor stub
		this.isOracleDB=isOracleDB;
	}

	@Override
	public Object SetNewObject(Object obj, boolean isBatch, String updateUser) throws Exception {
		// TODO Auto-generated method stub
		String sSQL="";
		if(isOracleDB)
			sSQL="INSERT INTO foxlinkrobot.ROBOT_CONTROL_TEMP(SN,UPDATE_DATE)"
				+ " VALUES(?,SYSDATE)";
		else
			sSQL="INSERT INTO foxlinkrobot.ROBOT_CONTROL_TEMP(SN,UPDATE_DATE)"
					+ " VALUES(?,now())";
		
		PreparedStatement pstmt=null;
		int insertRow=-1;
		try{
			super.getDBConnection();
			Conn.setAutoCommit(false);
			pstmt=Conn.prepareStatement(sSQL);
			String SN=(String)obj;
			pstmt.setString(1, SN);
			insertRow=pstmt.executeUpdate();
			pstmt.close();
			Conn.commit();
		}
		catch(Exception ex){
			logger.error("Inserting the robot controller tempature is failed, due to: ",ex);
			Conn.rollback();
		}
		finally{
			if(!Conn.isClosed())
				Conn.close();
		}
		return insertRow;
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
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		JsonArray robotTempatures=new JsonArray();
		JsonObject robotTempature=new JsonObject();
		if(isShowHistory){
			if(isOracleDB)
				sSQL="SELECT TO_CHAR(A.CREATE_DATE,'hh24:mi'),A.AVG_TEMP,A.MAX_TEMP,A.MIN_TEMP"
						+ " FROM foxlinkrobot.ROBOT_CONTROL_TEMP_HISTORY A"
						+ " WHERE SN=?"
						+ " AND A.CREATE_DATE>=sysdate-(10/1440)"
						+ "  order by to_char(a.create_date,'hh24:mi') asc"; //先用假日期測試，to_date('2016/8/24 17:22:16','yyyy/mm/dd hh24:mi:ss')
			else
				sSQL="SELECT DATE_FORMAT(A.CREATE_DATE,'%H:%i'),A.AVG_TEMP,A.MAX_TEMP,A.MIN_TEMP"
						+ " FROM foxlinkrobot.ROBOT_CONTROL_TEMP_HISTORY A"
						+ " WHERE SN=?"
						+ " AND A.CREATE_DATE>=now() - interval 10 minute "
						+ "  order by DATE_FORMAT(A.CREATE_DATE,'%H:%i') asc"; 
		}
		else{
			sSQL="SELECT UPDATE_DATE,AVG_TEMP,MAX_TEMP,MIN_TEMP"
					+ " FROM foxlinkrobot.ROBOT_CONTROL_TEMP"
					+ " WHERE SN=?";
		}
		
		try{
			super.getDBConnection();
			RobotTemp robotTemp=(RobotTemp)obj;
			pstmt=Conn.prepareStatement(sSQL);
			pstmt.setString(1, robotTemp.getSN());
			rs=pstmt.executeQuery();
			while(rs.next()){
				if(isShowHistory){
					JsonObject tempature=new JsonObject();
					tempature.addProperty("TIME", rs.getString(1));
					tempature.addProperty("AVG", rs.getString(2));
					tempature.addProperty("MAX", rs.getString(3));
					tempature.addProperty("MIN", rs.getString(4));
					robotTempatures.add(tempature);
				}
				else{
					robotTempature.addProperty("TIME", rs.getString(1));
					robotTempature.addProperty("AVG", rs.getString(2));
					robotTempature.addProperty("MAX", rs.getString(3));
					robotTempature.addProperty("MIN", rs.getString(4));
					robotTempatures.add(robotTempature);
				}
			}
			rs.close();
			pstmt.close();
		}
		catch(Exception ex){
			logger.error("Getting the robot controller's tempature is failed, due to: ",ex);
			throw ex;
		}
		finally{
			if(!Conn.isClosed())
				Conn.close();
		}
		
		return robotTempatures.toString();
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
