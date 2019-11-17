package com.yykj.system.service;

import com.yykj.system.dto.SysUserDto;
import com.yykj.system.commons.MD5Password;
import com.yykj.system.commons.StringUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import com.yykj.system.dao.SysRoleUserMapper;
import com.yykj.system.dao.SysUserMapper;
import com.yykj.system.entity.SysRole;
import com.yykj.system.entity.SysRoleUser;
import com.yykj.system.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserService extends AbstractBaseCrudService<SysUser, Integer> {
    @Autowired
    SysRoleUserMapper sysRoleUserMapper;
    @Autowired
    SysUserMapper sysUserMapper;

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    public SysUser findByUsername(String username) {
        Example example = new Example(tClass);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", username).andEqualTo("status", 0);
        return mapper.selectOneByExample(example);
    }

    /**
     * 根据关键字查找用户列表
     *
     * @param keyword
     * @return
     */
    public List<SysUser> selectByKeyword(String keyword) {
        Example example = new Example(tClass);
        Example.Criteria criteriae = example.createCriteria();
        if (StringUtils.isNotEmpty(keyword)) {
            String keywordStr = new StringBuffer("%").append(keyword).append("%").toString();
            criteriae.orLike("name", keywordStr).orLike("phone", keywordStr).orLike("realName", keywordStr);
        }
        example.setOrderByClause("id desc");
        return mapper.selectByExample(example);
    }

    /**
     * 用户保存
     *
     * @param sysUserDto
     */
    @Transactional()
    public void save(SysUserDto sysUserDto) throws Exception {
        if (this.checkUserName(sysUserDto.getName(), sysUserDto.getId())) {
            throw new Exception("该登录账号已存在");
        }
        if (sysUserDto.getId() == null || sysUserDto.getId() < 1) {
            sysUserDto.setId(null);
            if (StringUtils.isNotEmpty(sysUserDto.getPassword())) {
                sysUserDto.setPassword(MD5Password.md5(sysUserDto.getPassword()));
            } else {
                sysUserDto.setPassword(MD5Password.md5(SystemConstants.defaultPassword));
            }
            mapper.insert(sysUserDto);
        } else {
            if (sysUserDto.getPassword() != null) {
                sysUserDto.setPassword(MD5Password.md5(sysUserDto.getPassword()));
            } else {
                sysUserDto.setPassword(null);
            }
            mapper.updateByPrimaryKeySelective(sysUserDto);
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setUid(sysUserDto.getId());
            sysRoleUserMapper.delete(sysRoleUser);
        }
        List<SysRoleUser> sysRoleUsers = getSysRoleUsers(sysUserDto);
        sysRoleUserMapper.insertList(sysRoleUsers);
    }

    /**
     * 构建用户与角色的关系
     *
     * @param sysUserDto
     * @return
     */
    private List<SysRoleUser> getSysRoleUsers(SysUserDto sysUserDto) {
        List<SysRoleUser> sysRoleUsers = new ArrayList<>(sysUserDto.getRoleIds().size());
        SysRoleUser sysRoleUser;
        for (Integer roleId : sysUserDto.getRoleIds()) {
            sysRoleUser = new SysRoleUser();
            sysRoleUser.setRoleId(roleId);
            sysRoleUser.setUid(sysUserDto.getId());
            sysRoleUsers.add(sysRoleUser);
        }
        return sysRoleUsers;
    }


    /**
     * 校验是否存在用户名
     *
     * @param username
     * @param userId
     * @return
     */
    private boolean checkUserName(String username, Integer userId) {
        Example example = new Example(tClass);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", username);
        if (userId != null) {
            criteria.andNotEqualTo("id", userId);
        }
        List<SysUser> sysUsers = mapper.selectByExample(example);
        if (sysUsers.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查询该学校的老师
     *
     * @param keyword
     * @return
     */
    public List<SysUser> selectTeacher(String keyword, Integer schoolId) {
        keyword = StringUtils.isNotEmpty(keyword) ? new StringBuffer("%").append(keyword).append("%").toString() : null;
        return sysUserMapper.selectTeacher(keyword, schoolId);
    }

    /**
     * 保存教师信息
     *
     * @param sysUser
     */
    @Transactional
    public void saveTeacher(SysUser sysUser,SysRoleService sysRoleService) throws Exception {
        if (this.checkUserName(sysUser.getName(), sysUser.getId())) {
            throw new Exception("该登录账号已存在");
        }
        if (sysUser.getId() == null || sysUser.getId() < 1) {
            sysUser.setId(null);
            if (StringUtils.isNotEmpty(sysUser.getPassword())) {
                sysUser.setPassword(MD5Password.md5(sysUser.getPassword()));
            } else {
                sysUser.setPassword(MD5Password.md5(SystemConstants.defaultPassword));
            }
            mapper.insert(sysUser);
            SysRole sysRole=sysRoleService.findRoleByName(SystemConstants.SCHOOL_DEFAULT_TEACHER_NAME,sysUser.getSchoolId());
            SysRoleUser sysRoleUser=new SysRoleUser();
            sysRoleUser.setUid(sysUser.getId());
            sysRoleUser.setRoleId(sysRole.getId());
            sysRoleUserMapper.insert(sysRoleUser);
        } else {
            if (sysUser.getPassword() != null) {
                sysUser.setPassword(MD5Password.md5(sysUser.getPassword()));
            } else {
                sysUser.setPassword(null);
            }
            mapper.updateByPrimaryKeySelective(sysUser);
        }
    }

    /**
     * 查询所有用户登录名称
     * @return
     */
    public List<String> selectUserName() {
        return sysUserMapper.selectUserName();
    }

    /**
     * 批量出入
     * @param teacherList
     */
    public void insertList(List<SysUser> teacherList) {
        sysUserMapper.insertList(teacherList);
    }

    /**
     * 批量插入用户角色关系
     */
    public void insertSysRoleUserList(List<SysRoleUser> sysRoleUsers) {
        sysRoleUserMapper.insertList(sysRoleUsers);
    }
    /**
     * description: 根据家长ID查询教师信息
     * create by: qhw
     * create time: 2019/11/6 0006 下午 16:14
     */
    public List<SysUser> selectTeacherInfoByParentId(Integer parentId, Integer schoolId) {
        return sysUserMapper.selectTeacherInfoByParentId(parentId,schoolId);
    }
    /**
    * @Description 教师列表（启用）
    * @Author 王晓
    * @Date 2019/11/7 15:42
    * @Param keyword
    * @Param schoolId
    * @Return java.util.List<com.yykj.system.entity.SysUser>
    * @Exception
    */
    public List<SysUser> getListByStatus(String keyword, Integer schoolId) {
        return sysUserMapper.getListByStatus(keyword,schoolId);
    }

    public SysUser getUserInfoByNameAndPhone(Integer schoolId, String classTeacherName, String classTeacherPhone) {
        Example example = new Example(tClass);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("realName", classTeacherName);
        criteria.andEqualTo("schoolId", schoolId);
        criteria.andEqualTo("phone", classTeacherPhone);
        List<SysUser> sysUsers = mapper.selectByExample(example);
        return sysUsers.size()> 0 ? sysUsers.get(0):null;
    }
}
