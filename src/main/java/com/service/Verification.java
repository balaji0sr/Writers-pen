//$Id$
package com.service;

import java.sql.SQLException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.object.Users;

public class Verification {

	public static Users verifylogin(String mail, String pas) throws ClassNotFoundException, SQLException {
		Session ses = Hibernate.getSessionFactoryOfUsers().openSession();
		Transaction t = ses.beginTransaction();

		Criteria criteria = ses.createCriteria(Users.class);
		Users u = (Users) criteria.add(Restrictions.eq("mail", mail)).uniqueResult();
		t.commit();
		return u;
	}
}
