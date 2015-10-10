package com.skyline.sms.caster.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.skyline.sms.caster.db.HibernateCallBack;
import com.skyline.sms.caster.db.HibernateSessionFactory;
import com.skyline.sms.caster.util.CollectionUtil;

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
	public List<T> findByCondition(final List<QueryCondition> conditions, final Page page) {
		return doHibernateTemplate(new HibernateCallBack<List<T>>() {
			@SuppressWarnings("unchecked")
			public List<T> doSession(Session session) {
				
				List<QueryCondition> validConditions = new ArrayList<QueryCondition>();
				if (CollectionUtil.hasElements(conditions)) {
					for (QueryCondition condition : conditions) {
						if (condition.isValidCondition()) {
							validConditions.add(condition);
						}
					}
				}
				
				Criteria qc = session.createCriteria(getEntityClass());
				if (CollectionUtil.hasElements(validConditions)) {
					appendConditions(qc, validConditions);
				}
				qc.setFirstResult(page.getFirstResultIndex());
				qc.setMaxResults(page.getPageSize());
				return qc.list();
			}
		});
	}
	
	private void appendConditions(Criteria qc, List<QueryCondition> conditions){
		for (QueryCondition condition : conditions) {
			ConditionType conditionType = condition.getConditionType();
			switch (conditionType) {
			case EQUALS:
				qc.add(Restrictions.eq(condition.getParamName(), condition.getParamValue()));
				break;
			case NOT_EQUALS:
				qc.add(Restrictions.not(Restrictions.eq(condition.getParamName(), condition.getParamValue())));
				break;
			case LIKE:
				qc.add(Restrictions.like(condition.getParamName(), condition.formatedParamValue(), MatchMode.ANYWHERE));
				break;
			case LEFT_LIKE:
				qc.add(Restrictions.like(condition.getParamName(), condition.formatedParamValue(), MatchMode.END));
				break;
			case RIGHT_LIKE:
				qc.add(Restrictions.like(condition.getParamName(), condition.formatedParamValue(), MatchMode.START));
				break;
			case GT:
				qc.add(Restrictions.gt(condition.getParamName(), condition.getParamValue()));
				break;
			case LT:
				qc.add(Restrictions.lt(condition.getParamName(), condition.getParamValue()));
				break;
			case GE:
				qc.add(Restrictions.ge(condition.getParamName(), condition.getParamValue()));
				break;
			case LE:
				qc.add(Restrictions.le(condition.getParamName(), condition.getParamValue()));
				break;
			case NULL:
				qc.add(Restrictions.isNull(condition.getParamName()));
				break;
			case NOT_NULL:
				qc.add(Restrictions.isNotNull(condition.getParamName()));
				break;
			case IN:
			case NOT_IN:
			default:
				break;
			}
		}
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
