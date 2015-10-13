package com.skyline.sms.caster.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;

import com.skyline.sms.caster.db.HibernateCallBack;
import com.skyline.sms.caster.db.HibernateSessionFactory;

public class HibernateDao<T> implements BaseDao<T> {
	
	

	@SuppressWarnings("unchecked")
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
	public void batchUpdate(final List<T> entitys) throws Exception {
		doHibernateTemplate(new HibernateCallBack<Boolean>() {
			@Override
			public Boolean doSession(Session session) {
				for (T entity : entitys) {
					session.saveOrUpdate(entity);
				}
				session.flush();
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
				return session.createCriteria(getEntityClass()).list();
			}
		});
	}
	
	
	@Override
	public List<T> findByEntity(final T entity, final Page page) {
		return doHibernateTemplate(new HibernateCallBack<List<T>>() {
			@SuppressWarnings("unchecked")
			public List<T> doSession(Session session) {
				return session.createCriteria(getEntityClass())
					.add(Example.create(entity))
					.setFirstResult(page.getFirstResultIndex())
					.setMaxResults(page.getPageSize())
					.list();
			}
		});
	}

	@Override
	public List<T> findByPage(final Page page) {
		return doHibernateTemplate(new HibernateCallBack<List<T>>() {
			@SuppressWarnings("unchecked")
			public List<T> doSession(Session session) {
				return session.createCriteria(getEntityClass())
					.setFirstResult(page.getFirstResultIndex())
					.setMaxResults(page.getPageSize())
					.list();
			}
		});
	}

	@Override
	public List<T> findByDetachedCriteria(final DetachedCriteria detachedCriteria, final Page page) {
		return doHibernateTemplate(new HibernateCallBack<List<T>>() {
			@SuppressWarnings("unchecked")
			public List<T> doSession(Session session) {
				return (List<T>)detachedCriteria.getExecutableCriteria(session)
					.setFirstResult(page.getFirstResultIndex())
					.setMaxResults(page.getPageSize())
					.list();
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
