function saveBtn(){
	if($('#name').val() == '') {
        layer.msg('请填写员工名称');
    }else if($('#account').val() == '') {
        layer.msg('请填写账号名称');
    } else if($('#password').val()==''){
		layer.msg('账号密码不能为空');
	}else {
		  $('form').ajaxSubmit({
				url: path + "/platform/shop/account/save",
				dataType:'json',
				success:function(res){
					if(res.result == 'ok'){
						layer.alert('保存成功',function(){
							window.location.href = path +"/platform/shop/account?shopId="+$('#shopId').val();
						});
					}else{
						layer.msg(res.errorMsg);
					}
				}
		 });
	  }
}

