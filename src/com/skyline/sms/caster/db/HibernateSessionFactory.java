package com.skyline.sms.caster.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateSessionFactory {
	
	static Configuration configuration;
    static SessionFactory sessionFactory;
    static ServiceRegistry serviceRegistry;
     
    static{
        configuration=new Configuration().configure("hibernate.cfg.xml");// hibernate.cfg.xml
        //  创建服务注册对象 
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        // 创建sessionFactory 工厂对象
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
         
    }
     
    // 获取session 对象
    public static Session getSession(){
        return sessionFactory.openSession();
    }
    
    public static void close(){
    	Session session = sessionFactory.getCurrentSession();
    	if (session != null) {
			session.close();
		}
    	if (!sessionFactory.isClosed()) {
			sessionFactory.close();
		}
    }

}
