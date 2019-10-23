<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="jstl.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set value="${pageContext.request.contextPath }" var="ctx" />
<script type="text/javascript">
	var path = "${ctx}";
</script>
<link href="${ctx }/public/plugins/layui/css/layui.css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx }/public/plugins/layui/layui.js"></script>