/**
 * 文件上传
 */
layui.use(['upload','element'], function(){
  var upload = layui.upload;
  var layer = layui.layer;
  
  $('.layui-btn').click(function(){
	  var _btn = $(this);
	  var _form = _btn.parents('form');
	  if(_form.find('input[name="type"]').val() == '0'){
		  if(_form.find('input[name="val"]').val() == ''){			  
			  layer.msg('请输入配送费'); return ;
		  }else if (!validate.isMoney(_form.find('input[name="val"]').val())){
			  layer.msg('请输入配送费格式不正确'); return ;
		  }
	  }else if(_form.find('input[name="type"]').val() == '1'){
		  if(_form.find('input[name="val"]').val() == ''){			  
			  layer.msg('请输入平台提成'); return ;
		  }else if (!validate.isMoney(_form.find('input[name="val"]').val())){
			  layer.msg('提成格式不正确'); return ;
		  }
	  }else if(_form.find('input[name="type"]').val() == '2'){
		  if(_form.find('input[name="val"]').val() == ''){			  
			  layer.msg('请输入配送距离');
			  return ;
		  }else if (!validate.isInt(_form.find('input[name="val"]').val())){
			  layer.msg('配送距离必须是整数');
			  return ;
		  }
	  }
	  _btn.attr("disabled",true);
	  _form.ajaxSubmit({
			url: path + "/platform/delivery",
			type:'post',
			dataType:'json',
			success:function(res){
				if(res.result == 'ok'){
					layer.alert('保存成功',function(){
						window.location.reload();
					});
				}else{
                    $("#saveBtn").attr("disabled",false);
					layer.msg('保存失败');
				}
				_btn.removeAttr("disabled");
			}
	 });
  });
});
