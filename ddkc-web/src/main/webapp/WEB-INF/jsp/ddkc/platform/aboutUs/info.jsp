<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
  <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>个人信息 </title>
      <%@include file='../../../common/title.jsp'%>
  </head>
  <body>

  	<div class="layui-container">

	      <form class="layui-form" action="" method="post">
			  <input type="hidden" id="id" name="id" value="${aboutUs.id}"/>
			  <input type="hidden" id='types' name="types" value="${aboutUs.types}">
			  <div class="layui-form-item layui-form-text">
				  <label class="layui-form-label" id="tileUs"></label>
				  <div class="layui-input-block">
					  <textarea id="content" name="content" placeholder="请输入描述" class="fsLayedit" height="80">${aboutUs.content}</textarea>
				  </div>
			  </div>

			<div class="layui-form-item">
			    <div class="layui-input-block">
			      <button  id="saveBtn" type="button" class="layui-btn">保 存</button>
				  <button  id="editBtn" type="button" class="layui-btn">编 辑</button>
			    </div>
			  </div>
	      </form>

	</div>
      <script type="text/javascript" src="${ctx }/public/plugins/jquery.form.min.js"></script>
      <script type="text/javascript">
		  var type=${type};
		  if(type==''){
			  type=0;
		  }
		  if(type==1){
			  $('#tileUs').html('平台服务协议：');
		  }else if (type==2) {
			  $('#tileUs').html('商家入驻协议：');
		  }else{
			  $('#tileUs').html('关于我们：');
		  }
		  layui.use('layedit', function(){
			  var layedit = layui.layedit;
			  layedit.set({
				  uploadImage: {
					  url: path+'/app/commons/upload/img' //接口url
					  ,type: 'post' //默认post
				  }
			  });
			  var index;
			  if($('#id').val()==''){
				 index= layedit.build('content',{
				 	height:500
				 });
				 $('#editBtn').hide();
			  }else{
				  index= layedit.build('content',{
					  hideTool:['strong' //加粗
						  ,'italic' //斜体
						  ,'underline' //下划线
						  ,'del' //删除线

						  ,'|' //分割线

						  ,'left' //左对齐
						  ,'center' //居中对齐
						  ,'right' //右对齐
						  ,'link' //超链接
						  ,'unlink' //清除链接
						  ,'face' //表情
						  ,'image' //插入图片
						  ,'help'],
					  height:500
				  }); //建立编辑器
				  $("#content").next().find('iframe').contents().find('body').prop("contenteditable",false);
				  $('#saveBtn').hide();
			  }
			  $('#editBtn').click(function () {
				  index= layedit.build('content',{
					  height:500
				  });
				  $('#saveBtn').show();
				  $('#editBtn').hide();
			  })



			  $('#saveBtn').click(function (){
				  console.log(index);
				  $('#content').val(layedit.getContent(index));
				  console.log($('#content').val());
			  if($('#content').val()==''){
				  alert('请填写内容');
				  return;
			  }
			  $('#types').val(type);
			  $('form').ajaxSubmit({
				  url: path+'/platform/aboutUs/save',
				  success:function(res){
					  if(res.result == "ok"){
						  alert('保存成功');
						  window.location.href = path + '/platform/aboutUs?type='+type;
					  }else{
						  alert('保存失败');
					  }
				  }
			     });
			  });
		  });

	  </script>
  </body>
</html>