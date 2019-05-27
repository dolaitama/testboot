<!DOCTYPE html>
<html>
<head>
<title>权限管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/js/plugins/bootstrap-3.0.3/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/alert.css" />
<link rel="stylesheet" href="${request.contextPath}/static/js/plugins/jstree-3.3/dist/themes/default/style.min.css" />
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-3.4.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/plugins/bootstrap-3.0.3/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/plugins/jstree-3.3/dist/jstree.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/common/alert.js"></script>
<style type="text/css">
body {
	margin: 0;
	font-family: Verdana, "Helvetica Neue", Helvetica, Arial, sans-serif;
	color: #333333;
	background-color: #ffffff;
	font-weight: normal;
}

label {
	font-size: 14px;
	font-weight: normal;
}

.col-sm-8 {
	width: 50%;
}
</style>
<script type="text/javascript" class="source">
    $(function () {
      $("#functions").jstree({ 
          "plugins" : [ "themes", "html_data", "checkbox", "sort", "ui" ],
          "core" : {
              "animation" : 100,
              "initially_open" : [${firstLevelRole!""} ]
          },
          "checkbox": { "two_state" : true }
      });
      $("#functions").jstree('open_all');
      $("input[name='status'][value=${staff.status!""}]").attr("checked",true);
    });

    var api = frameElement.api, W = api.opener;    
    function checkAccount(){
      if($("#loginName").val() != "") {            
        $.ajax({
          url:'${request.contextPath}/sys/accountInfo_checkAccount.action',
          type:'post',
          data:{'adminAccount' : $("#loginName").val()},
          success:function(data) {
            if(data == "0"){
              $("#gzdiv").css("display","block");
            }
            if(data == "1"){
              $alert.warning("该账户名称已被注册, 请重新输入!");
              $("#loginName").val("");
              $("#loginName").focus();
              $("#gzdiv").css("display","none");
            }
          },
          async:false
        });        
      } else {
        $("#gzdiv").css("display","none");
      }  
    }    

    function checkInputTel() {
      if($("#tel").val() != "") {    
        if($("#tel").val().length==11) {
          $("#telDiv").css("display","block");
        } else {
          $alert.warning("您输入的手机号码不正确, 请重新输入!");
          $("#tel").val("");
          $("#tel").focus();
          $("#telDiv").css("display","none");
        }
      } else {
        $("#telDiv").css("display","none");
      }  
    }

    function checkEnableAccount() {
        if('${editType!""}' == 'edit') 
        {
          var checkAccountRes = checkAccountNum();
          if (checkAccountRes == 0) {
        	$alert.warning("对不起，您允许开通的最大账户数超额，不允许添加帐号，请联系云潮的客服人员。");
            $("input[name='staff.status'][value=0]").attr("checked",true);
            return;
          }
        }
    }

    function checkAccountNum() {
      var result = 0;
      $.ajax({    
        type:'post',        
        url:'${request.contextPath}/sys/corp_getCorpMaxCount.action',    
        data:"",    
        cache:false,    
        dataType:'json',
        async : false, //默认为true 异步     
        success:function(data){
          if(data == 0){
             result = 0;
          }else{
             result = 1;
          }
        }    
      }); 
      return result;
    }

    function do_submit() { 
      var dialog_object=W.get_dialog_instance();
      dialog_object.button({id:'ok',disabled: true});         
         
      if($('#name').val() == ""){
    	$alert.warning("请输入姓名！");
        $('#name').focus();
        dialog_object.button({id:'ok',disabled: false});
        return;
      }          
      if($('#loginName').val() == ""){
    	$alert.warning("请输入账户名称！");
        $('#loginName').focus();
        dialog_object.button({id:'ok',disabled: false});
        return;
      }  
      if($('#tel').val() == ""){
    	$alert.warning("请输入手机号码！");
        $('#tel').focus();
        dialog_object.button({id:'ok',disabled: false});
        return;
      } 
      var status=$("input[name='status']:checked").val();
      if(!status){
    	$alert.warning("请选择是否启用！");
        dialog_object.button({id:'ok',disabled: false});
        return;
      }
      
	  var formData=$('#userform').serialize();
	  var functionid = get_checked_right();
      console.log("the right is :%o",functionid);
      formData += "&functionid="+functionid;
	  console.log("the after formData is :%o",formData)
	  $.ajax({
	       type:'post',
	       url:'${request.contextPath}/staff/save',
	       data:formData,
	       cache:false,
	       dataType:'json',
	       async : false, //默认为true 异步
	       success:function(data){
	         if(data.success){
	           W.alertTip('操作成功');
	           api.get("infosource_dialog",1).close();
	           W.query1();
	         }else{
	           $alert.danger('操作失败');
	           dialog_object.button({id:'ok',disabled:false});
	         }
	       }
      });
    }

    function get_checked_right(){
      var checked_ids = $("#functions").jstree("get_checked");
      var t = checked_ids.toString();
      return checked_ids.toString();
    }
        
    function showMacDiv(){
      $("#macDiv").css("display","block");
    }
    	
    function dismissMacDiv(){
      $("#macDiv").css("display","none");
    }
    	
  </script>
</head>
<body>
	<div class="panel panel-default">
		<table class="table">
			<tr>
				<td width="50%">
					<form class="form-horizontal" role="form" id="userform"
						name="userform">
						<input type="hidden" name="id" id="id" value="${staff.id!""}" /> <input
							type="hidden" name="loginPwd" id="staff.loginPwd" value="123" />
						<fieldset>
							<legend>1.基本信息</legend>
							<div class="form-group">
								<label for="name" class="col-sm-4 control-label"
									style="font-weight: normal;">姓名<span style="color: red">*</span></label>
								<div class="col-sm-8">
									<input type="input" class="form-control input-sm" name="name"
										id="name" placeholder="请输入姓名" value="${staff.name!""}">
								</div>
							</div>
							<div class="form-group">
								<label for="name" class="col-sm-4 control-label"
									style="font-weight: normal;">账户名称<span
									style="color: red">*</span></label>
								<div class="col-sm-8">
									<input type="input" class="form-control  input-sm"
										name="loginname" id="loginName" placeholder="请输入账户名称"
										value="${staff.loginname!""}"
										onchange="javascript:checkAccount();">
								</div>
								<div id="gzdiv" style="position: relative; display: none;">
									<img alt="" src="${request.contextPath}/static/img/tick.png"
										style="position: absolute; top: 50%; height: 15px; margin-top: 7px;">
								</div>
							</div>
							<div class="form-group">
								<label for="name" class="col-sm-4 control-label"
									style="font-weight: normal;">性别<span style="color: red">*</span></label>
								<div class="col-sm-8">
									<select name="sex" class="easyui-combobox" style="width:100px;">
										<option value='0'>男</option>
										<option value='1'>女</option>
									</select>
								</div>
							</div>
							<div class="form-group"></div>
							<div class="form-group">
								<label for="tel" class="col-sm-4 control-label"
									style="font-weight: normal;">手机号码 <span
									style="color: red">*</span>
								</label>
								<div class="col-sm-8">
									<input type="tel" class="form-control input-sm" id="tel"
										name="tel" placeholder="请输入手机号码" value="${staff.tel!""}"
										onchange="javascript:checkInputTel();">
								</div>
								<div id="telDiv" style="position: relative; display: none;">
									<img alt="" src="${request.contextPath}/static/img/tick.png"
										style="position: absolute; top: 50%; height: 15px; margin-top: 7px;">
								</div>
							</div>

							<div class="form-group">
								<label for="radio-1" class="col-sm-4 control-label"
									style="font-weight: normal;">是否启用 <span
									style="color: red"></span>
								</label>
								<div class="form-inline">
									<label class="radio-inline" style="margin-left: 12px;">
										<input type="radio" name="status" id="statusTrue" value="1">启用
									</label> <label class="radio-inline"> <input type="radio"
										name="status" id="statusFalse" value="0">停用
									</label>
								</div>
							</div>

							<div class="form-group">
								<label for="name" class="col-sm-4 control-label"
									style="font-weight: normal;">备注</label>
								<div class="col-sm-8">
									<textarea class="form-control" rows="3" id="remark"
										name="remark" placeholder="备注">${staff.remark!""}</textarea>
								</div>
							</div>
						</fieldset>
					</form>
				</td>
				<td width="50%">
					<form role="form" id="funform" name="funform">
						<fieldset>
							<legend>2.请选择角色</legend>
							<div class="form-group">
								<div id="functions">
									<ul>
										${roleTreeStr!""}
									</ul>
								</div>
							</div>
						</fieldset>
					</form>
				</td>
			<tr>
		</table>
	</div>
</body>
</html>