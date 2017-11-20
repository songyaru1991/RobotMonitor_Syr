<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="CreateRobotDialog" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h3>新增機械手臂基本資料</h3>
			</div>
			<div class="modal-body">
				<!-- <form id="RobotForm" action=""  method="post" class="form-horizontal"> -->
					<div class="control-group">
    					<label class="control-label" for="SN">手臂序號</label>
    					<div class="controls">
      						<input type="text" id="SN" name="SN" class="required" placeholder="手臂序號">
    					</div>
  					</div>
  				
  					<div class="control-group">
    					<label class="control-label" for="ModelNO">生產機種</label>
    					<div class="controls">
      						<input type="text" id="ModelNO" name="ModelNO" class="required" placeholder="生產機種">
    					</div>
  					</div>
  				
  					<div class="control-group">
    					<label class="control-label" for="FactoryCode">所屬廠區</label>
    					<div class="controls">
      						<select id="FactoryCode">
      							<option value=""> --- 請選擇 ---</option>
      							<option value="HQ">總部</option>
      							<option value="FD">富東廠</option>
      							<option value="FQ">富強廠</option>
      							<option value="LK">龍坑廠</option>
      							<option value="KS">昆山廠</option>
      							<option value="Maanshan">馬鞍山</option>
      						</select>
    					</div>
  					</div>
  				
  					<div class="control-group">
    					<label class="control-label" for="WorkShop">所屬車間</label>
    					<div class="controls">
      						<input type="text" id="WorkShop" name="WorkShop" class="required" placeholder="所屬車間">
    					</div>
  					</div>
  				
  					<div class="control-group">
    					<label class="control-label" for="LineNO">所屬線別</label>
    					<div class="controls">
      						<input type="text" id="LineNO" name="LineNO" class="required" placeholder="所屬線別">
    					</div>
  					</div>
  				
  					<button  id="submitNewUser" class="btn btn-primary">新增</button>
  					<button type="reset" id="resetSubmit" class="btn">清除</button>
				<!-- </form> -->
			</div>
		</div>
	</div>
</div>