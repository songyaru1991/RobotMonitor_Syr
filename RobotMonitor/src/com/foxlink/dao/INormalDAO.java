package com.foxlink.dao;

public interface INormalDAO {
	
	public Object SetNewObject(Object obj,boolean isBatch,String updateUser)throws Exception;
	
	public Object GetObject(Object obj)throws Exception;
	
	public Object GetObject()throws Exception;
	
	public Object GetObject(Object obj,boolean isShowHistory)throws Exception;
	
	public Object GetObject(int offset,int numOfRecords,int currentPage,String queryCritirea,String queryParam)throws Exception;

	public boolean UpdateObject(Object obj,String updateUser)throws Exception;
	
	public Object DeleteObject(Object obj,boolean isBatch,String updateUser)throws Exception;

	public Object GetRobotStateAndDetailInfos(String robotSN)throws Exception;
}
