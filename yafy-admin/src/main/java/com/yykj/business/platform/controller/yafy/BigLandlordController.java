package com.yykj.business.platform.controller.yafy;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.business.BaseController;
import com.yykj.business.dto.BigLandlordDto;
import com.yykj.business.entity.Agreement;
import com.yykj.business.entity.BigLandlord;
import com.yykj.business.entity.RentManage;
import com.yykj.business.response.BigLandlordDetailResponse;
import com.yykj.business.response.BigLandlordResponse;
import com.yykj.business.service.AgreementService;
import com.yykj.business.service.BigLandlordService;
import com.yykj.business.service.RentManageService;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName BigLandlordController
 * @Description: TODO
 * @Author qhw
 * @Date 2019/11/17 0017 下午 12:04
 * @Version V1.0
 **/
@Api(tags = "大房东")
@RestController
@RequestMapping("bigLandlord")
public class BigLandlordController extends BaseController {
    @Autowired
    BigLandlordService bigLandlordService;
    @Autowired
    RentManageService rentManageService;
    @Autowired
    AgreementService agreementService;

//    /**
//     * description: 大房东列表
//     * create by: qhw
//     * create time: 2019/11/17 0017 下午 16:37
//     */
//    @ApiOperation(value = "大房东列表",response = BigLandlordResponse.class)
//    @GetMapping("index")
//    @ApiImplicitParam(name = "status" ,value = "0-未交，1-已交")
//    public JsonResult index(@RequestParam(value = "page", defaultValue = "1") Integer page,
//                            @RequestParam(value = "limit", defaultValue = "20") Integer limit,@RequestParam Integer status){
//            try {
//                PageHelper.startPage(page,limit);
//                List<BigLandlordResponse> bigLandlordResponses=bigLandlordService.selectBigLandlordDto(getSysUer().getId(),status);
//                return JsonResultUtils.buildJsonOK(new PageInfo<>(bigLandlordResponses));
//            }catch (Exception e){
//                e.printStackTrace();
//                return JsonResultUtils.buildJsonFailMsg(e.getMessage());
//            }
//    }

//    /**
//     * description:  添加大房东房间信息
//     * create by: qhw
//     * create time: 2019/11/17 0017 下午 16:50
//     */
//    @ApiOperation("添加大房东房间信息")
//    @PostMapping("saveBigLandlord")
//    public JsonResult saveBigLandlord(@RequestBody BigLandlordDto bigLandlordDto){
//        try {
//            bigLandlordService.save(bigLandlordDto,getSysUer());
//            return JsonResultUtils.buildJsonOK();
//        }catch (Exception e){
//            e.printStackTrace();
//            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
//        }
//    }
   /**
    * description: 大房东信息列表
    * create by: qhw
    * create time: 2019/11/20 0020 下午 17:47
    */
    @GetMapping("bigLandlordList")
    @ApiOperation(value = "大房东信息列表",response = BigLandlord.class)
    public JsonResult bigLandlordList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "limit", defaultValue = "20") Integer limit){
        try {
            PageHelper.startPage(page,limit);
            List<BigLandlord> bigLandlords=bigLandlordService.selectBigLandlordsByUserId(getSysUer().getId());
            return JsonResultUtils.buildJsonOK(new PageInfo<>(bigLandlords));
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * description:保存大房东
     * create by: qhw
     * create time: 2019/11/20 0020 下午 18:08
     */
    @PostMapping("save")
    @ApiOperation("编辑保存大房东")
    public JsonResult save(@RequestBody BigLandlord bigLandlord){
        try {
            if(bigLandlord.getId()==null){
                bigLandlord.setCreateTime(CalendarUtils.getDate());
                bigLandlord.setCreatorId(getSysUer().getId());
                bigLandlordService.insert(bigLandlord);
            }else{
                bigLandlordService.updateById(bigLandlord);
            }
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }
    /**
     * description:大房东详情
     * create by: qhw
     * create time: 2019/11/25 0025 下午 22:51
     */
    @ApiOperation(value = "大房东详情",response = BigLandlord.class)
    @GetMapping("detail")
    public JsonResult detail(@RequestParam Integer bigLandlordId){
        try {
            BigLandlord bigLandlord=bigLandlordService.getById(bigLandlordId);
            return JsonResultUtils.buildJsonOKData("bigLandlord",bigLandlord);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }
    /**
     * description:删除大房东
     * create by: qhw
     * create time: 2019/11/25 0025 下午 22:53
     */
    @ApiOperation("删除大房东")
    @GetMapping("delete")
    public JsonResult delete(@RequestParam Integer bigLandlordId){
        try {
            BigLandlord bigLandlord=new BigLandlord();
            bigLandlord.setId(bigLandlordId);
            bigLandlord.setStatus(SystemConstants.STATUS_NO.byteValue());
            bigLandlordService.updateById(bigLandlord);
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }
}
