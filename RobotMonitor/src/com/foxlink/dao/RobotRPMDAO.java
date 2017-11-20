package com.foxlink.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.foxlink.model.RobotRPM;

public class RobotRPMDAO extends DAO implements INormalDAO {
	private static Logger logger=Logger.getLogger(RobotRPMDAO.class);
	private boolean isOracleDB=false;
	public RobotRPMDAO(String dbURL, String dbUser, String dbPassword,boolean isOracleDB) {
		super(dbURL, dbUser, dbPassword,isOracleDB);
		// TODO Auto-generated constructor stub
		this.isOracleDB=isOracleDB;
	}

	@Override
	public Object SetNewObject(Object obj, boolean isBatch, String updateUser) throws Exception {
		// TODO Auto-generated method stub
		String sSQL="";
		if(isOracleDB)
			sSQL="INSERT INTO foxlinkrobot.ROBOT_RPM_INFOS(SN,POSITION,UPDATE)"
				+ " VALUES(?,?,sysdate)";
		else
			sSQL="INSERT INTO foxlinkrobot.ROBOT_RPM_INFOS(SN,POSITION,UPDATE)"
					+ " VALUES(?,?,now())";
		PreparedStatement pstmt=null;
		int[] insertRows=null;
		try{
			RobotRPM robotRPM=(RobotRPM)obj;
			super.getDBConnection();
			Conn.setAutoCommit(false);
			pstmt=Conn.prepareStatement(sSQL);
			for(int i=0;i<6;i++){
				pstmt.setString(1, robotRPM.getSN());
				pstmt.setString(2,"J"+(i+1));
				pstmt.addBatch();
			}
			insertRows=pstmt.executeBatch();
			pstmt.close();
			Conn.commit();
		}
		catch(Exception ex){
			Conn.rollback();
			logger.error("",ex);
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
		JsonArray robotRPMInfos=new JsonArray();
		JsonObject robotRPMInfo=new JsonObject();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		if(isShowHistory){
			if(isOracleDB)
				sSQL="SELECT TO_CHAR(A.CREATE_DATE,'hh24:mi'),A.AVG_RPM,A.MAX_RPM,A.MIN_RPM"
						+ " FROM foxlinkrobot.ROBOT_RPM_INFOS_HISTORY A"
						+ " WHERE SN=?"
						+ " AND POSITION=?"
						+ " AND A.CREATE_DATE>=sysdate-(10/1440)"
						+ "  order by to_char(a.create_date,'hh24:mi') asc";
			//測試用，有填入固定日期to_date('2016/8/24 17:22:16','yyyy/mm/dd hh24:mi:ss')
			else
				sSQL="SELECT DATE_FORMAT(A.CREATE_DATE,'%H:%i'),A.AVG_RPM,A.MAX_RPM,A.MIN_RPM"
						+ " FROM foxlinkrobot.ROBOT_RPM_INFOS_HISTORY A"
						+ " WHERE SN=?"
						+ " AND POSITION=?"
						+ " AND A.CREATE_DATE>=now() - interval 30 minute "
						+ "  order by DATE_FORMAT(A.CREATE_DATE,'%H:%i') asc";
		}
		else{
			sSQL="select update_date,avg_rpm,max_rpm,min_rpm"
					+ " from foxlinkrobot.robot_rpm_infos"
					+ " where sn=?"
					+ " and position=?";
		}
		
		try{
			super.getDBConnection();
			RobotRPM robotRPM=(RobotRPM)obj;
			pstmt=Conn.prepareStatement(sSQL);
			pstmt.setString(1, robotRPM.getSN());
			pstmt.setString(2, robotRPM.getPosition());
			rs=pstmt.executeQuery();
			while(rs.next()){
				if(isShowHistory){
					JsonObject RPM=new JsonObject();
					RPM.addProperty("TIME", rs.getString(1));
					RPM.addProperty("AVG", rs.getString(2));
					RPM.addProperty("MAX", rs.getString(3));
					RPM.addProperty("MIN", rs.getString(4));
					robotRPMInfos.add(RPM);
				}
				else{
					robotRPMInfo.addProperty("TIME", rs.getString(1));
					robotRPMInfo.addProperty("AVG", rs.getString(2));
					robotRPMInfo.addProperty("MAX", rs.getString(3));
					robotRPMInfo.addProperty("MIN", rs.getString(4));
					robotRPMInfos.add(robotRPMInfo);
				}
			}
			rs.close();
			pstmt.close();
		}
		catch(Exception ex){
			logger.error("",ex);
			
		}
		finally{
			if(!Conn.isClosed())
				Conn.close();
		}
		
		return robotRPMInfos.toString();
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
