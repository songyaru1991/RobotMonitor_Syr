package com.foxlink.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
	private Connection Conn;
	public DBConnectionManager(String dbURL,String dbUser,String dbPassword,boolean isOracleDB) throws ClassNotFoundException, SQLException{
		if(isOracleDB)
			Class.forName("oracle.jdbc.OracleDriver");
		else
			Class.forName("com.mysql.jdbc.Driver");
		this.Conn=DriverManager.getConnection(dbURL,dbUser,dbPassword);
	}
	public Connection getConnection(){
		return this.Conn;
	}
}
