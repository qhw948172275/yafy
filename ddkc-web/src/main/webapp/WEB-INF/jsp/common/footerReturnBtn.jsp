<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="jstl.jsp"%>
<style type="text/css">
    body{
        padding-bottom: 60px !important;
    }
    .layui-footer {
        position: fixed;
        bottom: 0;
        width: 100%;
        height: 60px;
        background: #ccc;
        line-height: 60px;
        z-index: 1000;
    }
    .layui-footer ul {
        list-style: none;
        margin: 0 10%;
        width: 80%;
        display: inline-block;
        background: #ccc;
    }
    .layui-footer ul li {
        float: right;
    }
</style>
<footer id="footer" class="layui-footer">
    <!-- 底部固定区域btn -->
    <ul class="footer-row-menu">
        <li class="go-back"><a href="javascript:history.go(-1);" class="layui-btn layui-btn-primary">返回</a></li>
    </ul>
</footer>