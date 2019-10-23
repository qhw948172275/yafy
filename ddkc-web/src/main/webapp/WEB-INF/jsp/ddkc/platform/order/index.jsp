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
	  搜索订单：
	  <div class="layui-inline">
		  <input class="layui-input" name="shopName" id="shopName" autocomplete="off" placeholder="商铺名称">
	  </div>
      <div class="layui-inline">
          <input class="layui-input" name="shopId" id="shopId" autocomplete="off" placeholder="商铺ID">
      </div>
      <div class="layui-inline">
          <input class="layui-input" name="dateStr" id="dateStr" autocomplete="off" placeholder="日历">
      </div>
      <div class="layui-inline">
          <select id="status" name="status">
              <option value="">全部</option>
              <option value="0">待支付</option>
              <option value="1">待接单</option>
              <option value="2">待收货</option>
              <option value="3">已完成</option>
              <option value="4">已取消</option>
              <option value="5">退款中</option>
              <option value="6">已退款</option>
          </select>
      </div>
	  <button class="layui-btn" data-type="reload">搜索</button>

   <table id="userTables" lay-filter="userFilter"></table>
      <script type="text/javascript">
      layui.use(['table','element'], function(){
    	  var table = layui.table;
    	  var element=layui.element;
          var laydate = layui.laydate;
    	  var $=layui.jquery;

          laydate.render({
              elem: '#dateStr' //指定元素
              ,range:true
          });
    	  //第一个实例
    	  table.render({
    	    elem: '#userTables'
    	    ,url: path + '/platform/order/ajaxLoad' //数据接口
    	    ,cellMinWidth:60
    	    ,cols: [[ //表头
				  {type: 'numbers', title: '序号'}
				  ,{field: 'createTime', title: '订单日期'}
				  ,{field: 'orderNumber', title: '订单编号'}
				  ,{field: 'shopName', title: '店铺名称'}
				  ,{field: 'shopId', title: '商家ID号'}
                  ,{field: 'totalPrice', title: '订单金额(元)'}
                  ,{field: '', title: '状态',templet:function (row) {
                          if(row.status==0){
                              return '待支付';
                          }else if(row.status==1){
                              return '待接单';
                          }else if(row.status==2){
                              return '待收货';
                           }else if(row.status==3){
                              return '已完成';
                          }else if(row.status==4){
                              return '已取消';
                          }else if(row.status==5){
                              return '退款中';
                          }else if(row.status==6){
                              return '已退款';
                          }
                      }}
				  ,{title:'操作',align:'center',templet:function (row) {
                              return '<a class="layui-btn " lay-event="detail">查看详情</a>';

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
   		    if(obj.event === 'detail'){
   		      window.location.href = path +"/platform/order/detail?orderId=" + data.id;
   		    }
   		  });
    	  
    	  var $ = layui.$, active = {
   		        reload: function(){
   		            var shopName = $('#shopName');
   		            var shopId=$('#shopId');
   		            var dateStr=$('#dateStr');
   		            var status=$('#status');
   		            table.reload('userTables', {
   		                where: {
                            shopName: shopName.val(),
                            shopId:shopId.val(),
                            dateStr:dateStr.val(),
                            status:status.val()
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