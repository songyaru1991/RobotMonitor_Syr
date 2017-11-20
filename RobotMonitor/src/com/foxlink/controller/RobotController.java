package com.foxlink.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import com.foxlink.model.RobotMaster;
import com.foxlink.service.ImplRobotService;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/Maintain")
public class RobotController implements ServletContextAware {
	private String dbURL,dbUser,dbPassword;
	private boolean isOracleDB=false;
	private ImplRobotService robotService;
	private static Logger logger=Logger.getLogger(RobotMonitorController.class);
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.dbURL=servletContext.getInitParameter("dbURL");
		this.dbUser=servletContext.getInitParameter("dbUser");
		this.dbPassword=servletContext.getInitParameter("dbPassword");
		if(servletContext.getInitParameter("isOracleDB").equals("1"))
			this.isOracleDB=true;
		else
			this.isOracleDB=false;
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String ShowMainPage(){
		return "RobotMaintain";
	}
	
	@RequestMapping(value="/Robot.show",method=RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String ShowRobotMaterInfos(@RequestParam("currentPage")int currentPage,
			@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam){
		String results=null;
		int offset=0,numberOfRecords=10;
		robotService=new ImplRobotService(dbURL,dbUser,dbPassword,isOracleDB);
		try{
			offset=(currentPage-1)*numberOfRecords;
			results=(String)robotService.ShowRobotList(offset, currentPage, numberOfRecords, queryCritirea, queryParam);
		}
		catch(Exception ex){
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得機械手臂基本檔發生錯誤，原因："+ex.toString());
			results=exception.toString();
		}
		return results;
	}
	
	@RequestMapping(value="/UpdateRobot.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody
	public  String UpdateRobotMasterInfos(HttpSession session,@RequestBody RobotMaster robotMaster){
		JsonObject UpdateResult=new JsonObject();
		robotService=new ImplRobotService(dbURL,dbUser,dbPassword,isOracleDB);
		try{
			String updateUser=(String) session.getAttribute("USERID");
			if(robotService.UpdateRobotInfos(robotMaster, updateUser)){
				UpdateResult.addProperty("StatusCode", "200");
				UpdateResult.addProperty("Message", "更新機械手臂資料成功");
			}
			else{
				UpdateResult.addProperty("StatusCode", "200");
				UpdateResult.addProperty("Message", "更新機械手臂資料失敗");
			}
		}
		catch(Exception ex){
			logger.error("Updating the robot info is failed, due to: ",ex);
			UpdateResult.addProperty("StatusCode", "200");
			UpdateResult.addProperty("Message", "更新機械手臂資料發生錯誤，原因："+ex.toString());
		}
		return UpdateResult.toString();
	}
	
	@RequestMapping(value="/DisableRobot.do",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DisableRobotMasterInfos(HttpSession session,@RequestParam("SN")String SN){
		JsonObject DisableResult=new JsonObject();
		robotService=new ImplRobotService(dbURL,dbUser,dbPassword,isOracleDB);
		try{
			String updateUser=(String) session.getAttribute("username");
			if(robotService.DisableRobotInfos(SN, updateUser)){
				DisableResult.addProperty("StatusCode", "200");
				DisableResult.addProperty("Message", "機械手臂基本主檔已失效");
			}
			else{
				DisableResult.addProperty("StatusCode", "500");
				DisableResult.addProperty("Message", "失效機械手臂基本主檔發生錯誤");
			}
		}
		catch(Exception ex){
			logger.error("",ex);
			DisableResult.addProperty("StatusCode", "500");
			DisableResult.addProperty("Message", "失效機械手臂基本主檔發生錯誤，原因:"+ex.toString());
		}		
		return null;
	}
	
	@RequestMapping(value="/AddRobot.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String SaveRobotMasterInfos(HttpSession session,@RequestBody RobotMaster robotMaster){    
		JsonObject AddResult=new JsonObject();
		robotService=new ImplRobotService(dbURL,dbUser,dbPassword,isOracleDB);
		try{
			String updateUser=(String) session.getAttribute("username");
			if(robotService.SaveRobotInfos(robotMaster, updateUser)){
				AddResult.addProperty("StatusCode", "200");
				AddResult.addProperty("Message", "建立機械手臂基本資料成功");
			}
			else{
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "建立機械手臂基本資料失敗");
			}
		}
		catch(Exception ex){
			logger.error("Adding the new robot info is failed, due to: ",ex);
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "建立機械手臂基本資料發生錯誤，原因："+ex.toString());
		}
		return AddResult.toString();
	}
	
}
