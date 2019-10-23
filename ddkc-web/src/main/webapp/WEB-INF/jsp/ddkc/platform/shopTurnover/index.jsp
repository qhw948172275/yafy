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
	  搜索日期
      <div class="layui-inline">
          <input class="layui-input" name="dateStr" id="dateStr" autocomplete="off" placeholder="日历">
      </div>

	  <button class="layui-btn" data-type="reload">搜索</button>

   <table id="userTables" lay-filter="userFilter"></table>
      <c:if test="${dateStr !=null}">
          <%@include file="../../../common/footerReturnBtn.jsp"%>
      </c:if>
      <script type="text/javascript">
      layui.use(['table','element'], function(){
    	  var table = layui.table;
          var laydate = layui.laydate;
    	  var $=layui.jquery;

          laydate.render({
              elem: '#dateStr' //指定元素
              ,range:true
          });
    	  //第一个实例
    	  table.render({
    	    elem: '#userTables'
    	    ,url: path + '/platform/order/shopAjaxLoad?dateStr=${dateStr}' //数据接口
    	    ,cellMinWidth:60
    	    ,cols: [[ //表头
                   {type:'numbers',title:'序号'}
				  ,{field: 'dateStr', title: '日期'}
                  ,{field: 'shopName', title: '商家名称'}
                  ,{field: 'shopId', title: '商家ID号'}
				  ,{field: 'orderCount', title: '订单总量'}
				  ,{field: 'successCount', title: '完成订单量'}
				  ,{field: 'totalPrice', title: '营业额（元）'}
				  ,{field: 'platformCost', title: '平台抽成'}

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
   		      // window.location.href = path +"/platform/order/detail?orderId=" + data.id;
   		    }
   		  });
    	  
    	  var $ = layui.$, active = {
   		        reload: function(){
   		            var dateStr=$('#dateStr');
   		            table.reload('userTables', {
   		                where: {
                            dateStr:dateStr.val()
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