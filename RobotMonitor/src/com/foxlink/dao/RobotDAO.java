package com.foxlink.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.foxlink.model.*;

public class RobotDAO extends DAO implements INormalDAO {
	private static Logger logger=Logger.getLogger(RobotDAO.class);
	private boolean isOracleDB=false;
	public RobotDAO(String dbURL, String dbUser, String dbPassword,boolean isOracleDB) {
		super(dbURL, dbUser, dbPassword,isOracleDB);
		// TODO Auto-generated constructor stub
		this.isOracleDB=isOracleDB;
	}
	
	/*建立機械手臂主檔*/
	@Override
	public Object SetNewObject(Object obj, boolean isBatch, String updateUser) throws Exception {
		// TODO Auto-generated method stub
		PreparedStatement pstmt=null;
		String sSQL="INSERT foxlinkrobot.INTO ROBOT_MASTER_INFOS(SN,FACTORY,WORK_SHOP,MODEL_NO,LINE_NO,UPDATE_USER)"
				+ " VALUES(?,?,?,?,?,?)";
		int createRow=-1;
		int[] createRows=null;
		try{
			super.getDBConnection();
			Conn.setAutoCommit(false);
			pstmt=Conn.prepareStatement(sSQL);
			if(isBatch){
				@SuppressWarnings("unchecked")
				List<RobotMaster> robotMasters=(ArrayList<RobotMaster>)obj;
				Iterator<RobotMaster> iterator=robotMasters.iterator();
				while(iterator.hasNext()){
					RobotMaster robotMaster=iterator.next();
					pstmt.setString(1, robotMaster.getSN());
					pstmt.setString(2, robotMaster.getFactoryCode());
					pstmt.setString(3, robotMaster.getWorkShop());
					pstmt.setString(4, robotMaster.getModelNO());
					pstmt.setString(5, robotMaster.getLineNO());
					pstmt.setString(6, updateUser);
					pstmt.addBatch();
				}
				createRows=pstmt.executeBatch();
			}
			else{
				RobotMaster robotMaster=(RobotMaster)obj;
				pstmt.setString(1, robotMaster.getSN());
				pstmt.setString(2, robotMaster.getFactoryCode());
				pstmt.setString(3, robotMaster.getWorkShop());
				pstmt.setString(4, robotMaster.getModelNO());
				pstmt.setString(5, robotMaster.getLineNO());
				pstmt.setString(6, updateUser);
				createRow=pstmt.executeUpdate();
			}
			pstmt.close();
			Conn.commit();
		}
		catch(Exception ex){
			Conn.rollback();
			logger.error("Creating the new robot master info is failed, due to: ",ex);
		}
		finally{
			if(!Conn.isClosed())
				Conn.close();
		}
		if(isBatch)
			return createRows;
		else
			return createRow;
	}
	
	public Object GetRobotStateAndDetailInfos(String SN)throws Exception{
		String sSQL="SELECT a.FACTORY,A.LINE_NO,A.MODEL_NO,A.WORK_SHOP,C.ERROR_MESSAGE,D.AVG_TEMP "+
				" FROM foxlinkrobot.robot_master_infos a "+
				" JOIN FOXLINKROBOT.ROBOT_STATE_INFOS B ON A.SN = B.SN "+
				" JOIN FOXLINKROBOT.ROBOT_ERROR_CODE C ON B.ERROR_CODE = C.ERROR_CODE "+
				" JOIN FOXLINKROBOT.ROBOT_CONTROL_TEMP D ON A.SN = D.SN "+
				" WHERE A.SN = ? ";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		JsonObject robotStateAndDetail=new JsonObject();
		try{
			super.getDBConnection();
			pstmt=Conn.prepareStatement(sSQL);
			pstmt.setString(1, SN);
			rs=pstmt.executeQuery();
			while(rs.next()){
				robotStateAndDetail.addProperty("FACTORY", rs.getString("FACTORY"));
				robotStateAndDetail.addProperty("LINE_NO", rs.getString("LINE_NO"));
				robotStateAndDetail.addProperty("MODEL_NO", rs.getString("MODEL_NO"));
				robotStateAndDetail.addProperty("WORK_SHOP", rs.getString("WORK_SHOP"));
				robotStateAndDetail.addProperty("ERROR_MESSAGE", rs.getString("ERROR_MESSAGE"));
				robotStateAndDetail.addProperty("TEMP", rs.getString("AVG_TEMP"));
			}
			rs.close();
			pstmt.close();
		}
		catch(Exception ex){
			logger.error("Getting the robot's state and detail info is failed, due to: ",ex);
			robotStateAndDetail.addProperty("QueryFailed", "Getting the robot's state and detail info is failed, due to: "+ex.toString());
		}
		finally{
			if(!Conn.isClosed())
				Conn.close();
		}
		return robotStateAndDetail.toString();
	}

	@Override
	public Object GetObject(Object obj) throws Exception {
		// TODO Auto-generated method stub
		String sSQL="SELECT * "
				+ " FROM foxlinkrobot.ROBOT_MASTER_INFOS "
				+ " WHERE SN=?"
				+ "   and is_enable=1";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		JsonObject robotMasterInfo=new JsonObject();
		try{
			RobotMaster robotMaster=(RobotMaster)obj;
			super.getDBConnection();
			pstmt=Conn.prepareStatement(sSQL);
			pstmt.setString(1, robotMaster.getSN());
			rs=pstmt.executeQuery();
			while(rs.next()){
				robotMasterInfo.addProperty("SN", rs.getString("SN"));
				robotMasterInfo.addProperty("FACTORY", rs.getString("FACTORY"));
				robotMasterInfo.addProperty("MODELNO", rs.getString("MODEL_NO"));
				robotMasterInfo.addProperty("WORKSHOP", rs.getString("WORK_SHOP"));
				robotMasterInfo.addProperty("LINENO", rs.getString("LINE_NO"));
			}
			rs.close();
			pstmt.close();
		}
		catch(Exception ex){
			logger.error("Getting the robot's master info is failed, due to: ",ex);
			robotMasterInfo.addProperty("QueryFailed", "Getting the robot's master info is failed, due to: "+ex.toString());
		}
		finally{
			if(!Conn.isClosed())
				Conn.close();
		}
		return robotMasterInfo.toString();
	}
	
	/*取得機械手臂列表*/
	public Object GetObject()throws Exception{
		String sSQL="SELECT SN FROM foxlinkrobot.ROBOT_MASTER_INFOS WHERE is_enable=1";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		JsonArray robotSNs=new JsonArray();
		try{
			super.getDBConnection();
			pstmt=Conn.prepareStatement(sSQL);
			rs=pstmt.executeQuery();
			while(rs.next()){
				JsonObject RobotSN=new JsonObject();
				RobotSN.addProperty("SN", rs.getString(1));
				robotSNs.add(RobotSN);
			}
			rs.close();
			pstmt.close();
		}
		catch(Exception ex){
			logger.error("Getting avaliable robot sn is failed, due to: ",ex);
		}
		finally{
			if(!Conn.isClosed())
				Conn.close();
		}
		return robotSNs.toString();
	}
	
	
	
	/* *
	 * 修改機械手臂基本資料
	 * */
	@Override
	public boolean UpdateObject(Object obj, String updateUser) throws Exception {
		// TODO Auto-generated method stub
		String sSQL="";
		
		if(isOracleDB)
			sSQL="UPDATE foxlinkrobot.ROBOT_MASTER_INFOS "
				+ "SET FACTORY=?,WORK_SHOP=?,MODEL_NO=?,LINE_NO=?,UPDATE_DATE=sysdate"
				+ " WHERE SN=?";
		else
			sSQL="UPDATE foxlinkrobot.ROBOT_MASTER_INFOS "
			+ "SET FACTORY=?,WORK_SHOP=?,MODEL_NO=?,LINE_NO=?,UPDATE_DATE=now()"
			+ " WHERE SN=?";
		PreparedStatement pstmt=null;
		int effectRows=-1;
		boolean isUpdate=false;
		try{
			super.getDBConnection();
			Conn.setAutoCommit(false);
			pstmt=Conn.prepareStatement(sSQL);
			RobotMaster masterInfo=(RobotMaster)obj;
			pstmt.setString(1, masterInfo.getFactoryCode());
			pstmt.setString(2, masterInfo.getWorkShop());
			pstmt.setString(3, masterInfo.getModelNO());
			pstmt.setString(4, masterInfo.getLineNO());
			pstmt.setString(5, masterInfo.getSN());
			effectRows=pstmt.executeUpdate();
			if(effectRows==1)
				isUpdate=true;
			
			pstmt.close();
			Conn.commit();
		}
		catch(Exception ex){
			logger.error("",ex);
			Conn.rollback();
		}
		finally{
			if(!Conn.isClosed())
				Conn.close();
		}
		return isUpdate;
	}
	
	/* *
	 * 將機械手臂基本資料失效(非刪除)
	 * */
	@Override
	public Object DeleteObject(Object obj, boolean isBatch, String updateUser) throws Exception {
		// TODO Auto-generated method stub
		String sSQL="UPDATE foxlinkrobot.ROBOT_MASTER_INFOS SET IS_ENABLE=0"
				+ " WHERE SN=?";
		PreparedStatement pstmt=null;
		int disableRow=-1;
		int[] disableRows=null;
		
		try{
			super.getDBConnection();
			Conn.setAutoCommit(false);
			pstmt=Conn.prepareStatement(sSQL);
			if(isBatch){
				String[] disableSNs=(String[])obj;
				for(int i=0;i<disableSNs.length;i++){
					pstmt.setString(1, disableSNs[i]);
					pstmt.addBatch();      
				}
				disableRows=pstmt.executeBatch();
			}
			else{
				String disableSN=(String)obj;
				pstmt.setString(1, disableSN);
				disableRow=pstmt.executeUpdate();
			}
			Conn.commit();
			pstmt.close();
		}
		catch(Exception ex){
			logger.error("",ex);
			Conn.rollback();
		}
		finally{
			if(Conn.isClosed())
				Conn.close();
		}
		
		if(isBatch)
			return disableRows;
		else
			return disableRow;
	}

	@Override
	public Object GetObject(Object obj, boolean isShowHistory) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public Object GetObject(int offset,int numOfRecords,int currentPage,String queryCritirea,String queryParam)throws Exception{
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		JsonArray RobotList=new JsonArray();
		String sSQL="";
		
		if(isOracleDB){
			sSQL="SELECT * FROM (SELECT A.*,ROWNUM AS RNUM,COUNT(*) OVER() FOUND_ROWS"
					+ " FROM (SELECT A.SN,A.FACTORY,A.WORK_SHOP,A.MODEL_NO,A.LINE_NO"
					+ " 	  FROM ROBOT_MASTER_INFOS A"
					+ "		  WHERE IS_ENABLE=1 ";
			if(queryCritirea.equals("FACTORY")){
				sSQL+=" and FACTORY=?";
			}
			else if(queryCritirea.equals("MODELNO")){
				sSQL+=" and MODEL_NO like ?";
			}
			else if(queryCritirea.equals("LINENO")){
				sSQL+=" and LINE_NO LIKE ?";
			}
			else{
				sSQL+="";
			}
			sSQL+=") A )  WHERE RNUM>"+offset+" AND RNUM<="+numOfRecords+"";
		}
		else{
			sSQL=" SELECT A.SN,A.FACTORY,A.WORK_SHOP,A.MODEL_NO,A.LINE_NO,(select count(*) from foxlinkrobot.ROBOT_MASTER_INFOS)"
					+ " FROM foxlinkrobot.ROBOT_MASTER_INFOS A"
					+ "	WHERE IS_ENABLE=1 ";
			if(queryCritirea.equals("FACTORY")){
				sSQL+=" and FACTORY=?";
			}
			else if(queryCritirea.equals("MODELNO")){
				sSQL+=" and MODEL_NO like ?";
			}
			else if(queryCritirea.equals("LINENO")){
				sSQL+=" and LINE_NO LIKE ?";
			}
			else{
				sSQL+="";
			}
			sSQL+=" limit "+offset+","+numOfRecords;
		}
		
		try{
			super.getDBConnection();
			pstmt=Conn.prepareStatement(sSQL);
			if(queryCritirea.equals("FACTORY"))
				pstmt.setString(1, queryParam);
			if(queryCritirea.equals("MODELNO") || queryCritirea.equals("LINENO"))
				pstmt.setString(1, '%'+queryParam+'%');
			rs=pstmt.executeQuery();
			while(rs.next()){
				JsonObject robotInfo=new JsonObject();
				robotInfo.addProperty("SN", rs.getString("SN"));
				robotInfo.addProperty("FACTORY", rs.getString("FACTORY"));
				robotInfo.addProperty("MODELNO", rs.getString("MODEL_NO"));
				robotInfo.addProperty("WORKSHOP", rs.getString("WORK_SHOP"));
				robotInfo.addProperty("LINENO", rs.getString("LINE_NO"));
				robotInfo.addProperty("CurrentPage", currentPage);
				robotInfo.addProperty("NumOfPages", Math.ceil(rs.getInt(6)*1.0/10));
				RobotList.add(robotInfo);
			}
			rs.close();
			pstmt.close();
		}
		catch(Exception ex){
			logger.error("Finding the robot info by search critirea is failed, due to: ",ex);
			JsonObject error=new JsonObject();
			error.addProperty("StatusCode", "500");
			error.addProperty("Message", ex.toString());
			RobotList.add(error);
		}
		finally{
			if(!Conn.isClosed())
				Conn.close();
		}
		return RobotList.toString();
	}
	
}
