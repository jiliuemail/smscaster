package com.skyline.sms.caster.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface BaseDao<T> {
	
	T findById(Integer id);
	
    void deleteById(Integer id) throws Exception;
 
    void update(T entity)throws Exception;
    
    void batchUpdate(final List<T> entitys) throws Exception;
    
    void insert(T entity)throws Exception;
     
    List<T> findAll();
    
    List<T> findByEntity(T entity, Page page);
    
    List<T> findByPage(Page page);
    
    List<T> findByDetachedCriteria(DetachedCriteria detachedCriteria, Page page);
}
