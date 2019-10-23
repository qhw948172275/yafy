<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
  <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>角色管理</title>
      <%@include file="../../common/title.jsp" %>
      <link rel="stylesheet" href="${ctx }/public/plugins/zTree/css/zTreeStyle/zTreeStyle.css" />
      <link href="${ctx }/public/css/system/role/info.css" rel="stylesheet">
  </head>
  <body>
  	<div class="layui-container">  
	  <div class="layui-row">
	    <div class="layui-col-md5">
	      <form class="layui-form" action="" method="post">
	      	<input id="id" name="id" type="hidden" value="${role.id }"/>
	      	<div class="layui-form-item">
			    <label class="layui-form-label">角色名称</label>
			    <div class="layui-input-inline">
			    	<input data-ready-exists="0" type="text" name="roleName" id="roleName" class="layui-input" placeholder="角色名称" value="${role.roleName }">
			    </div>
			</div>
			<div class="layui-form-item">
			    <label class="layui-form-label">备注</label>
			    <div class="layui-input-inline">
			    	<textarea placeholder="请输入内容" class="layui-textarea" name="description">${role.description }</textarea>
			    </div>
			</div>
			<div class="layui-form-item">
			    <label class="layui-form-label">状态</label>
			    <div class="layui-input-block">
			    	<c:choose>
		               <c:when test="${not empty role && role.status == 0 }">
		               	   <input type="radio" name="status" value="0" checked="checked"><div class="layui-unselect layui-form-radio layui-form-radioed"><i class="layui-anim layui-icon"></i><div>启用</div></div>
		               	   <input type="radio" name="status" value="1"><div class="layui-unselect layui-form-radio"><i class="layui-anim layui-icon"></i><div>禁用</div></div>
		                </c:when>
		                <c:otherwise>
		                   <input type="radio" name="status" value="0" checked="checked"><div class="layui-unselect layui-form-radio layui-form-radioed"><i class="layui-anim layui-icon"></i><div>启用</div></div>
		               	   <input type="radio" name="status" value="1" ><div class="layui-unselect layui-form-radio"><i class="layui-anim layui-icon"></i><div>禁用</div></div>
		                </c:otherwise>
		            </c:choose>
			    </div>
			  </div>
			  <div class="layui-form-item">
			  	<input type="hidden" name="resourceArr" id="resourceArr" />
			    <div class="layui-input-block">
			      <button onclick="_role.saveBtn()" type="button" class="layui-btn">保 存</button>
				  <button onclick="history.go(-1);" type="button" class="layui-btn">返 回</button>
			    </div>
			  </div>
	      </form>
	    </div>
	    <div class="layui-col-md3">
	      所属权限:&nbsp;
			<div id="permissionsList" class="zTreeDemoBackground left">
				<ul id="tree" class="ztree">
				</ul>
			</div>
	    </div>
	  </div>
	</div>
      <script type="text/javascript" src="${ctx }/public/js/system/role/info.js"></script>
      <script type="text/javascript" src="${ctx }/public/plugins/jquery.form.min.js"></script>
      <script type="text/javascript" src="${ctx }/public/plugins/zTree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${ctx }/public/plugins/zTree/js/jquery.ztree.excheck-3.5.js"></script>
  </body>
</html>