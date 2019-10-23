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
  </head>
  <body>
  	<form class="layui-form" action="" method="post">
      <input id="id" name="id" type="hidden" value="${users.id }"/>
	  <div class="layui-form-item">
	    <label class="layui-form-label">用户昵称</label>
	    <div class="layui-input-inline">
	       <input data-ready-exists="0" type="text" autocomplete="off" name="name" id="name" class="layui-input" placeholder="登录名" value="${users.name }">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">密码</label>
	    <div class="layui-input-inline">
	      <c:choose>
          		<c:when test="${not empty users }">
          			<button type="button" id="updatePass" class="layui-btn layui-btn-primary layui-btn-sm" data="0" onclick="_user.updateNewPass($(this))">修改密码</button>
          			<input type="password" name="newPass" id="newPass" class="layui-input hide" placeholder="设置新密码">
          		</c:when>
          		<c:otherwise>
          			<input type="password" name="password" id="password" class="layui-input" placeholder="登录密码" value="${users.password }">
          		</c:otherwise>
          	</c:choose>
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">姓名</label>
	      <div class="layui-input-inline">
	         <input type="text" class="layui-input" id="realName" name="realName" placeholder="真实姓名" value="${users.realName }">
	      </div>
	    </div>
	   </div>
	   <div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">验证邮箱</label>
	      <div class="layui-input-inline">
	        <input data-ready-exists="0" type="email" class="layui-input" id="email" name="email" placeholder="邮箱" value="${users.email }">
	      </div>
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">联系电话</label>
	      <div class="layui-input-inline">
	        <input data-ready-exists="0" type="tel" class="layui-input" id="phone" name="phone" placeholder="联系电话" value="${users.phone }">
	      </div>
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">角色</label>
	    <div class="layui-input-block">
	    	<c:if test="${not empty roles }">
             <c:forEach items="${roles }" var="role">
              		<c:choose>
              			<c:when test="${not empty currentRoles }">
              				<c:set var="flag" value="false"></c:set>
              				<c:forEach items="${currentRoles }" var="crole">
              					<c:if test="${crole.id == role.id }">
              						<c:set var="flag" value="true"></c:set>
              					</c:if>
              				</c:forEach>
              				<input type="checkbox" name="rid" value="${role.id }" lay-skin="primary" <c:if test="${flag }">checked="checked"</c:if>><div class="layui-unselect layui-form-checkbox <c:if test="${flag }">layui-form-checked</c:if>" lay-skin="primary"><span>${role.roleName }</span><i class="layui-icon layui-icon-ok"></i></div>
              			</c:when>
              			<c:otherwise>
              				<input type="checkbox" name="rid" value="${role.id }" lay-skin="primary"><div class="layui-unselect layui-form-checkbox" lay-skin="primary"><span>${role.roleName }</span><i class="layui-icon layui-icon-ok"></i></div>
              			</c:otherwise>
              		</c:choose>
              </c:forEach>
           </c:if>
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">状态</label>
	    <div class="layui-input-block">
	    	<c:choose>
               <c:when test="${not empty users && users.status == 0 }">
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
	    <div class="layui-input-block">
	      <button onclick="_user.saveBtn()" type="button" class="layui-btn">保 存</button>
			<button onclick="history.go(-1);" type="button" class="layui-btn">返 回</button>
	    </div>
	  </div>
	</form>
	<script type="text/javascript" src="${ctx }/public/js/base.js"></script>
    <script type="text/javascript" src="${ctx }/public/js/system/user/info.js"></script>
    <script type="text/javascript" src="${ctx }/public/plugins/jquery.form.min.js"></script>
  </body>
</html>