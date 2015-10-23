package com.skyline.sms.caster.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface BaseDao<T> {
	
	T findById(Integer id);
	
	T findById(final Integer id, final String initialPropertyName);
	
    void deleteById(Integer id);
 
    void update(T entity);
    
    void batchUpdate(final List<T> entitys);
    
    void insert(T entity);
     
    List<T> findAll();
    
    List<T> findByEntity(T entity, Page page);
    
    List<T> findByPage(Page page);
    
    List<T> findByDetachedCriteria(DetachedCriteria detachedCriteria, Page page);

	List<T> findByDetachedCriteria(DetachedCriteria detachedCriteria);
}
