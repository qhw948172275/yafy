/**
 * 文件上传
 */
layui.config({
    dir: path+'/public/plugins/layui/',
    base: path+'/public/js/',
    version: new Date().getTime()
}).use(['table','city','upload'], function(){
	  var table = layui.table;
	  var citys = layui.city;
	  var laydate = layui.laydate;
	  var upload = layui.upload;

//上传图片
	var uploadCover = upload.render({
		elem: '#coverBtn' //绑定元素
		,url: path+'/app/commons/upload/img'
		,accept:'images'
		,acceptMime:'image/*'
		,done: function(res, index, upload){

			//上传完毕回调
			$('#coverBtn').parents('.layui-input-block').find('.prew-imgs').append(function(){
				return ' <div class="img-item" style="display:inline"><img src="'+res.data.src+'!100x100"><input type="hidden"  value="'+res.data.src+'"><a onclick="javascript:removePrewImg(this);">&times;</a></div>';
			});
		}
		,error: function(){
			//请求异常回调
			layer.msg('上传失败');
		}
	});

	  
	  $('#cityDiv').citys({
//		  provinceField: 'provinces', //省份字段名
//		  cityField: 'citys', //城市字段名
//		  areaField: 'areas', //地区字段名
		  valueType:'name',
		  province:(province=='' ? '四川省':province),
		  city:city,
		  area:area,
		  required: true, //是否必须选一个
		  nodata: 'hidden', //当无数据时的表现形式:'hidden'隐藏,'disabled'禁用,为空不做任何处理
		  onChange: function() {
			  
		  } //地区切换时触发,回调函数传入地区数据
	  },function(api){
	//	  console.log('=================='+JSON.stringify(api.getInfo()));
	  });

});
function saveBtn(){
	if($('#name').val() == '') {
        layer.msg('请填写店铺名称');
    }
	else if($('#cert').val()==''){
		  layer.msg('营业执照不能为空');
		}
	  else if($('#contact').val() == ''&&!validate.identityCodeValid($('#idCard').val())){
            layer.msg('请填写联系人');
	  }else if($('#phone').val() == ''){
		  layer.msg('请填写电话号码');
	  }else if($('#province').val() == ''||$('#city').val() == ''||$('#area').val() == ''||$('#address').val() == ''){
		  layer.msg('请填写完整地址');
	  }else if($('#estate').val() == ''){
        layer.msg('请填写所在小区');
     }else if($('#lat').val() == ''||$('#lng').val() == ''){
        layer.msg('请填写横纵坐标');
     }else {
         $("#footerSave").attr("onclick","");

            var annexAddress=[];
        $('.img-item').each(function (item,index) {

           var address= $(this).find("input").val();
            annexAddress.push(address);
        });
        if(annexAddress.length>1){
            annexAddress.join(',');
        }
        $('#annexAddress').val(annexAddress.toString());
		  $('form').ajaxSubmit({
				url: path + "/platform/shop/save",
				dataType:'json',
				success:function(res){
					if(res.result == 'ok'){
						layer.alert('保存成功',function(){
							window.location.href = path +"/platform/shop";
						});
					}else{
                        $("#footerSave").attr("onclick","javascript:saveBtn();");
						layer.msg(res.errorMsg);
					}
				}
		 });
	  }
}

