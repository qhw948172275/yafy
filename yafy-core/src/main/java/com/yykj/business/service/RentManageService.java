package com.yykj.business.service;

import com.yykj.business.dao.RentManageMapper;
import com.yykj.business.entity.RentManage;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RentManageService
 * @Description: TODO
 * @Author qhw
 * @Date 2019/11/17 0017 下午 20:54
 * @Version V1.0
 **/
@Service
public class RentManageService extends AbstractBaseCrudService<RentManage,Integer> {
    @Autowired
    RentManageMapper rentManageMapper;
    /**
     * description: TODO 根据套房ID查询缴费记录
     * create by: qhw
     * create time: 2019/11/17 0017 下午 20:55
     */
    public List<RentManage> selectRentManage(Integer areaId) {
      return   rentManageMapper.selectRentManage(areaId);
    }
}
