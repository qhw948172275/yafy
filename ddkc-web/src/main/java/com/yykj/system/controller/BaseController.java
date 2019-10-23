package com.yykj.system.controller;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import com.yykj.commons.ServiceConstants;
import com.yykj.system.commons.*;
import com.yykj.system.entity.SystemRole;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import com.yykj.system.UserUtils;
import com.yykj.system.entity.SystemResource;
import com.yykj.system.entity.SystemRoleResource;
import com.yykj.system.response.SystemUserExt;
import com.yykj.system.service.SystemResourceService;
import com.yykj.system.service.SystemRoleService;
import com.yykj.system.service.SystemSubManageService;
import com.yykj.system.service.SystemUserLoginLogService;
import com.yykj.system.service.SystemUserService;

/**
 * 所有控制类的基类
 *
 * @author kimi
 * @dateTime 2012-6-20 上午11:06:03
 */
@Controller("systemBaseController")
public class BaseController {

    protected static Logger log = LoggerFactory.getLogger(BaseController.class);
    /**
     * 用户管理接口
     *
     * @author kimi
     * @dateTime 2012-3-9 下午5:58:48
     */
    @Autowired
    protected SystemUserService systemUserService;

    /**
     * 角色管理接口
     *
     * @author kimi
     * @dateTime 2012-3-19 下午12:12:27
     */
    @Autowired
    protected SystemRoleService systemRoleService;

    /**
     * @author kimi
     * @dateTime 2012-4-18 下午5:58:45
     */
    @Autowired
    protected SystemResourceService systemResourceService;

    @Autowired
    protected SystemUserLoginLogService systemUserLoginLogService;

    @Autowired
    protected SystemSubManageService systemSubManageService;

    // @Autowired
    // protected SystemConfig systemConfig;
    //
    // @Autowired
    // protected AmqpProducer amqpProducer;

    /**
     * 获取当前登陆用户信息
     *
     * @param request
     * @return
     * @author kimi
     * @dateTime 2012-7-9 下午3:00:43
     */
    protected SystemUserExt getLoginUser(HttpServletRequest request) {
        return UserUtils.getCurrentUserInfo();
    }
    /**
     * 获取当前登录用户ID
    * @author chenbiao
    * @date 2018年7月9日 上午2:23:35
    * 参数说明 
    * 
    * @param request
    * @return
     */
    protected int getLoginUserId(HttpServletRequest request) {
        return UserUtils.getCurrentUserInfo().getUser().getId();
    }

    /**
     * 转换zTree
     *
     * @param ls1            系统资源列表
     * @param ownResource    当前用户拥有的资源
     * @param ls2            角色拥有的资源
     * @param flag           角色是否是超级管理员
     * @param isCurUserAdmin 当前登录用户是否是超级用户
     * @return
     */
    protected List<Map<String, Object>> convertResourceList(List<SystemResource> ls1, List<SystemResource> ownResource,
                                                            List<SystemRoleResource> ls2, Boolean flag, Boolean isCurUserAdmin) {
        List<Map<String, Object>> zTreeList = new ArrayList<Map<String, Object>>(ls1.size());
        if (ls1 == null || ls1.isEmpty()) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("id", null);
            m.put("pId", -2);
            m.put("name", "当前没有菜单");
            m.put("open", false);
            zTreeList.add(m);
            return zTreeList;
        }

        if (ownResource == null || ownResource.isEmpty()) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("id", null);
            m.put("pId", -2);
            m.put("name", "您没有操作权限");
            m.put("open", false);
            zTreeList.add(m);
            return zTreeList;
        }
        // 获取资源id 作为map的key
        Map<Integer, SystemResource> ownResourceMap = new HashMap<>();
        ownResource.forEach(n -> {
            ownResourceMap.put(n.getId(), n);
        });


        // 当前角色所拥有的权限 资源id座位key ,角色id座位value
        Map<Integer, Integer> roleResourceMap = new HashMap<>();
        if (ls2 != null && !ls2.isEmpty()) {
            ls2.forEach(n -> {
                roleResourceMap.put(n.getResourceId(), n.getRoleId());
            });
        }

        // 构建zTree数据
        ls1.forEach(n -> {
            Map<String, Object> map = new HashMap<>();
            zTreeList.add(map);
            Integer resourceId = n.getId();
            map.put("id", resourceId);
            map.put("pId", n.getParentId());
            map.put("name", n.getResourceName());
            // 当前编辑角色是否是超级用户
            if (flag) {
                map.put("checked", true);
            } else {
                // 当前编辑角色拥有的权限, 勾选
                if (roleResourceMap.containsKey(resourceId)) {
                    map.put("checked", true);
                }

                // 当前用户是超级管理员 ,可以编辑所有
                // 当前用户拥有该权限菜单, 没有则全部禁用勾选
                if (!isCurUserAdmin && !ownResourceMap.containsKey(resourceId)) {
                    map.put("chkDisabled", true);
                }
            }

        });


        return zTreeList;
    }

    /**
     * 转换zTree
     *
     * @param ls1  系统资源列表
     * @param ls2  角色拥有的资源
     * @param flag 是否是超级管理员
     * @return
     */
    protected synchronized List<Map<String, Object>> convertResourceList(List<SystemResource> ls1,
                                                                         List<SystemRoleResource> ls2, Boolean flag) {
        if (null != ls1 && ls1.size() > 0) {
            List<Map<String, Object>> ls3 = new ArrayList<Map<String, Object>>(ls1.size());
            // Map<String, Object> m1 = new HashMap<String, Object>();
            // m1.put("id", 0);
            // m1.put("pId", -1);
            // m1.put("name", "所有权限");
            // m1.put("open", true);
            // ls3.add(m1);
            for (SystemResource r : ls1) {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("id", r.getId());
                m.put("pId", r.getParentId());
                m.put("name", r.getResourceName());
                m.put("open", false);
                // m.put("checked", r.getIsBasic() == 0 ? true : false);
                ls3.add(m);
            }
            if (null != ls2 && ls2.size() > 0) {
                if (flag) {
                    for (Map<String, Object> re : ls3) {
                        for (int i = 0; null != ls2 && i < ls2.size(); i++) {
                            re.put("checked", true);
                        }
                    }
                } else {
                    for (Map<String, Object> re : ls3) {
                        for (int i = 0; null != ls2 && i < ls2.size(); i++) {
                            if (re.get("id").equals(ls2.get(i).getResourceId())) {
                                re.put("checked", true);
                            }
                        }
                    }
                }
            }
            return ls3;
        }
        return null;
    }

    /**
     * 设置分页对象
     *
     * @param request
     * @return
     * @author kimi
     * @dateTime 2012-6-20 下午3:54:03
     */
    public synchronized PageTools getPage(HttpServletRequest request) {
        String pageSize = request.getParameter("limit");
        String currentPage = request.getParameter("page");
        PageTools page = new PageTools();

        if (!StringUtils.isEmpty(pageSize) && ValidateUtils.isNumeric(pageSize)) {
            page.setPageSize(Integer.parseInt(pageSize));
        } else {
            page.setPageSize(10);
        }
        if (!StringUtils.isEmpty(currentPage) && ValidateUtils.isNumeric(currentPage)) {
            page.setCurrentPage(Integer.parseInt(currentPage) - 1);
        }
        return page;
    }
    /**
     * 
    * 构造异步分页数据
    * @author chenbiao
    * @date 2018年7月4日 下午7:32:41
    * 参数说明 
    * 
    * @param page
    * @return
     */
    public ObjectNode buildPageResultByAsync(MyPageInfo<?> page){
    	ObjectNode result = JsonUtils.getMapperInstance().createObjectNode();
    	result.put("code", 0);
    	result.put("msg", "");
    	result.put("count", page.getTotal());
    	result.putPOJO("data", page.getList());
    	return result;
    }
    
    public ObjectNode buildPageResultByAsync(PageInfo<?> page){
    	ObjectNode result = JsonUtils.getMapperInstance().createObjectNode();
    	result.put("code", 0);
    	result.put("msg", "");
    	result.put("count", page.getTotal());
    	result.putPOJO("data", page.getList());
    	return result;
    }

    /**
     * 通用分页组件抽离
     * @author chenbiao
     * @date 2018年7月18日 下午5:20:26
     * 参数说明
     *
     * @param request
     * @return
     */
    protected Map<String,Object> getMapByExample(HttpServletRequest request) {
        Map<String, Object> map = new ConcurrentHashMap<>();
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        map.put("pageNumber", (null != page) ? Integer.parseInt(page): 1);
        map.put("pageSize", (null != limit) ? Integer.parseInt(limit):10);
        return map;
    }

    /**
     * 验证是否是超级管理员
     * @param request
     * @return
     */
    public Boolean checkingSuperAdministrator(HttpServletRequest request){
        List<SystemRole> systemRoles=systemRoleService.getCurrentRolesByUid(getLoginUserId(request));
        if(systemRoles.size()>0){
            Set<String> roleNames=new HashSet<>();
            for(SystemRole systemRole:systemRoles){
                roleNames.add(systemRole.getRoleName());
            }
            if(roleNames.contains(ServiceConstants.SUPER_ADMIN_CODE)){
                return true;
            }
            return false;
        }
        return null;
    }
}
