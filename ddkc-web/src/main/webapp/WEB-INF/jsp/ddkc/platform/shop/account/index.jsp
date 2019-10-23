<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
  <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <meta http-equiv="pragma" content="no-cache">
      <meta http-equiv="cache-control" content="no-cache">
      <meta http-equiv="expires" content="0">
      <title>用户管理</title>
      <%@include file="../../../../common/title.jsp"%>
      <link href="${ctx }/public/css/system/user/index.css" rel="stylesheet">
  </head>
  <body>
  <div class="demoTable">
	  <shiro:hasPermission name="/platform/shop/add">
		  <a href="${ctx }/platform/shop/account/add?shopId=${shopId}" class="layui-btn">
			  添加店铺账号
		  </a>
	  </shiro:hasPermission>
	  <input id="shopId" type="hidden" value="${shopId }"/>
   <table id="userTables" lay-filter="userFilter"></table>
      <script type="text/javascript">
      layui.use(['table','element'], function(){
    	  var table = layui.table;
    	  var element=layui.element;
    	  var $=layui.jquery;
    	  //第一个实例
    	  table.render({
    	    elem: '#userTables'
    	    ,url: path + '/platform/shop/account?ajaxLoad&shopId='+$('#shopId').val() //数据接口
    	    ,cellMinWidth:60
    	    ,cols: [[ //表头
				  {field: 'id', title: 'ID'}
				  ,{field: 'account', title: '账号'}
				  ,{field: 'isMaster', title: '主账号',templet:function(row){
					  if(row.isMaster == 0){
						  return '否';
					  }
					  return '是';
				  }}
				  ,{field: 'isNotice', title: '接收语音通知',templet:function(row){
					  if(row.isNotice == 0){
						  return '否';
					  }
					  return '是';
				  }}
				  ,{field: 'status', title: '状态',templet:function(row){
					  if(row.status == 0){
						  return '启用';
					  }
					  return '禁用';
				  }}
				  ,{field: 'createTime', title: '创建时间'}
				  ,{title:'操作',align:'center',templet:function (row) {
					  var htm = '';
					  if(row.isMaster == 1){
						  return '<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>';
					  }else if(row.status == 0){
						  return '<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a><a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="changeStatus">禁用</a>';
					  }else{
						  return ' <a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="changeStatus">启用</a><a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="delete">删除</a>';
					  }
                  }}
    	    ]]
    	    ,skin: 'row' //表格风格
            ,even: true
            ,page: true //是否显示分页
            ,limits: [10,20,50]
            ,limit: 10 //每页默认显示的数量
            ,done:function(res){
               // return {code: 0,msg:'',data: res.object.list, count: res.object.total};
             }
    	  });
    	  table.on('tool(userFilter)', function(obj){
   		    var data = obj.data;
   		    if(obj.event === 'edit'){
   		      window.location.href = path +"/platform/shop/account/edit?id=" + data.id;
   		    }else if(obj.event==='changeStatus'){
   		    	var status = 0;
   		    	if(data.status == 0){
   		    		status = 1;
   		    	}
   		    	$.ajax({
   		    		url:path+'/platform/shop/account/changStatus?id='+data.id+'&status='+status,
   		    		type:'get',
   		    		dataType:'json',
   		    		success:function(res){
   		    			if(res.result = 'ok'){
   		    				layer.alert('操作成功');
   		    				table.reload('userTables');
   		    			}else{
   		    				layer.alert('操作失败');
   		    			}
   		    		}
   		    	});
            }else if(obj.event === 'delete'){
            	layer.confirm('确认删除此账号吗？',{icon: 3, title:'提示'}, function(index){
            		  $.ajax({
            			  url:path+'/platform/shop/account?id='+data.id,
            			  type:'delete',
            			  dataType:'json',
            			  success:function(res){
            				  if(res.result == 'ok'){
            					  layer.alert('删除成功');
            					  table.reload('userTables');
            				  }else{
            					  layer.alert('删除失败');
            				  }
            			  }
            		  });
            		  layer.close(index);
            	});
            }
   		  });
    	  
      });
      </script>
  </body>
</html>