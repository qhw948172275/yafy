package com.yykj.system.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.yykj.system.commons.controller.CallbackAjax;
import com.yykj.system.commons.controller.CallbackPageInfo;
import com.yykj.system.commons.controller.IController;
import com.yykj.system.commons.result.AjaxJsonResults;
import com.yykj.system.constants.SystemConfig;
import com.yykj.system.entity.SystemSubManage;
import com.yykj.system.service.SystemSubManageService;

@Controller
@RequestMapping(value = "/system/subSystem")
public class SystemSubManageController implements IController<SystemSubManage> {

    @Resource
    private SystemSubManageService systemSubManageService;

    @Override
    @RequestMapping(value = "/index")
    public String index(Map<String, Object> queryParams) {
        return "/system/subSystem/index";
    }

    @RequestMapping(value = "/listJson")
    @ResponseBody
    public Object listJson( @RequestBody final Map<String, Object> queryParams) {

        return AjaxJsonResults.returnBootTable(new CallbackPageInfo() {
            @Override
            public PageInfo callback() {
                PageInfo list = systemSubManageService.selectAllPage(queryParams);
                return list;
            }
        }, SystemConfig.IS_OUT_ERROR);

    }

    @RequestMapping(value = "/listAllJson")
    @ResponseBody
    public Object listAllJson() {

        return AjaxJsonResults.returnAjax(new CallbackAjax() {
            @Override
            public List callback() {
                List list = systemSubManageService.selectAllList(null);
                return list;
            }
        }, SystemConfig.IS_OUT_ERROR);

    }

    @Override
    @RequestMapping(value = "/single")
    public String single(Map<String, Object> queryParams) {
        return "/system/subSystem/single";
    }


    @Override
    @ResponseBody
    @RequestMapping(value = "/getById")
    public Object getObjById(@RequestParam("id") final Integer id) {

        return AjaxJsonResults.returnAjax(new CallbackAjax() {
            @Override
            public Object callback() {
                SystemSubManage manage = systemSubManageService.getById(id);
                return manage;
            }
        }, SystemConfig.IS_OUT_ERROR);

    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/insert")
    public Object insert(final SystemSubManage o) {

        return AjaxJsonResults.returnAjax(new CallbackAjax() {
            @Override
            public Object callback() {
                Object obj = systemSubManageService.insert(o);
                return obj;
            }
        }, SystemConfig.IS_OUT_ERROR);

    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/update")
    public Object update(final SystemSubManage o) {

        return AjaxJsonResults.returnAjax(new CallbackAjax() {
            @Override
            public Object callback() {
                Object obj = systemSubManageService.updateById(o);
                return obj;
            }
        }, SystemConfig.IS_OUT_ERROR);

    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object delete(final Object id) {
        return AjaxJsonResults.returnAjax(new CallbackAjax() {
            @Override
            public Object callback() {
                Object obj = systemSubManageService.delete(id);
                return obj;
            }
        }, SystemConfig.IS_OUT_ERROR);
    }

    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Object deleteList(@RequestParam(value = "ids[]") final Integer[] ids) {
        return AjaxJsonResults.returnAjax(new CallbackAjax() {
            @Override
            public Object callback() {
                Object obj = systemSubManageService.deleteList(Arrays.asList(ids));
                return obj;
            }
        }, SystemConfig.IS_OUT_ERROR);
    }
}
