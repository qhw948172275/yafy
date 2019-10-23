<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<form id="dataForm" class="form-horizontal hidden" role="form" style="width: 90%;">

    <div class="form-group">
        <div class="col-sm-12">
            <label class="control-label col-sm-4 col-lg-3"></label>
            <div class="col-sm-8">
                <input id="id" name="id" type="hidden" class="form-control input-sm" v-model="obj.id"
                       placeholder=""/>
            </div>
        </div>
    </div>

    <div class="form-group">

        <div class="col-sm-12">
            <label class="control-label col-sm-4 col-lg-3">系统key</label>
            <div class="col-sm-8">
                <input id="name" name="name" type="text" class="form-control input-sm" v-model="obj.name"
                       placeholder="系统key"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-12">
            <label class="control-label col-sm-4 col-lg-3">系统名称</label>
            <div class="col-sm-8">
                <input id="value" name="value" type="text" class="form-control input-sm" v-model="obj.value"
                       placeholder="系统名称"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-12">
            <label class="control-label col-sm-4 col-lg-3">访问路径</label>
            <div class="col-sm-8">
                <input name="url" type="text" class="form-control input-sm" v-model="obj.url"
                       placeholder="访问路径"/>
            </div>
        </div>
    </div>

</form>