<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>叮咚开超后台</title>
<%@include file="title.jsp"%>
<link rel="stylesheet" href="${ctx }/public/css/system/login.css" />
<script type="text/javascript">
var shiroLoginFailure = "${shiroLoginFailure}";
</script>
</head>

<body class="beg-login-bg">
	<div class="beg-login-box">
		<header>
			<h1>后台管理系统</h1>
		</header>
		<div class="beg-login-main">
			<form action="${ctx }/system/login" class="layui-form" method="post">
				<input type="hidden" name="redirectUrl" value="${redirectUrl }" />
				<div class="layui-form-item">
					<label class="beg-login-icon"> <i class="layui-icon">&#xe612;</i>
					</label> <input autofocus="autofocus" type="text" id="username" name="username" lay-verify="username"
						autocomplete="off" placeholder="这里输入登录名" class="layui-input" value="${username }">
				</div>
				<div class="layui-form-item">
					<label class="beg-login-icon"> <i class="layui-icon">&#xe642;</i>
					</label> <input type="password" id="password" name="password" lay-verify="password"
						autocomplete="off" placeholder="这里输入密码" class="layui-input">
				</div>
				<div class="layui-form-item">
					<div class="beg-pull-left beg-login-remember">
						<label>记住帐号？</label> <input  type="checkbox" name="remember" value="true" lay-skin="switch" title="记住帐号" checked >
					</div>
					<div class="beg-pull-right">
						<button class="layui-btn layui-btn-primary" lay-submit
							lay-filter="login">
							<i class="layui-icon">&#xe650;</i> 登录
						</button>
					</div>
					<div class="beg-clear"></div>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript" src="${ctx }/public/js/system/login.js"></script>
</body>
</html>
