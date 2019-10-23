<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link
	href="${ctx }/public/plugins/ueditor/themes/default/css/ueditor.min.css"
	type="text/css" rel="stylesheet">
<style type="text/css">
.edui-default {
	padding: 0;
}
</style>
<%-- <script src="${ctx }/public/plugins/ueditor/third-party/template.min.js"></script> --%>
<script type="text/javascript" charset="utf-8"
	src="${ctx }/public/plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx }/public/plugins/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript"
	src="${ctx }/public/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript" charset="utf-8"
	src="${ctx }/public/plugins/ueditor/kityformula-plugin/addKityFormulaDialog.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx }/public/plugins/ueditor/kityformula-plugin/getKfContent.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx }/public/plugins/ueditor/kityformula-plugin/defaultFilterFix.js"></script>

<script type="text/javascript">
    function initEditor(editorId){
    	return UE.getEditor(editorId, {
    		toolbars: [
    	         ['undo', 'redo','bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc',
    	        	 'insertimage', '|', 'kityformula']
    	    ],
    	    autoHeightEnabled: true,
    	    autoFloatEnabled: true,
    	    elementPathEnabled:false,
    	    wordCount:false
        });
    }

    function initEditorDetail(editorId){
        return UE.getEditor(editorId, {
            toolbars: [
                []
            ],
            autoHeightEnabled: true,
            autoFloatEnabled: true,
            elementPathEnabled:false,
            wordCount:false,
            readonly:true
        });
    }
</script>