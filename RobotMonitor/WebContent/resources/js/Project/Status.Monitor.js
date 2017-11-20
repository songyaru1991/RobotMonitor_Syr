/**
 * 
 */
$(document).ready(function(){
	var RobotSN='',Position='',URL='',SelectedSN='',SelectedPosition='J1',SelectedMonitorInfo='電控箱溫度';
	init();
	
	//設定每分鐘更新一次畫面
	setInterval(function(){
		//處理DIV更新
		//更新機械手臂基本主檔
		GetRobotRawDatas(RobotSN,"",false,"../Monitor/RobotMaster.show?RobotSN="+RobotSN+"&isShowStateAndDetail=false",$('#robotMasterTable'),false,"RobotMaster");
		GetRobotRawDatas(RobotSN,"",false,"../Monitor/RobotState.show?RobotSN="+RobotSN+"&isHistory=false",$('#robotStatusTable'),false,"RobotState");
		//更新監測項目
		RefreshView();	
	},60000);
	
	$('.robotListOption').click(function(){
		RobotSN=$(this).text();
	});
	
	$('.robotPositionOption').click(function(){
		SelectedPosition=$(this).text();
		//顯示該Position目前的資訊
		$('#robotImg').attr('src','../resources/images/Robots/'+SelectedPosition+'.png');
		RefreshView();
	});
	
	$('.robotMonitorOption').click(function(){
		SelectedMonitorInfo=$(this).text();
		RefreshView();
	});
	
	function init(callback){
		//先取得機械手臂列表資料
		ShowRobotList();
	}
	
	function RefreshView(){
		if(SelectedMonitorInfo=="電控箱溫度"){
			GetRobotRawDatas(RobotSN,SelectedPosition,false,"../Monitor/RobotTempature.show?RobotSN="+RobotSN+"&isHistory=false",
					$('#robotCurrentDataTable'),false,"RobotTempature");
			GetRobotRawDatas(RobotSN,SelectedPosition,false,"../Monitor/RobotTempature.show?RobotSN="+RobotSN+"&isHistory=true",
					$('#robotHistoryDataTable'),true,"RobotTempature");
		}
		else if(SelectedMonitorInfo=="機械手臂負載"){
			GetRobotRawDatas(RobotSN,SelectedPosition,false,"../Monitor/Load.show?RobotSN="+RobotSN+"&Position="+SelectedPosition+"&isHistory=false",
					$('#robotCurrentDataTable'),false,"RobotLoad");
			GetRobotRawDatas(RobotSN,SelectedPosition,false,"../Monitor/Load.show?RobotSN="+RobotSN+"&Position="+SelectedPosition+"&isHistory=true",
					$('#robotHistoryDataTable'),true,"RobotLoad");
		}
		else if(SelectedMonitorInfo=="機械手臂轉速"){
			GetRobotRawDatas(RobotSN,SelectedPosition,false,"../Monitor/RPM.show?RobotSN="+RobotSN+"&Position="+SelectedPosition+"&isHistory=false",
					$('#robotCurrentDataTable'),false,"RobotRPM");
			GetRobotRawDatas(RobotSN,SelectedPosition,false,"../Monitor/RPM.show?RobotSN="+RobotSN+"&Position="+SelectedPosition+"&isHistory=true",
					$('#robotHistoryDataTable'),true,"RobotRPM");
		}
		else{
			//轉矩
			GetRobotRawDatas(RobotSN,SelectedPosition,false,"../Monitor/Torque.show?RobotSN="+RobotSN+"&Position="+SelectedPosition+"&isHistory=false",
					$('#robotCurrentDataTable'),false,"RobotTorque");
			GetRobotRawDatas(RobotSN,SelectedPosition,false,"../Monitor/Torque.show?RobotSN="+RobotSN+"&Position="+SelectedPosition+"&isHistory=true",
					$('#robotHistoryDataTable'),true,"RobotTorque");
		}
	}
		
	function ShowRobotList(){
		$.getJSON('../Monitor/RobotList.show',function(data){
			if(data.StatusCode!="500"){
				if(data.length>0){
					var ListAppend='';
					for(var i=0;i<data.length;i++){
						ListAppend+='<li role="presentation"><a role="menuitem" tabindex="-1" href="#" class="robotListOption">'+data[i]["SN"]+'</a></li>';
					}
					$('#RobotList').empty()
					$('#RobotList').append(ListAppend);
					$('.robotListOption').click(function(){
						//alert($(this).text());
						$('#robotNO').text($(this).text());
						RobotSN=$(this).text();
						//Show Robot Master Infos
						GetRobotRawDatas(RobotSN,"",false,"../Monitor/RobotMaster.show?RobotSN="+RobotSN,$('#robotMasterTable'),false,"RobotMaster");
						GetRobotRawDatas(RobotSN,"",false,"../Monitor/RobotState.show?RobotSN="+RobotSN+"&isHistory=false",$('#robotStatusTable'),false,"RobotState");
					});
				}
				else{
					alert("NO Data!!");
				}
			}
			else{
				alert(data.ErrorMessage);
			}
		});
	}
	
	
	function ShowTable(ReportType,RbotArray,xhr){
		
		if(ReportType!="RobotMaster" && ReportType!="RobotState"){
			$(xhr).find('thead').empty();
			var HeaderElements=ShowTableHeader(ReportType);
			$(xhr).find('thead').append(HeaderElements);
		}
		var BodyElements=ShowTableContents(RbotArray,ReportType);
		/*將資料匯入Table*/
		$(xhr).find('tbody').empty();
		$(xhr).find('tbody').append(BodyElements);
	}
	
	
	function GetRobotRawDatas(RobotSN,Position,isHistory,URL,xhr,isShowChart,ReportType){
		$.getJSON(URL,function(data){
			if(data.StatusCode == "500")
				alert(data.ErrorMessage);
			else{
				if(isShowChart==true){
					ShowTrendChart(RobotSN,data,ReportType);
				}
				ShowTable(ReportType,data,xhr);
			}
		});
	}
});