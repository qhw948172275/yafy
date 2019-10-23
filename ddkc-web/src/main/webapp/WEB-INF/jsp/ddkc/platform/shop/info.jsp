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
 <%--<style type="text/css">--%>
   <%--.select2-container .select2-selection--single{--%>
		<%--height: 38px;--%>
		<%--line-height: 38px;--%>
	<%--}--%>
	<%--.select2-container--default .select2-selection--single .select2-selection__rendered{--%>
		<%--line-height: 38px;--%>
	<%--}--%>
	<%--.select2-container--default .select2-selection--single .select2-selection__arrow{--%>
		<%--line-height: 38px;--%>
		<%--height: 38px;--%>
	<%--}--%>
 <%--</style>--%>
</head>
<body>

	<form class="layui-form" action="" method="post">
		<input id="id" name="id" type="hidden" value="${shop.id }" />
		<input id="annexAddress" name="annexAddress" type="hidden" value="${shop.annexAddress }" />
		<div class="layui-card">
			<div class="layui-card-body">
				<div class="layui-row layui-col-space10">
					<div class="layui-col-md9">
						<div class="layui-col-md9">
							<div class="layui-form-item">
								<label class="layui-form-label">店铺名称：</label>
								<div class="layui-input-inline">
									<input  name="name" id="name" class="layui-input" placeholder="请输入店铺名称"
										value="${shop.name }">
								</div>
							</div>
						</div>
						<div class="layui-col-md9">
							<div class="layui-form-item">
								<label class="layui-form-label">营业执照：</label>
								<div class="layui-input-inline">
									<input  name="cert" id="cert" class="layui-input" placeholder="请输入营业执照号" value="${shop.cert }">
								</div>
							</div>
						</div>
						<div class="layui-col-md12">
							<div class="layui-form-item">
								<label class="layui-form-label">联系方式：</label>
								<div class="layui-input-inline">
									<input  name="contact" id="contact" maxlength="18" class="layui-input" placeholder="请输入联系人姓名"
										value="${shop.contact }">
								</div>
								<div class="layui-input-inline">
									<input type="text" id="phone" name="phone" class="layui-input" placeholder="请输入联系电话" value="${shop.phone}" />
								</div>
							</div>
						</div>

						<div class="layui-col-md12">
							<div class="layui-form-item">
								<label class="layui-form-label">商家地址：</label>
								<div id="cityDiv">
								  <div class="layui-input-inline">
								  	<select name="province"></select>
								  </div>
								  <div class="layui-input-inline">
								  	<select name="city"></select>
								  </div>
								  <div class="layui-input-inline">
								  	<select name="area"></select>
								  </div>
								</div>
							</div>
						</div>
						<div class="layui-col-md12">
							<div class="layui-form-item">
								<label class="layui-form-label">详细地址：</label>
								<div class="layui-input-inline">
									<input  name="address" id="address" class="layui-input" placeholder="详细地址：如道路、门牌号、小区、楼栋号、单元室等"
											value="${shop.address }" style="min-width: 500px;" >
								</div>
							</div>
						</div>
						<div class="layui-col-md6">
							<div class="layui-form-item">
								<label class="layui-form-label">所在小区：</label>
								<div class="layui-input-inline">
									<input  name="estate" id="estate" class="layui-input" placeholder="请输入所在小区" style="width: 500px"
										value="${shop.estate }">
								</div>
							</div>
						</div>
						<div class="layui-col-md12">
							<div class="layui-form-item">
								<label class="layui-form-label">定  位：</label>
								<div class="layui-input-inline">
									<input  name="lng" id="lng" class="layui-input" placeholder="经度" maxlength="11"
											value="${shop.lng }">
								</div>
								<div class="layui-input-inline">
									<input  name="lat" id="lat" class="layui-input" placeholder="纬度" maxlength="11"
											value="${shop.lat }">
								</div>
								<div class="layui-input-inline">
									<a class="layui-btn layui-btn-xs" lay-event="edit" target='_blank' href="http://api.map.baidu.com/lbsapi/getpoint/index.html">获取定位</a>
								</div>

							</div>
						</div>

						<div class="layui-col-md12">
							<div class="layui-form-item">
								<label class="layui-form-label">状态：</label>
								<div class="layui-input-inline">
									<select id="status" name="status">
										<option value="0" <c:if test="${shop.status==0 }">selected</c:if>>启用</option>
										<option value="1" <c:if test="${shop.status==1 }">selected</c:if>>禁用</option>
									</select>
								</div>
							</div>
						</div>

						<div class="layui-col-md12">
							<div class="layui-form-item">
								<label class="layui-form-label">附  件：</label>
								<div id="coverDiv" class="layui-input-block">
									<button type="button" class="layui-btn" id="coverBtn">
										<i class="layui-icon">&#xe67c;</i>上传图片
									</button>
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
	<%@include file="../../../common/footerBtn.jsp"%>
	<script type="text/javascript">
		var province="${shop.province}";
		var city="${shop.city}";
		var area="${shop.area}";
		var annexs='${shop.annexAddress}';
		if(annexs!=''){
            var annex=annexs.split(',');
            for(var i=0;i<annex.length;i++){
                $('#coverBtn').parents('.layui-input-block').find('.prew-imgs').append(function(){
                    return ' <div class="img-item" style="display:inline"><img src="'+annex[i]+'!100x100"><input type="hidden"  value="'+annex[i]+'"><a onclick="javascript:removePrewImg(this);">&times;</a></div>';
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
	<script type="text/javascript" src="${ctx }/public/js/shop/info.js"></script>
</body>
</html>