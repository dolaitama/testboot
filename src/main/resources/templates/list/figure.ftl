<#include "${request.contextPath}/common/meta.ftl">
<!DOCTYPE html>
<html>
<head>
	<title> ${title!"" }</title>
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<script type="text/javascript" src="${request.contextPath}/static/js/common/syUtil.js"></script>
	<script type="text/javascript" src="${request.contextPath}/static/js/common/menu.js"></script>
	<script type="text/javascript" src="${request.contextPath}/static/js/common/china.js"></script>
<style type="text/css">
form {
   	margin: 0;
   	padding: 0px 0 0px;
}
.stock_table {
  border: 0;
  width: 100%;
}
.stock_table p a{ 
    font-size: 13px;
    font-weight:bold;
}

.stock_fee_table {
  border: 0;
  width: 100%;
}

.stock_fee_table p a{ 
    font-size: 13px;
    text-decoration:underline;
    color: #81BEF7;
}

.format_btn a:hover{ 
	background:#ffcc99; 
} 

</style>

<body>
	<div class="top-work" id="toolBar">
		<div class="search" style="width:100%">
			<form  name="rptForm" action="" class="" method="post" style="">
				<input type="hidden"  name="dyViewId" id="listParams.dyViewId" value="${listParams.dyViewId!""}"/>
				<input type="hidden"  name="condStr" id="listParams.condStr" value=""/>
				<#if formHtml??>
					<div style="padding: 3px; height: auto;" id="userListtb" class="datagrid-toolbar">
						<div name="searchColums">
							${formHtml!""}
							<a href="#"  id="page_model_find" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:70px">查询</a>
						</div>
					</div>
				</#if>
			</form>
		</div>		
	</div>  
<div class="easyui-layout" fit="true" style="margin: 0px;border: 0px;overflow: hidden;width:100%;height:100%;">
	<div id="figure" style="padding: 0px; height: 100%;width:100%; overflow: hidden;" align="left">	
	</div>
</div>
</html>
<script type="text/javascript">
// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('figure'));

// 指定图表的配置项和数据
${option!""}
$(function(){
	query1();
	$("#page_model_find").click(function(){
		query1();
	});
	$(window).resize(function(){
		myChart.resize();
    });
})
//查询
function query1(){
	$(".btn").each(function(){
		var text=$(this).text();
		$(this).removeClass("cur");
		$(this).html(text);	
	});		
	var params=getRequestParamsStr();
	$.ajax({
		url: '${request.contextPath}/list/queryPageData?*=1'+params,
		type: "post",
		dataType: "json",
		data: {},
		success:function(data){
			optoinSetter(data);
			myChart.setOption(option);
		}
	})
}
//设置option
function optoinSetter(data){
	${optionsetter!""}
}
//提示框
function alertTip(type,tip){
	if(type=='warning'){
		$alert.warning(tip);
	}else if(type=='danger'){
		$alert.danger(tip);
	}else if(type=='info'){
		$alert.info(tip);
	}else{
		$alert.success(tip);
	}
}
${js!""}

</script>