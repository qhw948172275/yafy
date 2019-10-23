<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>添加班级 - 后台管理系统</title>
<%@include file="../../../common/title.jsp"%>
 <style type="text/css">
   .select2-container .select2-selection--single{
		height: 38px;
		line-height: 38px;
	}
	.select2-container--default .select2-selection--single .select2-selection__rendered{
		line-height: 38px;
	}
	.select2-container--default .select2-selection--single .select2-selection__arrow{
		line-height: 38px;
		height: 38px;
	}
 </style>
</head>
<body>

	<form class="layui-form" action="" method="post">
		<div class="layui-card">
			<div class="layui-card-body">
				<div class="layui-row layui-col-space12">
					<div class="layui-col-md9">
						<div class="layui-col-md9">
							<div class="layui-form-item">
								<label class="layui-form-label">订单编号：</label>
								<div class="layui-input-inline" style="padding: 7px">${orderDetailResponse.orderNumber}</div>

							</div>
						</div>
						<div class="layui-col-md9">
							<div class="layui-form-item">
								<label class="layui-form-label">配送信息：</label>

							</div>

						</div>
						<div class="layui-col-md9">
							<div class="layui-form-item">
								<label class="layui-form-label">收 货 人 ：</label>
								<div class="layui-input-inline" style="padding: 7px">${orderDetailResponse.contact}</div>
							</div>
						</div>
						<div class="layui-col-md9">
							<div class="layui-form-item">
								<label class="layui-form-label">联系电话：</label>
								<div class="layui-input-inline" style="padding: 7px">${orderDetailResponse.contactPhone }</div>
							</div>
						</div>
						<div class="layui-col-md9">
							<div class="layui-form-item">
								<label class="layui-form-label">收货地址：</label>
								<div class="layui-input-inline" style="padding: 7px">${orderDetailResponse.contactAddress}</div>
							</div>
						</div>
						<div class="layui-col-md9">
							<div class="layui-form-item">
								<label class="layui-form-label">基础信息：</label>
							</div>
						</div>
						<div class="layui-col-md12">
							<table class="layui-table" >
								<colgroup>
									<col width="150">
									<col width="150">
									<col>
								</colgroup>
								<thead>
								<tr>
									<th>店铺名称</th>
									<th>商家ID号</th>
									<th>下单日期</th>
									<th>下单时间</th>
									<th>订单金额（元）</th>
									<th>状态</th>
								</tr>
								<tr>
									<th>${orderDetailResponse.shopName}</th>
									<th>${orderDetailResponse.shopId}</th>
									<th>${orderDetailResponse.createDate}</th>
									<th>${orderDetailResponse.createTimeStr}</th>
									<th>${orderDetailResponse.totalPrice}</th>
									<th id="status"></th>
								</tr>
								</thead>
							</table>
						</div>
						<div class="layui-col-md9">
							<div class="layui-form-item">
								<label class="layui-form-label">订单明细：</label>
							</div>
						</div>
						<div class="layui-col-md12">
							<table class="layui-table" >
								<colgroup>
									<col width="150">
									<col width="150">
									<col>
								</colgroup>
								<thead>
								<tr>
									<th>序号</th>
									<th>商品名称</th>
									<th>单价（元）</th>
									<th>商品数量</th>
									<th>金额</th>
								</tr>
								<c:forEach items="${orderDetailResponse.orderDetailsList}" var="orderDetail" varStatus ="index">
									<tr>
										<th>${index.index +1}</th>
										<th>${orderDetail.goodsName}</th>
										<th>${orderDetail.salesPrice}</th>
										<th>${orderDetail.counts}</th>
										<th>${orderDetail.salesPrice * orderDetail.counts}</th>
									</tr>
								</c:forEach>

								</thead>
							</table>
						</div>

					</div>
				</div>
			</div>
		</div>
	</form>
    <%@include file="../../../common/footerReturnBtn.jsp"%>
	<script type="text/javascript">
		var status='${orderDetailResponse.status}';

		$('#status').html(getStatus(status));
		function getStatus(status) {
			if(status==0){
				return '待支付';
			}else if(status==1){
				return '待确认';
			}else if(status==2){
				return '待收货';
			}else if(status==3){
				return '已完成';
			}else if(status==4){
				return '已取消';
			}else if(status==5){
				return '退款中';
			}else if(status==6){
				return '已退款';
			}
		}

	</script>
	<script type="text/javascript" src="${ctx }/public/plugins/jquery.form.min.js"></script>
	<script type="text/javascript" src="${ctx }/public/js/base.js"></script>
</body>
</html>