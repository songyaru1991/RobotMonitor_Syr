package com.foxlink.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import com.google.gson.JsonObject;
import com.foxlink.model.RobotLoad;
import com.foxlink.model.RobotMaster;
import com.foxlink.model.RobotRPM;
import com.foxlink.model.RobotState;
import com.foxlink.model.RobotTemp;
import com.foxlink.model.RobotTorque;
import com.foxlink.service.ImplRobotService;

@Controller
@RequestMapping("/Monitor")
public class RobotMonitorController implements ServletContextAware {
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
	public String showMainPage(){
		return "StatusMonitor";
	}
	
	
	@RequestMapping(value="/SevenAxis",method=RequestMethod.GET)
	public String ShowAxisPage(){
		return "StatusMonitorSevenAxis";
	}
		
	/*取得機械手臂轉矩資訊*/
	@RequestMapping(value="/Torque.show",method=RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String FindRobotTorqueInfos(HttpSession session,@RequestParam(value="RobotSN")String SN,
			@RequestParam(value="Position")String position,@RequestParam(value="isHistory")boolean isHistory){
		robotService=new ImplRobotService(dbURL,dbUser,dbPassword,isOracleDB);
		String results=null;
		RobotTorque robotTorque=new RobotTorque();
		robotTorque.setSN(SN);
		robotTorque.setPosition(position);
		try{
			results=(String)robotService.FindTorqueInfos(robotTorque, isHistory);
		}
		catch(Exception ex){
			logger.error(ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得轉矩資訊發生錯誤，原因："+ex.toString());
			results=exception.toString();
		}
		return results;
	}
	
	/*取得機械手臂負載資訊*/
	@RequestMapping(value="/Load.show",method=RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String FindRobotLoadInfos(HttpSession session,@RequestParam(value="RobotSN")String SN,
			@RequestParam(value="Position")String position,@RequestParam(value="isHistory")boolean isHistory){
		robotService=new ImplRobotService(dbURL,dbUser,dbPassword,isOracleDB);
		String results=null;
		RobotLoad robotLoad=new RobotLoad();
		robotLoad.setSN(SN);
		robotLoad.setPosition(position);
		try{
			results=(String)robotService.FindLoadInfos(robotLoad, isHistory);
		}
		catch(Exception ex){
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得負載資訊發生錯誤，原因："+ex.toString());
			results=exception.toString();
		}
		return results;
	}
	
	/*取得機械手臂轉速資訊*/
	@RequestMapping(value="/RPM.show",method=RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String FindRobotRPMInfos(HttpSession session,@RequestParam(value="RobotSN")String SN,
			@RequestParam(value="Position")String position,@RequestParam(value="isHistory")boolean isHistory){
		robotService=new ImplRobotService(dbURL,dbUser,dbPassword,isOracleDB);
		String results=null;
		RobotRPM robotRPM=new RobotRPM();
		robotRPM.setSN(SN);
		robotRPM.setPosition(position);
		
		try{
			results=(String)robotService.FindRPMInfos(robotRPM, isHistory);
		}
		catch(Exception ex){
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得轉速資訊發生錯誤，原因："+ex.toString());
			results=exception.toString();
		}
		return results;
	}
	
	/*取得機械手臂狀態資訊*/
	@RequestMapping(value="/RobotState.show",method=RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String FindRobotStateInfo(HttpSession session,@RequestParam(value="RobotSN")String SN,
			@RequestParam(value="isHistory")boolean isHistory){
		robotService=new ImplRobotService(dbURL,dbUser,dbPassword,isOracleDB);
		String results=null;
		RobotState robotState=new RobotState();
		robotState.setSN(SN);
		try{
			results=(String)robotService.FindRobotState(robotState, isHistory);
		}
		catch(Exception ex){
			logger.error(ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得手臂狀態資訊發生錯誤，原因："+ex.toString());
			results=exception.toString();
		}
		return results;
	}
	
	/*取得機械手臂電控箱溫度*/
	@RequestMapping(value="/RobotTempature.show",method=RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String FindRobotTempatureInfos(HttpSession session,@RequestParam(value="RobotSN")String SN,
			@RequestParam(value="isHistory")boolean isHistory)
	{
		robotService=new ImplRobotService(dbURL,dbUser,dbPassword,isOracleDB);
		String results=null;
		RobotTemp robotTemp=new RobotTemp();
		robotTemp.setSN(SN);
		try{
			results=(String)robotService.FindTempatureInfos(robotTemp, isHistory);
		}
		catch(Exception ex){
			logger.error(ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得機械手臂電控箱溫度，原因："+ex.toString());
			results=exception.toString();
		}
		return results;
	}
	
	/*取得機械手臂基本檔*/
	@RequestMapping(value="/RobotMaster.show",method=RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String FindRobotMasterInfos(HttpSession session,@RequestParam(value="RobotSN")String SN,
			@RequestParam(value="isShowStateAndDetail")String isShowStateAndDetail){
		String results=null;
		robotService=new ImplRobotService(dbURL,dbUser,dbPassword,isOracleDB);
		RobotMaster robotMaster=new RobotMaster();
		robotMaster.setSN(SN);
		try{
			
			if(isShowStateAndDetail.equals("true"))
				results=(String)robotService.FindRobotStateAndDetail(SN);
			else
				results=(String)robotService.FindRobotMasterInfos(robotMaster);
		}
		catch(Exception ex){
			logger.error(ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得機械手臂基本檔發生錯誤，原因："+ex.toString());
			results=exception.toString();
		}
		return results;
	}
	
	@RequestMapping(value="/RobotList.show",method=RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String FindAllRobots(HttpSession session){
		String results="";
		robotService=new ImplRobotService(dbURL,dbUser,dbPassword,isOracleDB);

		try{
			results=(String)robotService.FindRobotList();
		}
		catch(Exception ex){
			logger.error(ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得機械手臂列表，原因："+ex.toString());
			results=exception.toString();
		}
		return results;
	}
}
