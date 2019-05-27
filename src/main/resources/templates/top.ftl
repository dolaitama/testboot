<script type="text/javascript" src="${request.contextPath}/static/js/common/menu.js"></script>
<script type="text/javascript">
	$(function() {
		init_top_menu();
	});

	function init_top_menu() {
		$.ajax({
			type : "POST",
			url : "${request.contextPath}/list/navTreeListByMenu",
			data : "",
			async : false,
			success : function(data) { //提交成功后的回调
				var menulist = "";
				var count = data.length;
				$.each(data, function(i, n) {
					if (("${RequestParameters['menuId']!""}" == "" && i==0) || n.id == "${RequestParameters['menuId']!""}") {
						$("#chickMenuText").html(n.text);
					}
					menulist += "<a href='${request.contextPath}/list/mainPage?menuId=" + n.id
							+ "'><b class='nb5'></b>" + n.text + "</a>";
				});
				$("#navb").html(menulist).css("width", count * 114 + "px");
			}
		});
	}

	//注销
	function logout() {
		$.messager.confirm('确认提示！', '您确定要退出系统吗？', function(r) {
			if (r) {
				window.location.href = "${request.contextPath}/staff/doLoginout";
			}
		});
	}
	function editLoginUserPassword() {
		//弹出对话窗口
		menu.create_dialog_identy('change_pwd_dialog', '修改密码',
				'/sys/accountInfo_toChangePwd.action', 500, 150, true);
	}

	function create_dialog(title, url) {
		//menu.create_model_max_dialog_identy("id",title,url);
		window.location.href = '${request.contextPath}' + url;
	}

	function change_first_level_menu(url, obj) {
		var click_text = obj.getAttribute("text");
		//console.log(click_text);
		//$(".t").text(click_text);
		window.location.href = url;
	}
</script>
<div id="header">
	<div class="box">
		<div class="logo">
			<a href="#">
				
			</a>
		</div>
		<div class="nav">
			<div class="homenav">
				<a href="#">主页</a>
			</div>
			<div class="helpnav">
				<a href="#" class="t" id="chickMenuText"></a>
				<div class="navb" id="navb">
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<div class="ttool">
			欢迎：${currentStaff.name} <a href="#" onclick="logout()" class="tb1">退出</a>
			<!-- <a href="javascript:void(0);"  onclick="editLoginUserPassword();"class="tb2">修改密码</a> -->
		</div>
		<div class="clear"></div>
	</div>
</div>