<#include "/common/meta.ftl">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>列表页面</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<style>
textarea {
	width: 100%;
	height: 100%;
	display: block;
}
</style>
</head>
<body>
	<!--box-center-->
	<form action="${request.contextPath}/list/viewEdit" method="post">

		<table style="width: 100%; height: 100%;">
			<tr>
				<td class="align">报表编码：</td>
				<td><input type="text" readonly name="id"
					value="${listParams.dyView.id!""}" autofocus="autofocus"/></td>
			</tr>
			<tr>
				<td class="align">sql配置：</td>
				<td><textarea name="defsql" style="height: 200px;"
						rows="30">${listParams.dyView.defsql!""}</textarea></td>
			</tr>
			<tr>
				<td class="align">条件配置：</td>
				<td><textarea cols="" rows="50" style="height: 300px;"
						name="querydef">${listParams.dyView.querydef!""}</textarea></td>
			</tr>
			<#if listParams.dyView.type==0>
				<tr>
					<td class="align" style="width: 10%;">表列配置：</td>
					<td><textarea cols="40" rows="40" style="height: 300px;"
							name="defcol">${listParams.dyView.defcol}</textarea></td>
				</tr>
			<#else>
				<tr>
					<td class="align">option定义：</td>
					<td><textarea cols="" rows="50" style="height: 400px;"
							name="eoption">${listParams.dyView.eoption!""}</textarea></td>
				</tr>
				<tr>
					<td class="align" style="width: 10%;">option设置：</td>
					<td><textarea cols="40" rows="40" style="height: 200px;"
							name="optionsetter">${listParams.dyView.optionsetter!""}</textarea></td>
				</tr>
			</#if>
			<tr>
				<td class="align">js配置：</td>
				<td><textarea cols="" rows="10" style="height: 500px;"
						name="defjs">${listParams.dyView.defjs!""}</textarea></td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>
<script type="text/javascript">
var api = frameElement.api, W = api.opener;
$(function(){
	shortcut.add("Ctrl+S",function() {
		do_submit();
	});
	if(${listParams.winIsClose!""}){
		W.alertTip("操作成功");
		api.get("page_view_detail",1).close();
	}
})
function do_submit() {
	document.forms[0].submit();
}
</script>
