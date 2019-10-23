//访问子系统管理列表操作
var _subSystem = {
    // 初始化bootstrap table
    initTable: function (btSelector, searchForm, url, columns) {
        var cfg = {
            url: url,
            contentType: "application/json",
            toolbar: "#bootTableTool",
            queryParams: function (params) {
                var query = {};
                var searchParams = getFormParams($(searchForm));
                query = searchParams;
                query.pageSize = params.pageSize;
                query.pageNumber = params.pageNumber;
                return query;
            },
            columns: columns
        };

        $(btSelector).BtInit(cfg);
    },
    // 带参数刷新bootstrap table
    refresh: function () {
        $(_subSystem.cfg.bt.bootTable).BtRefresh();
    },
    delList: function () {
        var _this = this;
        $.LyConfirm("确认删除吗？", function (index) {
            var list = $(_this.cfg.bt.bootTable).BtSelected();
            var arr = [];
            for (var obj in list) {
                arr.push(list[obj].id);
            }
            if (arr.length <= 0) {
                layer.close(index);
                return;
            }
            $.LyAjax({
                url: _this.cfg.url.delList,
                data: {
                    ids: arr
                }
            });
            $(_this.cfg.bt.bootTable).BtRefresh();
        });
    },

    openDetail: function (dataObj) {
        var _this = this;
        _this.vmObj.obj = Object.assign({}, dataObj);
        _this.iframe("detail");
    },

    // 打开表单
    // type add or edit or detail
    openForm: function (type) {
        var _this = this;
        if (type === "add") {
            _this.iframe("form");
        } else if (type === "edit") {
            var obj = $(_this.cfg.bt.bootTable).BtSelectSingleLy();
            var id = obj.id;
            _this.getJsonById(id, function (resultObj) {
                _this.iframe("form");
                _this.vmObj.obj = Object.assign({}, resultObj.data);
            });
        }


    },
    vmObj: {},
    initVm: function () {
        var _this = this;
        var vm = new Vue({
            // 选项
            el: _this.cfg.detail.form,
            data: {
                obj: {},
            }
        });
        _this.vmObj = vm;
    },
    // 保存方法
    save: function (okCall, failCall) {
        var _this = this;
        var $form = $(_this.cfg.detail.form);
        var idEle = $form.find("input[name=id]");
        var url = "";
        if (idEle && idEle.length > 0 && $.trim(idEle.val())) {
            url = _this.cfg.url.update;
        } else {
            url = _this.cfg.url.insert;
        }
        $.LyAjax({
            url: url,
            data: $form.serialize(),
            success: function (data) {
                if (data.status === true) {
                    $.LyMsgOk(data.msg);
                    if (okCall) {
                        okCall(data);
                    }
                } else {
                    $.LyMsgFail(data.msg);
                    if (failCall) {
                        failCall(data);
                    }

                }
            },
        })
    },
    // 获取数据
    getJsonById: function (id, okCall) {
        var _this = this;
        $.LyAjax({
            url: _this.cfg.url.getById + "?id=" + id,
            success: function (data) {
                if (data.status === true) {
                    // $.LyMsgOk(data.msg);
                    okCall(data);
                } else {
                    $.LyMsgFail(data.msg);
                }
            },
        })
    },
    del: function (id) {
        var _this = this;
        if (confirm('确认删除吗？')) {
            $.ajax({
                url: _this.cfg.url.del + "?id=" + id,
                type: 'get',
                dataType: 'json',
                success: function (res) {
                    if (res.message == 0) {
                        alert('删除成功');
                        location.reload();
                    } else {
                        alert('删除失败');
                    }
                }
            });
        }
        return false;
    },
    // form or detail
    iframe: function (type) {
        var _this = this;
        var $form = $(_this.cfg.detail.form);
        var formCfg = {
            title: "子系统新增/编辑",
            type: 1, // html代码1, url ：2
            // skin: 'layui-layer-rim', //加上边框
            area: ['450px', '300px'], //宽高
            content: $form,
            btn: ['保存并新建', '保存并关闭'],
            yes: function (index, layero) {
                _this.save(function () {
                    _this.vmObj.obj = Object.assign({});
                });
            },
            btn2: function (index, layero) {
                //按钮【按钮二】的回调
                _this.save(function () {
                    layer.close(index);
                });
                //return false 开启该代码可禁止点击该按钮关闭
            },
            end: function () {
                $("#dataForm").toggleClass("hidden");
                _this.vmObj.obj = Object.assign({});
                $(_this.cfg.bt.bootTable).BtRefresh();
            }
        };

        var detailCfg = {
            title: "子系统详情",
            type: 1, // html代码1, url ：2
            // skin: 'layui-layer-rim', //加上边框
            area: ['450px', '300px'], //宽高
            content: $form,
            btn: null,
            end: function () {
                $("#dataForm").toggleClass("hidden");
                _this.vmObj.obj = Object.assign({});
            }
        };
        var cfg = {};
        if (type === "form") {
            cfg = formCfg;
        } else if (type === "detail") {
            cfg = detailCfg;
        }

        $form.toggleClass("hidden");
        $.LyiFrame(null, cfg);
    },
};

$(function () {
    _subSystem = Object.assign(_subSystem, {
        cfg: {
            url: {
                listPage: basePath + "/system/subSystem/listJson",
                getById: basePath + "/system/subSystem/getById",
                insert: basePath + "/system/subSystem/insert",
                update: basePath + "/system/subSystem/update",
                del: basePath + "/system/subSystem/delete",
                delList: basePath + "/system/subSystem/delete",
            },
            detail: {
                form: "#dataForm",
            },
            bt: {
                searchForm: "#searchForm",
                bootTable: "#bootTable",

            },
        }
    });

    _subSystem.initTable(
        _subSystem.cfg.bt.bootTable,
        _subSystem.cfg.bt.searchForm,
        _subSystem.cfg.url.listPage, [
            {
                idField: "id",
                checkbox: true,
            },
            {
                field: "name",
                title: "系统key"
            },
            {
                field: "value",
                title: "系统名称"
            },
            {
                field: "url",
                title: "访问路径"
            }
        ]);
    // onDblClickCell
    $(_subSystem.cfg.bt.bootTable).on('dbl-click-row.bs.table', function (e, row, $element) {
        _subSystem.openDetail(row);
    });
    _subSystem.initVm();
});