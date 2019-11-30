package com.yykj.business.conf;

import com.yykj.system.commons.RedisUtils;
import com.yykj.system.entity.SysResource;
import com.yykj.system.entity.SysUser;
import com.yykj.system.service.SysResourceService;
import com.yykj.system.service.SysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {
    private final String resourceKey="resourceKey:";

    @Autowired
    RedisUtils redisUtils;
    @Autowired
    SysResourceService sysResourceService;
    @Autowired
    SysUserService sysUserService;
    /**
     * 权限认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        SysUser sysUser = (SysUser) principalCollection.getPrimaryPrincipal();
//        StringBuffer stringBuffer=new StringBuffer(resourceKey);
//        stringBuffer.append(sysUser.getName());
//        stringBuffer.append(":");
        //该用户的权限菜单
//        List<String> stringSet=redisUtils.getListT(stringBuffer.toString());
//        if(stringSet.isEmpty()){
        List<SysResource>  sysResourceList=sysResourceService.selectByUserId(sysUser.getId());
        Set<String> stringPermissions=new HashSet<>(sysResourceList.size());
        while(sysResourceList.iterator().hasNext()){
            SysResource sysResource=(SysResource)sysResourceList.iterator();
            stringPermissions.add(sysResource.getResourceUrl());
        }
//            redisUtils.setList(stringBuffer.toString(),stringPermissions);
//            stringSet.addAll(stringPermissions);
//        }

        simpleAuthorizationInfo.setStringPermissions(stringPermissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 身份认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        SysUser sysUser;
        try {

//        String userInfoStr= redisUtils.getStringForKey("userKey:"+username);
 //       if(StringUtils.isEmpty(userInfoStr)){
            sysUser = sysUserService.findByUsername(username);
//            redisUtils.setStringForKey("userKey:"+username,JsonUtils.beanToJson(sysUser));
//           redisUtils.setExpireKey("userKey:"+username,1L, TimeUnit.DAYS);
//        }else{
//            sysUser= JsonUtils.jsonToBean(userInfoStr,SysUser.class);
 //       }

        if (sysUser == null) {
            return null;
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                sysUser, //用户名
                sysUser.getPassword(), //密码
                getName()  //realm name
        );

        return authenticationInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
