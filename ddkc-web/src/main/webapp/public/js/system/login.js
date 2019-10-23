layui.use([ 'layer', 'form' ], function() {
	var layer = layui.layer;
	$ = layui.jquery, form = layui.form();
	if(getCookie('username')!='' && getCookie('password')!=''){
		$("#username").val(getCookie('username'));
		$("#password").val(getCookie('password'));
		$("input[type='checkbox']").prop('checked',true);
	}else {
		$("input[type='checkbox']").removeProp('checked');
		var x = document.getElementsByClassName("layui-unselect layui-form-switch layui-form-onswitch");
		x[0].setAttribute("class", "layui-unselect layui-form-switch");
	}
	form.on('submit(login)', function(data) {
		if ($('#username').val() == '') {
			layer.msg('请输入用户名');
			return false;
		}
		if ($('#password').val() == '') {
			layer.msg('请输入密码');
			return false;
		}
		if($("input[type='checkbox']").is(':checked')){
			var username=$('#username').val();
			var password=$('#password').val();
			setCookie("username",username,7);
			setCookie("password",password,7);
		}
		if(!$("input[type='checkbox']").is(':checked')){
			setCookie("username","",-1);
			setCookie("password","",-1);
		}
		return true;
	});
	if(shiroLoginFailure != ''){
		layer.msg(shiroLoginFailure);
	}
});

/**
 * 获取cookie
 * @param name
 * @returns {string}
 */
function getCookie(name) {
	var reg = RegExp(name+'=([^;]+)');
	var arr = document.cookie.match(reg);
	if(arr){
		return arr[1];
	}else{
		return '';
	}
}

/**
 * 设置cookie
 * @param name
 * @param value
 * @param day
 */
function setCookie(name,value,day){
	var date = new Date();
	date.setDate(date.getDate() + day);
	document.cookie = name + '=' + value + ';expires='+ date;
}