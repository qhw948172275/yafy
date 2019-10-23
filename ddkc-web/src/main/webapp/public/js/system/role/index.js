//角色管理列表操作
var _role = {
		init:function(){
			
		},
		search:function(){
			$('form').submit();
		},
		delRole:function(id){
			if(confirm('确认删除吗？')){
				$.ajax({
					url:path + "/system/role/delete?id="+id,
					type:'get',
					dataType:'json',
					success:function(res){
						if(res.message == 0){
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
	_role.init();
	//分页处理
	_page.init($('.pagination'),function(){
		$('#pageSize').val(_page.pageData.limit);
		$('#currentPage').val(_page.pageData.page);
		_role.search();
	});
});