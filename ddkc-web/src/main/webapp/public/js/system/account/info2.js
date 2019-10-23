var _user = {
		init:function(){
			this.validateListener();
		},
		validateListener:function(){
			$('#name').on('blur',function(){
				if($('#name').val() != ''){
					$.post(path+'/system/user/validate/name/',{name:$('#name').val()},function(res){
						if(res.result != "ok"){
							showMessage('用户昵称已经存在');
							$('#name').attr("data-ready-exists",'1');
						}else
							$('#name').attr("data-ready-exists",'0');
					});
				}
			});
			$('#email').on('blur',function(){
				if($('#email').val() != '' && validate.isEmail($('#email').val())){
					$.post(path+'/system/user/validate/email/',{name:$('#email').val()},function(res){
						if(res.result != "ok"){
							showMessage('邮箱已经存在');
							$('#email').attr("data-ready-exists",'1');
						}else
							$('#email').attr("data-ready-exists",'0');
					});
				}
			});
			$('#phone').on('blur',function(){
				if($('#phone').val() != '' && validate.isMobile($('#phone').val())){
					$.post(path+'/system/user/validate/',{name:$('#phone').val()},function(res){
						if(res.result != "ok"){
							showMessage('联系电话已经存在');
							$('#phone').attr("data-ready-exists",'1');
						}else
							$('#phone').attr("data-ready-exists",'0');
					});
				}
			});
		},
		saveBtn:function(){
			$('form').ajaxSubmit({
				beforeSend:_user.validation,
				success:function(res){
					if(res.result != "ok"){
						alert(res.object);
						window.location.href=path;
					}else{
						showMessage(res.object);
						
					}
				}
			});
		},
		validation:function(){
			if($('#name').val() == ''){
				showMessage('请填写用户昵称');
				return false;
			}else if($('#name').attr('data-ready-exists') == '1'){
				showMessage('用户昵称已存在，请填写其他用户昵称');
				return false;
			}
			if($('#realName').val() == ''){
				showMessage('请填写姓名');
				return false;
			}
			if($('#phone').val() == ''){
				showMessage('请填写联系电话');
				return false;
			}else if(!validate.isMobile($('#phone').val())){
				showMessage('请正确填写联系电话');
				return false;
			}else if($('#name').attr('data-ready-exists') == '1'){
				showMessage('联系电话已存在，请填写其他联系电话');
				return false;
			}
			if($('#email').val() == ''){
				showMessage('请填写邮箱');
				return false;
			}else if(!validate.isEmail($('#email').val())){
				showMessage('请正确填写邮箱');
				return false;
			}else if($('#email').attr('data-ready-exists') == '1'){
				showMessage('邮箱已存在，请填写其他邮箱');
				return false;
			}
			showMessage("");
			return true;
		},
		savePwdBtn:function(){
			$('form').ajaxSubmit({
				beforeSend:_user.validationpwd,
				success:function(res){
					if(res.result != "ok"){
						alert(res.object);
						window.location.href=path;
					}else{
						showMessage(res.object);
					}
				}
			});
		},validationpwd:function(){
			if($("#oldPassword").val()==""){
				showMessage('请填写原密码');
				return false;
			}else if($("#password").val()==""){
				showMessage('请填写新密码');
				return false;
			}else if($("#password").val()!=$("#repassword").val()){
				showMessage('重复密码不正确');
				return false;
			}
			showMessage("");
			return true;
		}
};
$(function(){
	_user.init();
});

function showMessage(message){
	$("#errorMsg").html(message);
}
