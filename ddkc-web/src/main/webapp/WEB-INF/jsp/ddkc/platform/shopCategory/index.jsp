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
      <%@include file="../../../common/footerReturnBtn.jsp"%>
  </head>
  <body>
  <div class="demoTable">
	  搜索类别：
	  <div class="layui-inline">
		  <input class="layui-input" name="keyword" id="keyword" autocomplete="off" placeholder="输入类别名称">
	  </div>
	  <button class="layui-btn" data-type="reload">搜索</button>

   <table id="userTables" lay-filter="userFilter"></table>
      <script type="text/javascript">
      layui.use(['table','element'], function(){
    	  var table = layui.table;
    	  var element=layui.element;
    	  var $=layui.jquery;
    	  //第一个实例
    	  table.render({
    	    elem: '#userTables'
    	    ,url: path + '/platform/shopCategory?ajaxLoad&shopId=${shopId}' //数据接口
    	    ,cellMinWidth:60
    	    ,cols: [[ //表头
                  {type: 'numbers', title: '序号'}
				  ,{field: 'name', title: '分类名称'}
				  ,{field: 'status', title: '状态',templet:function (row) {
                          if(row.status==0){
                              return '启用';
                          }else{
                              return '禁用';
                          }
                      }}
				  ,{title:'操作',align:'center',templet:function (row) {

                          if(row.status==0){
                              return'<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">禁用</a>';
                          }else{
                              return'<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="enable">启用</a>';
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
   		    if(obj.event === 'del'){
   		      layer.confirm('确认禁用？', function(index){
   		        layer.close(index);
	   		     $.ajax({
						url:path + "/platform/shopCategory/changStatus?shopCosId=" + data.id+'&status=1',
						type:'get',
						dataType:'json',
						success:function(res){
							if(res.result == "ok"){
								layer.alert('禁用成功',function () {
                                    window.location.reload();
                                });
							}else{
								layer.msg('禁用失败');
							}
						}
				  });
   		      });
   		    }else if(obj.event==='enable'){
                layer.confirm('确认启用？', function(index){
                    layer.close(index);
                    $.ajax({
                        url:path + "/platform/shopCategory/changStatus?shopCosId=" + data.id+'&status=0',
                        type:'get',
                        dataType:'json',
                        success:function(res){
                            if(res.result == "ok"){
                                layer.alert('启用成功',function () {
                                    window.location.reload();
                                });
                            }else{
                                layer.msg('启用失败');
                            }
                        }
                    });
                });
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