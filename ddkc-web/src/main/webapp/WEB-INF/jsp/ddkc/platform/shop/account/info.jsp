<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>添加班级 - 后台管理系统</title>
<%@include file="../../../../common/title.jsp"%>
</head>
<body>

	<form class="layui-form" action="" method="post">
		<input id="id" name="id" type="hidden" value="${account.id }" />
		<input id="shopId" name="shopId" type="hidden" value="${shopId }"/>
		<div class="layui-card">
			<div class="layui-card-body">
				<div class="layui-row layui-col-space10">
					<div class="layui-col-md9">
						<div class="layui-form-item">
							<label class="layui-form-label">员工姓名：</label>
							<div class="layui-input-inline">
								<input  name="name" id="name" class="layui-input" placeholder="请输入员工姓名"
									value="${account.name }" maxlength="11">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">账号：</label>
							<div class="layui-input-inline">
								<input  name="account" id="account" class="layui-input" placeholder="请输入员工账号"
									value="${account.account }" maxlength="11">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">密码：</label>
							<div class="layui-input-inline">
								<input name="password" id="password" class="layui-input" placeholder="请输入密码" value="${account.password }">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">是否接收语音通知：</label>
							<div class="layui-input-inline">
								<select id="isNotice" name="isNotice">
									<option value="0" <c:if test="${account.isNotice==0 }">selected</c:if>>否</option>
									<option value="1" <c:if test="${account.isNotice==1 }">selected</c:if>>是</option>
								</select>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label"></label>
							<div class="layui-input-inline">
								<button type="button" onclick="javascript:saveBtn();" class="layui-btn" id="footerSave">保存</button>
								<button type="button" onclick="javascript:history.go(-1);" class="layui-btn layui-btn-primary">返回</button>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<script type="text/javascript" src="${ctx }/public/plugins/jquery.form.min.js"></script>
	<script type="text/javascript" src="${ctx }/public/js/base.js"></script>
	<script type="text/javascript" src="${ctx }/public/js/shop/account/info.js"></script>
</body>
</html>