package com.yykj.business.platform.controller.yafy;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.business.BaseController;
import com.yykj.business.dto.AgreementBigRepsonse;
import com.yykj.business.entity.Agreement;
import com.yykj.business.response.AgreementResponse;
import com.yykj.business.service.AgreementService;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author qhw
 * @Date 2019/11/25 0025 下午 22:01
 * @Version V1.0
 **/
@Api(tags = "合同管理")
@RestController
@RequestMapping("agreement")
public class AgreementController extends BaseController {
    @Autowired
    AgreementService agreementService;
    /**
     * description:合同管理列表
     * create by: qhw
     * create time: 2019/11/25 0025 下午 22:28
     */
    @ApiOperation(value = "合同管理列表",response =Agreement.class )
    @GetMapping("index")
    public JsonResult index(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "limit", defaultValue = "20") Integer limit,
                            Integer areaId,String areaName,String startTime,String endTime){
        try {
            PageHelper.startPage(page,limit);
            List<AgreementResponse> agreementList=agreementService.selectAgreementResponse(getSysUer().getId(),areaId,areaName,startTime,endTime);
            return JsonResultUtils.buildJsonOK(new PageInfo<>(agreementList));
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }
    /**
     * description:编辑保存合同信息
     * create by: qhw
     * create time: 2019/11/25 0025 下午 22:33
     */
    @PostMapping("save")
    @ApiOperation("编辑保存合同信息")
    public JsonResult save(@RequestBody Agreement agreement){
        try {
            if (agreement.getId()==null){
                agreement.setCreatorId(getSysUer().getId());
                agreement.setCreateTime(CalendarUtils.getDate());
                agreementService.insert(agreement);
            }else{
                agreementService.updateById(agreement);
            }
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * description:删除
     * create by: qhw
     * create time: 2019/11/25 0025 下午 22:36
     */
    @ApiOperation("删除")
    @GetMapping("delete")
    public JsonResult delete(@RequestParam Integer agreementId){
        try {
            Agreement agreement=new Agreement();
            agreement.setId(agreementId);
            agreement.setStatus(SystemConstants.STATUS_NO.byteValue());
            agreementService.updateById(agreement);
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * description:合同详情
     * create by: qhw
     * create time: 2019/11/25 0025 下午 22:45
     */
    @ApiOperation(value = "合同详情",response = Agreement.class)
    @GetMapping("detail")
    public JsonResult detail(@RequestParam Integer agreementId){
        try {
            Agreement agreement=agreementService.getById(agreementId);
            return JsonResultUtils.buildJsonOKData("agreement",agreement);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * description:合同续期
     * create by: qhw
     * create time: 2019/12/1 0001 下午 19:48
     */
    @ApiOperation("合同续期")
    @PostMapping("addAgr")
    public JsonResult addAgr(@RequestBody AgreementBigRepsonse agreementBigRepsonse){
        try {
            agreementBigRepsonse.setCreatorId(getSysUer().getId());
            agreementBigRepsonse.setCreateTime(CalendarUtils.getDate());
            agreementService.addAgr(agreementBigRepsonse);
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * description:根据套房ID查询合同列表
     * create by: qhw
     * create time: 2019/12/1 0001 下午 20:19
     */
    @ApiOperation("根据套房ID查询合同列表")
    @GetMapping("agreementList")
    @ApiImplicitParam(name = "areaId",value = "套房Id")
    public JsonResult agreementList(@RequestParam Integer areaId,
                                    @RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "limit", defaultValue = "20") Integer limit){
        try {
            PageHelper.startPage(page,limit);
            List<Agreement> agreements=agreementService.selectAgreement(areaId,getSysUer().getId());
            return JsonResultUtils.buildJsonOK(new PageInfo<>(agreements));
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }
}
