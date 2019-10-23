package com.yykj.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yykj.system.UserUtils;
import com.yykj.system.commons.MD5Password;
import com.yykj.system.commons.MyPageInfo;
import com.yykj.system.commons.PageTools;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import com.yykj.system.entity.SystemRole;
import com.yykj.system.entity.SystemUser;
import com.yykj.system.response.SystemUserExt;

/**
 * 系统用户
 *
 * @author kimi
 * @dateTime 2012-3-13 下午7:59:03
 */
@Controller("systemUserController")
@RequestMapping("/system/user")
public class UserController extends BaseController {

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    protected ModelAndView userList(@RequestParam(value = "name", required = false) String name,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
//        Map<String, Object> model = new HashMap<>();
//        SystemUserExt users = super.getLoginUser(request);
//        boolean isAdmin = systemRoleService.isAdmin(users.getRoles());
//        PageTools page = super.getPage(request);
//        MyPageInfo<SystemUser> pageInfo;
//        if (isAdmin) {
//            pageInfo = systemUserService.getUserListByName(name, page);
//        } else {
//            pageInfo = systemUserService.getUserListByNoAdmin(name,page);
//        }
//        page.setRecordCount((int) pageInfo.getTotal());
//
//        model.put("userlist", pageInfo.getList());
//        model.put("page", page);
//        model.put("name", name);
//        model.put("currentUser", UserUtils.getCurrentUserInfo());
        return new ModelAndView("/system/user/index");
    }
    /**
     * 异步加载用户数据
    * @author chenbiao
    * @date 2018年7月4日 下午6:55:14
    * 参数说明 
    * 
    * @param name
    * @param request
    * @param response
    * @return
    * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "",params="ajaxLoad", method = RequestMethod.GET)
    protected Object ajaxLoad(@RequestParam(value = "name", required = false) String name,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        SystemUserExt users = super.getLoginUser(request);
        boolean isAdmin = systemRoleService.isAdmin(users.getRoles());
        PageTools page = super.getPage(request);
        MyPageInfo<SystemUser> pageInfo;
        if (isAdmin) {
            pageInfo = systemUserService.getUserListByName(name, page);
        } else {
            pageInfo = systemUserService.getUserListByNoAdmin(name,page);
        }
        return buildPageResultByAsync(pageInfo);
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:09
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    protected ModelAndView addUser(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
//		model.put("roles", systemRoleService.getRoleListByCurrentUser());
        model.put("roles", systemRoleService.getAllRole());
        return new ModelAndView("/system/user/info", model);
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:12
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    protected JsonResult addUserPost(SystemUser user, @RequestParam(value = "rid", required = false) Integer[] roleIds, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.clear();
        SystemUserExt users = super.getLoginUser(request);
        user.setCreatetime(System.currentTimeMillis());
        user.setCreator(users.getUsername());
        user.setLoginTimes(0);
        user.setStatus(0);
        user.setPassword(MD5Password.md5(user.getPassword()));

        List<Integer> rids = null;
        if (null != roleIds && roleIds.length > 0) {
            rids = Arrays.asList(roleIds);
        }
        if (this.systemUserService.addUser(user, rids)) {
            model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_OK);
            return JsonResultUtils.buildJsonOK(model);
        }
        model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_NO);
        return JsonResultUtils.buildJsonFail(model);
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:12
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    protected ModelAndView editUserGet(int id, final HttpServletRequest request,
                                       final HttpServletResponse response) throws Exception {
        SystemUserExt user = super.getLoginUser(request);
        boolean isAdmin = systemRoleService.isAdmin(user.getRoles());
        Map<String, Object> model = new HashMap<>();
        SystemUser updateUser = this.systemUserService.getUserById(id);
        model.put("users", updateUser);
        // model.put("roles", systemRoleService.getRoleListByCurrentUser());
        List<SystemRole> roles = new ArrayList<>();
        if (isAdmin) {
            roles = systemRoleService.getAllRole();
        } else {
            // 当前登录人的权限
            roles = systemRoleService.getCurrentRolesByUid(UserUtils.getCurrentUserInfo().getUser().getId());
            // 当前修改用户的权限
            List<SystemRole> updateUserRoles = systemRoleService.getCurrentRolesByUid(updateUser.getId());

            roles.addAll(updateUserRoles);
            // 查询用户创建的角色
            List<SystemRole> creatorList = systemRoleService.getRoleListByCreator(user.getUsername());
            if (creatorList != null && !creatorList.isEmpty()) {
                roles.addAll(creatorList);
            }
        }

        // 角色去重
        Map<Integer, Integer> map = new HashMap<>();
        List<SystemRole> roleList = new ArrayList<>();
        if (roles != null && !roles.isEmpty()) {
            for (SystemRole role : roles) {
                Integer roleId = role.getId();
                if (!map.containsKey(roleId)) {
                    map.put(roleId, 1);
                    roleList.add(role);
                }
            }
        }

        model.put("roles", roleList);
        model.put("currentRoles", systemRoleService.getCurrentRolesByUid(id));
        return new ModelAndView("/system/user/info", model);
    }

    /**
     * 保存用户信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:12
     */
    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    protected JsonResult editUserPost(SystemUser user, @RequestParam(value = "rid", required = false) Integer[] roleIds, @RequestParam(value = "newPass", required = false) String newPass, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        try {
            if (null != newPass && newPass.length() > 0) {
                user.setPassword(newPass);
            }
            List<Integer> rids = null;
            if (null != roleIds && roleIds.length > 0) {
                rids = Arrays.asList(roleIds);
            }
            this.systemUserService.updateUser(user, rids);
            return JsonResultUtils.buildJsonOK();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }

    }

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:12
     */
    @RequestMapping(value = "/edit", params = "validate", method = RequestMethod.POST)
    @ResponseBody
    protected JsonResult changeUserName(String type,
                                        @RequestParam("name") String name,
                                        final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        JsonResult jsonResult = null;
        switch (type) {
            case "name":// 校验登录名是否存在
                if (!this.systemUserService.validateUserNameExists(name))
                    jsonResult = JsonResultUtils.buildJsonOK();
                else
                    jsonResult = JsonResultUtils.buildJsonFail();
                break;
            case "phone":// 校验手机号是否存在
                if (!this.systemUserService.validatePhoneExists(name))
                    jsonResult = JsonResultUtils.buildJsonOK();
                else
                    jsonResult = JsonResultUtils.buildJsonFail();
                break;
            case "email":// 校验邮箱是否存在
                if (!this.systemUserService.validateEmailExists(name))
                    jsonResult = JsonResultUtils.buildJsonOK();
                else
                    jsonResult = JsonResultUtils.buildJsonFail();
                break;

            default:
                break;
        }
        return jsonResult;
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:12
     */
    @RequestMapping(value = "/edit", params = "changestatus", method = RequestMethod.POST)
    @ResponseBody
    protected JsonResult changestatusUser(int id, int status, final HttpServletRequest request,
                                          final HttpServletResponse response) throws Exception {
        SystemUser user = new SystemUser();
        user.setId(id);
        user.setStatus(status);
        if (this.systemUserService.updateUser(user, null) > 0) {
            return JsonResultUtils.buildJsonOK();
        } else {
            return JsonResultUtils.buildJsonFail();
        }
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:12
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    protected JsonResult delUser(int id, final HttpServletRequest request,
                                 final HttpServletResponse response) throws Exception {
        if (this.systemUserService.deleUser(id) > -1) {
            return JsonResultUtils.buildJsonOK();
        } else {
            return JsonResultUtils.buildJsonFail();
        }
    }
}
