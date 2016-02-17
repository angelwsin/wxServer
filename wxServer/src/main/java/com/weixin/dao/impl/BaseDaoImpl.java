package com.weixin.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.weixin.dao.BaseDao;
/*
 * Hibernate3 dao层支持事物  Hibernate4 dao层不支持事物
 */
@Repository(value="baseDao")
@Transactional
public  class BaseDaoImpl<T> implements BaseDao<T> {
	
	    private SessionFactory sessionFactory;
	    protected Class<T> clazz;

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public BaseDaoImpl() {
			Class type = getClass();
			if (type != BaseDaoImpl.class) {
				Class parent = type.getSuperclass();
				while (parent != BaseDaoImpl.class) {
					parent = (type = parent).getSuperclass();
				}
				Type[] types = ((ParameterizedType) type.getGenericSuperclass()).getActualTypeArguments();
				if (types.length > 0) {
					this.clazz = (Class<T>) types[0];
				}
			}
		}

		public Class<T> getClazz() {
			return this.clazz;
		}
	    
	    
      public SessionFactory getSessionFactory() {
			return sessionFactory;
		}

        @Resource(name="sessionFactory")
		public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
		}
        
        public Session getSession(){
        	return    this.sessionFactory.getCurrentSession();
        }

	  public void save(T entiry) {
    	// TODO Auto-generated method stub
    	  getSession().save(entiry);
    }
	@SuppressWarnings("unchecked")
	public List<T> findAll(){
	Criteria criteria=getSession().createCriteria(getClazz());
		 return (List<T>)	criteria.list();
		//  return (List<T>) getSession().createQuery(" from  "+getClazz().getSimpleName()).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findObjectByMap(Map<String,Object> map){
		Criteria criteria=getSession().createCriteria(getClazz());
		for(String key:map.keySet()){
			 criteria.add(Restrictions.eq(key, map.get(key)));
		}
		return (List<T>)criteria.list();
	}
	
	
	 public void saveOrUpdate(T entiry){
		   getSession().saveOrUpdate(entiry);
	 }
	 @SuppressWarnings("unchecked")
	public T  findObjectById(Serializable id){
		                 
		      return   (T) getSession().get(getClazz(),id);
	 }
	 
	 
	
	
}
