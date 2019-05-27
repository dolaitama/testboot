<!DOCTYPE html>
<html>
<head>
<title>按钮管理</title>
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
</style>
<script type="text/javascript" class="source">
	var api = frameElement.api, W = api.opener;

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
		$.ajax({
			type : 'post',
			url : '${request.contextPath}/button/save',
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
						<input type="hidden" name="id" id="id" value="${item.id!""}" />
						<fieldset>
							<div class="form-group">
								<label for="menutext" class="col-sm-4 control-label"
									style="font-weight: normal;">按钮名称<span
									style="color: red">*</span></label>
								<div class="col-sm-8">
									<input type="text" class="form-control input-sm"
										name="name" id="name" placeholder="请输入菜单名称"
										value="${item.name!""}">
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