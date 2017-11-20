$(document).ready(function(){
	var RobotSN='',SelectedSN='',SelectedPosition='J1',SelectedMonitorInfo='電控箱溫度';
	init();
		
	//設定每30秒更新一次畫面
	setInterval(function(){
		//更新監測項目
		GetRobotStateAndDetailInfos("../Monitor/RobotMaster.show?RobotSN="+RobotSN+"&isShowStateAndDetail=true");
		RefreshView();	
	},30000);
	
	
	$('.robotMonitorOption').click(function(){
		SelectedMonitorInfo=$(this).text();
		GetRobotStateAndDetailInfos("../Monitor/RobotMaster.show?RobotSN="+RobotSN+"&isShowStateAndDetail=true");
		GetRobotRawDatas();
		RefreshView();
	});
	
	$('.gauge').click(function(){
		var currentPosition=$(this).attr("id");
		if(SelectedMonitorInfo=="機械手臂負載"){
			URL="../Monitor/Load.show?RobotSN="+RobotSN+"&Position="+currentPosition+"&isHistory=true";
			ReportType='RobotLoad';
			$('h3').text('近十分鐘 '+currentPosition+' 軸負載趨勢');
		}
		else if(SelectedMonitorInfo=="機械手臂轉速"){
			URL="../Monitor/RPM.show?RobotSN="+RobotSN+"&Position="+currentPosition+"&isHistory=true";
			ReportType='RobotRPM';
			$('h3').text('近十分鐘 '+currentPosition+' 軸轉速趨勢');
		}
		else{
			URL="../Monitor/Torque.show?RobotSN="+RobotSN+"&Position="+currentPosition+"&isHistory=true";
			ReportType='RobotTorque';
			$('h3').text('近十分鐘 '+currentPosition+' 軸轉矩趨勢');
		}
		$.getJSON(URL,function(data){
			if(data.StatusCode=="500")
				alert(data.ErroMessage);
			else{
				ShowTrendChart(RobotSN,data,ReportType);
				$('#ShowTrendChartDailog').modal('show');
			}
		});
	})
   
	function init(callback){
		ShowRobotList();
	}
	
	function RefreshView(){
		for(var i=1;i<7;i++){
			var Position='J'+i;
			var URL='';
			var ReportType='';
			if(SelectedMonitorInfo=="機械手臂負載"){
				URL="../Monitor/Load.show?RobotSN="+RobotSN+"&Position="+Position+"&isHistory=false";
				ReportType='RobotLoad';
			}
			else if(SelectedMonitorInfo=="機械手臂轉速"){
				URL="../Monitor/RPM.show?RobotSN="+RobotSN+"&Position="+Position+"&isHistory=false";
				ReportType='RobotRPM';
			}
			else{
				URL="../Monitor/Torque.show?RobotSN="+RobotSN+"&Position="+Position+"&isHistory=false";
				ReportType='RobotTorque';
			}
			GetRobotRawDatas(URL,Position,ReportType);
		}	
	}
	
	function GetRobotStateAndDetailInfos(URL){
		$.getJSON(URL,function(data){
			if(data.statusCode=="500")
				alert(data.ErrorMessage);
			else{
				$('#robotStatus').removeClass();
				$('#robotStatus').empty();
				if(data.ERROR_MESSAGE=='正常運行')
					$('#robotStatus').addClass("alert alert-success");
				else
					$('#robotStatus').addClass("alert alert-danger");
				$('#robotStatus').text(data.ERROR_MESSAGE);
				$('#robotTempature').text(data.TEMP+' °C');
			}
		});
	}
		
	function GetRobotRawDatas(URL,Position,ReportType){
		$.getJSON(URL,function(data){
			if(data.StatusCode=="500")
				alert(data.ErroMessage);
			else{
				var xhr=Position;
				var MonitorObject=new Object();
				MonitorObject.AVG=Number(data[0]["AVG"]).toFixed(2);
				MonitorObject.MAX=Number(data[0]["MAX"]).toFixed(2);
				MonitorObject.MIN=Number(data[0]["MIN"]).toFixed(2);
				ShowChart(xhr,Position,MonitorObject,ReportType);
			}
		});
	}
	
	//畫Gauge Chart
	function ShowChart(xhr,CurrentPosition,MonitorObject,ReportType){
		var typeTitle='',ValueSuffix='';
		var MinValue=0,MaxValue=0;
		if(ReportType=='RobotLoad'){
			typeTitle='軸當前負載量';
			ValueSuffix='Percentage(%)';
			MinValue=0;
			MaxValue=100;
		}
		else if(ReportType=="RobotRPM"){
			typeTitle='軸當前轉速';
			ValueSuffix='Revolutions Per Minute(RPM)';
			MinValue=-6000;
			MaxValue=6000;
		}
		else{
			typeTitle='軸當前轉矩';
			ValueSuffix='Degree(°)';
			MinValue=-3000;
			MaxValue=3000;
		}
		
		var TitleText=CurrentPosition+typeTitle;
		
		var chart=new Highcharts.Chart({
			chart: {
	            renderTo: xhr,
	            type: 'solidgauge',
	        },
	        title: {
	            text: TitleText
	        },
	        pane: {
	            center: ['50%', '50%'],
	            size: '70%',
	            startAngle: -90,
	            endAngle: 90,
	            background: {
	                backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
	                innerRadius: '60%',
	                outerRadius: '100%',
	                shape: 'arc'
	            }
	        },

	        tooltip: {
	            enabled: false
	        },
	        // the value axis
	        yAxis: {
	        	min:MinValue,
				max:MaxValue,
				title:{
					text:CurrentPosition+typeTitle
				},
	        	stops: [
	                [0.1, '#55BF3B'], // green
	                [0.5, '#DDDF0D'], // yellow
	                [0.9, '#DF5353'] // red
	            ],
	            lineWidth: 0,
	            minorTickInterval: null,
	            tickAmount: 2,
	            title: {
	                y: -70
	            },
	            labels: {
	                y: 16
	            }
	        },
	        series:[{
	        	name:CurrentPosition+typeTitle,
	        	data:[Number(MonitorObject.AVG)],
	        	dataLabels: {
	               format: '<div style="text-align:center"><span style="font-size:25px;color:' +
	                     ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' +
	                        '<span style="font-size:12px;color:silver">'+ValueSuffix+'</span></div>'
	             },
	        	tooltip:{
		    		valueSuffix:ValueSuffix
		    	}
	        }],
	        plotOptions: {
	            solidgauge: {
	                dataLabels: {
	                    y: 5,
	                    borderWidth: 0,
	                    useHTML: true
	                }
	            }
	        }
		});	
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
});