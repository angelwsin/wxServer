package com.weixin.service.impl;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.weixin.dao.BaseDao;
import com.weixin.service.BaseService;

@Service
public class BaseServiceImpl<T> implements BaseService<T>{
	        @Resource(name="baseDao")
	        private BaseDao<T> baseDao;

			public void save(T entity) {
				// TODO Auto-generated method stub
				baseDao.save(entity);
			}
	        
	        
	      

}
