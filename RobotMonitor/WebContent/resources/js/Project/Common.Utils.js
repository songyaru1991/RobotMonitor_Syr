/**
 * 常用的javascript function
 */

/*畫趨勢圖*/
	function ShowTrendChart(RobotSN,RobotArray,ReportType){
		
		var typeTitle='';
		var TimeSeries=[],AVGSeries=new Array(10),MAXSeries=new Array(10),MinSeries=new Array(10),ValueSuffix='';
		if(ReportType=="RobotLoad")
			typeTitle='近十分鐘負載量趨勢';
		else if(ReportType=="RobotRPM"){
			typeTitle='近十分鐘轉速趨勢';
			ValueSuffix='RPM'
		}
		else if(ReportType=="RobotTorque"){
			typeTitle='近十分鐘轉矩趨勢';
			ValueSuffix='°'
		}
		else{
			typeTitle='近十分鐘電控箱溫度趨勢';
			ValueSuffix='°C'
		}

		
		for(var i=0;i<RobotArray.length;i++){
			TimeSeries.push(RobotArray[i]["TIME"]);
			AVGSeries[i]=Number(RobotArray[i]["AVG"]);
			MAXSeries[i]=Number(RobotArray[i]["MAX"]);
			MinSeries[i]=Number(RobotArray[i]["MIN"]);
		}
		
		var reportTrendChart=new Highcharts.Chart({
			chart:{
				renderTo:'trendChart'
			},
			title:{
				text:typeTitle,
				x:-20
			},
			xAxis:{
				categories:TimeSeries
			},
			yAxis:{
				title:{
					text:typeTitle+ValueSuffix
				},
				 plotLines: [{
		                value: 0,
		                width: 1,
		                color: '#808080'
		            }],
				labels:{
					formatter: function () {
			               if(ReportType=='RobotTempature')
			            	   return this.value + '°C';
			                else if(ReportType=='RobotTorque')
			                	return this.value+' °';
			                else
			                	return this.value;
			        }
				}
			},
			series:[{
				name: typeTitle+'(平均值)',
				data: AVGSeries
				},
				{
					name: typeTitle+'(最大值)',
					data: MAXSeries
				},
			    {
					name: typeTitle+'(最小值)',
					data: MinSeries
			    }
			]
		});
	}
	


function ShowTableHeader(reportType){
	var Title='';
	switch(reportType){
		case 'RobotLoad':
			Title = '<tr><th>時間</th>'+
			'<th>最大負載</th>'+
			'<th>平均負載</th>'+
			'<th>最小負載</th></tr>';
			break;
		case 'RobotRPM':
			Title='<tr><th>時間</th>'+
			'<th>最大轉速</th>'+
			'<th>平均轉速</th>'+
			'<th>最小轉速</th></tr>';
		break;
		case 'RobotTorque':
			Title='<tr><th>時間</th>'+
			'<th>最大轉矩</th>'+
			'<th>平均轉矩</th>'+
			'<th>最小轉矩</th></tr>';
			break;
		case 'RobotTempature':
			Title='<tr><th>時間</th>'+
			'<th>電控箱最大溫度</th>'+
			'<th>電控箱平均溫度</th>'+
			'<th>電控箱最小溫度</th></tr>';
			break;
		default:
			break;
	
	}
	return Title;
}

function ShowTableContents(rawData,reportType){
	var tableContents='';
	if(reportType=='RobotMaster'){
		tableContents+='<tr><td>'+rawData.SN+'</td>'+
			'<td>'+rawData.FACTORY+'</td>'+
			'<td>'+rawData.WORKSHOP+'</td>'+
			'<td>'+rawData.LINENO+'</td>'+
			'<td>'+rawData.MODELNO+'</td></tr>';
	}
	else{
		for(var i=0;i<rawData.length;i++){
			 if(reportType=='RobotState'){
				 if(rawData[i]["ERRORCODE"]=='正常運行'){
					tableContents+='<tr class="success"><td>'+rawData[i]["TIME"]+'</td>'+
					'<td>'+rawData[i]["STATUSCODE"]+'</td>'+
					'<td>'+rawData[i]["ERRORCODE"]+'</td></tr>';
				 }
				 else{
						tableContents+='<tr class="danger"><td>'+rawData[i]["TIME"]+'</td>'+
						'<td>'+rawData[i]["STATUSCODE"]+'</td>'+
						'<td>'+rawData[i]["ERRORCODE"]+'</td></tr>';
				 }

			}
			else{
				tableContents+='<tr><td>'+rawData[i]["TIME"]+'</td>'+
					'<td>'+rawData[i]["MAX"]+'</td>'+
					'<td>'+rawData[i]["AVG"]+'</td>'+
					'<td>'+rawData[i]["MIN"]+'</td>'+
				'</tr>';
			}
		}
	}
	return tableContents;
}



/* *
 * 繪製趨勢圖
 * */
function DrawTrendChart(targetChart,reportType){
	var TrendCharts=new Highcharts.Chart({
		chart:{
			renderTo:targetChart.RenderTO
		},
		title:{
			text:targetChart.Title
		},
		xAxis:{
			categories: targetChart.xtext
		},
		yAxis:{
			title: {
				text: targetChart.yAxisTitle
	        },
	        labels: {
	        	formatter: function () {
	               if(reportType=='RobotTempature')
	            	   return this.value + '°C';
	                else if(reportType=='RobotTorque')
	                	return this.value+' °';
	                else
	                	return this.value;
	            }
	        }
		},
		series:targetChart.series
	});
	return TrendCharts
}

