<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="jstl.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set value="${pageContext.request.contextPath }" var="ctx" />
<c:set value="https://cdn.benecess.com" var="cdnHomeUrl" />
<script type="text/javascript">
	var path = "${ctx}";
</script>
<link href="${ctx }/public/plugins/layui-v2/css/layui.css"
	  rel="stylesheet">
<script type="text/javascript"
		src="${ctx }/public/plugins/layui-v2/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/public/plugins/jquery.min.js"></script>
<style>
	.demoTable .layui-form-label {
		width: auto;
	}

	.demoTable .width-sm {
		width: 120px;
	}

	.hide {
		display: none !important;
	}

	.layui-table img {
		width: 100%;
		max-height: 100px;
	}

	.layui-table-cell {
		height: auto;
	}

	.demoTable {
		margin-left: 15px;
	}

	.prew-imgs {

	}

	.prew-imgs:after {
		clear: both;
		display: block;
	}

	.prew-imgs .img-item {
		width: 120px;
		float: left;
		margin-right: 15px;
		height: 120px;
		position: relative;
		margin-top: 15px;
	}

	.prew-imgs .img-item img {
		width: 100%;
		height: 100%;
	}

	.prew-imgs .img-item a {
		position: absolute;
		right: -10px;
		top: -10px;
		color: red;
		border: 1px solid #ed0831;
		border-radius: 50%;
		padding: 2px;
		width: 10px;
		height: 10px;
		line-height: 8px;
		cursor: pointer;
	}

	form.layui-form select, .layui-inline select {
		display: block;
		height: 38px;
		line-height: 1.3;
		line-height: 38px;
		border-width: 1px;
		border-style: solid;
		background-color: #fff;
		border-radius: 2px;
		width: 100%;
	}
	.layui-form-item .layui-inline{
		margin-bottom: 0;
	}

	.layui-laypage-limits select {
		height: 26px;
		line-height: 26px;
		background: #fff;
	}

	.clear {
		clear: both;
		display: block;
	}

	.red {
		color: #ed0831;
	}

	.margin-top-20 {
		margin-top: 20px;
	}

	.center {
		text-align: center !important;
	}

	.right {
		float: right;
	}

	.right:after {
		clear: both;
		display: block;
		content: '';
	}

	.layui-laypage-limits select {
		height: -webkit-fill-available !important;
	}
</style>