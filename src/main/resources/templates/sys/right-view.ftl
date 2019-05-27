<!DOCTYPE html>
<html>
<head>
<title>权限管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/js/plugins/bootstrap-3.0.3/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/alert.css" />
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/js/plugins/jquery-easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/js/plugins/jquery-easyui/demo/demo.css" />
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-3.4.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/common/alert.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/plugins/bootstrap-3.0.3/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/plugins/jquery-easyui/jquery-1.6.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/plugins/jquery-easyui/jquery.easyui.min.js"></script>
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
.datagrid{
    margin:auto;
}
</style>
<script type="text/javascript" class="source">
	var api = frameElement.api, W = api.opener;

	$(function() {
		<#if (right.parentrightcode)??>
		$('#parentrightcode').combobox('setValue', '${right.parentrightcode}');
		</#if>
		<#if (right.menuid)??>
		$('#menuid').combobox('setValue', '${right.menuid}');
		</#if>
	});

	function do_submit() {
		var dialog_object = W.get_dialog_instance();
		dialog_object.button({
			id : 'ok',
			disabled : true
		});

		if ($('#rightname').val() == "") {
			$alert.warning("请输入菜单名称！");
			$('#rightname').focus();
			dialog_object.button({
				id : 'ok',
				disabled : false
			});
			return;
		}
		var formData = $('#form').serialize();
		console.log("the formData is :%o", formData);
		console.log("the after formData is :%o", formData)
		$.ajax({
			type : 'post',
			url : '${request.contextPath}/sysright/save',
			data : formData,
			cache : false,
			dataType : 'json',
			async : false, //默认为true 异步
			success : function(data) {
				if (data.success) {
					W.alertTip('操作成功');
					api.get("infosource_dialog", 1).close();
					W.query1();
				} else {
					$alert.danger('操作失败');
					dialog_object.button({
						id : 'ok',
						disabled : false
					});
				}
			}
		});
	}
</script>
</head>
<body>
	<div class="panel panel-default">
		<table class="table" style="width: 95%">
			<tr>
				<td width="100%">
					<form class="form-horizontal" role="form" id="form" name="form">
						<input type="hidden" name="id" id="id" value="<#if (right.id)??>${right.id}</#if>" />
						<fieldset>
							<div class="form-group">
								<label for="rightname" class="col-sm-4 control-label"
									style="font-weight: normal;">权限名称<span
									style="color: red">*</span></label>
								<div class="col-sm-8">
									<input type="text" class="form-control input-sm"
										name="rightname" id="rightname" placeholder="请输入权限名称"
										value="<#if (right.rightname)??>${right.rightname}</#if>">
								</div>
							</div>
							<div class="form-group">
								<label for="rightcode" class="col-sm-4 control-label"
									style="font-weight: normal;">权限编码<span
									style="color: red">*</span></label>
								<div class="col-sm-8">
									<input type="text" class="form-control input-sm" name="rightcode"
										id="rightcode" placeholder="(全系统唯一)" value="${right.rightcode!""}" <#if (editType!"") == 'edit'>readonly="readonly"</#if>>
								</div>
							</div>
							<div class="form-group">
								<label for="parentrightcode" class="col-sm-4 control-label"
									style="font-weight: normal;">父级权限<span
									style="color: red">*</span></label>
								<div class="col-sm-8">
									<input id="parentrightcode" class="easyui-combobox" name="parentrightcode"
										data-options="valueField:'rightcode',textField:'rightname',url:'parentList',editable:false">
								</div>
							</div>
							<div class="form-group">
								<label for="parentrightcode" class="col-sm-4 control-label"
									style="font-weight: normal;">所属菜单<span
									style="color: red">&nbsp;&nbsp;</span></label>
								<div class="col-sm-8">
									<select id="menuid" class="easyui-combobox" name="menuid">
										<option value>请选择</option>
										<#list menus as menu>
											<option value="<#if (menu.id)??>${menu.id}</#if>"><#if (menu.menutext)??>${menu.menutext}</#if></option>
										</#list>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="rightdesc" class="col-sm-4 control-label"
									style="font-weight: normal;">权限描述<span
									style="color: red">*</span></label>
								<div class="col-sm-8">
									<input type="text" class="form-control input-sm" name="rightdesc"
										id="rightdesc" placeholder="请输入描述" value="<#if (right.rightdesc)??>${right.rightdesc}</#if>">
								</div>
							</div>
							<div class="form-group" style="margin-left:10px">
								<table id="dg" class="easyui-datagrid" toolbar="#toolbar" style="width:500px">
									<thead>
										<tr>
											<th field='paramkey' width="120" align="center">参数名</th>
											<th field='paramvalue' width="120" align="center">参数值</th>
											<th field='operate' width="120" align="center">运算符号</th>
											<th field='opt' width="120" align="center">操作</th>
										</tr>
									</thead>
									<div id="toolbar">
								        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newParam()">New Param</a>
								    </div>
								    <div id="dlg" class="easyui-dialog" style="width:400px" data-options="closed:true,modal:true,border:'thin',buttons:'#dlg-buttons'">
								        <form id="fm" method="post" novalidate style="margin:0;padding:20px 50px">
								            <h3>Param Information</h3>
								            <div style="margin-bottom:10px">
								                <input name="key" class="easyui-textbox" required="true" label="Key:" style="width:50%">
								            </div>
								            <div style="margin-bottom:10px">
								                <select name="operate" class="easyui-combobox" required="true" label="Operate:" style="width:50%">
								                	<option value="=" selected="selected">=</option>
								                	<option value="<"><</option>
								                	<option value=">">></option>
								                	<option value="like">like</option>
								                	<option value="in">in</option>
								                	<option value="not in">not in</option>
								                </select>
								            </div>
								            <div style="margin-bottom:10px">
								                <input name="value" class="easyui-textbox" required="true" label="Value:" style="width:50%">
								            </div>
								        </form>
    								</div>
    								<div id="dlg-buttons">
								        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveParam()" style="width:90px">Save</a>
								        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">Cancel</a>
								    </div>
									<tbody id="data">
										<#list rps as rp>
											<tr>
												<td><#if (rp.paramkey)??>${rp.paramkey}</#if></td>
												<td><#if (rp.v)??>${rp.paramvalue}</#if></td>
												<td><#if (rp.operate)??>${rp.operate}</#if></td>
												<td></td>
											</tr>
										</#list>
									</tbody>
								</table>
							</div>
						</fieldset>
					</form>
				</td>
			<tr>
		</table>
	</div>
</body>
</html>
<script>
function newParam(){
	$('#dlg').dialog('open').dialog('center').dialog('setTitle','New Param');
    $('#fm').form('clear');
}

function saveParam(){
	$('#dlg').dialog('close');
	var key = $("input[name='key']").val();
	var value=$("input[name='value']").val();
	var operate=$("input[name='operate']").val();
	var rightcode=<#if (right.rightcode)??>"${right.rightcode}"</#if>;
	$.ajax({
		url:"${request.contextPath}/rightParam/save",
		data:{rightcode:rightcode,paramkey:key,paramvalue:value,operate:operate},
		type:"post",
		success:function(data){
			
		}
	});
	$("#dg").datagrid("appendRow",{
		paramkey:key,
		paramvalue:value,
		operate:operate
	})
}
</script>