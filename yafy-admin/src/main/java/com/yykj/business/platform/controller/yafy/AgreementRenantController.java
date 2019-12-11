package com.yykj.business.platform.controller.yafy;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.business.BaseController;
import com.yykj.business.dto.AgreementRenantDto;
import com.yykj.business.response.AgreementRenantResponse;
import com.yykj.business.service.AgreementService;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author qhw
 * @Date 2019/12/3 0003 下午 17:40
 * @Version V1.0
 **/
@Api(tags ="租客合同管理" )
@RestController
@RequestMapping("renantAgreement")
public class AgreementRenantController extends BaseController {
    @Autowired
    AgreementService agreementService;
    /**
     * description:租客合同列表
     * create by: qhw
     * create time: 2019/12/3 0003 下午 17:42
     */
    @ApiOperation(value = "租客合同列表",response = AgreementRenantResponse.class)
    @GetMapping("index")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaId",value = "套房ID"),
            @ApiImplicitParam(name = "areaName",value = "小区名称")
    })
    public JsonResult index(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "limit", defaultValue = "20") Integer limit,
                            Integer areaId,String areaName,String startTime,String endTime){
        try {
            PageHelper.startPage(page,limit);
            List<AgreementRenantResponse> agreementList=agreementService.selectAgreementRenantResponse(getSysUer().getId(),areaId,areaName,startTime,endTime);
            return JsonResultUtils.buildJsonOK(new PageInfo<>(agreementList));
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * description:租客合同续期
     * create by: qhw
     * create time: 2019/12/3 0003 下午 18:07
     */
    @ApiOperation("租客合同续期")
    @PostMapping("addAgree")
    public JsonResult addAgree(@RequestBody AgreementRenantDto agreementRenantDto){
        agreementRenantDto.setCreateTime(CalendarUtils.getDate());
        agreementRenantDto.setCreatorId(getSysUer().getId());
        agreementService.addAgreeRenant(agreementRenantDto);
        return JsonResultUtils.buildJsonOK();
    }
}
