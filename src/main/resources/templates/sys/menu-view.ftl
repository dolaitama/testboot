<!DOCTYPE html>
<html>
<head>
<title>菜单管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/js/plugins/bootstrap-3.0.3/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/js/plugins/jquery-easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/js/plugins/jquery-easyui/demo/demo.css" />
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/alert.css" />
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/default.css" />
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-3.4.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/common/alert.js"></script>
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
</style>
<script type="text/javascript" class="source">
	var api = frameElement.api, W = api.opener;
	$(function() {
		$('#parentid').combobox('setValue', '${menu.parentid!""}');
		$('#rightcode').combobox('setValue', '${menu.rightcode!""}');
		$('#type').combobox('setValue', '${dv.type!""}');
		$('#urltype').combobox({
			onChange:function(cur, ex){
				if(cur == '0'){
					$("#url").val('');
				}
				$(".toggle").toggleClass('hide');
			}
		});
	});

	function do_submit() {
		var dialog_object = W.get_dialog_instance();
		dialog_object.button({
			id : 'ok',
			disabled : true
		});

		if ($('#menutext').val() == "") {
			$alert.warning("请输入菜单名称！");
			$('#menutext').focus();
			dialog_object.button({
				id : 'ok',
				disabled : false
			});
			return;
		}
		var formData = $('#form').serialize();
		var code = 0;
		$("[name='bcode']:checked").each(function(i, b){
			code = code | parseInt($(this).val());
		});
		formData += "&code="+code;
		console.log("the formData is :%o", formData);
		$.ajax({
			type : 'post',
			url : '${request.contextPath}/menu/save',
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
						<input type="hidden" name="id" id="id" value="${menu.id!""}" />
						<fieldset>
							<div class="form-group">
								<label for="menutext" class="col-sm-4 control-label"
									style="font-weight: normal;">菜单名称<span
									style="color: red">*</span></label>
								<div class="col-sm-8">
									<input type="text" class="form-control input-sm"
										name="menutext" id="menutext" placeholder="请输入菜单名称"
										value="${menu.menutext!""}">
								</div>
							</div>
							<div class="form-group">
								<label for="parentid" class="col-sm-4 control-label"
									style="font-weight: normal;">菜单类型<span
									style="color: red">*</span></label>
								<div class="col-sm-8">
									<select id="type" class="easyui-combobox" name="type" style="width:100px;" <#if editType == 'edit'>readonly="readonly"</#if>>
										<option value="0">列表</option>
										<option value="1">图表</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="parentid" class="col-sm-4 control-label"
									style="font-weight: normal;">父级菜单<span
									style="color: red">*</span></label>
								<div class="col-sm-8">
									<input id="parentid" class="easyui-combobox" name="parentid"
										data-options="valueField:'id',textField:'menutext',url:'parentList',editable:false">
								</div>
							</div>
							<#if (bts?size>0)>
								<div class="form-group">
									<label for="url" class="col-sm-4 control-label"
										style="font-weight: normal;">操作按钮&nbsp;&nbsp;</label>
									<div class="col-sm-8">
										<#list bts as b>
											${b.name!"" }<input class="easyui-checkbox" <#if menu.checkCode(b.code)>checked="true"</#if> name="bcode" type="checkbox" value="${b.code!"" }">
										</#list>
									</div>
								</div>
							</#if>
							<div class="form-group">
								<label for="parentid" class="col-sm-4 control-label"
									style="font-weight: normal;">链接类型<span
									style="color: red">*</span></label>
								<div class="col-sm-8">
									<select id="urltype" class="easyui-combobox" name="type" style="width:100px;">
										<option value="0" <#if (menu.urltype)?? && menu.urltype == 0>selected</#if>>动态配置</option>
										<option value="1" <#if (menu.urltype)?? && menu.urltype == 1>selected</#if>>固定链接</option>
									</select>
								</div>
							</div>
							<div class="form-group toggle<#if !(menu.urltype)?? || menu.urltype == 0> hide</#if>">
								<label for="url" class="col-sm-4 control-label"
									style="font-weight: normal;">链接地址<span
									style="color: red">*</span></label>
								<div class="col-sm-8">
									<input id="url" type="text" class="form-control input-sm" name="url"
										value="${menu.url!""}">
								</div>
							</div>
							<div class="form-group toggle<#if (menu.urltype)?? && menu.urltype == 1> hide</#if>">
								<label for="url" class="col-sm-4 control-label"
									style="font-weight: normal;">动态配置编号<span
									style="color: red">*</span></label>
								<div class="col-sm-8">
									<input type="text" class="form-control input-sm" name="dyviewid"
										id="url" placeholder="(全系统唯一)" value="${menu.dyviewid!""}" <#if (editType!"") == "edit">readonly="readonly"</#if>>
								</div>
							</div>
							<div class="form-group">
								<label for="orderno" class="col-sm-4 control-label"
									style="font-weight: normal;">菜单排序<span
									style="color: red">*</span></label>
								<div class="col-sm-8">
									<input type="text" class="form-control input-sm" name="orderno"
										id="orderno" placeholder="请输入菜单排序" value="${menu.orderno!""}">
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