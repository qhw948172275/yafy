package com.yykj.system.commons.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yykj.system.commons.BaseService;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
* @ClassName: AbstractBaseService 
* @author zhaoqiang 
* @date 2017年11月8日 下午3:07:52 
* 
* @param <T>
* @param <ID>
 */
public class AbstractBaseService<T, ID extends Serializable> implements BaseService<T, ID> {
	 @Autowired
	 private Mapper<T> mapper;

	// public void setmapper(Mapper<T> mapper) {
	// 	this.mapper = mapper;
	// }

	@Override
	public int deleteByPrimaryKey(Object key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int delete(T record) {
		return mapper.delete(record);
	}

	@Override
	public int insert(T record) {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(T record) {
		return mapper.insertSelective(record);
	}

	@Override
	public List<T> selectAll() {
		return mapper.selectAll();
	}

	@Override
	public T selectByPrimaryKey(Object key) {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public int selectCount(T record) {
		return mapper.selectCount(record);
	}

	@Override
	public List<T> select(T record) {
		return mapper.select(record);
	}

	@Override
	public T selectOne(T record) {
		return mapper.selectOne(record);
	}

	@Override
	public int updateByPrimaryKey(T record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(T record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

//	@Override
//	public int deleteByExample(Object example) {
//		return mapper.deleteByExample(example);
//	}
//
//	@Override
//	public List<T> selectByExample(Object example) {
//		return mapper.selectByExample(example);
//	}
//
//	@Override
//	public int selectCountByExample(Object example) {
//		return mapper.selectCountByExample(example);
//	}
//
//	@Override
//	public int updateByExample(T record, Object example) {
//		return mapper.updateByExample(record, example);
//	}
//
//	@Override
//	public int updateByExampleSelective(T record, Object example) {
//		return mapper.updateByExampleSelective(record, example);
//	}

}
