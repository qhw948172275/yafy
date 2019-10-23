<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
  <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>个人信息</title>
      <%@include file="../../common/title.jsp" %>
  </head>
  <body>
  
  	<div class="layui-container">  
	  <div class="layui-row" style="margin-top: 5%;">
	    <div class="layui-col-md-offset4 layui-col-md4">
	      <form class="layui-form" action="" method="post">
	      	<input id="id" name="id" type="hidden" value="${user.id }"/>
	      	<div class="layui-form-item">
			    <label class="layui-form-label">真实名称</label>
			    <div class="layui-input-inline">
			    	<input data-ready-exists="0" type="text" name="realName" id="realName" class="layui-input" placeholder="真实名称" value="${user.realName }">
			    </div>
			</div>
			<div class="layui-form-item">
			    <label class="layui-form-label">邮箱</label>
			    <div class="layui-input-inline">
			    	<input data-ready-exists="0" type="text" name="email" id="email" class="layui-input" placeholder="邮箱" value="${user.email }">
			    </div>
			</div>
			<div class="layui-form-item">
			    <label class="layui-form-label">联系电话</label>
			    <div class="layui-input-inline">
			    	<input data-ready-exists="0" type="text" name="phone" id="phone" class="layui-input" placeholder="联系电话" value="${user.phone }">
			    </div>
			</div>
			<div class="layui-form-item">
			    <div class="layui-input-block">
			      <button id="saveBtn" onclick="javascript:_user.saveBtn();" type="button" class="layui-btn">保 存</button>
					<button onclick="javascript:history.go(-1);" type="button" class="layui-btn layui-btn-primary">返 回</button>
			    </div>
			  </div>
	      </form>
	    </div>
	  </div>
	</div>
      <script type="text/javascript" src="${ctx }/public/js/system/account/info.js"></script>
      <script type="text/javascript" src="${ctx }/public/plugins/jquery.form.min.js"></script>
  </body>
</html>