var code;
var setting = {
	view : {
		showIcon : false
	},
	check : {
		enable : true,
		autoCheckTrigger : true,
		chkStyle : "checkbox",
		chkboxType : {
			"Y" : "ps",
			"N" : "s"
		}
	},
	data : {
		simpleData : {
			enable : true
		}
	}
};
function setCheck() {
	var zTree = $.fn.zTree.getZTreeObj("tree"), type = {
		"Y" : "ps",
		"N" : "ps"
	};
	zTree.setting.check.chkboxType = type;
	showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
}
function showCode(str) {
	if (!code)
		code = $("#code");
	code.empty();
	code.append("<li>" + str + "</li>");
}

var _role = {
		init:function(){
			this.validateListener();
			this.loadTree();
		},
		loadTree:function(){
			$.ajax({
				url : path + "/system/role?resourcelist&roleId=" + $("#id").val(),
				type : "POST",
				dataType : "json",
				success : function(res) {
					var zNodes = res.resourceList;
					var nodes = JSON.parse(zNodes);
					$.fn.zTree.init($("#tree"), setting, nodes);
				}
			});
		},
		validateListener:function(){
			$('#roleName').on('blur',function(){
				if($('#resourceName').val() != ''){
					$.post(path+'/system/role/edit?validate',{name:$('#roleName').val()},function(res){
						if(res.message == 0){
							alert('角色名称已经存在');
							$('#roleName').attr("data-ready-exists",'1');
						}else
							$('#roleName').attr("data-ready-exists",'0');
					});
				}
			});
			$('.layui-form-radio').click(function(){
				$(this).parents('.layui-input-block').find('.layui-form-radio').removeClass('layui-form-radioed');
				$(this).addClass('layui-form-radioed');
				$(this).prev('input').click();
			});
		},
		saveBtn:function(){
			var treeObj = $.fn.zTree.getZTreeObj("tree");
			var nodes = treeObj.getCheckedNodes(true);
			var resourcearr = "";
			if (nodes.length > 0) {
				for (var i = 0; i < nodes.length; i++) {
					resourcearr += nodes[i].id + ",";
				}
				resourcearr = resourcearr.substring(0, resourcearr.length - 1);
			} else {
				if (!confirm("此角色没有任何权限，确认添加吗？")) {
					return false;
				}
			}
			$("#resourceArr").val(resourcearr);
			var urlPath;
			if($("#id").val() && $("#id").val() != ""){
				urlPath = path + "/system/role/edit";
			} else {
				urlPath = path + "/system/role/add";
			}
			$('form').ajaxSubmit({
				url: urlPath,
				beforeSend:_role.validation,
				success:function(res){
					if(res.message == 0){
						alert('保存成功');
						window.location.href = path + '/system/role';
					}else{
						alert('保存失败');
					}
				}
			});
		},
		validation:function(){
			if($('#roleName').val() == ''){
				alert('请填写角色名称');
				return false;
			}else if($('#roleName').attr('data-ready-exists') == '1'){
				alert('角色名称已存在，请填写其他角色名');
				return false;
			}
			return true;
		}
};
$(function(){
	_role.init();
});

