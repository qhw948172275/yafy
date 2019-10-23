<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>控制面板</title>
	<%@include file="../common/title.jsp"%>
	<link href="${ctx }/public/css/system/user/index.css" rel="stylesheet">
	<script src="${ctx }/public/plugins/echarts/echarts.min.js" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
.layui-col-md12 h3{
	margin: 10% auto;
	text-align: center;
}
.box-list{
	display: flex;
	justify-content: space-around;
	align-items: center;
}
.box-list li{
	display: flex;
	width: 33%;
	height: 78px;
	justify-content: center;
	align-items: center;
	margin: 16px;
	border: 1px solid #3c3c3c;
}
.box-list li p{
	text-align: center;
}
.box-list li p span{
	font-size: 27px;
}
.chart_tile{
	width:100%;
	font-size: 20px;
	margin: 30px 0 10px;
	text-align: center
}
.btn_center{
	width:500px;
	display: flex;
	justify-content: center;
	align-items: center;
}
</style>
</head>

<body>
<div class="layui-container">
	<ul class="box-list">
		<li>
			<p>今日下单量&emsp;<span>${orderCountResponse.orderCount}</span>&emsp;笔</p>
		</li>
		<li>
			<p>今日成交额&emsp;<span>${orderCountResponse.totalPrice}</span>&emsp;元</p>
		</li>
		<li>
			<p>今日抽成额&emsp;<span>${orderCountResponse.platformCost}</span>&emsp;元</p>
		</li>
		<li>
			<p>平台商户量&emsp;<span>${shopCount}</span>&emsp;户</p>
		</li>
	</ul>
	<div class="layui-row">
		<div class="layui-col-md6">
			<div class="chart_tile">平台流水统计图</div>
			<div class="btn_center">
				<button id="upMonth" style="padding: 0 20px;height:26px;line-height:26px;" type="button" class="layui-btn layui-btn-sm">上月</button>
				<span style="margin: 0 30px;" id="oldTime">2019-08</span>
				<button id="downMonth" style="padding: 0 20px;height:26px;line-height:26px;" type="button" class="layui-btn layui-btn-sm">下月</button>
			</div>
			<div id='main' style='width:100%;height:400px;'></div>
		</div>
		<div class="layui-col-md6">
			<div class="chart_tile">平台商户数量</div>
			<div id='histogram' style='width:100%;height:400px;padding-top: 26px;'></div>
		</div>
		<div class="layui-col-md6">
			<div id='pieChart' style='width:100%;height:400px;padding-top: 30px'></div>
		</div>
		<div class="layui-col-md6">
			<div class="chart_tile">本周成功提现流水</div>
			<table class="layui-table" >
				<thead>
				<tr >
					<td align="center" >提现日期</td>
					<td align="center">提现商家数</td>
					<td align="center">提现金额</td>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${drawResponses}" var="drawResponse">
					<tr >
						<td align="center">${drawResponse.dateStr}</td>
						<td align="center">${drawResponse.shopCount}</td>
						<td align="center">${drawResponse.amount}</td>
					</tr>
				</c:forEach>
				<c:if test="${drawResponses.size()==0}">
					<tr >
						<td align="center" colspan="3">暂无数据</td>
					</tr>
				</c:if>
				</tbody>
			</table>
		</div>
	</div>
</div>
<script>
	createChart(9,"");
	histogram();
	pieChart();
	$('#upMonth').click(function () {

		createChart(0,$('#oldTime').html());
	});
	$('#downMonth').click(function () {
		createChart(1,$('#oldTime').html());
	});

	function createChart(type,oldTime) {
	    var days=[];
	    var amount=[];
		$.ajax({
            url:path+'/platformCostCensus?type='+type+'&oldTime='+oldTime,
            type:'get',
            dateType:'json',
            async: false,
            success:function (res) {
                console.log(res);
                if(res.result=='ok'){
                    $('#oldTime').html(res.object.newTime);
                    days=res.object.dayList;
                    amount=res.object.amountList;
                }
            }
        });
		var myChart = echarts.init(document.getElementById('main'));
	    var 	option = {
			grid: {
				left: '10',
				right: '10',
				top: '16',
				bottom: '10',
				containLabel: true
			},
			xAxis: {
				type: 'category',
				data: days,
				axisTick: {
					alignWithLabel: true
				}
			},
			yAxis: {
				type: 'value'
			},
			tooltip: {
				trigger: 'axis',
				formatter: '<br/>{b} : {c}'
			},
			series: [{
				data: amount,
				type: 'line'
			}]
		};
		//使用刚指定的配置项和数据显示图表
		myChart.setOption(option);
		// 自适应图表
		window.addEventListener('resize',function () {
			myChart.resize()
		})
	}

	function histogram() {
		var dayList=[];
		var shopCountList=[];
		$.ajax({
			url:path+'/histogram',
			type:'get',
			dateType:'json',
			async: false,
			success:function (res) {
				console.log(res);
				if(res.result=='ok'){
					dayList=res.object.dayList;
					shopCountList=res.object.shopCountList;
				}
			}
		});



		var myChart = echarts.init(document.getElementById('histogram'));
		var option = {
			color: ['#3398DB'],
			tooltip : {
				trigger: 'axis',
				axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			grid: {
				left: '10',
				right: '10',
				top: '16',
				bottom: '10',
				containLabel: true
			},
			xAxis : [
				{
					type : 'category',
					data :dayList ,
					axisTick: {
						alignWithLabel: true
					}
				}
			],
			yAxis : [
				{
					type : 'value'
				}
			],
			series : [
				{
					type:'bar',
					barWidth: '60%',
					data:shopCountList
				}
			]
		};
		myChart.setOption(option);
		// 自适应图表
		window.addEventListener('resize',function () {
			myChart.resize()
		})
	}
	
	function pieChart() {
		var myChart = echarts.init(document.getElementById('pieChart'));
		var option = {
			title : {
				text: '上月订单',
				x:'center'
			},
			grid: {
				left: '10',
				right: '10',
				top: '0',
				bottom: '10',
				containLabel: true
			},
			tooltip : {
				trigger: 'item',
				formatter: "{a} <br/>{b} : {c} ({d}%)"
			},
			legend: {
				orient: 'vertical',
				left: 'left',
				data: ['支付订单','取消订单','完成订单']
			},
			series : [
				{
					name: '访问来源',
					type: 'pie',
					radius : '55%',
					// center: ['50%', '60%'],
					data:[
						{value:${orderPieResponse.payOrder}, name:'支付订单'},
						{value:${orderPieResponse.cancelOrder}, name:'取消订单'},
						{value:${orderPieResponse.finishOrder}, name:'完成订单'}
					],
					itemStyle: {
						emphasis: {
							shadowBlur: 10,
							shadowOffsetX: 0,
							shadowColor: 'rgba(0, 0, 0, 0.5)'
						}
					}
				}
			]
		};
		myChart.setOption(option);
		// 自适应图表
		window.addEventListener('resize',function () {
			myChart.resize()
		})
	}
</script>
</body>

</html>