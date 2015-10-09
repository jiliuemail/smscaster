package com.skyline.sms.caster.dao;

import java.util.List;

public interface BaseDao<T> {
	
	T findById(Integer id);
	
    void deleteById(Integer id) throws Exception;
 
    void update(T entity)throws Exception;
    
    void batchUpdate(final List<T> entitys) throws Exception;
    
    void insert(T entity)throws Exception;
     
    List<T> findAll();
    
    List<T> findByPage(Page page);
    
    List<T> findByCondition(List<QueryCondition> conditions, Page page);

}
