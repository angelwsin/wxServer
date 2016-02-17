package com.weixin.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

public interface BaseDao<T> {
	 public void save(T entiry);
	 public List<T> findAll();
	 public List<T> findObjectByMap(Map<String,Object> map);
	 public T  findObjectById(Serializable id);
	 public void saveOrUpdate(T entiry);
	 public SessionFactory getSessionFactory() ;

}
