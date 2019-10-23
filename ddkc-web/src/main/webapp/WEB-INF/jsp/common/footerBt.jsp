<%--
  bootstrap 插件引入版本
  User: 韩峰
  Date: 2018/3/3
  Time: 15:23
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/system/jstl.jsp" %>
<script src="${cdnHomeUrl}/public/libs/bower_components/jquery/dist/jquery.min.js"></script>
<script src="${spl}/public/libs/vue.min.js"></script>
<script src="${spl}/public/libs/bootstrap/bootstrap-3.3.5/js/bootstrap.min.js"></script>
<script src="${spl}/public/libs/bootstrap/bootstrap-select/js/bootstrap-select.min.js"></script>
<script src="${spl}/public/libs/bootstrap/bootstrap-select/js/i18n/defaults-zh_CN.min.js"></script>
<script src="${spl}/public/libs/layer-v3.1.1/layer/layer.js"></script>
<script src="${spl}/public/js/utils/layer.extend.js"></script>
<!-- Placeholder for IE9 -->
<!--[if IE 9 ]>
<script src="${cdnHomeUrl}/public/libs/bower_components/jquery-placeholder/jquery.placeholder.min.js"></script>
<![endif]-->
<script type="text/javascript" src="${spl}/public/js/utils/base.js"></script>


<%-- 注意 : 公共头 请乎引入[专题]插件, 如:bootstrap-table列表插件, bootstrap-validator表单插件--%>

<script>
    $(function () {
        // 在iframe中, 隐藏头部和菜单
        if (self != top) {
            $("#header").hide();
            $("#sidebar").hide();
            $("#main").css("padding-top", "0px");
            $("#content").css("padding-left", "5px");
        }
    })
</script>
