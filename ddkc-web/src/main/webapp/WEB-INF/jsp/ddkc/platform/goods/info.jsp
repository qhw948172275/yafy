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
				<div class="layui-row layui-col-space10">
					<div class="layui-col-md9">
						<div class="layui-col-md9">
							<div class="layui-form-item">
								<label class="layui-form-label">商品名称：</label>
								<div class="layui-input-inline" style="padding: 7px">${goodsResponse.goodsName}</div>
							</div>
						</div>
						<div class="layui-col-md9">
							<div class="layui-form-item">
								<label class="layui-form-label">商家名称：</label>
								<div class="layui-input-inline" style="padding: 7px">${goodsResponse.shopName}</div>

							</div>
						</div>

						<div class="layui-col-md9">
							<div class="layui-form-item">
								<label class="layui-form-label">商 家 I D：</label>
								<div class="layui-input-inline" style="padding: 7px">${goodsResponse.shopId }</div>

							</div>
						</div>
						<div class="layui-col-md12">
							<div class="layui-form-item">
								<label class="layui-form-label">封面图片：</label>
								<div id="coverDiv" class="layui-input-block">
									<div class="prew-imgs">
										<img src="${goodsResponse.cover}!100x100">
									</div>
								</div>
							</div>
						</div>


						<div class="layui-col-md12">
							<div class="layui-form-item">
								<label class="layui-form-label">商品详情：</label>
								<div id="detail" class="layui-input-block">
									<div class="prew-imgs">

									</div>
                                    </div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../../../common/footerReturnBtn.jsp"%>
	<script type="text/javascript">
		var province="${shop.province}";
		var city="${shop.city}";
		var area="${shop.area}";
		var details='${goodsResponse.details}';
		if(details!=''){
            var detail=details.split(',');
            console.log(detail[0]);
            for(var i=0;i<detail.length;i++){
                $('#detail').find('.prew-imgs').append(function(){
                    return ' <div class="img-item" style="display:inline"><img src="'+detail[i]+'"></div>';
                });
            }
        }

		/**
		 * 删除预览图片
		 * @param _img
		 */
		function removePrewImg(_img){
			if(confirm("确认删除当前图片吗？")){
				$(_img).parents('.layui-input-block').find('.layui-btn').removeAttr('disabled');
				$(_img).parents('.img-item').remove();
			}
		}
	</script>
	<script type="text/javascript" src="${ctx }/public/plugins/jquery.form.min.js"></script>
	<script type="text/javascript" src="${ctx }/public/js/base.js"></script>
</body>
</html>