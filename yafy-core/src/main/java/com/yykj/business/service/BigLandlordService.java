package com.yykj.business.service;

import com.yykj.business.dao.*;
import com.yykj.business.dto.BigLandlordDto;
import com.yykj.business.entity.*;
import com.yykj.business.response.BigLandlordDetailResponse;
import com.yykj.business.response.BigLandlordResponse;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import com.yykj.system.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ClassName BigLandlordService
 * @Description: TODO
 * @Author qhw
 * @Date 2019/11/17 0017 下午 12:06
 * @Version V1.0
 **/
@Service
public class BigLandlordService extends AbstractBaseCrudService<BigLandlord,Integer> {
    @Autowired
    AreaMapper areaMapper;
    @Autowired
    LandlordAreaMapper landlordAreaMapper;
    @Autowired
    AgreementMapper agreementMapper;
    @Autowired
    AreaAgreementMapper areaAgreementMapper;
    @Autowired
    RentManageMapper rentManageMapper;
    @Autowired
    RentAreaMapper rentAreaMapper;
    @Autowired
    BigLandlordMapper bigLandlordMapper;
    /**
     * description:  保存大房东信息
     * create by: qhw
     * create time: 2019/11/17 0017 下午 17:10
     */
    @Transactional
    public void save(BigLandlordDto bigLandlordDto, SysUser sysUser) {
        BigLandlord bigLandlord=new BigLandlord(bigLandlordDto.getBigLandlordName(),bigLandlordDto.getBigLandlordPhone(),sysUser);
        this.mapper.insert(bigLandlord);

        Area area=new Area(bigLandlordDto.getAreaName(),bigLandlordDto.getBlock(),bigLandlordDto.getUnit(),bigLandlordDto.getRoomNumber(),sysUser);
        areaMapper.insert(area);

        LandlordArea landlordArea=new LandlordArea(bigLandlord.getId(),area.getId());
        landlordAreaMapper.insert(landlordArea);

        Agreement agreement=new Agreement(bigLandlordDto.getDeposit(),bigLandlordDto.getAgreementStratTime()
        ,bigLandlordDto.getAgreementEndTime(),bigLandlordDto.getDegree(),sysUser.getId(),bigLandlordDto.getRemake(),bigLandlordDto.getPayType());
        agreementMapper.insert(agreement);

        AreaAgreement areaAgreement=new AreaAgreement(area.getId(),agreement.getId());
        areaAgreementMapper.insert(areaAgreement);

        RentManage rentManage=new RentManage(bigLandlordDto.getRentCost(),bigLandlordDto.getLastPayTime(),bigLandlordDto.getNextPayTime(),sysUser.getId());
        rentManageMapper.insert(rentManage);

        RentArea rentArea=new RentArea(area.getId(),rentManage.getId());
        rentAreaMapper.insert(rentArea);

    }
    /**
     * description:
     * create by: qhw
     * create time: 2019/11/17 0017 下午 20:04
     */
    public List<BigLandlordResponse> selectBigLandlordDto(Integer userId, Integer status) {
        return bigLandlordMapper.selectBigLandlordDto(userId,status);
    }
    /**
     * description:  大房东信息详情
     * create by: qhw
     * create time: 2019/11/17 0017 下午 21:07
     */
    public BigLandlordDetailResponse selectBigLandlordDtoByAreaId(Integer areaId) {
        return bigLandlordMapper.selectBigLandlordDtoByAreaId(areaId);
    }
    /**
     * description: 查询大房东列表
     * create by: qhw
     * create time: 2019/11/20 0020 下午 17:55
     */
    public List<BigLandlord> selectBigLandlordsByUserId(Integer userId) {
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("status",0).andEqualTo("creatorId",userId);
        example.setOrderByClause("id desc");
        return mapper.selectByExample(example);
    }
}
