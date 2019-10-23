<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>用户管理</title>
<%@include file="../../common/title.jsp"%>
<link href="${ctx}/public/css/system/resource/info.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/public/plugins/zTree/css/bootstrapStyle/bootstrapStyle.css" type="text/css">
<link rel="stylesheet" href="${ctx }/public/plugins/font-awesome/css/font-awesome.min.css">
<style type="text/css">
.row {
	width: 100%;
}

.row:after {
	clear: both;
	display: block;
}

.row-40 {
	width: 40%;
	float: left;
}

.row-60 {
	width: 60%;
	float: left;
}

.panel {
	border: 1px solid #ccc;
	margin: 0 15px;
}
.hidden{
	display: none;
}
.layui-form input[type=radio]{
	display: block !important;
}
.radio-inline{
	line-height: 14px;
    margin: 14px 10px 0 0;
    padding-right: 10px;
    cursor: pointer;
    display: -webkit-inline-box;
    vertical-align: middle;
}
.radio-inline span{
	margin-left: 3px;
}

</style>
</head>
<body>

	<div class="row">
		<div class="row-40">
			<div class="panel">
				<div class="panel-heading">
					<h5>菜单</h5>
				</div>
				<div class="panel-body">
					<div style="height: 700px; overflow-y: auto;">
						<input id="refreshNode" type="button" value="刷新节点" />
						<ul id="treeDemo" class="ztree"></ul>
					</div>
				</div>
			</div>
		</div>
		<div class="row-60">
			<div class="panel">
				<div class="panel-heading">
					<h5>菜单</h5>
				</div>
				<div class="panel-body">
					<form id="vueForm" class="layui-form" method="post">
						<input id="id" name="id" type="hidden" v-model.trim="resource.id"/>
						<div class="layui-form-item hidden">
							<label class="layui-form-label">所属系统</label>
							<div class="layui-input-inline">
								<select id="resourceKind" name="resourceKind" v-model="resource.resourceKind">
									<option v-for="option in systemSubManage" v-bind:value="option.id">
		                                {{ option.value}}
		                            </option>
								</select>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">资源名称</label>
							<div class="layui-input-block">
								<input type="text" data-ready-exists="0" name="resourceName"
                                                   id="resourceName" placeholder="资源名称"
                                                   v-model.trim="resource.resourceName" class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">资源路径</label>
							<div class="layui-input-inline">
								<input data-ready-exists="0" type="text" name="resourceUrl" id="resourceUrl"
                                       placeholder="资源访问路径"  v-model.trim="resource.resourceUrl" autocomplete="off" class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">资源图标</label>
							<div class="layui-input-block">
								<input data-ready-exists="0" type="text" name="icon" id="icon"
                                        placeholder="资源图标"  v-model.trim="resource.icon" autocomplete="off" class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">资源顺序</label>
							<div class="layui-input-block">
								<input data-ready-exists="0" type="text" name="seq" id="seq"
                                       placeholder="资源顺序"  v-model.trim="resource.seq" autocomplete="off" class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">是否基础功能</label>
							<div class="layui-input-block">
								<label class="radio-inline">
									<input type="radio" id="isBasic" name="isBasic" value="0" v-model.trim="resource.isBasic"> <span>是</span>
								</label>
								<label class="radio-inline">
									<input type="radio" name="isBasic" value="1" v-model.trim="resource.isBasic"> <span>否</span>
								</label>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">资源类型</label>
							<div class="layui-input-block">
								<label class="radio-inline">
									<input type="radio" id="resourceType" name="resourceType" value="0" v-model.trim="resource.resourceType"> <span>菜单</span>
								</label>
								<label class="radio-inline">
									<input type="radio" name="resourceType" value="1"  v-model.trim="resource.resourceType"> <span>按钮</span>
								</label>
							</div>
						</div>
						<input type="hidden" name="level" value="" v-model.trim="resource.level"/>
                        <input type="hidden" name="parentId" value="" v-model.trim="resource.parentId"/>
						<div class="layui-form-item layui-form-text">
							<label class="layui-form-label">备注</label>
							<div class="layui-input-block">
								<textarea id="remark" name="remark" rows="3" 
                                          v-model.trim="resource.remark" class="layui-textarea" ></textarea>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">状态</label>
							<div class="layui-input-block">
								 <label class="radio-inline">
                                   <input type="radio" id="status" name="status" value="0"  v-model="resource.status"> <span>启用</span>
                                 </label>
                                 <label class="radio-inline">
                                   <input type="radio" name="status" value="1" v-model="resource.status"> <span>禁用</span>
                                 </label>
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-input-block">
								<button type="button" class="layui-btn" onclick="_resource.saveBtn()">立即提交</button>
								<button type="reset" class="layui-btn layui-btn-primary">重置</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/public/js/system/resource/info.js"></script>
	<script type="text/javascript" src="${ctx}/public/plugins/jquery.form.min.js"></script>
	<script type="text/javascript" src="${ctx}/public/plugins/zTree/js/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/public/plugins/vue.js"></script>
	<script>
	var systemSubManage = [];
    $.ajax({
        url: path + "/system/subSystem/listAllJson",
        success: function (res) {
            if (res.status) {
                systemSubManage = res.data;
            }
        }
    });

    var defaultResource = {
        id: null,
        resourceName: null,
        resourceUrl: null,
        icon: null,
        seq: 1,
        isBasic: 1,
        resourceType: 0,
        resourceKind: null,
        level: null,
        parentId: null,
        remark: null,
        status: 0,

        parentNode: null,
        tempNode: true
    };

    var vm = new Vue({
        // 选项
        el: '#vueForm',
        data: {
            systemSubManage: systemSubManage,
            resource: defaultResource
        }
    });

    function repalceResourceObj(vm, resourceObj) {
        if (resourceObj.tempNode === true) {
            resourceObj.id = null;
        }
        vm.resource = Object.assign({}, vm.resource, resourceObj);
    }
	</script>
	<script>
		var setting = {
			view : {
				addHoverDom : addHoverDom,
				removeHoverDom : removeHoverDom,
				selectedMulti : false
			},
			check : {
				enable : true
			},
			data : {
				key : {
					name : "resourceName"
				},
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId",
				},
			},
			edit : {
				enable : true,
				showRenameBtn : false,
			},
			async : {
				enable : true,
				url : path + "/system/resource/selectListByResourceKind",
				autoParam : [ "id", "parentId" ],
			// otherParam:{"otherParam":"zTreeAsyncTest"},
			// dataFilter: filter
			},
			callback : {
				beforeClick : beforeClick,
				onClick : zTreeOnClick,
				beforeAsync : beforeAsync,
				onAsyncError : onAsyncError,
				onAsyncSuccess : onAsyncSuccess,
				onNodeCreated : zTreeOnNodeCreated,
				beforeRemove : zTreeBeforeRemove
			}

		};

		function filter(treeId, parentNode, childNodes) {
			if (!childNodes)
				return null;
			for (var i = 0, l = childNodes.length; i < l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}

		function beforeClick(treeId, treeNode) {
			if (!treeNode.id) {
				alert("请选择父节点");
				return false;
			} else {
				return true;
			}
		}

		// 点击树节点,加载表单
		function zTreeOnClick(event, treeId, treeNode) {
			console.log(treeNode);
			if (treeNode.level === 0) {
				repalceResourceObj(vm, defaultResource);
				console.info("不允许修改顶级节点");
				displayInfoHiddenForm("不允许修改顶级节点");
			} else {
				repalceResourceObj(vm, treeNode);
				displayFormHiddenInfo();
			}
// 			repalceResourceObj(vm, treeNode);
		}

		// 新建节点,表单回显名称
		function zTreeOnNodeCreated(event, treeId, treeNode) {
			var resourceKind = "";
			var tempObj = Object.assign({}, treeNode);
			while (!(tempObj.level === 0)) {
				tempObj = tempObj.getParentNode();
			}
			resourceKind = tempObj.id;
			repalceResourceObj(vm, Object.assign({}, defaultResource, {
				id : null,
				resourceName : treeNode.name,
				parentId : treeNode.pId,
				level : treeNode.level,
				parentNode : treeNode.getParentNode(), // 页面使用对象
				resourceKind : resourceKind
			}));
		}

		// 删除节点前判断
		function zTreeBeforeRemove(treeId, treeNode) {
			if (treeNode.level === 0) {
				alert("不允许修改顶级节点");
				return false;
			}
			if (confirm('确认删除吗？')) {
				var is = $.ajax({
					async : false,
					url : path + "/system/resource/delete?id=" + treeNode.id,
					type : 'get',
					dataType : 'json',
					success : function(res) {
						if (res.message == 0) {
							return true;
						} else {
							alert('删除失败');
							return false;
						}
					}
				});
				return is;
			} else {
				return false;
			}

		}

		var log, className = "dark";

		function beforeAsync(treeId, treeNode) {
			className = (className === "dark" ? "" : "dark");
			showLog("[ "
					+ getTime()
					+ " beforeAsync ]&nbsp;&nbsp;&nbsp;&nbsp;"
					+ ((!!treeNode && !!treeNode.name) ? treeNode.name : "root"));
			return true;
		}

		function onAsyncError(event, treeId, treeNode, XMLHttpRequest,
				textStatus, errorThrown) {
			showLog("[ "
					+ getTime()
					+ " onAsyncError ]&nbsp;&nbsp;&nbsp;&nbsp;"
					+ ((!!treeNode && !!treeNode.name) ? treeNode.name : "root"));
		}

		function onAsyncSuccess(event, treeId, treeNode, msg) {
			showLog("[ "
					+ getTime()
					+ " onAsyncSuccess ]&nbsp;&nbsp;&nbsp;&nbsp;"
					+ ((!!treeNode && !!treeNode.name) ? treeNode.name : "root"));
		}

		function showLog(str) {
			if (!log)
				log = $("#log");
			log.append("<li class='" + className + "'>" + str + "</li>");
			if (log.children("li").length > 8) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}

		function getTime() {
			var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now
					.getSeconds(), ms = now.getMilliseconds();
			return (h + ":" + m + ":" + s + " " + ms);
		}

		/**
		 * 显示消息,隐藏表单
		 */
		function displayInfoHiddenForm(msg) {
			$("#treeInfo").show();
			$("#vueForm").hide();
			if ($("#treeMsg").length <= 0) {
				$("#treeInfo ul")
						.append(
								"<li id='treeMsg' style='color: red;'>" + msg
										+ "</li>");
			}
		}

		/**
		 * 显示表单,隐藏消息
		 */
		function displayFormHiddenInfo() {
			$("#treeInfo").hide();
			$("#vueForm").show();
			$("#treeMsg").remove();
		}

		/**
		 * 选中刷新节点
		 * @param e
		 */
		function refreshNode(e) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"), type = e.data.type, silent = e.data.silent, nodes = zTree
					.getSelectedNodes();
			if (nodes.length == 0) {
				alert("请先选择一个父节点");
			}
			for (var i = 0, l = nodes.length; i < l; i++) {
				zTree.reAsyncChildNodes(nodes[i], type, silent);
				if (!silent)
					zTree.selectNode(nodes[i]);
			}
		}

		/**
		 * 保存刷新父节点
		 * @param e
		 */
		function refreshNodeForSave(e, nodes) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"), type = e.data.type, silent = e.data.silent;
			if (!nodes || nodes.length == 0) {
				alert("缺少父节点");
			}
			for (var i = 0, l = nodes.length; i < l; i++) {
				zTree.reAsyncChildNodes(nodes[i], type, silent);
				if (!silent)
					zTree.selectNode(nodes[i]);
			}
		}

		$.fn.zTree.init($("#treeDemo"), setting);
		// 方法绑定
		$("#refreshNode").bind("click", {
			type : "refresh",
			silent : false
		}, refreshNode);
		$("#refreshNodeSilent").bind("click", {
			type : "refresh",
			silent : true
		}, refreshNode);
		$("#addNode").bind("click", {
			type : "add",
			silent : false
		}, refreshNode);
		$("#addNodeSilent").bind("click", {
			type : "add",
			silent : true
		}, refreshNode);

		var newCount = 1;

		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag
					|| $("#addBtn_" + treeNode.tId).length > 0)
				return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
					+ "' title='add node' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_" + treeNode.tId);
			if (btn)
				btn.bind("click", function() {

					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					zTree.addNodes(treeNode, {
						id : (100 + newCount),
						pId : treeNode.id,
						name : "new node" + (newCount++),
						tempNode : true
					});
					zTree.updateNode(treeNode);
					return false;
				});
		};

		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_" + treeNode.tId).unbind().remove();
		};
		
	</script>
</body>
</html>