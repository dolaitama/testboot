<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>轻松管理</title>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/css.css" />
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/default.css" />
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-3.4.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/plugins/easyui-1.3.4/jquery.easyui.mine.js" charset="utf-8"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/plugins/easyui-1.3.4/portal/jquery.portal.js" charset="utf-8"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/plugins/jquery/easyui-1.3.4/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/plugins/jquery-extend.js" charset="utf-8"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/plugins/easyui-extend.js" charset="utf-8"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/plugins/validatebox-extend.js" charset="utf-8"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/plugins/validatebox-ajax.js" charset="utf-8"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/plugins/lhgdialog/lhgdialog.min.js?skin=mac"></script>
<script type="text/javascript">
	var loginForm;
	var login_linkbutton;
	$(function() {
		loginForm = $('#loginForm').form();
		$('#username').focus();
	});

	// 登录
	function login() {
		var login_linkbutton = $("#login_linkbutton");
		login_linkbutton.attr("disabled","disabled");
		$.ajax({
			url:'doLogin',
			type:"post",
			data:{username:$("#username").val(),password:$("#password").val()},
			success:function(Data){
				if (Data.success){
					window.location = "${request.contextPath}/list/mainPage";//操作结果提示
				}else {
					alert(Data.message);
					login_linkbutton.removeAttr("disabled");
				}
			}
		});
	}
</script>
</head>
<body style="background:#fff;">
	<div id="wrap">
		<div id="header" style="min-width:1200px;background:#fff; height:70px;">
			<div class="box">
				<div class="logo">
					<a href="#">
						<!-- <img src="${request.contextPath}/static/img/logo.png" /> -->
					</a>
				</div>
			</div>
		</div>
		<div class="clear"></div>
		<div id="mainer" style="min-width:1200px;">
			<div class="loginmain" style="height: 463px;">
				<div class="box" style="margin-top: 0">
					<div class="loginl">
						<ul id="lslider">
							<!-- <li><a href="#"><img src="${request.contextPath}/static/img/loginban1.png" /></a></li>
            <li><a href="#"><img src="${request.contextPath}/static/img/loginban2.png" /></a></li> -->
						</ul>
						<div id="lslider-nav"></div>
					</div>
					<div class="loginr">
						<h2>轻松管理库存，从此开始！</h2>
						<div class="loginform">
							<ul>
								<li><span>帐号：</span><input id="username" name="username" type="text"
									class="input1" onkeypress="if(event.keyCode==13) {$('#password').focus();}"/></li>
								<li><span>密码：</span><input id="password" name="password" type="password"
									class="input2" onkeypress="if(event.keyCode==13) {$('#login_linkbutton').click();}"/></li>
								<li><span>&nbsp;</span><a href="#">忘记密码？</a></li>
								<li><span>&nbsp;</span><input id="login_linkbutton" name="" type="submit"
									class="btn" value="登 录" onclick="login();"/></li>
								<div class="clear"></div>
							</ul>
						</div>
					</div>
					<div class="clear"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<!-- <div id="footer">
    <div class="box">
      <div class="copy">Copyright@ 2011-2014 Inc. All Rights Reserved. xxxxxxx</div>
    </div>
  </div> -->
	</div>

</body>
</html>
