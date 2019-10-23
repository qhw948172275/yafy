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
	  搜索商品：
	  <div class="layui-inline">
		  <input class="layui-input" name="goodsName" id="goodsName" autocomplete="off" placeholder="输入商品名称">
	  </div>
      <div class="layui-inline">
          <input class="layui-input" name="shopName" id="shopName" autocomplete="off" placeholder="输入店铺名称">
      </div>
	  <button class="layui-btn" data-type="reload">搜索</button>

   <table id="userTables" lay-filter="userFilter"></table>
      <c:if test="${shopId != -1}">
          <%@include file="../../../common/footerReturnBtn.jsp"%>
      </c:if>
      <script type="text/javascript">
      layui.use(['table','element'], function(){
    	  var table = layui.table;
    	  var element=layui.element;
    	  var $=layui.jquery;
    	  //第一个实例
    	  table.render({
    	    elem: '#userTables'
    	    ,url: path + '/platform/goods?ajaxLoad&shopId=${shopId}' //数据接口
    	    ,cellMinWidth:60
    	    ,cols: [[ //表头
                  {type: 'numbers', title: '序号'}
				  ,{field: 'goodsName', title: '商品名称'}
                  ,{field: 'shopName', title: '店铺名称'}
                  ,{field: 'shopId', title: '店铺ID'}
                  ,{field: '', title: '商品图片',templet:function (row) {
                          return '<a href="goods/detail?goodsId=+'+row.id+' ">查看详情</a>';
                      }}
                  ,{field: 'price', title: '商品原价',templet:function (row) {
                          return row.price+'元/'+row.quanity;
                      }}
                  ,{field: 'salesPrice', title: '商品价格',templet:function (row) {
                          return row.salesPrice+'元/'+row.quanity;
                      }}
                  ,{field: 'stock', title: '库存量'}
                  ,{field: 'salesCounts', title: '销量'}
				  ,{title:'操作',align:'center',templet:function (row) {

                          if(row.status==0){
                              return'<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">下架</a>';
                          }else{
                              return'<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="enable">上架</a>';
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
   		      layer.confirm('确认下架？', function(index){
   		        layer.close(index);
	   		     $.ajax({
						url:path + "/platform/goods/changStatus?goodsId=" + data.id+'&status=1',
						type:'get',
						dataType:'json',
						success:function(res){
							if(res.result == "ok"){
								layer.alert('下架成功',function () {
                                    window.location.reload();
                                });
							}else{
								layer.msg('下架失败');
							}
						}
				  });
   		      });
   		    }else if(obj.event==='enable'){
                layer.confirm('确认上架？', function(index){
                    layer.close(index);
                    $.ajax({
                        url:path + "/platform/goods/changStatus?goodsId=" + data.id+'&status=0',
                        type:'get',
                        dataType:'json',
                        success:function(res){
                            if(res.result == "ok"){
                                layer.alert('上架成功',function () {
                                    window.location.reload();
                                });
                            }else{
                                layer.msg('上架失败');
                            }
                        }
                    });
                });
            }

   		  });
    	  
    	  var $ = layui.$, active = {
   		        reload: function(){
   		            var shopName = $('#shopName');
                    var goodsName = $('#goodsName');
   		            table.reload('userTables', {
   		                where: {
                            goodsName: goodsName.val(),
                            shopName:shopName.val()
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