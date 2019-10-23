var _resource = {
		init:function(){
			this.validateListener();
			$('#level').change();
		},
		validateListener:function(){
			$('#resourceName').on('blur',function(){
				if($('#resourceName').val() != ''){
					$.post(path+'/system/resource/edit?validate&type=name&',{name:$('#resourceName').val()},function(res){
						if(res.message == 0){
							alert('资源名称已经存在');
							$('#resourceName').attr("data-ready-exists",'1');
						}else
							$('#resourceName').attr("data-ready-exists",'0');
					});
				}
			});
			$('#resourceUrl').on('blur',function(){
				if($('#resourceUrl').val() != '' && $('#resourceUrl').val() != '#'){
					$.post(path+'/system/resource/edit?validatetype=url&',{name:$('#resourceUrl').val()},function(res){
						if(res.message == 0){
							alert('访问路径已经存在');
							$('#resourceUrl').attr("data-ready-exists",'1');
						}else
							$('#resourceUrl').attr("data-ready-exists",'0');
					});
				}
			});
			$('#level').on('change',function(){
				if($(this).val() == '-1'){
					$('#parentId').hide().html('');
				}else{
					$('#parentId').show();
					var parentId = $('#parentId').attr('data-id');
					$.get(path+'/system/resource/edit?setLevel',{level:$(this).val()},function(res){
						if(null != res.object.ls){
							var html = '';
							$.each(res.object.ls,function(index,e){
								if(parentId != '' && e.id == parentId){
									html+='<option value="'+e.id+'" selected="selected">';
								}else{
									html+='<option value="'+e.id+'">';
								}
								html+=e.resourceName+'</option>';
							});
							$('#parentId').html(html);
						}
					});
				}
			});
		},
		saveBtn:function(){
			var urlPath;
			if($("#id").val() && $("#id").val() != ""){
				urlPath = path + "/system/resource/edit";
			} else {
				urlPath = path + "/system/resource/add";
			}
			$('form').ajaxSubmit({
				url: urlPath,
				beforeSend:_resource.validation,
				success:function(res){
					if(res.message == 0){
						alert('保存成功');
						// 重新加载节点
                        refreshNodeForSave({data : {type:"refresh", silent:false}},[vm.resource.parentNode]);
						// 重置表单
                        repalceResourceObj(vm, defaultResource);
					}else{
						alert('保存失败');
					}
				}
			});
		},
		validation:function(){
			if($('#resourceName').val() == ''){
				alert('请填写资源名称');
				return false;
			}else if($('#resourceName').attr('data-ready-exists') == '1'){
				alert('资源名称已存在，请填写其他资源名');
				return false;
			}
			if($('#resourceUrl').val() == ''){
				alert('请填写资源路径');
				return false;
			}else if($('#resourceUrl').attr('data-ready-exists') == '1'){
				alert('资源路径已存在，请填写其他资源路径');
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
	_resource.init();
});
