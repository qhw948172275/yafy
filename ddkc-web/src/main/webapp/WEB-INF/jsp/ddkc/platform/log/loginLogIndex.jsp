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
      <%@include file="../../../common/title.jsp"%>
      <link href="${ctx }/public/css/system/user/index.css" rel="stylesheet">
  </head>
  <body>
  <div class="demoTable">
	  搜索用户：
	  <div class="layui-inline">
		  <input class="layui-input" name="keyword" id="keyword" autocomplete="off" placeholder="用户登录名或真实名">
	  </div>

	  <button class="layui-btn" data-type="reload">搜索</button>

   <table id="userTables" lay-filter="userFilter"></table>
      <script type="text/javascript">
      layui.use(['table','element'], function(){
    	  var table = layui.table;

    	  var $=layui.jquery;

    	  //第一个实例
    	  table.render({
    	    elem: '#userTables'
    	    ,url: path + '/platform/log/loginLogData' //数据接口
    	    ,cellMinWidth:60
    	    ,cols: [[ //表头
				   {field: 'account', title: '登录账号'}
				  ,{field: 'realName', title: '真实姓名'}
				  ,{field: 'loginTime', title: '登录时间'}
				  ,{field: 'loginIp', title: '登录IP'}
				  ,{field: 'province', title: '省份'}
                  ,{field: 'address', title: '地址'}

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
    	  
    	  var $ = layui.$, active = {
   		        reload: function(){
   		            var keyword = $('#keyword');

   		            table.reload('userTables', {
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
  </div>
  </body>
</html>