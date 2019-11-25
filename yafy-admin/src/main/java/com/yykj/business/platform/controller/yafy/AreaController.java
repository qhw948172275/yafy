package com.yykj.business.platform.controller.yafy;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.business.BaseController;
import com.yykj.business.entity.Agreement;
import com.yykj.business.entity.Area;
import com.yykj.business.entity.RentManage;
import com.yykj.business.response.BigLandlordDetailResponse;
import com.yykj.business.service.AgreementService;
import com.yykj.business.service.AreaService;
import com.yykj.business.service.BigLandlordService;
import com.yykj.business.service.RentManageService;
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
 * @Date 2019/11/20 0020 下午 17:07
 * @Version V1.0
 **/
@Api(tags = "套房管理")
@RestController
@RequestMapping("area")
public class AreaController extends BaseController {
    @Autowired
    AreaService areaService;
    @Autowired
    BigLandlordService bigLandlordService;
    @Autowired
    RentManageService rentManageService;
    @Autowired
    AgreementService agreementService;

    /**
     * description: 套房列表
     * create by: qhw
     * create time: 2019/11/20 0020 下午 17:09
     */
    @ApiOperation(value = "套房列表",response =Area.class )
    @GetMapping("areaList")
    public JsonResult areaList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "limit", defaultValue = "20") Integer limit){
        try {
            PageHelper.startPage(page,limit);
            List<Area> areaList=areaService.selectAreaListByUserId(getSysUer().getId());
            return JsonResultUtils.buildJsonOK(new PageInfo<>(areaList));
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * description: 编辑保存套房
     * create by: qhw
     * create time: 2019/11/20 0020 下午 17:11
     */
    @ApiOperation("编辑保存套房")
    @PostMapping("save")
    public JsonResult save(@RequestBody Area area){
        try {
            if(area.getId()==null){
                area.setCreateTime(CalendarUtils.getDate());
                area.setCreatorId(getSysUer().getId());
                areaService.insert(area);
            }else{
                areaService.updateById(area);
            }
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * description:删除套间
     * create by: qhw
     * create time: 2019/11/20 0020 下午 17:23
     */
    @GetMapping("delete")
    @ApiOperation("删除套间")
    public JsonResult delete(@RequestParam Integer areaId){
            try {
                Area area=new Area();
                area.setId(areaId);
                area.setStatus(SystemConstants.STATUS_NO.byteValue());//删除
                areaService.updateById(area);
                return JsonResultUtils.buildJsonOK();
            }catch (Exception e){
                e.printStackTrace();
                return JsonResultUtils.buildJsonFailMsg(e.getMessage());
            }
    }

    /**
     * description:套房详情
     * create by: qhw
     * create time: 2019/11/25 0025 下午 22:49
     */
    @ApiOperation(value = "套房详情",response = Area.class)
    @GetMapping("detail")
    public JsonResult detail(@RequestParam Integer areaId){
        try {
            Area area=areaService.getById(areaId);
            return JsonResultUtils.buildJsonOKData("area",area);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

//    /**
//     * description:  大房东信息详情
//     * create by: qhw
//     * create time: 2019/11/17 0017 下午 21:04
//     */
//    @ApiImplicitParam(name = "areaId",value = "小区套房ID")
//    @GetMapping("bigLandlordDetail")
//    @ApiOperation(value = "大房东信息详情",response = BigLandlordDetailResponse.class)
//    public JsonResult bigLandlordDetail(@RequestParam Integer areaId){
//        try {
//            BigLandlordDetailResponse bigLandlordDto=bigLandlordService.selectBigLandlordDtoByAreaId(areaId);
//            return JsonResultUtils.buildJsonOK(bigLandlordDto);
//        }catch (Exception e){
//            e.printStackTrace();
//            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
//        }
//
//    }

//    /**
//     * description:  缴费记录
//     * create by: qhw
//     * create time: 2019/11/17 0017 下午 20:42
//     */
//    @ApiOperation(value = "缴费记录",response = RentManage.class)
//    @GetMapping("payTimeLog")
//    @ApiImplicitParam(name = "areaId",value = "小区套房ID")
//    public JsonResult payTimeLog(@RequestParam Integer areaId,@RequestParam(value = "page", defaultValue = "1") Integer page,
//                                 @RequestParam(value = "limit", defaultValue = "20") Integer limit){
//        try {
//            PageHelper.startPage(page,limit);
//            List<RentManage> rentManages=rentManageService.selectRentManage(areaId);
//            return JsonResultUtils.buildJsonOK(new PageInfo<>(rentManages));
//        }catch (Exception e){
//            e.printStackTrace();
//            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
//        }
//    }

//    /**
//     * description:  合同记录
//     * create by: qhw
//     * create time: 2019/11/17 0017 下午 22:47
//     */
//    @ApiOperation(value = "合同记录",response = Agreement.class )
//    @GetMapping("agreementLog")
//    @ApiImplicitParam(name = "areaId",value = "小区套房ID")
//    public JsonResult agreementLog(@RequestParam Integer areaId,@RequestParam(value = "page", defaultValue = "1") Integer page,
//                                   @RequestParam(value = "limit", defaultValue = "20") Integer limit){
//        try {
//            PageHelper.startPage(page,limit);
//            List<Agreement> agreements=agreementService.selectAgreementByAreaId(areaId);
//            return JsonResultUtils.buildJsonOK(new PageInfo<>(agreements));
//        }catch (Exception e){
//            e.printStackTrace();
//            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
//        }
//    }
}
