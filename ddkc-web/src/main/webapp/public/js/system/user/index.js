//用户管理列表操作
var _user = {
		init:function(){
			
		},
		search:function(){
			$('form').submit();
		},
		delUser:function(id){
			if(confirm('确认删除吗？')){
				$.ajax({
					url:path + "/system/user/delete?id="+id,
					type:'get',
					dataType:'json',
					success:function(res){
						if(res.result == "ok"){
							alert('删除成功');
							location.reload();
						}else{
							alert('删除失败');
						}
					}
				});
			}
			return false;
		}
};

$(function(){
	_user.init();
	//分页处理
	_page.init($('.pagination'),function(){
		$('#pageSize').val(_page.pageData.limit);
		$('#currentPage').val(_page.pageData.page);
		_user.search();
	});
});

function search() {
	$("#pageSize").val("");
	$("#currentPage").val("");
	$("#pageForm").submit();
}


function syncWechatEnterUser() {
	ajaxWrap({
		url : path + "/wechatenter/user/sync",
	})
}