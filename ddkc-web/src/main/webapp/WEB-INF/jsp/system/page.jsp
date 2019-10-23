<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="jstl.jsp"%>
<c:choose>
	<c:when test="${not empty page && page.totalPage > 0 }">
		<div>
			<ul class="pagination lg-pagination" data-current-page="${page.currentPage }" data-page-size="${page.pageSize }"
				data-total-page="${page.totalPage }">
			</ul>
			
			<div class="page-go">
				<input type="number" style="width: 50px;" id="pageCurrent" value="${page.currentPage + 1}">
				<button type="button" onclick="_page.goPage()">GO</button> 
			</div>
			
			<div style="clear: both;"></div>
		</div>
	</c:when>
</c:choose>