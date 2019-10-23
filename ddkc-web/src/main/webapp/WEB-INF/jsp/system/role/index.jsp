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
      <link href="${ctx }/public/css/system/role/index.css" rel="stylesheet">
  </head>
  <body>
  	<div class="demoTable">
	    搜索角色：
	    <div class="layui-inline">
	        <input class="layui-input" name="keyword" id="keyword" autocomplete="off" placeholder="角色名称">
	    </div>
	    <button class="layui-btn" data-type="reload">搜索</button>
	    <shiro:hasPermission name="/system/role/add">
	       <a href="${ctx }/system/role/add" class="layui-btn">
	            添加角色
	       </a>
	   </shiro:hasPermission>
	</div>
  	<table id="roleTables" lay-filter="roleFilter"></table>
      <script type="text/html" id="barDemo">
		<shiro:hasPermission name="/system/role/edit">
       		<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="/system/role/delete">
      		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
		</shiro:hasPermission>
   	  </script>
      <script type="text/javascript">
      layui.use('table', function(){
    	  var table = layui.table;
    	  //第一个实例
    	  table.render({
    	    elem: '#roleTables'
    	    ,url: path + '/system/role?ajaxLoad' //数据接口
    	    ,cellMinWidth:60
    	    ,cols: [[ //表头
    	      {field: 'id', title: '编号'}
    	      ,{field: 'roleName', title: '角色名称'}
    	      ,{field: 'description', title: '备注'}
    	      ,{field: 'status', title: '状态',templet:function(row){
    	    	  return (row.status == 0) ? '启用' : '禁用';
    	      }} 
    	      ,{title:'操作',align:'center', toolbar: '#barDemo'}
    	    ]]
    	    ,skin: 'row' //表格风格
            ,even: true
            ,page: true //是否显示分页
            ,limits: [3,5,10]
            ,limit: 10 //每页默认显示的数量
			,done:function(res){
				  // return {code: 0,msg:'',data: res.object.list, count: res.object.total};
			  }
    	  });
    	  table.on('tool(roleFilter)', function(obj){
   		    var data = obj.data;
   		    if(obj.event === 'edit'){
   		      window.location.href = path +"/system/role/edit?id=" + data.id;
   		    } else if(obj.event === 'del'){
   		      layer.confirm('真的删除行么', function(index){
   		        layer.close(index);
	   		     $.ajax({
						url:path + "/system/role/delete?id=" + data.id,
						type:'get',
						dataType:'json',
						success:function(res){
							if(res.result == "ok"){
								layer.msg('删除成功');
								obj.del();
							}else{
								layer.msg('删除失败');
							}
						}
				  });
   		      });
   		    }
   		  });
    	  
    	  var $ = layui.$, active = {
   		        reload: function(){
   		            var keyword = $('#keyword');
   		            table.reload('roleTables', {
   		                where: {
							keyword: keyword.val()
   		                }
   		            });
   		        }
   		    };
    	  
    	  $('.demoTable .layui-btn').on('click', function(){
    		    var type = $(this).data('type');
    		    active[type] ? active[type].call(this) : '';
    		});
    	});
      </script>
  </body>
</html>