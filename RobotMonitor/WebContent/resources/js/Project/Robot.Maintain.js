/**
 * 
 */
$(document).ready(function(){
	var currentPage,prevTenPages,nextTenPages,numOfPages;
	init();
	
	$('#submitNewUser').click(function(){
		var data={},errorMessage='';
		data["SN"]=$('#SN').val();
		data["LineNO"]=$('#LineNO').val();
		data["ModelNO"]=$('#ModelNO').val();
		data["FactoryCode"]=$('#FactoryCode').find('option:selected').val();
		data["WorkShop"]=$('#WorkShop').val();
		
		if(data["SN"]==="null" || data["SN"]=='')
			errorMessage+='手臂序號未填寫\n';
		if(data["LineNO"]==="null" || data["LineNO"]=='')
			errorMessage+='線別未填寫\n';
		if(data["ModelNO"]==="null" || data["ModelNO"]=='')
			errorMessage+='機種未填寫\n';
		if(data["FactoryCode"]==="null" || data["FactoryCode"]=='')
			errorMessage+='未選取廠區\n';
		if(data["WorkShop"]==="null" || data["WorkShop"]=='')
			errorMessage+='車間未填寫\n';
		
		if(errorMessage.length>0){
			alert(errorMessage);
		}
		else{
			//新增資訊
			$.ajax({
				type:'POST',
				contentType: "application/json", 
				url:'../Maintain/AddRobot.do',
				data:JSON.stringify(data),     //stringify()用于从一个对象解析出字符串
				dataType:'json',
				success:function(data){
					$('#submitNewUser').prop("disabled",false);
					if(data.StatusCode=="200"){
						alert(data.Message);
						$('#CreateRobotDialog').modal('toogle');   //toogle手動切換模態框
					}
					else{
						alert(data.Message);
//						$('#RobotForm').trigger("reset");  //trigger() 方法触发被选元素的指定事件类型。
					}
				},
				error:function(e){
					alert('新增機械手臂基本資料發生錯誤');
//					$('#RobotForm').trigger("reset");
				}
			});
		}
	});
	
	function init(callback){
		$.getJSON('../Maintain/Robot.show?currentPage=1&queryCritirea=&queryParam=',function(data){
			RefreshRobotMasterTable(data);
		});
	}
	
	function RefreshRobotMasterTable(data){
		var tableBodyElement='';
		var SN,FactoryCode,WorkShop,LineNO,ModelNO;
		$('#robotMasterTable tbody').empty();
		for(var i=0;i<data.length;i++){
			tableBodyElement+='<tr><td>'+data[i]["SN"]+'</td>'+
				'<td>'+data[i]["FACTORY"]+'</td>'+
				'<td>'+data[i]["WORKSHOP"]+'</td>'+
				'<td>'+data[i]["LINENO"]+'</td>'+
				'<td>'+data[i]["MODELNO"]+'</td>'+
				'<td><a  class="editBtn btn btn-xs btn-primary" role="button">編輯</a>'+
				'<a  class="deleteBtn btn btn-xs btn-default" role="button">刪除</a></td></tr>';
		}
		
		$('#robotMasterTable tbody').append(tableBodyElement);
		
		$('.editBtn').click(function(){
			var parentElement=$(this).parent().parent();
			SN=$(parentElement).find('td').eq(0).text();
			FactoryCode=$(parentElement).find('td').eq(1).text();
			$(parentElement).find('td').eq(1).html('<select class="factoryCode input-small"><option value="HQ">總部</option>'+
	        		'<option value="FD">富東廠</option>'+
	  				'<option value="FQ">富強廠</option>'+
	  				'<option value="LK">龍坑廠</option>'+
	  				'<option value="KS">昆山廠</option>'+
	  				'<option value="Maanshan">馬鞍山</option></select>');
			
			$(parentElement).find('td .factoryCode option').each(function(){
				if($(this).val()==FactoryCode){
					$(this).prop('selected',true);
				}
			});
			
			WorkShop=$(parentElement).find('td').eq(2).text();
			$(parentElement).find('td').eq(2).html('<input type="text" class="workShop input-small" value="'+WorkShop+'">');
			
			LineNO=$(parentElement).find('td').eq(3).text();
			$(parentElement).find('td').eq(3).html('<input type="text" class="lineNO input-small" value="'+LineNO+'">');
			
			ModelNO=$(parentElement).find('td').eq(4).text();
			$(parentElement).find('td').eq(4).html('<input type="text" class="modelNO input-small" value="'+ModelNO+'">');
			$(parentElement).children().find('.editBtn .deleteBtn').hide();
			$(parentElement).find('td').eq(5).append('<a class="confirmBtn btn btn-xs btn-primary" role="button">確認</a>'+
	        		'<a class="cancelBtn btn btn-xs btn-default" role="button">取消</a>');
			$(parentElement).find('.editBtn,.deleteBtn').hide();
			
			$('.confirmBtn').click(function(){
				//還要加上驗證
				var parentElement=$(this).parent().parent();
				var RobotInfo=new Object();
				RobotInfo.SN=$(parentElement).find('td').eq(0).text();
				RobotInfo.FactoryCode=$(parentElement).find('option:selected').eq(1).val();
				RobotInfo.ModelNO=$(parentElement).find('td input:text').eq(1).val();
				RobotInfo.WorkShop=$(parentElement).find('td input:text').eq(2).val();
				RobotInfo.LineNO=$(parentElement).find('td input:text').eq(3).val();
				
				$.ajax({
					type:'POST',
					url:'../Maintain/UpdateRobot.do',
					data:{RobotForm:RobotInfo},
					error:function(e){
						alert(e);
					},
					success:function(data){
						if(data.StatusCode=="200"){
							alert(data.Message);
							var parentElement=$(this).parent().parent();
							$(parentElement).find('.editBtn,.deleteBtn').show();
							$(parentElement).find('td').eq(2).html(FactoryCode);
							$(parentElement).find('td').eq(3).html(WorkShop);
							$(parentElement).find('td').eq(4).html(LineNO);
							$(parentElement).find('td').eq(5).html(ModelNO);
							$(this).parent().find('.confirmBtn,.cancelBtn').remove();
						}
						else{
							alert(data.Message);
						}
					}
				});
			});
			
			$('.cancelBtn').click(function(){
				var parentElement=$(this).parent().parent();
				$(parentElement).find('.editBtn,.deleteBtn').show();
				$(parentElement).find('td').eq(1).html(FactoryCode);
				$(parentElement).find('td').eq(2).html(WorkShop);
				$(parentElement).find('td').eq(3).html(LineNO);
				$(parentElement).find('td').eq(4).html(ModelNO);
				$(this).parent().find('.confirmBtn,.cancelBtn').remove();
			})
		});
		
		$('.deleteBtn').click(function(){
			var parentElement=$(this).parent().parent();
			var deleteSN=$(parentElement).find('td').eq(0).text();
			var results=confirm("刪除機械手臂： "+deleteSN+" 的基本資料 ?");
			if(results==true){
				$.ajax({
					type:'GET',
					url:'../Maintain/DisableRobot.do',
					data:{SN:deleteSN},
					error:function(e){
						alert(e);
					},
					success:function(data){
						if(data.StatusCode=="200"){
							var parentElement=$(this).parent().parent();
							//刪除，所以將此列從畫面移除
							parentElement.remove();
						}
						else{
							alert(data.Message);
						}
					}
				});
			}
		});
	}
	
	function RefreshTableByPagination(CurrentPage){
		$.ajax({
			type:'GET',
			url:'../Maintain/Robot.show',
			data:{currentPage:CurrentPage,queryCritirea:"",queryParam:""},
			error:function(e){
				alert(e);
			},
			success:function(data){
				if(data.length>0){
					$('#robotMasterTable tbody').empty();
					RefreshRobotMasterTable(data);
				}
				else{
					alert('無資料');
				}
			}
		});
	}
	
	/* *
	 * 處理表格分頁顯示
	 * */
	function TablePagination(currentPage,numOfPages){
		var paginationElement='<nav><ul class="pagination pagination-mg">';
		
		if(prevTenPages==0)
			paginationElement+='<li class="disabled"><a>前十頁</a></li>';
		else
			paginationElement+='<li><a class="prevTenPages">前十頁</a></li>';
		
		if(currentPage!=1)
			paginationElement+='<li><a class="previousPage">前一頁</a></li>';
		else
			paginationElement+='<li><a class="previousPage">前一頁</a></li>';
		
		for(var i=prevTenPages*10+1;i<=prevTenPages*10+10;i++){
			if(currentPage==i)
				paginationElement+='<li class="disabled"><a>'+i+'</a></li>';
			else
				paginationElement+='<li><a class="currentPage">'+i+'</a></li>';
		}
		
		if(currentPage<numOfPages)
			paginationElement+='<li><a class="nextPage">下一頁</a></li>';
		else
			paginationElement+='<li class="disabled"><a>下一頁</a></li>'
				
		if((nextTenPages-1==prevTenPages) || (nextTenPages==1))
			paginationElement+='<li class="disabled"><a>後十頁</a></li></ul></nav>';
		else
			paginationElement+='<li><a class="nextTenPages">後十頁</a></li></ul></nav>';
		
		$('#RobotMasterPagination').append(paginationElement);
		
		$('.previousPage').click(function(){
			if((currentPage==prevTenPages*10+1)&&(currentPage!=1)){
				--prevTenPages;
				currentPage=prevTenPages*10+1;
			}
			else{
				--currentPage;
			}
			RefreshTableByPagination(currentPage);
		});
		
		$('.nextPage').click(function(){
			if(currentPage==prevTenPages*10+10){
				++prevTenPages;
				currentPage=prevTenPages*10+1;
			}
			else{
				++currentPage;
			}
			RefreshTableByPagination(currentPage);
		});
		
		$('.prevTenPages').click(function(){
			if(prevTenPages>0){
				--prevTenPages;
				currentPage=prevTenPages*10+1;
			
			}
			RefreshTableByPagination(currentPage);
		});
		
		$('.nextTenPages').click(function(){
			++prevTenPages;
			currentPage=prevTenPages*10+1;
			RefreshTableByPagination(currentPage);
		});
		
		$('.specificPage').click(function(){
			currentPage=$(this).text();
			RefreshTableByPagination(currentPage);
		});
	}
});