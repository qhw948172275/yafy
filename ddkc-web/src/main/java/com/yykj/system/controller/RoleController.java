package com.yykj.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yykj.system.UserUtils;
import com.yykj.system.commons.MyPageInfo;
import com.yykj.system.commons.PageTools;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import com.yykj.system.entity.SystemResource;
import com.yykj.system.entity.SystemRole;
import com.yykj.system.entity.SystemRoleResource;
import com.yykj.system.entity.SystemUser;
import com.yykj.system.response.SystemUserExt;

/**
 * @author kimi
 * @dateTime 2012-3-13 下午7:59:03
 */
@Controller("systemRoleController")
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    /**
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    protected ModelAndView roleList() throws Exception {
        return new ModelAndView("/system/role/index");
    }
    /**
     * 异步加载角色列表
    * @author chenbiao
    * @date 2018年7月4日 下午10:42:43
    * 参数说明 
    * 
    * @param request
    * @param response
    * @return
    * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "",params="ajaxLoad", method = RequestMethod.GET)
    protected Object roleList(final HttpServletRequest request, final HttpServletResponse response,String keyword) throws Exception {
         Map<String, Object> map = getMapByExample(request);
         PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNumber"))), Integer.valueOf(String.valueOf(map.get("pageSize"))));
         List<SystemRole> systemRoleList=systemRoleService.selectAllByKeyword(keyword);

        return buildPageResultByAsync(new PageInfo<SystemRole>(systemRoleList));
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    protected ModelAndView addRoleGet(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("/system/role/info", model);
    }

    /**
     * @param request
     * @param response /** = authc
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> addRolePost(@RequestParam String roleName,
                                              @RequestParam(value = "description", required = false) String description, @RequestParam String resourceArr,
                                              final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        Map<String, Object> model = new HashMap<>();
        SystemUserExt user = super.getLoginUser(request);
        SystemRole role = new SystemRole();
        role.setRoleName(roleName);
        role.setDescription(description);
        role.setCreatetime(System.currentTimeMillis());
        role.setStatus(SystemConstants.STATUS_OK);
        role.setLastUpdateCreator(user.getUsername());
        // role.setLastUpdateCreator("root");
        role.setLastUpdateCreatetime(System.currentTimeMillis());
        role.setCreator(user.getUsername());
        // role.setCreator("root");
        if (systemRoleService.addRole(role,
                (null != resourceArr && !"".equals(resourceArr)) ? resourceArr.split(",") : null)) {
            model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_OK);
        } else {
            model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_NO);
        }
        return model;
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "", params = "resourcelist", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> getresourceList(@RequestParam(value = "roleId", required = false) Integer roleId,
                                                  final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        SystemUserExt user = super.getLoginUser(request);
        List<SystemRole> roles = user.getRoles();
        // 当前用户是否是超级用户
        boolean isCurUserAdmin = false;
        for (SystemRole role : roles) {
            if (role.getRoleName().contains("administrator")) {
                isCurUserAdmin = true;
                break;
            }
        }

        Map<String, Object> model = new HashMap<>();
        List<SystemResource> resourceList = systemResourceService.getResourceList(null, 0, null);

        // 查询当前所拥有的菜单
        List<SystemResource> ownList = systemResourceService.getResourceListByUserId(user.getUser().getId());

        List<SystemRoleResource> roleResources = null;
        Boolean flag = false; // 默认不是超级管理员
        if (null != roleId) {

            // 当前拥有权限
            roleResources = systemRoleService.getRoleResourceList(roleId);
            // 当前角色
            SystemRole role = systemRoleService.selectByPrimaryKey(roleId);

            // 如果是超级管理员
            if ("administrator".equals(role.getRoleName())) {
                flag = true;
            }
        }
        List<Map<String, Object>> convertResourceList = convertResourceList(resourceList, ownList, roleResources, flag,isCurUserAdmin);

        ObjectMapper mapper = new ObjectMapper();
        String jsonlist = mapper.writeValueAsString(convertResourceList);
        model.put("resourceList", jsonlist);
        return model;
    }


    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/edit", params = "validate", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> validate(@RequestParam String name, final HttpServletRequest request,
                                           final HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        if (this.systemRoleService.validateRoleName(name)) {
            model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_OK);
        } else {
            model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_NO);
        }
        return model;
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/{id}/changestatus", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> changestatusRole(@PathVariable int id, @RequestParam int status,
                                                   final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        Map<String, Object> model = new HashMap<>();
        if (this.systemRoleService.changeStatusByRoleId(id, status)) {
            model.put(SystemConstants.MESSAGE, true);
        } else {
            model.put(SystemConstants.MESSAGE, false);
        }
        return model;
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    protected ModelAndView editRoleGet(int id, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        SystemRole re;
        if (null != (re = this.systemRoleService.getRoleById(id))) {
            model.put("role", re);
        }
        return new ModelAndView("/system/role/info", model);
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> editRolePost(int id, @RequestParam int status, @RequestParam String roleName,
                                               @RequestParam String description, @RequestParam String resourceArr, final HttpServletRequest request,
                                               final HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        // SystemUserExt user = super.getLoginUser(request);
        SystemRole re = new SystemRole();
        re.setId(id);
        re.setDescription(description);
        re.setStatus(status);
        re.setRoleName(roleName);
        re.setLastUpdateCreatetime(System.currentTimeMillis());
        // re.setLastUpdateCreator(user.getUsername());
        re.setLastUpdateCreator("root");
        if (this.systemRoleService.updateRole(re,
                (null != resourceArr && !"".equals(resourceArr)) ? resourceArr.split(",") : null)) {
            model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_OK);
        } else {
            model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_NO);
        }
        return model;
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/edit", params = "method=after", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> editRoleAfter(int id, final HttpServletRequest request,
                                                final HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        List<SystemResource> resourceList = systemResourceService.getResourceList(null, 0, null);
        List<SystemRoleResource> resourceList1 = systemRoleService.getRoleResourceList(id);
        model.put("resourceList", super.convertResourceList(resourceList, resourceList1, false));
        return model;
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    protected JsonResult delRole(int id, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        if (this.systemRoleService.deleRoleById(id)) {
        	return JsonResultUtils.buildJsonOK();
        } 
        return JsonResultUtils.buildJsonFail();
    }

    /*** 角色对用户的管理 ***/
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/{id}/manageUser", method = RequestMethod.GET)
    protected ModelAndView manageUserByRid(@PathVariable int id, final HttpServletRequest request,
                                           final HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        List<SystemUser> ls = systemUserService.getUserListInRoleId(id);// 已有用户
        List<SystemUser> ls2 = systemUserService.getUserListNotInRoleId(id);// 未有用户
        model.put("ls1", ls);
        model.put("ls2", ls2);
        model.put("Rid", id);
        return new ModelAndView("/system/manageuser", model);
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/{rid}/manageUser", params = "method=input", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> manageUserByRidByInput(@RequestParam String ulist, @PathVariable int rid,
                                                         final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        Map<String, Object> model = new HashMap<>();
        if (rid > 0 && null != ulist && !"".equals(ulist) && systemRoleService.addUserByRid(ulist.split(","), rid)) {
            List<SystemUser> ls = systemUserService.getUserListInRoleId(rid);// 已有用户
            List<SystemUser> ls2 = systemUserService.getUserListNotInRoleId(rid);// 未有用户
            model.put("ls1", ls);
            model.put("ls2", ls2);
            model.put(SystemConstants.MESSAGE, true);
        } else {
            model.put(SystemConstants.MESSAGE, false);
        }
        return model;
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/{rid}/manageUser", params = "method=output", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> manageUserByRidByOutput(@PathVariable int rid, @RequestParam String ulist,
                                                          final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        Map<String, Object> model = new HashMap<>();
        if (rid > 0 && null != ulist && !"".equals(ulist) && systemRoleService.removeUserByRid(ulist.split(","), rid)) {
            List<SystemUser> ls = systemUserService.getUserListInRoleId(rid);// 已有用户
            List<SystemUser> ls2 = systemUserService.getUserListNotInRoleId(rid);// 未有用户
            model.put("ls1", ls);
            model.put("ls2", ls2);
            model.put(SystemConstants.MESSAGE, true);
        } else {
            model.put(SystemConstants.MESSAGE, false);
        }
        return model;
    }
}
