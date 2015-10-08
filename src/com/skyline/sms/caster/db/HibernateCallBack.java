package com.skyline.sms.caster.db;

import org.hibernate.Session;

public interface HibernateCallBack<T> {
	
	public T doSession(Session session);

}
