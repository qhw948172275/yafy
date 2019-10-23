<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>用户管理</title>
    <%@include file="../../common/titleBt.jsp" %>
    <%@include file="../../common/titleBtList.jsp" %>
</head>
<body>

<div class="container-fluid">
    <ol class="breadcrumb"><%--bootstrap 路径导航 .active 当前--%>
        <li class="active">子系统管理</li>
        <%--<li><a href="">Library</a></li>--%>
        <%--<li class="active">Data</li>--%>
    </ol>
    <div class="">
        <%-- table 表格搜索表单 start--%>
        <div class="panel panel-default" style="margin-bottom: 0px">
            <%--<div class="panel-heading">搜索</div>--%>
            <div class="panel-body">
                <form id="searchForm" class="form-horizontal">
                    <div class="form-group">

                        <div class="col-sm-4">
                            <label class="control-label col-sm-4 col-lg-3">系统key</label>
                            <div class="col-sm-8">
                                <input name="name" type="text" class="form-control input-sm"
                                       placeholder="系统key"/>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <label class="control-label col-sm-4 col-lg-3">系统名称</label>
                            <div class="col-sm-8">
                                <input name="value" type="text" class="form-control input-sm"
                                       placeholder="系统名称"/>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <label class="control-label col-sm-4 col-lg-3">访问路径</label>
                            <div class="col-sm-8">
                                <input name="url" type="text" class="form-control input-sm"
                                       placeholder="访问路径"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group" style="margin-bottom: 0px">
                        <div class="col-sm-4 col-sm-offset-4">
                            <button type="button" class=" center-block btn btn-info btn-sm" onclick="_subSystem.refresh();">搜索</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <%-- table 表格搜索表单  end --%>

        <%--table 按钮工具栏 start--%>

        <div id="bootTableTool" class="btn-toolbar" role="toolbar" aria-label="Bootstrap Table Tool">
            <div class="btn-group" role="group" aria-label="Bootstrap Table Btn Group">
                <%--<shiro:hasPermission name="/system/subSystem/insert">--%>
                <%--</shiro:hasPermission>--%>
                <button type="button" class="btn btn-default btn-sm" onclick="_subSystem.refresh();">刷新</button>
                <button type="button" class="btn btn-default btn-sm" onclick="_subSystem.openForm('add')">新增</button>
                <button type="button" class="btn btn-default btn-sm" onclick="_subSystem.openForm('edit')">编辑</button>
                <button type="button" class="btn btn-default btn-sm" onclick="_subSystem.delList()">删除</button>
            </div>
            <div class="btn-group" role="group" aria-label="占位空格,请勿删除,请勿写入">
            </div>
        </div>

        <%-- table 按钮工具栏  end --%>

        <%--table 数据 表格 start --%>
        <table id="bootTable" class="table <%--table-condensed --%>table-responsive">

        </table>
        <%--table 数据 表格  end  --%>
    </div>
</div>

<jsp:include page="info.jsp"/>

<%@include file="../../common/footerBt.jsp" %>
<%@include file="../../common/footerBtList.jsp" %>
<script type="text/javascript" src="${spl}/public/js/system/subSystem/index.js"></script>
</body>
</html>