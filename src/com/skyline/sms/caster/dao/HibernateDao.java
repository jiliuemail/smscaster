package com.skyline.sms.caster.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.skyline.sms.caster.db.HibernateCallBack;
import com.skyline.sms.caster.db.HibernateSessionFactory;

public class HibernateDao<T> implements BaseDao<T> {
	
	

	@Override
	public T findById(Integer id) {
		return (T)HibernateSessionFactory.getSession().get(getEntityClass(), id);
	}

	@Override
	public void deleteById(final Integer id) throws Exception{
		doHibernateTemplate(new HibernateCallBack<Boolean>() {
			@Override
			public Boolean doSession(Session session) {
				session.delete(findById(id));
				return true;
			}
		});
	}

	@Override
	public void update(final T entity) throws Exception {
		doHibernateTemplate(new HibernateCallBack<Boolean>() {
			@Override
			public Boolean doSession(Session session) {
				session.saveOrUpdate(entity);
				return true;
			}
		});
	}

	@Override
	public void insert(final T entity) throws Exception {
		doHibernateTemplate(new HibernateCallBack<Boolean>() {
			@Override
			public Boolean doSession(Session session) {
				session.save(entity);
				return true;
			}
		});
	}

	@Override
	public List<T> findAll() {
		return doHibernateTemplate(new HibernateCallBack<List<T>>() {
			@SuppressWarnings("unchecked")
			public List<T> doSession(Session session) {
				return session.createQuery("from " + getEntityClass().getSimpleName()).list();
			}
		});
	}
	
	
	
	
	@Override
	public List<T> findByPage(final Page page) {
		return doHibernateTemplate(new HibernateCallBack<List<T>>() {
			@SuppressWarnings("unchecked")
			public List<T> doSession(Session session) {
				Query query = session.createQuery("from " + getEntityClass().getSimpleName());
				query.setFirstResult(page.getFirstResultIndex());
				query.setMaxResults(page.getPageSize());
				return query.list();
			}
		});
	}

	@Override
	public List<T> findByCondition(final List<QueryCondition> conditions, final Page page) {
		return doHibernateTemplate(new HibernateCallBack<List<T>>() {
			@SuppressWarnings("unchecked")
			public List<T> doSession(Session session) {
				Query query = session.createQuery("from " + getEntityClass().getSimpleName());
				query.setFirstResult(page.getFirstResultIndex());
				query.setMaxResults(page.getPageSize());
//				TODO set conditons
				return query.list();
			}
		});
	}

	protected <R> R doHibernateTemplate(HibernateCallBack<R> callBack){
		Session session = HibernateSessionFactory.getSession();
        Transaction tx = session.beginTransaction();
        try {
        	R result = callBack.doSession(session);
        	tx.commit();
        	return result;
        }catch(HibernateException he){
			tx.rollback();
        	throw he;
		} finally {
			 session.close();
		}
	}
	

	@SuppressWarnings("unchecked")
	protected Class<T> getEntityClass() {
		Class<T> entityClass = null;
        Type t = getClass().getGenericSuperclass();
        if(t instanceof ParameterizedType){
            Type[] p = ((ParameterizedType)t).getActualTypeArguments();
            entityClass = (Class<T>)p[0];
        }
        return entityClass;
	}
	
}
