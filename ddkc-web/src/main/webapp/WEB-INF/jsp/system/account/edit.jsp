<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
  <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>用户管理</title>
      <%@include file="../../common/title.jsp" %>
      <link href="${ctx }/public/css/system/user/info.css" rel="stylesheet">
  </head>
  <body>
      <%--<%@include file="../../header.jsp" %>--%>
      <section id="main">
      	  <%--<%@include file="../../sidebar.jsp" %>--%>
          <section id="content">
              <div class="container">
                    <div class="card">
                    	<div class="card-header">
                            <h2>编辑账号
                            </h2>
                        </div>
                        <form method="post" class="form-horizontal" role="form" action="${ctx }/system/account/save">
                        	<input id="id" name="id" type="hidden" value="${user.id }"/>
                            <div class="card-body card-padding">
                                <div class="form-group">
                                    <label for="name" class="col-sm-2 control-label">用户昵称</label>
                                    <div class="col-sm-5">
                                        <div class="fg-line">
                                            <input data-ready-exists="0" type="text" name="name" id="name" class="form-control input-sm" placeholder="登录名" value="${user.name }">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="realName" class="col-sm-2 control-label">姓名</label>
                                    <div class="col-sm-5">
                                        <div class="fg-line">
                                            <input type="text" class="form-control input-sm" id="realName" name="realName" placeholder="真实姓名" value="${user.realName }">
                                        </div>
                                    </div>
                                </div>
								<div class="form-group">
                                    <label for="email" class="col-sm-2 control-label">邮箱</label>
                                    <div class="col-sm-5">
                                        <div class="fg-line">
                                            <input data-ready-exists="0" type="email" class="form-control input-sm" id="email" name="email" placeholder="邮箱" value="${user.email }">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="phone" class="col-sm-2 control-label">联系电话</label>
                                    <div class="col-sm-5">
                                        <div class="fg-line">
                                            <input data-ready-exists="0" type="tel" class="form-control input-sm" id="phone" name="phone" placeholder="联系电话" value="${user.phone }">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="errorMsg" class="col-sm-2 control-label"></label>
                                    <div class="col-sm-5">
                                        <div class="fg-line red" id="errorMsg">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button onclick="_user.saveBtn()" type="button" class="btn btn-primary btn-sm waves-effect">保 存</button>
                                        <button onclick="javascript:history.go(-1);" type="button" class="layui-btn layui-btn-primary">返 回</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
              </div>
          </section>
      </section>
      <%@include file="../common/footer.jsp" %>
      <script type="text/javascript" src="${ctx }/public/js/system/account/info.js"></script>
      <script type="text/javascript" src="${ctx }/public/libs/jquery.form.min.js"></script>
  </body>
</html>