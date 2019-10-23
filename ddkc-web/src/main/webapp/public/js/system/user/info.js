var _user = {
		init:function(){
			this.validateListener();
		},
		validateListener:function(){
			$('#name').on('blur',function(){
				if($('#name').val() != ''){
					$.post(path+'/system/user/edit?validate&type=name',{name:$('#name').val()},function(res){
						if(res.result != "ok"){
							alert('登录名已经存在');
							$('#name').attr("data-ready-exists",'1');
						}else
							$('#name').attr("data-ready-exists",'0');
					});
				}
			});
			$('#email').on('blur',function(){
				if($('#email').val() != '' && validate.isEmail($('#email').val())){
					$.post(path+'/system/user/edit?validate&type=email',{name:$('#email').val()},function(res){
						if(res.result != "ok"){
							alert('邮箱已经存在');
							$('#email').attr("data-ready-exists",'1');
						}else
							$('#email').attr("data-ready-exists",'0');
					});
				}
			});
			$('#phone').on('blur',function(){
				if($('#phone').val() != '' && validate.isMobile($('#phone').val())){
					$.post(path+'/system/user/edit?validate&type=phone',{name:$('#phone').val()},function(res){
						if(res.result != "ok"){
							alert('手机号已经存在');
							$('#phone').attr("data-ready-exists",'1');
						}else
							$('#phone').attr("data-ready-exists",'0');
					});
				}
			});
			$('.layui-form-checkbox').click(function(){
				if($(this).hasClass('layui-form-checked')){
					$(this).removeClass('layui-form-checked');
				}else{
					$(this).addClass('layui-form-checked');
				}
				$(this).prev('input').click();
			});
			$('.layui-form-radio').click(function(){
				$(this).parents('.layui-input-block').find('.layui-form-radio').removeClass('layui-form-radioed');
				$(this).addClass('layui-form-radioed');
				$(this).prev('input').click();
			});
		},
		saveBtn:function(){
			var urlPath;
			if($("#id").val() && $("#id").val() != ""){
				urlPath = path + "/system/user/edit";
			} else {
				urlPath = path + "/system/user/add";
			}
			$('form').ajaxSubmit({
				url: urlPath,
				beforeSend:_user.validation,
				success:function(res){
					if(res.result == "ok"){
						alert('保存成功');
						window.location.href = path + '/system/user';
					}else{
						alert('保存失败');
					}
				}
			});
		},
		validation:function(){
			if($('#name').val() == ''){
				alert('请填写登录用户名');
				return false;
			}else if($('#name').attr('data-ready-exists') == '1'){
				alert('用户名已存在，请填写其他用户名');
				return false;
			}
			if($('#id').val() != ''){
				//编辑操作
				if(!$('#newPass').hasClass('hide') && $('#newPass').val() == ''){
					alert('请设置新密码');
					return false;
				}else if(!$('#newPass').hasClass('hide') && $('#newPass').val().length < 6){
					alert('设置新密码不能少于6位');
					return false;
				}
			}else{
				//添加操作
				if($('#password').val() == ''){
					alert('请填写密码');
					return false;
				}else if($('#password').val().length < 6){
					alert('设置密码不能少于6位');
					return false;
				}
				
			}
			if($('#realName').val() == ''){
				alert('请填写真实姓名');
				return false;
			}
			if($('input:radio[name="sex"]:checked').val() == 'undefined'){
				alert('请选择性别');
				return false;
			}
			if($('#phone').val() == ''){
				alert('请填写联系电话');
				return false;
			}else if(!validate.isMobile($('#phone').val())){
				alert('请正确填写联系电话');
				return false;
			}else if($('#name').attr('data-ready-exists') == '1'){
				alert('手机号已存在，请填写其他手机号');
				return false;
			}
			if($('#email').val() == ''){
				alert('请填写邮箱');
				return false;
			}else if(!validate.isEmail($('#email').val())){
				alert('请正确填写邮箱');
				return false;
			}else if($('#email').attr('data-ready-exists') == '1'){
				alert('邮箱已存在，请填写其他邮箱');
				return false;
			}
			if($('input:radio[name="status"]:checked').val() == 'undefined'){
				alert('请选择用户状态');
				return false;
			}
			return true;
		},//修改密码
		updateNewPass:function(btn){
			if(btn.attr("data") == 0){
				btn.attr('data',1);
				btn.html('取消设置');
				$('#newPass').removeClass('hide');
			}else{
				btn.attr('data',0);
				btn.html('修改密码');
				$('#newPass').addClass('hide').val('');
			}
		}
};
$(function(){
	_user.init();
});
