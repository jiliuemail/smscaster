package com.skyline.sms.caster.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.skyline.sms.caster.dao.Page;
import com.skyline.sms.caster.dao.UserDao;
import com.skyline.sms.caster.dao.impl.UserDaoImpl;
import com.skyline.sms.caster.pojo.TUser;
import com.skyline.sms.caster.service.UserService;
import com.skyline.sms.caster.util.StringUtil;

public class UserServiceImpl implements UserService {
	
	private UserDao userDao;
	
	public UserServiceImpl(){
		userDao = new UserDaoImpl();
	}

	@Override
	public List<TUser> findUsers(TUser user, Page page) throws Exception {
		return userDao.findByDetachedCriteria(DetachedCriteria.forClass(TUser.class)
				.add(Example.create(user).enableLike(MatchMode.ANYWHERE))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				, page);
	}

	public List<TUser> findUsersByName(TUser user, Page page) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(TUser.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		if (user != null && StringUtil.hasText(user.getUserName())) {
			criteria.add(Restrictions.or(
					Restrictions.like("userName", user.getUserName(), MatchMode.ANYWHERE),
					Restrictions.like("number", user.getUserName(), MatchMode.ANYWHERE)));
		}
		return userDao.findByDetachedCriteria(criteria, page);
	}
	

	@Override
	public TUser findUsersById(TUser user) throws Exception {
		if (user == null || user.getId() == null) {
			return user;
		}
		return userDao.findById(user.getId(), "TGroups");
	}
	
	@Override
	public void saveOrUpdateUsers(List<TUser> users) throws Exception {
		userDao.batchUpdate(users);
		
	}
	
	@Override
	public void deleteUser(TUser user) throws Exception {
		if (user == null || user.getId() == null) {
			return;
		}
		userDao.deleteById(user.getId());
	}

}
