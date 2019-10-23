<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>配送设置 - 后台管理系统</title>
<%@include file="../../../common/title.jsp"%>
</head>
<body>

	<div class="layui-card">
		<div class="layui-card-header">商家配送费用</div>
		<div class="layui-card-body">
			<form class="layui-form layui-form-pane" action="">
				<input type="hidden" name="id" value="${info0.id }"/>
				<input type="hidden" name="type" value="0"/>
				<div class="layui-form-item">
					<label class="layui-form-label">每单</label>
					<div class="layui-input-inline">
						<input type="number" name="val" placeholder="请输入商家每单配送费"
							autocomplete="off" class="layui-input" value="${info0.val }">
					</div>
					<div class="layui-form-mid layui-word-aux">元</div>
				</div>

				<div class="layui-form-item">
					<button class="layui-btn" type="button">确认保存</button>
				</div>
			</form>
		</div>
	</div>
	<div class="layui-card">
		<div class="layui-card-header">平台抽成</div>
		<div class="layui-card-body">
			<form class="layui-form layui-form-pane" action="">
				<input type="hidden" name="id" value="${info1.id }"/>
				<input type="hidden" name="type" value="1"/>
				<div class="layui-form-item">
					<label class="layui-form-label">每单</label>
					<div class="layui-input-inline">
						<input type="number" name="val" placeholder="每单配送费平台抽成"
							autocomplete="off" class="layui-input" value="${info1.val }">
					</div>
					<div class="layui-form-mid layui-word-aux">元</div>
				</div>

				<div class="layui-form-item">
					<button class="layui-btn" type="button">确认保存</button>
				</div>
			</form>
		</div>
	</div>
	<div class="layui-card">
		<div class="layui-card-header">配送范围</div>
		<div class="layui-card-body">
			<form class="layui-form layui-form-pane" action="">
				<input type="hidden" name="id" value="${info2.id }"/>
				<input type="hidden" name="type" value="2"/>
				<div class="layui-form-item">
					<label class="layui-form-label">周围</label>
					<div class="layui-input-inline">
						<input type="number" name="val" placeholder="请输入配送范围"
							autocomplete="off" class="layui-input" value="${info2.val }">
					</div>
					<div class="layui-form-mid layui-word-aux">米</div>
				</div>

				<div class="layui-form-item">
					<button class="layui-btn" type="button">确认保存</button>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript" src="${ctx }/public/plugins/jquery.form.min.js"></script>
	<script type="text/javascript" src="${ctx }/public/js/base.js"></script>
	<script type="text/javascript" src="${ctx }/public/js/delivery/index.js"></script>
</body>
</html>