package com.foxlink.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.foxlink.model.RobotLoad;

public class RobotLoadDAO extends DAO implements INormalDAO {
	private static Logger logger=Logger.getLogger(RobotLoadDAO.class);
	private boolean isOracleDB=false;
	public RobotLoadDAO(String dbURL, String dbUser, String dbPassword,boolean isOracleDB) {
		super(dbURL, dbUser, dbPassword,isOracleDB);
		// TODO Auto-generated constructor stub
		this.isOracleDB=isOracleDB;
	}

	@Override
	public Object SetNewObject(Object obj, boolean isBatch, String updateUser) throws Exception {
		// TODO Auto-generated method stub
		String sSQL="";
		if(isOracleDB)
			sSQL="INSERT INTO foxlinkrobot.ROBOT_LOAD_INFOS(SN,POSITION,UPDATE_DATE)"
				+ " VALUES(?,?,sysdate)";
		else
			sSQL="INSERT INTO foxlinkrobot.ROBOT_LOAD_INFOS(SN,POSITION,UPDATE_DATE)"
					+ " VALUES(?,?,now())";
		PreparedStatement pstmt=null;
		int[] insertRows=null;
		try{
			super.getDBConnection();
			Conn.setAutoCommit(false);
			pstmt=Conn.prepareStatement(sSQL);
			RobotLoad robotLoad=(RobotLoad)obj;
			for(int i=0;i<6;i++){
				pstmt.setString(1, robotLoad.getSN());
				pstmt.setString(2, "J"+(i+1));
				pstmt.addBatch();
			}
			insertRows=pstmt.executeBatch();
			pstmt.close();
			Conn.commit();
		}
		catch(Exception ex){
			Conn.rollback();
			logger.error("",ex);
			throw ex;
		}
		finally{
			if(!Conn.isClosed())
				Conn.close();
		}
		return insertRows;
	}

	
	/* *
	 * 近10分鐘電控箱溫度變化
	 * */
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
		JsonArray robotLoadInfos=new JsonArray();
		JsonObject robotLoadInfoResult=new JsonObject();
		if(isShowHistory){
			if(isOracleDB)
				sSQL="SELECT to_char(a.create_date,'hh24:mi'),A.AVG_LOAD,A.MAX_LOAD,A.MIN_LOAD"
						+ " from ROBOT_LOAD_INFOS_HISTORY A"
						+ " where sn=? "
						+ " and position=?"
						+ " and a.create_date >= sysdate -(10/1440)"
						+ " order by to_char(a.create_date,'hh24:mi') asc ";
				//測試用，填入固定日期 to_date('2016/08/24 17:22:16','yyyy/mm/dd hh24:mi:ss')
			else
				sSQL="SELECT DATE_FORMAT(A.CREATE_DATE,'%H:%i'),A.AVG_LOAD,A.MAX_LOAD,A.MIN_LOAD"
						+ " from foxlinkrobot.ROBOT_LOAD_INFOS_HISTORY A"
						+ " where sn=? "
						+ " and position=?"
						+ " and a.create_date >= now() - interval 30 minute "
						+ " order by DATE_FORMAT(A.CREATE_DATE,'%H:%i') asc ";
			//測試'2016/08/24 17:22:16' A.CREATE_DATE
		}
		else{
			sSQL="SELECT UPDATE_DATE,AVG_LOAD,MAX_LOAD,MIN_LOAD"
				+ " FROM foxlinkrobot.ROBOT_LOAD_INFOS"
				+ " WHERE SN=?"
				+ "	AND POSITION=?";
		}
		
		try{
			RobotLoad robotLoad=(RobotLoad)obj;
			super.getDBConnection();
			pstmt=Conn.prepareStatement(sSQL);
			pstmt.setString(1, robotLoad.getSN());
			pstmt.setString(2, robotLoad.getPosition());
			rs=pstmt.executeQuery();
			while(rs.next()){
				if(isShowHistory){
					JsonObject robotLoading=new JsonObject();
					robotLoading.addProperty("TIME", rs.getString(1));
					robotLoading.addProperty("AVG", rs.getString(2));
					robotLoading.addProperty("MAX", rs.getString(3));
					robotLoading.addProperty("MIN", rs.getString(4));
					robotLoadInfos.add(robotLoading);
				}
				else{
					//robotLoadInfoResult
					robotLoadInfoResult.addProperty("TIME", rs.getString(1));
					robotLoadInfoResult.addProperty("AVG", rs.getString(2));
					robotLoadInfoResult.addProperty("MAX", rs.getString(3));
					robotLoadInfoResult.addProperty("MIN", rs.getString(4));
					robotLoadInfos.add(robotLoadInfoResult);
				}

			}
			rs.close();
			pstmt.close();
		}
		catch(Exception ex){
			logger.error("Getting the robot's loading infos  are failed, due to: ",ex);
		}
		finally{
			if(Conn.isClosed())
				Conn.close();
		}
		
		return robotLoadInfos.toString();
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
