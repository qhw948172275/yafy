package com.yykj.system.dao;

import com.yykj.system.commons.service.MyMapper;
import com.yykj.system.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends MyMapper<SysUser> {
    /**
     * 查询该学校的老师
     * @param keyword
     * @param schoolId
     * @return
     */
    List<SysUser> selectTeacher(@Param("keyword") String keyword,@Param("schoolId") Integer schoolId);

    /**
     * 查询所有用户登录名称
     * @return
     */
    List<String> selectUserName();
    /**
     * description: 根据家长ID查询教师信息
     * create by: qhw
     * create time: 2019/11/6 0006 下午 16:15
     */
    List<SysUser> selectTeacherInfoByParentId(@Param("parentId") Integer parentId,@Param("schoolId") Integer schoolId);

    List<SysUser> getListByStatus(@Param("keyword")String keyword, @Param("schoolId")Integer schoolId);
}