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
	<style type="text/css">
		form {
		    margin: 0;
		    padding: 5px 0 0px;
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
				<div style="padding: 3px; height: auto;" id="userListtb" class="datagrid-toolbar">
					<div name="searchColums">
						${formHtml!""}
						<a href="#"  id="page_model_find" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:70px">查询</a>
						<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-print'" onclick="javascript:exportExcel()" style="width:70px">导出</a>
					</div>
				</div>
			</form>
		</div>
	</div>
<div class="easyui-layout" fit="true" style="margin: 0px;border: 0px;overflow: hidden;width:100%;height:100%;">
	<div data-options="region:'center',border:false,split:true" style="padding: 0px; height: 100%;width:100%; overflow-y: hidden;" align="left">
		${headHtml!""}
	</div>
</div>
</html>
<script type="text/javascript">
	$(document).ready(function() {
		var inputParams=getRequestParamsStr();
		console.log(inputParams)
	 	$('#tbgrid').datagrid({
			pagination:true,
		    pageSize:20,
		    pageList: [10,20,50,100,150,200,500],
		    striped:true,
		    onClickCell:function(rowIndex, field, value){
		    },
	        onClickRow:function(index,data){
				onClickRow(index);
			},
		    url:'${request.contextPath}/list/queryPageData?*=1'+inputParams
		});
		$("#page_model_find").click(function(){
			query1();
		});
	});
	/**
	 * 查询时获取调用参数
	 */
	function getRequestParams(queryParams){
		var inputs =document.getElementsByTagName("input");
		for(var j=0;j<inputs.length;j++){
			var name=inputs[j].getAttribute("name");
			if(name!=null&&inputs[j].value!=null){
				queryParams[name]=inputs[j].value;
			}
		}
		var selects =document.getElementsByTagName("select");
		for(var j=0;j<selects.length;j++){
			var name=selects[j].getAttribute("name");
			if(name!=null&&selects[j].value!=null){
				queryParams[name]=selects[j].value;
			}
		}
		return queryParams;
	}
	//查询方法
	function query1(){
		$(".btn").each(function(){
			var text=$(this).text();
			$(this).removeClass("cur");
			$(this).html(text);
		});
		$('#tbgrid').datagrid('options').url='${request.contextPath}/list/queryPageData';
		var queryParams=$('#tbgrid').datagrid('options').queryParams;
		var params=getRequestParams(queryParams);
		$('#tbgrid').datagrid('load');
	}
	/**
	 * 导出方法
	 */
	function exportExcel(){
		$('#tbgrid').datagrid('options').url='${request.contextPath}/list/queryPageData';
		var queryParams=$('#tbgrid').datagrid('options').queryParams;
		var params=getRequestParams(queryParams);
		$.ajax ({
			type: "POST",
			url: "${request.contextPath}/list/doHandleDataExport",
			data: queryParams,
			success: function(callback) {//提交成功后的回调
				window.location.href="${request.contextPath}/fileDownload/download?"+callback
			}
		});
	}
	//获取datagrid选中的复选框
	function get_checkbox_id(){
		var checkedItems = $('#tbgrid').datagrid('getChecked');
		var id=[];
		$.each(checkedItems, function(index, item){
			if(item.id){
				id[id.length]=item.id;
			}
		});
		return id.join(',');
	}
	//行点击事件
	function onClickRow(index){
		clickIndex=index;
		console.log("the index is :"+index);
	}
	//创建普通对话框
	function createDialogIdenty(id,title,url,width,height,lock){
		menu.create_dialog_identy(id,title,url,width,height,lock);
	}
	//创建全屏对话框
	function createMaxDialogIdenty(id,title,url,lock){
		menu.create_max_dialog_identy(id,title,url,lock);
	}
	//获取对话框实例
	function get_dialog_instance(){
        return menu.get_dialog_instance();
    }
	//提示框
	function alertTip(tip,type){
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