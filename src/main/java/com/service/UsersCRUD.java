//$Id$
package com.service;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.object.Users;

public class UsersCRUD {
	public static Users create(String name, String mail, String pas) {

		Users u = new Users();
		u.setName(name);
		u.setMail(mail);
		u.setPas(pas);

		Session ses = Hibernate.getSessionFactoryOfUsers().openSession();
		Transaction t = ses.beginTransaction();
		ses.save(u);

		t.commit();
		return u;
	}
	
	public static long read(String mail) {
		Session ses = Hibernate.getSessionFactoryOfUsers().openSession();

		Criteria criteria = ses.createCriteria(Users.class);
		Users a = (Users) criteria.add(Restrictions.eq("mail", mail)).uniqueResult();

		return a.getUserid();
	}
}
