/**
 * http://usejsdoc.org/
 */
$(document).ready(function(){
	var nextTenPages=1,prevTenPages=0;
	var startDate,endDate;
	var reportType,searchCondition,searchParameter,inspectSection;
	
	
	$('#startDate').datepicker({
		changeMonth: true, 
		changeYear: true,
		inline:true,
		dateFormat: 'yy/mm/dd', 
		onClose: function( selectedDate ) {
	        $( "#endDate" ).datepicker( "option", "minDate", selectedDate );
	     }
	});
	
	$('#endDate').datepicker({
		changeMonth: true, 
		changeYear: true,
		inline:true,
		dateFormat: 'yy/mm/dd', 
		onClose: function( selectedDate ) {
	        $( "#startDate" ).datepicker( "option", "maxDate", selectedDate );
	      }

	});
	
	$('#qryBtn').click(function(){
		var errorMsg='';
		reportType=$('#reportType option:selected').val();
		startDate=$('#startDate').val();
		endDate=$('#endDate').val();
		inspectSection=$('#inspectSection option:selected').val();
		
		if(startDate=='')
			errorMsg+='請選擇起始時間\n';
		if(endDate=='')
			errorMsg+='請選擇結束時間\n';
		if(reportType=='')
			errorMsg+='請選擇報表類型\n';
		if(inspectSection=='')
			errorMsg+='請選擇工段';
		
		if(errorMsg.length>0){
			alert(errorMsg);
		}
		else{
			$.ajax({
				type:'GET',
				url:'checkReport.do',
				data:{showType:reportType,startDate:startDate,endDate:endDate,inspectSection:inspectSection},
				error:function(e){},
				success:function(data){
					if(data.length>0){
						if(data.errorResponse!=null)
							alert(data.errorResponse);
						else{
							$('#reportTable thead').empty();
							$('#reportTable tfoot').empty();
							$('#reportTable tbody').empty();
							showTableTitles(reportType,inspectSection);
							showTableContents(data,reportType,inspectSection);
						}
					}
					else
						alert('找不到資料!!');
				}
			});
		}
	});
	
	
	$('#exportExcelBtn').click(function(){
		reportType=$('#reportType option:selected').val();
		startDate=$('#startDate').val();
		endDate=$('#endDate').val();
		inspectSection=$('#inspectSection option:selected').val();
		if(reportType=='ShowCheckResultsWithPagination')
			reportType='ExportCheckResults';
		else
			reportType='ExportCheckResultByEmp';
		window.open('checkReport.do?showType='+reportType+'&startDate='+startDate+'&endDate='+endDate+'&inspectSection='+inspectSection);
	});
	
	function  showTableTitles(tableType,inspectSection){
		var Titles='';
		if(tableType=='ShowCheckResultsWithPagination'){
			if(inspectSection=='SMT'){
				Titles='<tr><th>點檢時間</th>'+
				'<th>點檢批號</th>'+
				'<th>機種</th>'+
				'<th>成品料號</th>'+
				'<th>生產階段</th>'+
				'<th>客戶名稱</th>'+
				'<th>工令號</th>'+
				'<th>工令數</th>'+
				'<th>班別</th>'+
				'<th>PCB方向</th>'+
				'<th>線別</th>';
			}
			else if(inspectSection=='成型'){
				Titles='<tr><th>點檢時間</th>'+
				'<th>檢測批號</th>'+
				'<th>機種/品名</th>'+
				'<th>成品料號</th>'+
				'<th>模號</th>'+
				'<th>管控編號</th>'+
				'<th>成型週期</th>'+
				'<th>顏色</th>'+
				'<th>機台</th>'+
				'<th>重工時段</th>';
			}
		}
		else{
			Titles='<tr><th>點檢時間</th>'+
			'<th>點檢批號</th>'+
			'<th>機種</th>'+
			'<th>成品料號</th>'+
			'<th>檢測人員</th>';
		}
		$('#reportTable thead').append(Titles);
		$('#reportTable tfoot').append(Titles);
		
	}
	
	function showTableContents(data,tableType,inspectSection){
		var reportOfPages=data[0]["reportOfPages"];
		nextTenPages=Math.ceil(reportOfPages/10);
		var reportCurrentPage=data[0]["reportCurrentPage"];
		if(tableType=='ShowCheckResults'){
			for(var i=0;i<data.length;i++){
				var bodyElement='';
				if(inspectSection=='SMT'){
					bodyElement='<tr><td>'+data[i]["checkTime"]+'</td>'+
					'<td><a class="detailResults">'+data[i]["batchID"]+'</a></td>'+
					'<td>'+data[i]["modelNO"]+'</td>'+
					'<td>'+data[i]["partNO"]+'</td>'+
					'<td>'+data[i]["productStep"]+'</td>'+
					'<td>'+data[i]["customerName"]+'</td>'+
					'<td>'+data[i]["moNO"]+'</td>'+
					'<td>'+data[i]["moAmount"]+'</td>'+
					'<td>'+data[i]["shift"]+'</td>'+
					'<td>'+data[i]["pcbTopBottm"]+'</td>'+
					'<td>'+data[i]["lineNO"]+'</td></tr>';
				}
				else if(inspectSection=='成型'){
					if(data[i]["reworkSection"]=='無重工'){
						bodyElement='<tr>';
					}
					else{
						bodyElement='<tr class="warning">';
					}
					bodyElement+='<td>'+data[i]["checkTime"]+'</td>'+
					'<td><a class="detailResults">'+data[i]["batchID"]+'</a></td>'+
					'<td>'+data[i]["modelNO"]+'</td>'+
					'<td>'+data[i]["partNO"]+'</td>'+
					'<td>'+data[i]["modelSlotNO"]+'</td>'+
					'<td>'+data[i]["controlID"]+'</td>'+
					'<td>'+data[i]["partCycle"]+'</td>'+
					'<td>'+data[i]["color"]+'</td>'+
					'<td>'+data[i]["stage"]+'</td>'+
					'<td>'+data[i]["reworkSection"]+'</td></tr>';
				}
				else{
					//Assembly
				}
				$('#reportTable tbody').append(bodyElement);
			}

			$('.detailResults').click(function(){
				var clickedBatchID=$(this).text();
				window.open('checkReport.do?batchID='+clickedBatchID+'&showType=showCheckDetailResultsPage','巡檢批號:'+clickedBatchID+' 的檢測結果'
						,config='height=1024,width=768');
				return false;
			});
		}
		else{
			for(var i=0;i<data.length;i++){
				var bodyElement='<tr><td>'+data[i]["checkTime"]+'</td>'+
					'<td>'+data[i]["batchID"]+'</td>'+
					'<td>'+data[i]["modelNO"]+'</td>'+
					'<td>'+data[i]["finishedPartNO"]+'</td>'+
					'<td>'+data[i]["employeeName"]+'</td>';
				$('#reportTable tbody').append(bodyElement);
			}	
		}
		showTablePagination(reportCurrentPage,reportOfPages);
	}
	
	function showTablePagination(reportCurrentPage,reportOfPages){
		$('#reportTablePagination').empty();
		var paginationElement='<nav><ul class="pagination pagination-mg">';
		if(prevTenPages==0)
			paginationElement+='<li class="disabled"><a>前十頁</a></li>';
		else
			paginationElement+='<li><a id="prevTenPages">前十頁</a></li>';
		
		if(reportCurrentPage!=1)
			paginationElement+='<li><a id="prevPage">Previous</a></li>';
		else
			paginationElement+='<li class="disabled"><a>Previous</a></li>';
		
		for(var i=prevTenPages*10+1;i<=prevTenPages*10+10;i++){
			if(reportCurrentPage==i)
				paginationElement+='<li class="disabled"><a>'+i+'</a></li>';
			else
				paginationElement+='<li><a class="currentPage">'+i+'</a></li>';
		}
		
		if(reportCurrentPage<reportOfPages)
			paginationElement+='<li><a id="nextPage">Next</a></li>';
		else
			paginationElement+='<li class="disabled"><a>Next</a></li>';
		
		if(nextTenPages-1==prevTenPages*10+1 || nextTenPages==1)
			paginationElement+='<li class="disabled"><a>後十頁</a></li></ul></nav>';
		else
			paginationElement+='<li><a id="nextTenPages">後十頁</a></li></ul></nav>';
		
		$('#reportTablePagination').append(paginationElement);
		
		//前一頁的事件
		$('#prevPage').click(function(){
			if(reportCurrentPage==prevTenPages*10+1 && reportCurrentPage!=1){
				--prevTenPages;
				var currentPage=prevTenPages*10+1;
				getAsyncShowInfo(currentPage,searchCondition,searchParameter);
			}
			else
				getAsyncShowInfo(--reportCurrentPage,searchCondition,searchParameter);
		});
		
		//當前頁面的事件
		$('.currentPage').click(function(){
			var currentPage=$(this).text();
			getAsyncShowInfo(currentPage,searchCondition,searchParameter);
		});
		
		//下一頁的事件
		$('#nextPage').click(function(){
			if(reportCurrentPage==prevTenPages*10+10){
				++prevTenPages;
				var currentPage=prevTenLinePages*10+1;
				getAsyncShowInfo(currentPage,searchCondition,searchParameter);
			}
			else
				getAsyncShowInfo(++reportCurrentPage,searchCondition,searchParameter);
		});
		
		//下十頁的事件
		$('#nextTenPages').click(function(){
			++prevTenPages;
			var currentPage=prevTenPages*10+1;
			getAsyncShowInfo(currentPage,searchCondition,searchParameter);
		});
		
		//前十頁的事件
		$('#prevTenPages').click(function(){
			if(prevTenPages>0){
				--prevTenPages;
				var currentPage=prevTenPages*10+1;
				getAsyncShowInfo(currentPage,searchCondition,searchParameter);
			}
		});	
	}
	
	function getAsyncShowInfo(currentPage,searchCondition,searchParameter){
		$.ajax({
			type:'GET',
			url:'checkReport.do',
			data:{showType:reportType,inspectSection:inspectSection,searchCondition:searchCondition,searchParameter:searchParameter,
				startDate:startDate,endDate:endDate,currentPage:currentPage},
			error:function(e){},
			success:function(data){
				if(data.errorResponse!=null){
					alert(data.errorResponse);
				}
				else{
					var numOfRecords=data.length;
					if(numOfRecords>0){
						$('#reportTable tbody').empty();
						showTableContents(data,reportType,inspectSection);
					}
					else
						alert('找不到資料');
				}	
			}
		});
	}
});