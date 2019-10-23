<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/system/jstl.jsp" %>
<script src="${cdnHomeUrl }/public/libs/bower_components/jquery/dist/jquery.min.js"></script>
<script src="${cdnHomeUrl }/public/libs/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="${cdnHomeUrl }/public/libs/bower_components/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js"></script>
<!-- Placeholder for IE9 -->
<!--[if IE 9 ]>
<script src="${cdnHomeUrl }/public/libs/bower_components/jquery-placeholder/jquery.placeholder.min.js"></script>
<![endif]-->
<script src="${cdnHomeUrl }/public/libs/template/js/app.js"></script>
<script type="text/javascript" src="${ctx }/public/js/base.js"></script>

<script>
$(function(){
	// 在iframe中, 隐藏头部和菜单
	if (self != top) { 
		$("#header").hide();
		$("#sidebar").hide();
		$("#main").css("padding-top","0px");
		$("#content").css("padding-left","5px");
	} 
})
</script>
