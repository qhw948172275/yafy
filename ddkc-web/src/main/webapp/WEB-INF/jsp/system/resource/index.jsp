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
      <link href="${ctx }/public/css/system/resource/index.css" rel="stylesheet">
  </head>
  <body>
      <%--<%@include file="../../header.jsp" %>--%>
      <section id="main">
      	  <%--<%@include file="../../sidebar.jsp" %>--%>
          <section id="content">
              <div class="container">
                    <div class="card">
                        <div class="card-header">
                            <h2>资源列表</h2>
                            <ul class="actions">
	                            <li>
	                            <shiro:hasPermission name="/system/resource/add">
	                                <a href="${ctx }/system/resource/add" class="btn btn-xs btn-default">
	                                     <span> 添加</span>
	                                </a>
	                                </shiro:hasPermission>
	                            </li>
                            </ul>
                        </div>
                        <div class="card-body table-responsive">
                        	<form action="">
                        		<input type="hidden" name="pageSize" id="pageSize" value=""/>
                        		<input type="hidden" name="currentPage" id="currentPage" value=""/>
                        	</form>
                            <table class="table">
                                <thead>
	                                <tr>
	                                    <th>id</th>
	                                    <th>资源名称</th>
	                                    <th>资源路径</th>
	                                    <th>基础功能</th>
	                                    <th>状态</th>
	                                    <th>操作</th>
	                                </tr>
                                </thead>
                                <tbody>
                                <c:choose>
                                	<c:when test="${not empty resourceList }">
                                		<c:forEach items="${resourceList }" var="resource">
	                                		<tr>
			                                    <td>${resource.id }</td>
			                                    <td>${resource.resourceName }</td>
			                                    <td>${resource.resourceUrl }</td>
			                                    <td>${resource.isBasic }</td>
			                                    <td>${(resource.status == 0) ? '启用' : '禁用' }</td>
			                                    <td>
			                                    <shiro:hasPermission name="/system/resource/edit">
			                                    	<a href="${ctx }/system/resource/edit?id=${resource.id}" class="btn btn-info btn-xs">编辑</a>
			                                    	</shiro:hasPermission>
			                                    	<shiro:hasPermission name="/system/resource/delete">
			                                    	<a onclick="_resource.delResource('${resource.id}')" class="btn btn-danger btn-xs">删除</a>
			                                    	</shiro:hasPermission>
			                                    </td>
			                                </tr>
	                                	</c:forEach>
                                	</c:when>
                                	<c:otherwise>
                                		<tr>
		                                    <td colspan="6">暂无数据</td>
		                                </tr>
                                	</c:otherwise>
                                </c:choose>
                                </tbody>
                                <tfoot>
                                	<tr>
                                		<td colspan="6"><%@include file="../page.jsp" %></td>
                                	</tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
              </div>
          </section>
      </section>
      <%@include file="../../common/footer.jsp" %>
      <script type="text/javascript" src="${ctx }/public/js/system/resource/index.js"></script>
  </body>
</html>