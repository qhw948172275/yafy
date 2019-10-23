<%--
  bootstrap 插件引入版本
  User: 韩峰
  Date: 2018/3/3
  Time: 15:23
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/system/jstl.jsp" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set value="${pageContext.request.contextPath }" var="ctx"/>

<%--预留用作静态服务器路径变量 static path url--%>
<c:set value="${pageContext.request.contextPath }" var="spl"/>

<c:set value="https://cdn.hlhdj.cn" var="cdnHomeUrl"/>

<script type="text/javascript">
    var path = "${ctx}";
</script>
<link href="${spl}/public/libs/bootstrap/bootstrap-3.3.5/css/bootstrap.min.css" rel="stylesheet">
<link href="${spl}/public/libs/bootstrap/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet">
<link href="${spl}/public/libs/layer-v3.1.1/layer/theme/default/layer.css" rel="stylesheet">


<%-- 注意 : 公共头 请乎引入[专题]插件, 如:bootstrap-table列表插件, bootstrap-validator表单插件--%>
