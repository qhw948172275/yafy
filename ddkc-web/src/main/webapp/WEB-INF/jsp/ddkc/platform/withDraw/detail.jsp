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
	  搜索商家
      <div class="layui-inline">
          <input class="layui-input" name="shopName" id="shopName" autocomplete="off" placeholder="商家名称">
      </div>
      <div class="layui-inline">
          <input class="layui-input" name="shopId" id="shopId" autocomplete="off" placeholder="商家ID号">
      </div>
	  <button class="layui-btn" data-type="reload">搜索</button>

   <table id="userTables" lay-filter="userFilter"></table>
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
    	    ,url: path + '/platform/withDraw/detailAjaxLoad?dateStr=${dateStr}' //数据接口
    	    ,cellMinWidth:60
    	    ,cols: [[ //表头

				   {field: 'dateStr', title: '日期'}
				  ,{field: 'timeStr', title: '时间'}
                  ,{field: 'shopName', title: '商家名称'}
                  ,{field: 'shopId', title: '商家ID号'}
                  ,{field: 'amount', title: '提现金额'}
                  ,{field:'',title:'状态',templet:function (row) {
                          if(row.status==0){
                              return '提现中';
                          }else if(row.status==1){
                              return '提现成功';
                          }else if(row.status==2){
                              return '提现失败';
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
   		    if(obj.event === 'detail'){
   		      window.location.href = path +"/platform/withDraw/detail?dateStr=" + data.dateStr+" - "+data.dateStr;
   		    }
   		  });
    	  
    	  var $ = layui.$, active = {
   		        reload: function(){
   		            var shopName=$('#shopName');
                    var shopId=$('#shopId');
                        table.reload('userTables', {
   		                where: {
                            shopName:shopName.val(),
                            shopId:shopId.val()
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