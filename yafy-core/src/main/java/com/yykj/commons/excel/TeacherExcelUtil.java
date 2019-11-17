package com.yykj.commons.excel;

import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.MD5Password;
import com.yykj.system.commons.StringUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.entity.SysRole;
import com.yykj.system.entity.SysRoleUser;
import com.yykj.system.entity.SysUser;
import com.yykj.system.service.SysRoleService;
import com.yykj.system.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.Pattern;

@Component
public class TeacherExcelUtil extends ExcelUtils {
    private Logger log = LoggerFactory.getLogger(TeacherExcelUtil.class);

    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysRoleService sysRoleService;
    /**
     * 教师导出错误excel标题
     */
    public static String[] TITLE_COLUMNS_ERROR = new String[]{"登录账号", "姓名", "电话", "身份证信息", "错误信息"};

    /**
     * 教师导入
     *
     * @return
     */
    public static Map<String, Object> teacherImport(Set<String> userNameSet, MultipartFile file, SysUser sysUser) throws Exception {
        Map<String, Object> results = new HashMap<>();
        List<List<String>> data = readFile(file.getOriginalFilename(), file.getInputStream(), 1, 4);
        List<List<String>> errorList = new ArrayList<>();

        SysUser sysTeacher;
        List<SysUser> teacherList = new ArrayList<>();

        for (List<String> ls : data) {
            try {
                if (StringUtils.isEmpty(ls.get(0)) || StringUtils.isEmpty(ls.get(1))
                        || StringUtils.isEmpty(ls.get(2)) || StringUtils.isEmpty(ls.get(3))) {
                    throw new Exception("登录账号,姓名，电话，身份证信息 不能为空");
                }
                if (userNameSet.contains(ls.get(0))) {//校验登录账号是否存在
                    throw new Exception("该登录账号已存在");
                }
                //校验手机号格式
                if (!Pattern.matches(SystemConstants.PHONE_CHECK, ls.get(2))) {
                    throw new Exception("手机号格式不正确");
                }
                sysTeacher = new SysUser();
                sysTeacher.setStatus(SystemConstants.STATUS_OK);
                sysTeacher.setName(ls.get(0));
                sysTeacher.setType(1);//默认加盟校用户
                sysTeacher.setCreatorId(sysUser.getCreatorId());
                sysTeacher.setSchoolId(sysUser.getSchoolId());
                sysTeacher.setPassword(MD5Password.md5(SystemConstants.defaultPassword));
                sysTeacher.setCreator(sysUser.getName());
                sysTeacher.setCreatetime(CalendarUtils.getDate());
                sysTeacher.setPhone(ls.get(2));
                sysTeacher.setIdCard(ls.get(3));
                sysTeacher.setRealName(ls.get(1));
                teacherList.add(sysTeacher);
            } catch (Exception e) {
                ls.add(e.getMessage());
                errorList.add(ls);
            }
        }

        if (!errorList.isEmpty()) {
            results.put("errorList", errorList);
        }
        if(!teacherList.isEmpty()){
            results.put("teacherList", teacherList);
        }
        return results;
    }

    /**
     * 保存教师信息
     *
     * @return
     */
    @Transactional
    public Boolean save(List<SysUser> teacherList) {
        try {
            sysUserService.insertList(teacherList);
            List<SysRoleUser> sysRoleUsers = new ArrayList<>(teacherList.size());
            SysRole sysRole = sysRoleService.findRoleByName(SystemConstants.SCHOOL_DEFAULT_TEACHER_NAME, teacherList.get(0).getSchoolId());
            SysRoleUser sysRoleUser;
            for (SysUser sysUser : teacherList) {
                sysRoleUser = new SysRoleUser();
                sysRoleUser.setUid(sysUser.getId());
                sysRoleUser.setRoleId(sysRole.getId());
                sysRoleUsers.add(sysRoleUser);
            }
            sysUserService.insertSysRoleUserList(sysRoleUsers);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

    }
}
