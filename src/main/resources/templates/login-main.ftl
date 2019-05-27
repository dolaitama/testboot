<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>后台管理主界面</title>
<#include "${request.contextPath}/common/meta.ftl">
</head>
<body id="indexLayout" class="easyui-layout" style="height: 100%;width: 100%;overflow-y: hidden;">
	<noscript>
		<div style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
			<img src="${request.contextPath}/static/img/noscript.gif" alt='请开启脚本支持!' />
		</div>
	</noscript>
	<!--north顶部Logo区域 -->
	<div data-options="region:'north',border:false,split:false,href:'${request.contextPath}/list/redirect?toPage=north&menuId=${RequestParameters['menuId']!""}'"
		style="height: 89px;overflow: hidden;z-index: 100000;">
	</div>
	<!-- center主面板 -->
	<div data-options="region:'center',split:false,href:'${request.contextPath}/list/redirect?toPage=center'" style="overflow: hidden;z-index: 10;">
	</div>
	<!-- west菜单栏 -->
	<div data-options="region:'west',title:'',split:false,href:'${request.contextPath}/list/redirect?toPage=west&menuId=${RequestParameters['menuId']!""}'"
		style="width: 160px;overflow: hidden;">
	</div>
	<!-- south底部 -->
	<div data-options="region:'south',border:false,split:false,href:'${request.contextPath}/list/redirect?toPage=south'"
		style="height: 20px;overflow: hidden;">
	</div>
</body>
</html>