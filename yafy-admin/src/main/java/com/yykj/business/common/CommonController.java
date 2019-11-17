package com.yykj.business.common;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yykj.business.BaseController;
import com.yykj.commons.upyun.UpYunUtils;
import com.yykj.system.commons.JsonUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@RestController(value="consoleCommonController")
@RequestMapping(value = "commons")
public class CommonController extends BaseController {

    /**
     * 公共图片文件上传
     *
     * @return
     * @author chenbiao
     * @date 2017年3月14日 下午9:21:59 参数说明
     */
    @RequestMapping(value = "upload/img", method = RequestMethod.POST)
    protected ObjectNode uploadImage(@RequestParam(value = "file") MultipartFile file, Model model) {
        ObjectNode json = JsonUtils.getMapperInstance().createObjectNode();
        if (null != file && file.getSize() > 0) {
            String fileName = UpYunUtils.uploadFtpFile(file, SystemConstants.UPLOAD_DIR);
            json.put("code", 0);
            json.put("msg", "");
            ObjectNode objectNode = JsonUtils.getMapperInstance().createObjectNode();
            objectNode.put("src", SystemConstants.VISIT_DIR + fileName);
            json.putPOJO("data", objectNode);
        }
        return json;
    }


    /**
     * 单个文件上传
     * @param file
     * @return
     */
    @ApiOperation(value="单个文件上传")
    @PostMapping(value="upload/file")
    public JsonResult uploadFile(@RequestParam("file") MultipartFile file){
        try{
            if(file.isEmpty()){
                return JsonResultUtils.buildJsonFail("文件为空");
            }
            String newFileName = UpYunUtils.uploadFtpFile(file, SystemConstants.UPLOAD_DIR);
            String newFilePath= SystemConstants.VISIT_DIR + newFileName;

            return JsonResultUtils.buildJsonOK(newFilePath);
        }catch (Exception e){
            log.info(e.getLocalizedMessage());
        }
        return JsonResultUtils.buildJsonFail();
    }
    /**
     * 公共图片文件上传
     *
     * @return
     * @author chenbiao
     * @date 2017年3月14日 下午9:21:59 参数说明
     */
    @RequestMapping(value = "upload/imgs", method = RequestMethod.POST)
    protected JsonResult uploadImages(@RequestParam(value = "file") CommonsMultipartFile[] files, Model model) {
        if (files.length > 0) {
            ArrayNode arrayNode = JsonUtils.getMapperInstance().createArrayNode();
            for (CommonsMultipartFile file : files) {
                if (null != file && file.getSize() > 0) {
                    String fileName = UpYunUtils.uploadFile(file, SystemConstants.UPLOAD_DIR);
                    arrayNode.add(SystemConstants.VISIT_DIR + fileName);
                }
            }
            return JsonResultUtils.buildJsonOK(arrayNode);
        }
        return JsonResultUtils.buildJsonFail();
    }
}
