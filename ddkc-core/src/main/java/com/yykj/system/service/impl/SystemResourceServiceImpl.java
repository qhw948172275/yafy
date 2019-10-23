package com.yykj.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yykj.system.commons.MyPageInfo;
import com.yykj.system.commons.PageTools;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.dao.SystemLoginLogMapper;
import com.yykj.system.dao.SystemResourceMapper;
import com.yykj.system.dao.SystemRoleMapper;
import com.yykj.system.dao.SystemRoleResourceMapper;
import com.yykj.system.dao.SystemRoleUserMapper;
import com.yykj.system.dao.SystemUserMapper;
import com.yykj.system.entity.SystemResource;
import com.yykj.system.entity.SystemRole;
import com.yykj.system.entity.SystemRoleResource;
import com.yykj.system.entity.SystemSubManage;
import com.yykj.system.response.MenuTreeResponse;
import com.yykj.system.response.SystemUserExt;
import com.yykj.system.service.SystemResourceService;
import com.yykj.system.service.SystemSubManageService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 资源访问管理接口实现
 *
 * @author kimi
 * @dateTime 2012-3-20 上午10:44:09
 */
@Service
public class SystemResourceServiceImpl implements SystemResourceService {

    @Autowired
    protected SystemUserMapper userMapper;// 用户映射接口

    @Autowired
    protected SystemResourceMapper resourceMapper;// 资源映射接口

    @Autowired
    protected SystemRoleUserMapper roleUserMapper;// 用户角色关联关系映射接口
    /**
     * 角色映射接口
     *
     * @author kimi
     * @dateTime 2012-3-19 下午12:02:38
     */
    @Autowired
    protected SystemRoleMapper roleMapper;

    /**
     * 角色与资源的关联关系映射接口
     *
     * @author kimi
     * @dateTime 2012-3-20 下午3:45:52
     */
    @Autowired
    protected SystemRoleResourceMapper roleresourceMapper;

    @Autowired
    protected SystemLoginLogMapper userLoginLogMapper;

    @Autowired
    private SystemSubManageService systemSubManageService;

    /**
     * 得到资源访问权限
     *
     * @author kimi
     * @dateTime 2012-3-21 下午2:59:31
     * (non-JSDoc)
     */
    public MyPageInfo<SystemResource> getResourceList(String resourceName, PageTools pageTools) {
        Page<?> page = PageHelper.startPage(pageTools.getCurrentPage() + 1, pageTools.getPageSize());

        Example rre = new Example(SystemResource.class);
        Criteria criter = rre.createCriteria();
        if (null != resourceName && !"".equals(resourceName)) {
            criter.andLike("resourceName", SystemConstants.PERCENT + resourceName + SystemConstants.PERCENT);
        }
        rre.setOrderByClause("id desc");
        List<SystemResource> list = resourceMapper.selectByExample(rre);
        pageTools.setRecordCount(page.getTotal());

        return new MyPageInfo<SystemResource>(list, pageTools);
    }

    /**
     * 根据
     *
     * @author kimi
     * @dateTime 2013-8-5 上午11:59:11
     * (non-JSDoc)
     */
    @Override
    public List<SystemResource> getResourceList(String resourceName, int status, PageTools page) {
        Example rre = new Example(SystemResource.class);
        Criteria criter = rre.createCriteria();
        if (null != resourceName && !"".equals(resourceName)) {
            criter.andLike("resourceName", SystemConstants.PERCENT + resourceName + SystemConstants.PERCENT);
        }
        if (status == 0 || status == 1) {
            criter.andEqualTo("status", status);
        }
        if (null != page) {
            rre.setOrderByClause("id desc limit " + page.getLimitFrom() + "," + page.getLimitTo());
        }
        return resourceMapper.selectByExample(rre);
    }

    public long getResourceCounts(String resourceName) {
        Example rre = new Example(SystemResource.class);
        if (null != resourceName && !"".equals(resourceName)) {
            rre.createCriteria().andLike("resourceName", SystemConstants.PERCENT + resourceName + SystemConstants.PERCENT);
        }
        return resourceMapper.selectCountByExample(rre);
    }

    /**
     * 添加资源访问管理
     *
     * @author kimi
     * @dateTime 2012-3-21 下午2:59:22
     * (non-JSDoc)
     */
    public boolean addResource(SystemResource re) {
        if (null == re.getParentId()) {
            throw new NullPointerException("parentId");
        }
        formartUrl(re);
        SystemResource parent = getResourceById(re.getParentId());
        re.setParentPath(parent.getParentPath() + parent.getId() + "/");

        return this.resourceMapper.insertSelective(re) > 0 ? true : false;
    }

    /**
     * 删除资源访问管理
     *
     * @author kimi
     * @dateTime 2012-3-21 下午2:59:13
     * (non-JSDoc)
     */
    public boolean deleResource(int resourceId) {
        return this.resourceMapper.deleteByPrimaryKey(resourceId) > 0 ? true : false;
    }

    /**
     * 修改资源访问管理
     *
     * @author kimi
     * @dateTime 2012-3-21 下午2:58:59
     * (non-JSDoc)
     */
    public boolean updateResource(SystemResource re) {
        return this.resourceMapper.updateByPrimaryKeySelective(re) > 0 ? true : false;
    }

    /**
     * 根据资源ID，得到资源对象
     *
     * @author kimi
     * @dateTime 2012-3-21 下午2:58:09
     * (non-JSDoc)
     */
    public SystemResource getResourceById(int rid) {
        return this.resourceMapper.selectByPrimaryKey(rid);
    }

    /**
     * 根据功能级别，得到比它高一级别的功能节点
     *
     * @author kimi
     * @dateTime 2012-3-21 下午2:56:57
     * (non-JSDoc)
     */
    public List<SystemResource> getParentResourceByLevel(int level) {
        if (level > 0) {
            Example reb = new Example(SystemResource.class);
            reb.createCriteria().andEqualTo("level", level - 1);
            return this.resourceMapper.selectByExample(reb);
        }
        return null;
    }

    /**
     * 验证功能名称是否存在
     *
     * @author kimi
     * @dateTime 2012-3-21 下午2:56:44
     * (non-JSDoc)
     */
    public boolean validateResourceName(String resourceName) {
        if (null != resourceName && !"".equals(resourceName)) {
            Example rre = new Example(SystemResource.class);
            rre.createCriteria().andEqualTo("resourceName", resourceName);
            List<SystemResource> ls = this.resourceMapper.selectByExample(rre);
            if (null != ls && ls.size() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证资源访问路径是否存在
     *
     * @author kimi
     * @dateTime 2012-3-21 下午2:56:29
     * (non-JSDoc)
     */
    public boolean validateResourceUrl(String resourceUrl) {
        if (null != resourceUrl && !"".equals(resourceUrl)) {
            Example rre = new Example(SystemResource.class);
            rre.createCriteria().andEqualTo("resourceUrl", resourceUrl);
            if (this.resourceMapper.selectCountByExample(rre) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 访问当前登录用户所能访问的资源
     * <p>
     * 说明：这里涉及到了用户(User),角色(Role)和资源(resource)三者之间的关系。<br/>
     * 用户与角色的关系为N对N的关系，而角色与资源同样也属于N对N的关系。用户和访问资源无直接关系。
     *
     * @param uid 用户ID
     * @return
     * @author kimi
     * @dateTime 2012-6-12 下午2:56:53
     */
    public List<SystemResource> getResourceListByUserId(int uid) {
        if (uid > 0) {
            List<SystemResource> resourceList = new ArrayList<SystemResource>();
            // 如果不是超级管理员
            if (uid != 1) {
                List<SystemRole> roles = roleMapper.getRoleByUserId(uid);
                List<Integer> rIdList = null;
                if (null != roles && roles.size() > 0) {
                    rIdList = new ArrayList<Integer>();
                    for (SystemRole role : roles) {
                        rIdList.add(role.getId());
                    }
                }
                // 根据角色的ID，查询出这些角色能访问资源权限的并集
                if (null != rIdList && rIdList.size() > 0) {
                    resourceList = resourceMapper.getResourceListByRidIn(rIdList);
                }
            } else {
                // 如果是超级管理员
                Example rebe = new Example(SystemResource.class);
                rebe.createCriteria().andEqualTo("status", 0);
                rebe.setOrderByClause(" id asc");
                resourceList = resourceMapper.selectByExample(rebe);
            }
            resourceList = integrationResource(resourceList);// 加载一些验证的登录权限
            return resourceList;
        }
        return null;
    }

    /**
     * 整合请求资源
     *
     * @param res
     * @return
     * @author kimi
     * @dateTime 2012-7-9 下午7:58:47
     */
    public static List<SystemResource> integrationResource(List<SystemResource> res) {
//        String str;
//        final String replace_before = "\\$\\{id\\}";
//        final String replace_later = "*";
//        for (SystemResource re : res) {
//            str = re.getResourceUrl().replaceAll(replace_before, replace_later);
//            re.setResourceUrl(str);
//        }
        return res;
    }

    /**
     * chao.liu
     * 根据用户获取resource
     */
    public Map<String, List<MenuTreeResponse>> getResponseListByUser(SystemUserExt systemUserExt) {
        Map<String, List<MenuTreeResponse>> resourceMap = new HashMap<String, List<MenuTreeResponse>>();
        // shiro中缓存的用户角色
        List<String> roles = new ArrayList<String>();
        for (SystemRole role : systemUserExt.getRoles()) {
            roles.add(role.getRoleName());
        }
        if (roles.size() == 0) {
            return resourceMap;
        }

        List<SystemResource> resources = new ArrayList<SystemResource>();
        //超级管理员
        if (roles.contains("administrator")) {
            Example example = new Example(SystemResource.class);
            example.setOrderByClause("seq");
            example.createCriteria().andEqualTo("status", 0).andNotEqualTo("parentId", -1).andEqualTo("resourceType", 0);
            resources = this.resourceMapper.selectByExample(example);
        } else { //普通用户
            List<Integer> roleIdList = new ArrayList<Integer>();
            for (SystemRole role : systemUserExt.getRoles()) {
                roleIdList.add(role.getId());
            }
            if (roleIdList.size() == 0) {
                return resourceMap;
            }
            resources = this.roleMapper.selectResourceListByRoleIdList(roleIdList);
            if (resources == null) {
                return resourceMap;
            }
        }
        List<MenuTreeResponse> menuTreeList = new ArrayList<MenuTreeResponse>();
        for (SystemResource resource : resources) {
            menuTreeList.add(new MenuTreeResponse(resource.getId(), resource.getResourceName(), resource.getResourceUrl(),
                    resource.getIsBasic(), resource.getParentId(), resource.getLevel(), resource.getRemark(), resource.getStatus(),
                    resource.getOpened(), resource.getResourceType(), resource.getResourceKind(), resource.getSeq(), resource.getIcon(), resource.getOpenMode()));
        }

        //商城资源list
        List<MenuTreeResponse> mallList = new ArrayList<MenuTreeResponse>();
        //平台资源list
        List<MenuTreeResponse> platformList = new ArrayList<MenuTreeResponse>();
        //直播资源list
        List<MenuTreeResponse> liveList = new ArrayList<MenuTreeResponse>();
        //ERP资源List
        List<MenuTreeResponse> erpList = new ArrayList<MenuTreeResponse>();
        //系统资源List
        List<MenuTreeResponse> systemList = new ArrayList<MenuTreeResponse>();
        for (MenuTreeResponse tree : menuTreeList) {
            if (tree.getResourceKind() == 1) {
                mallList.add(tree);
            }
            if (tree.getResourceKind() == 2) {
                platformList.add(tree);
            }
            if (tree.getResourceKind() == 3) {
                liveList.add(tree);
            }
            if (tree.getResourceKind() == 5) {
                erpList.add(tree);
            }
            if (tree.getResourceKind() == 6) {
                systemList.add(tree);
            }

        }

        resourceMap.put("shop", recursionChildList(mallList));
        resourceMap.put("platform", recursionChildList(platformList));
        resourceMap.put("live", recursionChildList(liveList));
        resourceMap.put("erp", recursionChildList(erpList));
        resourceMap.put("system", recursionChildList(systemList));

        return resourceMap;
    }

    @Override
    public List<SystemResource> getParentResourceByResourceKind(Integer resourceKind) {
        Example example = new Example(SystemResource.class);
        if (resourceKind != null) {
            example.createCriteria().andEqualTo("resourceKind", resourceKind);
        }
        return resourceMapper.selectByExample(example);
    }

    @Override
    public List<SystemResource> getParentResourceByResourceKind(Integer id, Integer resourceKind) {
        Example example = new Example(SystemResource.class);
        Criteria criteria = example.createCriteria();
        if (resourceKind != null) {
            criteria.andEqualTo("resourceKind", resourceKind);
        }
        if (id != null) {
            criteria.andNotEqualTo("id", id);
        }
        return resourceMapper.selectByExample(example);
    }

    @Override
    public List<SystemResource> getSubResourceByParentId(Integer parentId) {
        SystemResource parent = resourceMapper.selectByPrimaryKey(parentId);
        String parentPath = parent.getParentPath();

        Example example = new Example(SystemResource.class);
        example.createCriteria().andLike("parentPath", parentPath + parentId + "/%");
        return resourceMapper.selectByExample(example);
    }

    @Override
    public List<SystemResource> getSubResourceNotBtnByParentId(Integer parentId) {
        SystemResource parent = resourceMapper.selectByPrimaryKey(parentId);
        String parentPath = parent.getParentPath();

        Example example = new Example(SystemResource.class);
        example.createCriteria().andLike("parentPath", parentPath + parentId + "/%").andEqualTo("resourceType", 0);
        example.setOrderByClause(" level asc, seq asc");
        return resourceMapper.selectByExample(example);
    }

    @Override
    public List<SystemResource> getCurSubResourceByParentId(Integer parentId) {
        Example example = new Example(SystemResource.class);
        example.createCriteria().andEqualTo("parentId", parentId);
        return resourceMapper.selectByExample(example);
    }

    /**
     * 生成菜单树 , id必须从大到小排序
     *
     * @param allList
     * @return
     * @throws JSONException 
     */
    private Map<Integer, JSONObject> buildMenuTree(List<SystemResource> allList) throws JSONException {
        Map<Integer, JSONObject> tree = new HashMap<>();
        // 构建树
        for (SystemResource systemResource : allList) {
            JSONObject json = new JSONObject();
            Integer id = systemResource.getId();
            Integer pid = systemResource.getParentId();
            Integer level = systemResource.getLevel();

            json.put("id", id);
            json.put("pid", pid);
            json.put("level", level);
            json.put("name", systemResource.getResourceName());
            json.put("obj", systemResource);
            json.put("child", new ArrayList<JSONObject>());
            if (tree.containsKey(pid)) {
                List<JSONObject> child = (List<JSONObject>) tree.get(pid).get("child");
                child.add(json);
            }
            tree.put(id, json);
        }
        return tree;

    }

    /**
     * 生成页面使用的左侧菜单树 , id必须从大到小排序
     *
     * @param allList 数据
     * @return
     * @throws JSONException 
     */
    private Map<Integer, JSONObject> buildMenuPageSubTree(List<SystemResource> allList, JSONObject params) throws JSONException {
        //layui 图标支持layui图标和fontawesome字体图标
        Map<Integer, JSONObject> tree = new TreeMap<>();
        // 构建树
        for (SystemResource systemResource : allList) {
            JSONObject json = new JSONObject();
            Integer id = systemResource.getId();
            Integer pid = systemResource.getParentId();
            Integer level = systemResource.getLevel();

            json.put("id", id);
            json.put("pid", pid);
            json.put("level", level);
            json.put("title", systemResource.getResourceName());
            String basePath = params.getString("basePath");
            String url = systemResource.getResourceUrl();

            json.put("href", basePath + url);
            String icon = systemResource.getIcon();
            json.put("icon", icon == null ? "" : icon);
            json.put("spread", false); // 如果为true则展开 (layui v1.0.3可用)
            json.put("isFirstNode", true);
            json.put("children", new ArrayList<JSONObject>());
            if (tree.containsKey(pid)) {
                List<JSONObject> child = (List<JSONObject>) tree.get(pid).get("children");
                json.put("isFirstNode", false);
                child.add(json);
            }
            tree.put(id, json);
        }

        return tree;

    }

    @Override
    public void buildParentPath() throws JSONException {
        List<SystemResource> allList = new ArrayList<>();
        List<SystemResource> resources = resourceMapper.selectAll();
        // List<Map<String, Object>> submanage = systemSubManageService.selectAllTree();
        // for (Map o : submanage) {
        //     SystemResource systemResource = new SystemResource();
        //     systemResource.setId(Integer.valueOf(String.valueOf(o.get("id"))));
        //     systemResource.setParentId(-1);
        //     systemResource.setResourceUrl("#");
        //     systemResource.setIsBasic(1);
        //     systemResource.setStatus(0);
        //     systemResource.setResourceType(0);
        //     systemResource.setResourceKind(Integer.valueOf(String.valueOf(o.get("id"))));
        //     systemResource.setResourceName(String.valueOf(o.get("resourceName")));
        //     allList.add(systemResource);
        // }
        allList.addAll(resources);

        Map<Integer, JSONObject> tree = buildMenuTree(allList);

        List<JSONObject> result = new ArrayList<>();
        for (Integer integer : tree.keySet()) {
            JSONObject json = tree.get(integer);
            if (json.getInteger("pid") == -1) {
                result.add(json);
            }
        }

//        System.out.println(JSONObject.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect));

        List<SystemResource> update = new ArrayList<>();

        Integer level = 0;
        String parentPath = "/";

        // 循环遍历子节点
        fomateDept(update, null, result, level, parentPath);

        for (SystemResource systemResource : update) {
            formartUrl(systemResource);
            resourceMapper.updateByPrimaryKey(systemResource);
        }
    }

    /**
     * 格式化不规范的url
     *
     * @param systemResource
     */
    private void formartUrl(SystemResource systemResource) {
        String url = systemResource.getResourceUrl();
        if ("#".equals(url)) {
            return;
        } else if ("/".equals(url.substring(0, 1))) {
            return;
        } else {
            systemResource.setResourceUrl("/" + url);
        }
    }

    @Override
    public List<JSONObject> selectSubNavbar(Integer parentId) throws JSONException {
        // 图标支持layui图标和fontawesome字体图标
        List<JSONObject> result = new ArrayList<>();
        List<SystemResource> list = getSubResourceByParentId(parentId);
        JSONObject params = new JSONObject();
        if (list == null || list.size() <= 0) {
            return result;
        }
        // 查询系统 ,并获取系统访问路径
        SystemSubManage systemSubManage = selectSystemSubManage(list);
        params.put("basePath", systemSubManage.getUrl());
        Map<Integer, JSONObject> formatTree = buildMenuPageSubTree(list, params);

        for (JSONObject json : formatTree.values()) {
            boolean is = json.getBoolean("isFirstNode");
            if (is) {
                result.add(json);
            }
        }
        return result;
    }

    @Override
    public List<JSONObject> selectTopNavbar(SystemUserExt user) throws JSONException {
        List result = new ArrayList();
        if (user == null || user.getRoles().isEmpty()) {
            return result;
        }

        List<SystemResource> list = new ArrayList<>();
        // boolean isAdministrator = SystemUserExt.isAdministrator(user);
        // if (isAdministrator) {
        //     list = getParentResourceByLevel(1);
        // } else {
        //     list = selectTopResourceByParentIdPermission(-1, user);
        // }

        list = getParentResourceByLevel(1);
        for (SystemResource systemResource : list) {
            JSONObject json = new JSONObject();
            json.put("id", systemResource.getId());
            json.put("title", systemResource.getResourceName());
            json.put("icon", systemResource.getIcon());
            result.add(json);
        }
        return result;
    }

    /**
     * 根据parentId查询(只会查询的等于parentId的数据),带权限
     *
     * @return
     */
    private List<SystemResource> selectTopResourceByParentIdPermission(Integer parentId, SystemUserExt user) {
        List<SystemResource> result = new ArrayList<>();

        List<SystemResource> subResources = getCurSubResourceByParentId(parentId);
        if (subResources.isEmpty()) {
            return null;
        }

        List<Integer> subResourceIds = new ArrayList<>();
        for (SystemResource subResource : subResources) {
            subResourceIds.add(subResource.getId());
        }

        // 查询权限
        Example example = new Example(SystemRoleResource.class);
        example.createCriteria().andIn("roleId", SystemUserExt.getRoleIds(user)).andIn("resourceId", subResourceIds);
        List<SystemRoleResource> roleResourceList = roleresourceMapper.selectByExample(example);

        if (roleResourceList == null || roleResourceList.size() <= 0) {
            return result;
        }

        // 获取资源id
        List<Integer> resourceIds = new ArrayList<>();
        for (SystemRoleResource roleResource : roleResourceList) {
            resourceIds.add(roleResource.getResourceId());
        }

        List<SystemResource> resources;

        // 查询资源
        resources = selectByIds(resourceIds, null);
        return resources;
    }

    /**
     * 根据用户查询权限访问的菜单
     *
     * @param parentId 顶级菜单(子系统)id
     * @param user
     * @return
     */
    private List<SystemResource> selectSubNavbarPermission(Integer parentId, SystemUserExt user) {
        List<SystemResource> result = new ArrayList<>();

        SystemResource parentResource = resourceMapper.selectByPrimaryKey(parentId);
        if (parentResource == null) {
            return null;
        }

        // 查询权限
        Example example = new Example(SystemRoleResource.class);
        example.createCriteria().andIn("roleId", SystemUserExt.getRoleIds(user)).andNotEqualTo("resourceId", parentId);
        List<SystemRoleResource> roleResourceList = roleresourceMapper.selectByExample(example);

        if (roleResourceList == null || roleResourceList.size() <= 0) {
            return result;
        }

        // 获取资源id
        List<Integer> resourceIds = new ArrayList<>();
        for (SystemRoleResource roleResource : roleResourceList) {
            resourceIds.add(roleResource.getResourceId());
        }

        List<SystemResource> resources;

        // 查询资源
        resources = selectNotBtnByIds(resourceIds, parentResource.getResourceKind());
        return resources;
    }

    @Override
    public List<JSONObject> selectSubNavbar(Integer parentId, SystemUserExt user) throws JSONException {
        List<JSONObject> result = new ArrayList<>();

        if (user == null || user.getRoles().isEmpty()) {
            return result;
        }

        boolean isAdministrator = SystemUserExt.isAdministrator(user);

        List<SystemResource> resources = new ArrayList<>();

        // 判断是否是超级管理员
        if (!isAdministrator) {
            // 查询资源
            resources = selectSubNavbarPermission(parentId, user);
        } else {
            // 查询所有子集,不包含按钮
            resources = getSubResourceNotBtnByParentId(parentId);
        }

        if (resources == null || resources.size() <= 0) {
            return result;
        }

        // 查询系统 ,并获取系统访问路径
        JSONObject params = new JSONObject();
        SystemSubManage systemSubManage = selectSystemSubManage(resources);
        params.put("basePath", systemSubManage.getUrl());

        // 构建资源树
        Map<Integer, JSONObject> formatTree = buildMenuPageSubTree(resources, params);

        for (JSONObject json : formatTree.values()) {
            boolean is = json.getBoolean("isFirstNode");
            if (is) {
                result.add(json);
            }
        }
        return result;
    }

    /**
     * 查询系统的访问路径
     *
     * @param list 查询出来的资源列表
     * @return
     */
    private SystemSubManage selectSystemSubManage(List<SystemResource> list) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        // 查询系统 ,并获取系统访问路径
        Integer resourceKind = list.get(0).getResourceKind();
        SystemSubManage systemSubManage = systemSubManageService.getById(resourceKind);
        return systemSubManage;
    }

    /**
     * 通过资源id集合查询资源
     *
     * @param ids          资源id集合
     * @param resourceKind 系统id
     * @return
     */
    public List<SystemResource> selectByIds(List<Integer> ids, Integer resourceKind) {
        Example example = new Example(SystemResource.class);
        Criteria criteria = example.createCriteria().andIn("id", ids);
        if (resourceKind != null) {
            criteria.andEqualTo("resourceKind", resourceKind);
        }
        return resourceMapper.selectByExample(example);
    }

    /**
     * 通过资源id集合查询资源,不包含按钮
     *
     * @param ids          资源id集合
     * @param resourceKind 系统id
     * @return
     */
    public List<SystemResource> selectNotBtnByIds(List<Integer> ids, Integer resourceKind) {
        Example example = new Example(SystemResource.class);
        Criteria criteria = example.createCriteria().andIn("id", ids).andEqualTo("resourceType", 0);
        if (resourceKind != null) {
            criteria.andEqualTo("resourceKind", resourceKind);
        }
        example.setOrderByClause(" level asc, seq asc");
        return resourceMapper.selectByExample(example);
    }

    private static void fomateDept(List update, JSONObject parentNode, List<JSONObject> child, Integer level, String parentPath) throws JSONException {
        for (JSONObject sys : child) {
            SystemResource systemResource = (SystemResource) sys.get("obj");
            systemResource.setLevel(level);
            systemResource.setParentPath(parentPath);

            // if (sys.getInteger("pid") == -1) {
            //     insert.add(systemResource);
            // } else {
            //     update.add(systemResource);
            // }
            if (parentNode != null) {
                systemResource.setResourceKind(((SystemResource) parentNode.get("obj")).getResourceKind());
            }
            update.add(systemResource);

            List<JSONObject> sub = (List<JSONObject>) sys.get("child");
            if (sub != null && sub.size() > 0) {
                fomateDept(update, sys, sub, level + 1, parentPath + sys.get("id") + "/");
            }
        }
    }

    private List<MenuTreeResponse> recursionChildList(List<MenuTreeResponse> list) {
        List<MenuTreeResponse> rList = new ArrayList<>();
        Map<Integer, List<MenuTreeResponse>> map = new HashMap<>();
        for (MenuTreeResponse menuTree : list) {
            if (menuTree.getParentId() == -1) {
                rList.add(menuTree);
            } else {
                Integer key = menuTree.getParentId();
                if (map.get(key) == null) {
                    map.put(key, new ArrayList<MenuTreeResponse>());
                }
                map.get(key).add(menuTree);
            }
        }
        for (MenuTreeResponse MenuTreeResponse : rList) {
            recursionAllTree(MenuTreeResponse, map);
        }
        return rList;
    }

    private void recursionAllTree(MenuTreeResponse topResonse, Map<Integer, List<MenuTreeResponse>> map) {
        topResonse.setChildList(map.get(topResonse.getId()));
        List<MenuTreeResponse> childList = topResonse.getChildList();
        if (childList != null && !childList.isEmpty()) {
            for (MenuTreeResponse MenuTreeResponse : childList) {
                recursionAllTree(MenuTreeResponse, map);
            }
        }
    }

}
