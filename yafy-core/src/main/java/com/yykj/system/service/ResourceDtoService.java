package com.yykj.system.service;


import com.yykj.system.dto.ResourceDto;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import com.yykj.system.dao.SysResourceMapper;
import com.yykj.system.entity.SysResource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResourceDtoService extends AbstractBaseCrudService<SysResource, Integer> {

   @Autowired
   private SysResourceMapper systemResourceMapper;

    /**
     * 查询本系统所有资源权限
     * @return
     */
    public List<ResourceDto> selectAll(){
        //查询顶级数据
        Example example = new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("parentId",0).andEqualTo("status",0);
        example.setOrderByClause("seq ,id ");
        List<SysResource> topList= mapper.selectByExample(example);

        //查询非定级数据结构
        Example example2 = new Example(tClass);
        Example.Criteria criteria2=example2.createCriteria();
        criteria2.andNotEqualTo("parentId",0).andEqualTo("status",0);
        example2.setOrderByClause("seq ,id  ");
        List<SysResource> list= mapper.selectByExample(example2);

        //对象转换
        List<ResourceDto> topListDto = changeList(topList);
        List<ResourceDto> notListDto = changeList(list);

        Map<Integer,Integer> newHashMapWithExpectedSize = new HashMap<>(notListDto.size());

        for(ResourceDto organDto:topListDto){
            getChild(organDto,notListDto,newHashMapWithExpectedSize);
        }

        return topListDto;

    }
    private List<ResourceDto> changeList( List<SysResource> oldListDto){
        List<ResourceDto> resourceDtoList=new ArrayList<>();
        for(SysResource  organ:oldListDto){
            ResourceDto organDto = new ResourceDto();
            BeanUtils.copyProperties(organ,organDto);
            resourceDtoList.add(organDto);
        }
        return resourceDtoList;
    }

    private  void  getChild(ResourceDto organDto,List<ResourceDto>organDtos,Map<Integer,Integer>map){
        List<ResourceDto>childList = new ArrayList<>();
        for(ResourceDto organDto1:organDtos){
            if(!map.containsKey(organDto1.getId())&&organDto.getId().intValue()==organDto1.getParentId().intValue()){
                map.put(organDto1.getId(),organDto1.getParentId());
                getChild(organDto1,organDtos,map);
                childList.add(organDto1);
            }
        }
        organDto.setResourceDtos(childList);

    }

    /**
     * 根据当前用户ID查询对应角色对应的资源
     * @param userId
     * @return
     */
 public  List<ResourceDto> selectByUserId(Integer userId){
    List<SysResource>  systemResources=systemResourceMapper.selectByUserId(userId);
    List<ResourceDto> topResource=new ArrayList<>();
    List<ResourceDto> notTopResource=new ArrayList<>();
    for(SysResource systemResource:systemResources){
        ResourceDto resourceDto=new ResourceDto();
        BeanUtils.copyProperties(systemResource,resourceDto);
        if(systemResource.getParentId()==0){
            topResource.add(resourceDto);
        }else{
            notTopResource.add(resourceDto);
        }
    }
     Map<Integer,Integer> newHashMapWithExpectedSize = new HashMap<>(notTopResource.size());

     for(ResourceDto organDto:topResource){
         getChild(organDto,notTopResource,newHashMapWithExpectedSize);
     }

     return topResource;
 }

    /**
     * 保存资源
     * @param systemResource
     */
    public void save(SysResource systemResource)throws Exception {
     if(this.checkResourceName(systemResource.getResourceName(),systemResource.getId())){
         throw new Exception("资源名称已存在");
     }
      if(systemResource.getId()==null||systemResource.getId()<1){//新建资源
          mapper.insert(systemResource);
      }else{
          mapper.updateByPrimaryKey(systemResource);
      }
    }

    /**
     * 校验资源名称是否已存在
     * @return
     */
    private boolean checkResourceName(String resourceName,Integer resourceId){
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("resourceName",resourceName).andEqualTo("status",0);
        if(resourceId!=null){
            criteria.andNotEqualTo("id",resourceId);
        }
        List<SysResource> sysResourceList=mapper.selectByExample(example);
        if(sysResourceList.size()>0){
            return true;
        }
        return false;
    }
}
