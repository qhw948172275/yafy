var _user = {
		init:function(){
			this.validateListener();
		},
		validateListener:function(){
			$('#email').on('blur',function(){
				if($('#email').val() != '' && validate.isEmail($('#email').val())){
					$.post(path+'/system/user/validate/email/',{name:$('#email').val()},function(res){
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
					$.post(path+'/system/user/validate/',{name:$('#phone').val()},function(res){
						if(res.result != "ok"){
							alert('联系电话已经存在');
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
				url:path + "/console/system/account/save",
				success:function(res){
					if(res.result == "ok"){
						alert(res.object.showmessage);
						window.location.href=path + "/system/index";
					}else{
						alert(res.object.showmessage);
					}
				}
			});
		},
		validation:function(){
			if($('#realName').val() == ''){
				alert('请填写真实名称');
				return false;
			}
			if($('#phone').val() == ''){
				alert('请填写联系电话');
				return false;
			}
			if($('#email').val() == ''){
				alert('请填写邮箱');
				return false;
			}
			return true;
		},
		savePwdBtn:function(){
			$('form').ajaxSubmit({
				beforeSend:_user.validationpwd,
				url:path + "/console/system/account/savepwd",
				success:function(res){
					if(res.result == "ok"){
						alert(res.object.showmessage);
						window.location.href=path + "/system/index";
					}else{
						alert(res.object.showmessage);
					}
				}
			});
		},validationpwd:function(){
			if($("#oldPassword").val()==""){
				alert('请填写原密码');
				return false;
			}else if($("#newPassword").val()==""){
				alert('请填写新密码');
				return false;
			}else if($("#newPassword").val() != $("#reNewPassword").val()){
				alert('重复密码不正确');
				return false;
			}
			return true;
		}
};
$(function(){
	_user.init();
});
