package com.foxlink.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.model.Account;

/*
 * 處理登入驗證及新增使用者的DAO
 * */
public class AccountDAO extends DAO implements INormalDAO {
	private static Logger logger=Logger.getLogger(AccountDAO.class);
	private boolean isOracleDB=false;
	public AccountDAO(String dbURL, String dbUser, String dbPassword,boolean isOracleDB) {
		super(dbURL, dbUser, dbPassword,isOracleDB);
		// TODO Auto-generated constructor stub
		this.isOracleDB=isOracleDB;
	}
	
	@Override
	public Object SetNewObject(Object obj, boolean isBatch, String updateUser) throws Exception {
		// TODO Auto-generated method stub
		PreparedStatement pstmt=null;
		int effectRows=-1;
		int [] effectRowsOfBatch = null;
		String sSQL="INSERT INTO foxlinkrobot.ROBOT_ACCOUNT (EMP_ID,EMP_NAME,EMP_DEPT_ID,ACCOUNT_PRIORITY,ACCOUNT_ENABLE,UPDATE_USER,EMP_PASSWORD)"
				+ " VALUES(?,?,?,?,?,?,?)";
		try{
			super.getDBConnection();
			Conn.setAutoCommit(false);
			pstmt=Conn.prepareStatement(sSQL);
			if(isBatch){
				@SuppressWarnings("unchecked")
				List<Account> accounts=(ArrayList<Account>)obj;
				Iterator<Account> iterator=accounts.iterator();
				while(iterator.hasNext()){
					Account newAccount=iterator.next();
					pstmt.setString(1, newAccount.getEmpID());
					pstmt.setString(2, newAccount.getEmpName());
					pstmt.setString(3, newAccount.getEmpDeptID());
					pstmt.setInt(4, newAccount.getAccountPriority());
					pstmt.setInt(5, newAccount.getAccountPriority());
					pstmt.setString(6, updateUser);
					pstmt.setString(7, newAccount.getEmpPassword());
					pstmt.addBatch();
				}
				effectRowsOfBatch=pstmt.executeBatch();
			}
			else{
				Account newAccount=(Account)obj;
				pstmt.setString(1, newAccount.getEmpID());
				pstmt.setString(2, newAccount.getEmpName());
				pstmt.setString(3, newAccount.getEmpDeptID());
				pstmt.setInt(4, newAccount.getAccountPriority());
				pstmt.setInt(5, newAccount.getAccountPriority());
				pstmt.setString(6, updateUser);
				pstmt.setString(7, newAccount.getEmpPassword());
				effectRows=pstmt.executeUpdate();
			}
			Conn.commit();
			pstmt.close();
		}
		catch(Exception ex){
			Conn.rollback();
			logger.error("",ex);
		}
		finally{
			if(!Conn.isClosed())
				Conn.close();
		}
		if(isBatch)
			return effectRowsOfBatch;
		else
			return effectRows;
	}

	@Override
	public Object GetObject(Object obj) throws Exception {
		// TODO Auto-generated method stub
		int userPriority=-1;
		PreparedStatement pstmt=null;
		String sSQL="select account_priority "
				+ " from foxlinkrobot.robot_account "
				+ " where emp_id=? "
				+ "	and emp_password=? "
				+ "	and account_enable=1";
		ResultSet rs=null;
		try{
			super.getDBConnection();
			Account account=(Account)obj;
			pstmt=Conn.prepareStatement(sSQL);
			pstmt.setString(1, account.getEmpID());
			pstmt.setString(2, account.getEmpPassword());
			rs=pstmt.executeQuery();
			int findRows=rs.getRow();
			if(findRows>0){
				userPriority=rs.getInt(1);
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
		return userPriority;
	}

	@Override
	public boolean UpdateObject(Object obj, String updateUser) throws Exception {
		// TODO Auto-generated method stub
		boolean isUpdate=false;
		PreparedStatement pstmt=null;
		int effectRows=-1;
		String sSQL="UPDATE foxlinkrobot.ROBOT_ACCOUNT SET EMP_NAME=?,EMP_DEPT_ID=?,ACCOUNT_PRIORITY=?"
				+ " WHERE EMP_ID=?";
		try{
			Account account=(Account)obj;
			super.getDBConnection();
			pstmt=Conn.prepareStatement(sSQL);
			pstmt.setString(1, account.getEmpName());
			pstmt.setString(2, account.getEmpDeptID());
			pstmt.setInt(3, account.getAccountPriority());
			pstmt.setString(4, account.getEmpID());
			effectRows=pstmt.executeUpdate();
			if(effectRows==1)
				isUpdate=true;
			pstmt.close();
		}
		catch(Exception ex){
			logger.error("",ex);
		}
		finally{
			if(!Conn.isClosed())
				Conn.close();
		}
		return isUpdate;
	}

	@Override
	public Object DeleteObject(Object obj, boolean isBatch, String updateUser) throws Exception {
		// TODO Auto-generated method stub
		PreparedStatement pstmt=null;
		String sSQL="UPDATE foxlinkrobot.ROBOT_ACCOUNT SET ACCOUNT_ENABLE=0 WHERE EMP_ID=?";
		int effectRows=-1;
		int[] deleteRows=null;
		try{
			super.getDBConnection();
			Conn.setAutoCommit(false);
			pstmt=Conn.prepareStatement(sSQL);
			if(isBatch){
				String[] deleteEmpIDs=(String[])obj;
				for(int i=0;i<deleteEmpIDs.length;i++){
					pstmt.setString(1, deleteEmpIDs[i]);
					pstmt.addBatch();
				}
				deleteRows=pstmt.executeBatch();
			}
			else{
				String deleteEmpID=(String)obj;
				pstmt.setString(1, deleteEmpID);
				effectRows=pstmt.executeUpdate();
			}
			pstmt.close();
			Conn.commit();
		}
		catch(Exception ex){
			logger.error("",ex);
		}
		finally{
			if(!Conn.isClosed())
				Conn.close();
		}
		
		if(isBatch)
			return deleteRows;
		else
			return effectRows;
	}

	@Override
	public Object GetObject(Object obj, boolean isShowHistory) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
