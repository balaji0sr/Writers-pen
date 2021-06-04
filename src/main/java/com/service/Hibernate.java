//$Id$
package com.service;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.object.Comment;
import com.object.Likes;
import com.object.Story;
import com.object.Users;

public class Hibernate {

	public static SessionFactory getSessionFactory() {

		Configuration config = new Configuration().configure().addAnnotatedClass(Story.class);

		ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();

		SessionFactory sf = config.buildSessionFactory(reg);
		return sf;
	}

	public static SessionFactory getSessionFactoryOfUsers() {

		Configuration config = new Configuration().configure().addAnnotatedClass(Users.class);

		ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();

		SessionFactory sf = config.buildSessionFactory(reg);
		return sf;
	}

	public static SessionFactory getSessionFactoryOfComment() {

		Configuration config = new Configuration().configure().addAnnotatedClass(Comment.class);

		ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();

		SessionFactory sf = config.buildSessionFactory(reg);
		return sf;
	}
	
	public static SessionFactory getSessionFactoryOfLike() {

		Configuration config = new Configuration().configure().addAnnotatedClass(Likes.class);

		ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();

		SessionFactory sf = config.buildSessionFactory(reg);
		return sf;
	}
}
