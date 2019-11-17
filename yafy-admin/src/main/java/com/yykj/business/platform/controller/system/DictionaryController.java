package com.yykj.business.platform.controller.system;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.business.BaseController;
import com.yykj.business.conf.aop.OperateLogAnnotation;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import com.yykj.system.entity.Dictionary;
import com.yykj.system.service.DictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  @author: 王晓
 *  @Date: 2019/10/29 9:58
 *  @Description: 系统字典控制器
 */
@Api(tags = "系统字典接口")
@RestController
@RequestMapping(value = "dictionary")
public class DictionaryController extends BaseController {
    @Autowired
    DictionaryService dictionaryService;

    /**
     * @Description
     * @Author 王晓
     * @Date 2019/10/31 15:51
     * @Param dictionary
     * @Param page
     * @Param limit
     * @Return com.yykj.system.commons.result.JsonResult
     * @Exception 分页查询数据
     */
    @ApiOperation(value="分页查询数据",response = Dictionary.class)
    @RequestMapping(value = "page", method = RequestMethod.POST)
    protected JsonResult page(@RequestBody Dictionary dictionary, @RequestParam(value="page",defaultValue="1")Integer page,
                              @RequestParam(value="limit",defaultValue="20")Integer limit) {
        try{
            PageHelper.startPage(page,limit);
            List<Dictionary> data=dictionaryService.getDataWithList(dictionary.getTypeCode(),dictionary.getLabel(),dictionary.getTypeValue());
            return JsonResultUtils.buildJsonOK(new PageInfo<>(data));
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
        return JsonResultUtils.buildJsonFail();
    }

    /**
     * @Description 根据id查询
     * @Author 王晓
     * @Date 2019/10/28 17:48
     * @Param id
     * @Return com.yykj.system.commons.result.JsonResult
     * @Exception
     */
    @ApiOperation(value="根据id查询")
    @GetMapping("/{id}" )
    public JsonResult getById(@PathVariable("id" ) Integer id) {
        return JsonResultUtils.buildJsonOK(dictionaryService.getById(id));
    }

    /**
     * @Description 新增
     * @Author 王晓
     * @Date 2019/10/28 17:08
     * @Param dictionary
     * @Return com.yykj.system.commons.result.JsonResult
     * @Exception
     */
    @OperateLogAnnotation(value = "新增字典")
    @ApiOperation(value="新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    protected JsonResult save(@RequestBody Dictionary dictionary){
        Dictionary info = new Dictionary();
        BeanUtils.copyProperties(dictionary,info);
        info.setCreateTime(CalendarUtils.getDate());
        if(dictionaryService.insert(info) > 0 ){
            return JsonResultUtils.buildJsonOK();
        }
        return JsonResultUtils.buildJsonFailMsg("新增失败，请联系管理员");
    }
    /**
     * @Description 修改
     * @Author 王晓
     * @Date 2019/10/28 17:45
     * @Param dictionary
     * @Return com.yykj.system.commons.result.JsonResult
     * @Exception
     */
    @OperateLogAnnotation(value = "修改字典")
    @ApiOperation(value="修改")
    @RequestMapping(value = "/updateById", method = RequestMethod.POST)
    protected JsonResult updateById(@RequestBody Dictionary dictionary){
        Dictionary info = new Dictionary();
        BeanUtils.copyProperties(dictionary,info);
        info.setCreateTime(CalendarUtils.getDate());
        if(dictionaryService.updateById(info) > 0 ){
            return JsonResultUtils.buildJsonOK();
        }
        return JsonResultUtils.buildJsonFailMsg("更新失败，请联系管理员");
    }
    /**
     * @Description 删除
     * @Author 王晓
     * @Date 2019/10/28 17:46
     * @Param dictionary
     * @Return com.yykj.system.commons.result.JsonResult
     * @Exception
     */
    @OperateLogAnnotation(value = "删除字典")
    @ApiOperation(value="删除")
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    protected JsonResult deleteById(@RequestBody Dictionary dictionary){
        if(dictionaryService.delete(dictionary) > 0 ){
            return JsonResultUtils.buildJsonOK();
        }
        return JsonResultUtils.buildJsonFailMsg("删除失败，请联系管理员");
    }

    @ApiOperation(value="根据类型代码查询")
    @GetMapping("/type/{typeCode}" )
    public JsonResult getRemoteByType(@PathVariable("typeCode" ) String typeCode) {
        return JsonResultUtils.buildJsonOK(dictionaryService.getRemoteByType(typeCode));
    }
}
