package com.yykj.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.yykj.system.commons.MyPageInfo;
import com.yykj.system.commons.PageTools;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.AjaxJsonResults;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import com.yykj.system.entity.SystemResource;
import com.yykj.system.response.SystemUserExt;

/**
 * 资源访问控制类
 *
 * @author kimi
 * @dateTime 2012-3-20 下午8:16:45
 */
@Controller("systemResourceController")
@RequestMapping("/system/resource")
public class ResourceController extends BaseController {

    /**
     * 资源列表
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    protected ModelAndView index(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        PageTools page = super.getPage(request);
        MyPageInfo<SystemResource> pageInfo = systemResourceService.getResourceList(null, page);
        page.setRecordCount((int) pageInfo.getTotal());

        model.put("resourceList", pageInfo.getList());
        model.put("page", page);
        return new ModelAndView("/system/resource/index", model);
    }

    /**
     * @param request
     * @param response
     * @return 改变状态
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/{id}/changestatus", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> changestatusresource(@PathVariable int id, @RequestParam int status,
                                                       final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        SystemResource re = new SystemResource();
        re.setId(id);
        re.setStatus(status);
        if (systemResourceService.updateResource(re)) {
            model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_OK);
        } else {
            model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_NO);
        }
        return model;
    }

    /**
     * 删除资源
     *
     * @param request
     * @param response
     * @return 删除资源访问路径
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    protected Map<String, Object> del(int id, final HttpServletRequest request,
                                      final HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        if (systemResourceService.deleResource(id)) {
            model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_OK);
        } else {
            model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_NO);
        }
        return model;
    }

    /**
     * @param request
     * @param response
     * @return 添加资源访问
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/edit", params = "setLevel", method = RequestMethod.GET)
    @ResponseBody
    protected JsonResult changeLevel(@RequestParam int level, final HttpServletRequest request,
                                     final HttpServletResponse response) throws Exception {
        List<SystemResource> ls = systemResourceService.getParentResourceByLevel(level);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("ls", ls);

        return JsonResultUtils.buildJsonOK(model);
    }

    /**
     * @param request
     * @param response
     * @return 添加资源访问
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    protected ModelAndView add(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("/system/resource/info", model);
    }

    /**
     * @param request
     * @param response
     * @return 添加资源访问
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> add(SystemResource resource, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.clear();
        resource.setResourceKind(6); // 设置当前资源权限只属于当前系统这个子系统
        if (systemResourceService.addResource(resource))
            model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_OK);
        else
            model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_NO);
        return model;
    }

    /**
     * 分类校验资源信息是否存在
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/edit", params = "validate", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> validate(String type, @RequestParam String name,
                                           final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        switch (type) {
            case "name"://校验资源名称是否存在
//			if (null != name && !"".equals(name) && systemResourceService.validateResourceName(name.trim()))
//				model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_OK);
//			 else 
                model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_NO);
                break;
            case "url"://校验资源访问路径是否存在
                if (null != name && !"".equals(name) && systemResourceService.validateResourceUrl(name.trim()))
                    model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_OK);
                else
                    model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_NO);
                break;

            default:
                break;
        }

        return model;
    }

    /**
     * 编辑资源信息
     *
     * @param request
     * @param response
     * @return 编辑资源访问
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    protected ModelAndView edit(int id, final HttpServletRequest request,
                                final HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        if (id > 0) {
            SystemResource re = systemResourceService.getResourceById(id);
            if (null != re) {
//				if (re.getLevel() > 0) {
//					model.put("ls", systemResourceService.getParentResourceByLevel(re.getLevel()));
//				}
                model.put("resource", re);
            }
            return new ModelAndView("/system/resource/info", model);
        }
        return null;
    }

    /**
     * 保存资源信息
     *
     * @param request
     * @param response
     * @return 添加资源访问
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> edit(int id, SystemResource resource, final HttpServletRequest request,
                                       final HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.clear();
        if (systemResourceService.updateResource(resource)) {
            model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_OK);
        } else {
            model.put(SystemConstants.MESSAGE, SystemConstants.STATUS_NO);
        }
        return model;
    }

    /**
     * 查询顶级菜单 - 子系统 ( 现在已经定义parent_id = -1 是为子系统,并且只能去数据库添加)
     * 框架页面使用
     * @return
     */
    @RequestMapping(value = "selectTopNavbar")
    @ResponseBody
    public Object selectTopNavbar(HttpServletRequest request) {
        SystemUserExt user = super.getLoginUser(request);
        List list = new ArrayList();
        try {
            list = systemResourceService.selectTopNavbar(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 查询子系统菜单,框架页面使用
     * @param id
     * @return
     */
    @RequestMapping(value = "selectSubNavbar")
    @ResponseBody
    public Object selectSubNavbar(Integer id,HttpServletRequest request) {
        SystemUserExt user = super.getLoginUser(request);
        List list = new ArrayList();
        try {
            List<JSONObject> list1 =  systemResourceService.selectSubNavbar(id,user);
            list.addAll(list1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 必须加,否则页面上length<=0 加载层不会关闭
        if (list.isEmpty()) {
            JSONObject json = new JSONObject();
            json.put("icon","");
            json.put("title","无权限访问");
            list.add(json);
        }

        return list;
    }

    /**
     * 添加资源,查询树
     * 根据系统id查询系统下的资源
     * 查询树第一层节点,默认查询t_sys_sub_manage ,所以不会存在resourceKind字段 ,并且只是parentId = -1
     * 异步查询数据,查询t_sys_resource
     * @param id
     * @param parentId
     * @return
     */
    @RequestMapping(value = "selectListByResourceKind")
    @ResponseBody
    public Object selectListByResourceKind(Integer id,Integer parentId) {
        List<SystemResource> list = new ArrayList();
        try {
            if (id == null) {
                list.addAll(systemResourceService.getParentResourceByResourceKind(null));
            } else {
                list.addAll(systemResourceService.getSubResourceByParentId(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 该字段和zTree字段重合,导致图标加载异常
        for (SystemResource o : list) {
            o.setIcon(null);
        }
        return list;
    }

    @RequestMapping(value = "buildParentPath")
    @ResponseBody
    public Object buildParentPath() {
        systemResourceService.buildParentPath();
        return AjaxJsonResults.returnAjax(true, "生存parentPath成功!");
    }
}
