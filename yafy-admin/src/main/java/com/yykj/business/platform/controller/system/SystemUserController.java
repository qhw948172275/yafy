package com.yykj.business.platform.controller.system;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.business.BaseController;
import com.yykj.business.conf.aop.OperateLogAnnotation;
import com.yykj.system.dto.SysUserDto;
import com.yykj.system.vo.PasswordVo;
import com.yykj.system.commons.*;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import com.yykj.system.entity.SysRoleUser;
import com.yykj.system.entity.SysUser;
import com.yykj.system.service.SysRoleUserService;
import com.yykj.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户管理接口")
@RestController
@RequestMapping("systemUser")
public class SystemUserController extends BaseController {
    public Logger log= LoggerFactory.getLogger(SystemUserController.class);
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    SysRoleUserService sysRoleUserService;

    /**
     * 用户列表
     * @return
     */
    @ApiOperation(value = "用户列表",response =SysUser.class )
    @GetMapping(value = "index")
    public JsonResult index(@RequestParam(value="page",defaultValue="1")Integer page,
                            @RequestParam(value="limit",defaultValue="20")Integer limit,String keyword){
        try {
            ObjectNode json=JsonUtils.getMapperInstance().createObjectNode();
            PageHelper.startPage(page,limit);
            List<SysUser> sysUsers= sysUserService.selectByKeyword(keyword);
            json.putPOJO("sysUsers",new PageInfo<>(sysUsers));
            return JsonResultUtils.buildJsonOK(json);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * 编辑用户信息
     * @param userId
     * @return
     */
    @ApiOperation(value="获取编辑用户信息",response = SysUser.class)
    @GetMapping(value = "edit")
    public JsonResult edit(Integer userId ){
        try {
            ObjectNode obj = JsonUtils.getMapperInstance().createObjectNode();
            SysUser sysUser=sysUserService.getById(userId);
            obj.putPOJO("sysUser",sysUser);
            List<SysRoleUser> sysRoleUsers=sysRoleUserService.selectByUserId(userId);
            obj.putPOJO("sysRoleUsers",sysRoleUsers);
            return JsonResultUtils.buildJsonOK(obj);
        }catch (Exception e){
            log.debug(e.getLocalizedMessage());
        }
        return JsonResultUtils.buildJsonFail();
    }

    /**
     * 保存用户信息
     * @return
     */
    @ApiOperation(value = "保存用户信息")
    @PostMapping(value = "save")
    @OperateLogAnnotation(value = "保存用户信息")
    public JsonResult save(@RequestBody SysUserDto sysUserDto){
            try {
                sysUserDto.setCreatetime(CalendarUtils.getDate());
                sysUserDto.setCreator(getSysUer().getName());
                sysUserDto.setCreatorId(getSysUer().getId());
                if(sysUserDto.getSchoolId()==null){
                    sysUserDto.setSchoolId(getSysUer().getSchoolId());
                }
                sysUserService.save(sysUserDto);
                return JsonResultUtils.buildJsonOK();
            }catch (Exception e){
                e.printStackTrace();
                return JsonResultUtils.buildJsonFailMsg(e.getMessage());
            }
    }


    /**
     * 重置密码
     * @return
     */
    @ApiOperation(value = "重置密码")
    @PostMapping(value = "resetPassword")
    public JsonResult resetPassword(@RequestBody PasswordVo passwordVo){
        try {
            SysUser sysUser=new SysUser();
            sysUser.setId(passwordVo.getUserId());
            sysUser.setPassword(MD5Password.md5(SystemConstants.defaultPassword));
            sysUserService.updateById(sysUser);
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * 个人信息修改
     * @return
     */
    @ApiOperation(value="个人信息修改")
    @PostMapping(value = "modifyUserInfo")
    public JsonResult modifyUserInfo(@RequestBody SysUser sysUser){
        try {
            if(StringUtils.isNotEmpty(sysUser.getPassword())){
                sysUser.setPassword(MD5Password.md5(sysUser.getPassword()));
            }else{
                sysUser.setPassword(null);
            }
            sysUserService.updateById(sysUser);
            SecurityUtils.getSubject().logout();//退出登陆
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }
}
