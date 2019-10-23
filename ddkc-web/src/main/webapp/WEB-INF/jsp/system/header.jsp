<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="jstl.jsp" %>
<header class="layui-header">
    <%--系统log start--%>
    <div class="layui-logo">叮咚快超后台业务管理系统</div>
    <div class="layui-logo kit-logo-mobile">叮咚快超管理系统</div>
    <%--系统log  end --%>

    <%--顶部导航条 start --%>
    <ul id="topNavbar" class="layui-nav layui-layout-left kit-nav">
    </ul>
    <%--顶部导航条  右侧  start --%>
    <ul class="layui-nav layui-layout-right kit-nav">
        <li class="layui-nav-item">
            <a href="javascript:;">
                <i class="layui-icon">&#xe63f;</i> 皮肤
            </a>
            <dl class="layui-nav-child skin">
                <dd><a href="javascript:;" data-skin="default" style="color:#393D49;"><i class="layui-icon">&#xe658;</i>
                    默认</a></dd>
                <dd><a href="javascript:;" data-skin="orange" style="color:#ff6700;"><i class="layui-icon">&#xe658;</i>
                    橘子橙</a></dd>
                <dd><a href="javascript:;" data-skin="green" style="color:#00a65a;"><i class="layui-icon">&#xe658;</i>
                    原谅绿</a></dd>
                <dd><a href="javascript:;" data-skin="pink" style="color:#FA6086;"><i class="layui-icon">&#xe658;</i>
                    少女粉</a></dd>
                <dd><a href="javascript:;" data-skin="blue.1" style="color:#00c0ef;"><i class="layui-icon">&#xe658;</i>
                    天空蓝</a></dd>
                <dd><a href="javascript:;" data-skin="red" style="color:#dd4b39;"><i class="layui-icon">&#xe658;</i> 枫叶红</a>
                </dd>
            </dl>
        </li>
        <li class="layui-nav-item">
            <shiro:user>
                <a href="javascript:;">
                    <img src="http://m.zhengjinfan.cn/images/0.jpg" class="layui-nav-img"> <shiro:principal/>
                </a>
            <dl class="layui-nav-child">
                <dd><a href="javascript:;" kit-target
                       data-options="{url:'basic.html',icon:'&#xe658;',title:'基本资料',id:'966'}"><span>基本资料</span></a>
                </dd>
                <dd><a href="javascript:;">安全设置</a></dd>
            </dl>
            </shiro:user>
        </li>
        <li class="layui-nav-item"><a href="${ctx}/logout"><i class="fa fa-sign-out" aria-hidden="true"></i> 注销</a></li>
    </ul>
    <%--顶部导航条  右侧 end  --%>

    <%--顶部导航条  end  --%>
</header>
