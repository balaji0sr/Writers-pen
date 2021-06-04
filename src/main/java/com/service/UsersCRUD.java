//$Id$
package com.service;


import org.hibernate.Session;
import org.hibernate.Transaction;

import com.object.Users;

public class UsersCRUD {
	public static void create(String name, String mail, String pas) {

		Users u = new Users();
		u.setName(name);
		u.setMail(mail);
		u.setPas(pas);

		Session ses = Hibernate.getSessionFactoryOfUsers().openSession();
		Transaction t = ses.beginTransaction();
		ses.save(u);

		t.commit();
	}
}
