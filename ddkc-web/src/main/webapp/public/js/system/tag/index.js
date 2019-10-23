$(function() {
	// 分页处理
	_page.init($('.pagination'), function() {
		$('#pageSize').val(_page.pageData.limit);
		$('#currentPage').val(_page.pageData.page);
		$("#pageForm").submit();
	});

});


function add(){
	
	// 初始化文字
	$('#myModalLabel').html("添加标签");
	// 初始化值
	$("#tag_id").val("");
	$("#tag_name").val("");
	$("#action_type").val("add");
	$("#tag_type option:first").prop("selected", 'selected'); // 选中第一个
	$("#tag_status option:first").prop("selected", 'selected');// 选中第一个

	$('#myModal').modal({
		keyboard : true
	})
	
}

function edit(tag_id, tag_name, tag_type, tag_status){
	
	// 初始化文字
	$('#myModalLabel').html("添加标签");
	// 初始化值
	$("#tag_id").val(tag_id);
	$("#tag_name").val(tag_name);
	$("#action_type").val("edit");
	$("#tag_type").val(tag_type); 
	$("#tag_status").val(tag_status);

	$('#myModal').modal({
		keyboard : true
	})
	
}


function addOver(){
	
	if($("#tag_name").val() == ""){
		alert("名称未填写");
		return;
	}
	var urlPath;
	if($("#tag_id").val() && $("#tag_id").val() != ""){
		urlPath = path + "/system/tag/edit";
	} else {
		urlPath = path + "/system/tag/add";
	}
	$.ajax({
		url : urlPath ,
		type : 'post',
		dataType : 'json',
		data : {
			id : $("#tag_id").val(),
			name : $("#tag_name").val(),
			action_type : $("#action_type").val(),
			type : $("#tag_type").val(),
			status : $("#tag_status").val()
		},
		success : function(res) {
			if (res.result == "ok") {
				location.reload();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(XMLHttpRequest.status);
			alert(XMLHttpRequest.readyState);
			alert(textStatus);
		}
	});
	
	

	
}
