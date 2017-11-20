package com.foxlink.dao;

import java.sql.Connection;
import com.foxlink.utils.DBConnectionManager;

public abstract class DAO {
	public Connection Conn=null;
	private String dbURL,dbUser,dbPassword;
	private boolean isOracleDB;
	
	public DAO(String dbURL,String dbUser,String dbPassword,boolean isOracleDB){
		this.dbURL=dbURL;
		this.dbUser=dbUser;
		this.dbPassword=dbPassword;
		this.isOracleDB=isOracleDB;
	}
	
	public void getDBConnection()throws Exception{
		DBConnectionManager dbConnectionManager=new DBConnectionManager(dbURL, dbUser, dbPassword,isOracleDB);
		Conn=dbConnectionManager.getConnection();
	}
}
