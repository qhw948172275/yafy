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
	  搜索店铺：
	  <div class="layui-inline">
		  <input class="layui-input" name="keyword" id="keyword" autocomplete="off" placeholder="商铺名称或商铺ID号">
	  </div>
	  <button class="layui-btn" data-type="reload">搜索</button>
	  <shiro:hasPermission name="/platform/shop/add">
		  <a href="${ctx }/platform/shop/add" class="layui-btn">
			  添加店铺
		  </a>
	  </shiro:hasPermission>
   <table id="userTables" lay-filter="userFilter"></table>
      <script type="text/javascript">
      layui.use(['table','element'], function(){
    	  var table = layui.table;
    	  var element=layui.element;
    	  var $=layui.jquery;
    	  //第一个实例
    	  table.render({
    	    elem: '#userTables'
    	    ,url: path + '/platform/shop?ajaxLoad' //数据接口
    	    ,cellMinWidth:60
    	    ,cols: [[ //表头
				  {field: 'name', title: '商铺名称'}
				  ,{field: 'id', title: '商铺ID号'}
				  ,{field: 'contact', title: '联系人'}
				  ,{field: 'phone', title: '登录账号'}
				  ,{field: 'createTime', title: '入驻时间'}
				  ,{field: '', title: '商品类别',templet:function (row) {

                          return '<a href="shopCategory?shopId='+row.id+'">查看详情</a>';
                      }}
				  ,{field: 'estate', title: '所在社区'}
				  ,{field:'wxaqrcode',title:'二维码下载',templet:function (row) {
				          if(typeof(row.wxaqrcode)!='undefined' && row.wxaqrcode!=0){
                              return '<a target="brank_" href="'+row.wxaqrcode+'"' +
                              '  download="二维码.png" >' +
                              '二维码下载</a>';
                          }else{
                              return '还未生成二维码';
                          }
                      }}
				  ,{title:'操作',align:'center',width:220,templet:function (row) {
				      var edit=' <shiro:hasPermission name="/platform/shop/edit">\n' +
                          '\t\t  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delShop">删除</a>  <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>\n' +
                          '\t   </shiro:hasPermission>';
                          edit+=' <shiro:hasPermission name="/platform/shop/account">\n' +
                          '\t\t   <a class="layui-btn layui-btn-xs" lay-event="account">账号管理</a>\n' +
                          '\t   </shiro:hasPermission>';
                          var goods='<a class="layui-btn layui-btn-xs" lay-event="goods">商品管理</a>';
                          if(row.status==0){
                              return  edit+' <shiro:hasPermission name="/platform/shop/delete">\n' +
                                  '\t\t   <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">禁用</a>\n' +
                                  '\t   </shiro:hasPermission>'+goods;
                          }else{
                              return  edit+' <shiro:hasPermission name="/platform/shop/delete">\n' +
                                  '\t\t   <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="enable">启用</a>\n' +
                                  '\t   </shiro:hasPermission>'+goods;
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
   		      window.location.href = path +"/platform/shop/edit?shopId=" + data.id;
   		    }else if(obj.event==='goods'){
   		        window.location.href=path+"/platform/goods?shopId="+data.id;
            }else if(obj.event==='account'){
   		        window.location.href=path+"/platform/shop/account?shopId="+data.id;
            }else if(obj.event === 'delShop'){
                layer.confirm('是否删除', function(index){
                    layer.close(index);
                    $.ajax({
                        url:path + "/platform/shop/delShop?shopId=" + data.id,
                        type:'get',
                        dataType:'json',
                        success:function(res){
                            if(res.result == "ok"){
                                location.reload();
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
  </body>
</html>