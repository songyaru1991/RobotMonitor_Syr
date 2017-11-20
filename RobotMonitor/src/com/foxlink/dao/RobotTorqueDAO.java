package com.foxlink.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.foxlink.model.RobotTorque;

public class RobotTorqueDAO extends DAO implements INormalDAO {
	private static Logger logger=Logger.getLogger(RobotTorqueDAO.class);
	private boolean isOracleDB=false;
	public RobotTorqueDAO(String dbURL, String dbUser, String dbPassword,boolean isOracleDB) {
		super(dbURL, dbUser, dbPassword,isOracleDB);
		// TODO Auto-generated constructor stub
		this.isOracleDB=isOracleDB;
	}

	@Override
	public Object SetNewObject(Object obj, boolean isBatch, String updateUser) throws Exception {
		// TODO Auto-generated method stub
		String sSQL="";
		if(isOracleDB)
			sSQL="INSERT INTO ROBOT_TORQUE_INFOS(SN,POSITION,UPDATE_DATE)"
				+ " VALUES(?,?,SYSDATE)";
		else
			sSQL+="INSERT INTO foxlinkrobot.ROBOT_TORQUE_INFOS(SN,POSITION,UPDATE_DATE)"
					+ " VALUES(?,?,now())";
		PreparedStatement pstmt=null;
		int[] insertRows=null;
		try{
			RobotTorque robotTorque=(RobotTorque)obj;
			super.getDBConnection();
			Conn.setAutoCommit(false);
			pstmt=Conn.prepareStatement(sSQL);
			for(int i=0;i<6;i++){
				pstmt.setString(1, robotTorque.getSN());
				pstmt.setString(2, "J"+(i+1));
				pstmt.addBatch();
			}
			insertRows=pstmt.executeBatch();
			pstmt.close();
			Conn.commit();
		}
		catch(Exception ex){
			Conn.rollback();
			logger.error("Inserting the robot torque data is failed, due to: ",ex);
			throw ex;
		}
		finally{
			if(!Conn.isClosed())
				Conn.close();
		}
		return insertRows;
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
		JsonArray robotTorqueInfos=new JsonArray();
		JsonObject robotTorqueInfo=new JsonObject();
		if(isShowHistory){
			if(isOracleDB)
				sSQL="SELECT TO_CHAR(A.CREATE_DATE,'hh24:mi'),A.AVG_TORQUE,A.MAX_TORQUE,A.MIN_TORQUE"
					+ " FROM ROBOT_TORQUE_INFOS_HISTORY A"
					+ " WHERE SN=?"
					+ " AND POSITION=?"
					+ " AND A.CREATE_DATE>=sysdate-(10/1440)"
					+ "  order by to_char(a.create_date,'hh24:mi') asc";
			else
				sSQL="SELECT DATE_FORMAT(A.CREATE_DATE,'%H:%i'),A.AVG_TORQUE,A.MAX_TORQUE,A.MIN_TORQUE"
						+ " FROM foxlinkrobot.ROBOT_TORQUE_INFOS_HISTORY A"
						+ " WHERE SN=?"
						+ " AND POSITION=?"
						+ " AND A.CREATE_DATE>=now() - interval 30 minute "
						+ "  order by DATE_FORMAT(A.CREATE_DATE,'%H:%i') asc";
			//測試用，有將時間固定to_date('2016/08/24 17:22:16','yyyy/mm/dd hh24:mi:ss')
		}
		else{
			sSQL="SELECT UPDATE_DATE,AVG_TORQUE,MAX_TORQUE,MIN_TORQUE"
					+ " FROM foxlinkrobot.ROBOT_TORQUE_INFOS"
					+ " WHERE SN=?"
					+ " AND POSITION=?";
		}
		
		try{
			RobotTorque robotTorque=(RobotTorque)obj;
			super.getDBConnection();
			pstmt=Conn.prepareStatement(sSQL);
			pstmt.setString(1, robotTorque.getSN());
			pstmt.setString(2, robotTorque.getPosition());
			rs=pstmt.executeQuery();
			while(rs.next()){
				if(isShowHistory){
					JsonObject torque=new JsonObject();
					torque.addProperty("TIME", rs.getString(1));
					torque.addProperty("AVG", rs.getString(2));
					torque.addProperty("MAX", rs.getString(3));
					torque.addProperty("MIN", rs.getString(4));
					robotTorqueInfos.add(torque);
				}
				else{
					robotTorqueInfo.addProperty("TIME", rs.getString(1));
					robotTorqueInfo.addProperty("AVG", rs.getString(2));
					robotTorqueInfo.addProperty("MAX", rs.getString(3));
					robotTorqueInfo.addProperty("MIN", rs.getString(4));
					robotTorqueInfos.add(robotTorqueInfo);
				}
			}
			rs.close();
			pstmt.close();
		}
		catch(Exception ex){
			logger.error("Getting the robot's torque info is failed, due to: ",ex);
		}
		finally{
			if(!Conn.isClosed())
				Conn.close();
		}
		
		return robotTorqueInfos.toString();
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
