package com.yykj.business.platform.controller.system;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.business.BaseController;
import com.yykj.business.conf.aop.OperateLogAnnotation;
import com.yykj.system.dto.ResourceDto;
import com.yykj.system.dto.RoleResourceDto;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.JsonUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import com.yykj.system.entity.SysRole;
import com.yykj.system.entity.SysRoleResource;
import com.yykj.system.service.ResourceDtoService;
import com.yykj.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "角色管理接口")
@RestController
@RequestMapping(value = "system/role")
public class SystemRoleController extends BaseController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private ResourceDtoService resourceDtoService;

    /**
     * 获取本校所有角色
     */
    @ApiOperation(value = "获取本校所有角色", response = SysRole.class)
    @GetMapping(value = "")
    public JsonResult getRoles(@RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "limit", defaultValue = "20") Integer limit, String sreach) {
        try {
            PageHelper.startPage(page, limit);
            List<SysRole> roleList = sysRoleService.getAllRole(getSysUer().getSchoolId(), sreach, null, null);
            return JsonResultUtils.buildJsonOK(new PageInfo<>(roleList));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return JsonResultUtils.buildJsonFail();
    }


    /**
     * 根据用户身份获取角色信息
     *
     * @return
     */
    @ApiOperation(value = "根据用户身份获取角色信息")
    @GetMapping(value = "getRoleList")
    public JsonResult getRoleList() {
        try {
            List<SysRole> sysRoles = sysRoleService.selectByUserSchoolId(getSysUer().getSchoolId());
            return JsonResultUtils.buildJsonOK(sysRoles);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResultUtils.buildJsonFail(e.getMessage());
        }
    }

    /**
     * 添加角色
     *
     * @return
     */
    @ApiOperation(value = "添加角色", response = ResourceDto.class)
    @GetMapping(value = "add")
    @OperateLogAnnotation(value = "添加角色")
    public JsonResult add() {
        try {
            ObjectNode obj = JsonUtils.getMapperInstance().createObjectNode();
            List<ResourceDto> resourceDtos = resourceDtoService.selectAll();
            obj.putPOJO("resourceDtos", resourceDtos);
            return JsonResultUtils.buildJsonOK(obj);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return JsonResultUtils.buildJsonFail();
    }

    /**
     * 编辑角色
     *
     * @param roleId
     * @return
     */
    @ApiOperation(value = "编辑角色")
    @GetMapping(value = "edit")
    @OperateLogAnnotation(value = "编辑角色")
    public JsonResult edit(@RequestParam(value = "roleId") Integer roleId) {
        try {
            ObjectNode obj = JsonUtils.getMapperInstance().createObjectNode();
            SysRole systemRole = sysRoleService.getById(roleId);
            obj.putPOJO("systemRole", systemRole);
            /**
             * ----查询该角色所拥有的资源
             */
            List<SysRoleResource> roleResourceIds = sysRoleService.selectRoleResourceIds(roleId);
            obj.putPOJO("roleResourceIds", roleResourceIds);
            List<ResourceDto> resourceDtos = resourceDtoService.selectAll();
            obj.putPOJO("resourceDtos", resourceDtos);
            return JsonResultUtils.buildJsonOK(obj);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return JsonResultUtils.buildJsonFail();
    }

    /**
     * 保存角色
     *
     * @param roleResourceDto
     * @return
     */
    @ApiOperation(value = "保存角色")
    @PostMapping(value = "save")
    @OperateLogAnnotation(value = "保存角色")
    public JsonResult save(@RequestBody RoleResourceDto roleResourceDto) {
        try {
            if (roleResourceDto.getId() == null || roleResourceDto.getId() < 1) {
                roleResourceDto.setSchoolId(getSysUer().getSchoolId());
                roleResourceDto.setCreatetime(CalendarUtils.getTimeStamp());
                roleResourceDto.setCreator(getSysUer().getName());
                roleResourceDto.setCreatorId(getSysUer().getId());
            } else {
                roleResourceDto.setLastUpdateCreatetime(CalendarUtils.getTimeStamp());
                roleResourceDto.setLastUpdateCreator(getSysUer().getName());
            }
            sysRoleService.save(roleResourceDto);
            return JsonResultUtils.buildJsonOK();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return JsonResultUtils.buildJsonFail(e.getMessage());
        }

    }

    /**
     * 更改角色状态
     *
     * @param roleId
     * @param status
     * @return
     */
    @ApiOperation(value = "更改角色状态")
    @GetMapping(value = "changeStatus")
    @OperateLogAnnotation(value = "更改角色状态")
    public JsonResult changeStatus(@RequestParam(value = "roleId") Integer roleId, @RequestParam(value = "status") Integer status) {
        try {
            SysRole systemRole = new SysRole();
            systemRole.setId(roleId);
            systemRole.setStatus(status);
            sysRoleService.updateById(systemRole);
            return JsonResultUtils.buildJsonOK();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return JsonResultUtils.buildJsonFail();
    }

    /**
     * 查找校管理员或平台角色
     *
     * @return
     */
    @ApiOperation(value = "查找校管理员或平台角色:0-平台角色，1-校管理员")
    @GetMapping(value = "findRoleByType")
    public JsonResult findRoleByType(@RequestParam Integer type) {
        try {
            ObjectNode json = JsonUtils.getMapperInstance().createObjectNode();
            List<SysRole> sysRoleList = new ArrayList<>();
            if (type == 0) {
                sysRoleList.addAll(sysRoleService.selectPlatformRole());
                json.putPOJO("sysRoleList", sysRoleList);
                return JsonResultUtils.buildJsonOK(json);
            } else if (type == 1) {
                SysRole sysRole = sysRoleService.findRoleByName(SystemConstants.SCHOOL_DEFAULT_ADMIN_NAME, getSysUer().getSchoolId());
                sysRoleList.add(sysRole);
                json.putPOJO("sysRoleList",sysRoleList);
                return JsonResultUtils.buildJsonOK(json);
            }
            return JsonResultUtils.buildJsonFailMsg("请传入正确的 type ");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

}
