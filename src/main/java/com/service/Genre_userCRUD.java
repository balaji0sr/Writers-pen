//$Id$
package com.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.object.Genre_user;

public class Genre_userCRUD {
	public static ArrayList<Genre_user> get(long userid) {

		Session ses = Hibernate.getSessionFactoryOfGenre_user().openSession();
		Transaction t = ses.beginTransaction();

		Criteria criteria = ses.createCriteria(Genre_user.class);
		ArrayList<Genre_user> 	gulist = (ArrayList<Genre_user>) criteria.add(Restrictions.eq("userid", userid)).list();
		t.commit();
		return gulist;
	}

	public static void create(long userid, int genreid) {

		Genre_user gu = new Genre_user();
		gu.setGenreid(genreid);
		gu.setUserid(userid);

		Session ses = Hibernate.getSessionFactoryOfGenre_user().openSession();
		Transaction t = ses.beginTransaction();
		ses.save(gu);

		t.commit();
	}

	public static void delete(long genre_userid) throws ClassNotFoundException, SQLException, IOException {

		Session ses = Hibernate.getSessionFactoryOfGenre_user().openSession();
		Transaction t = ses.beginTransaction();
		Genre_user c = (Genre_user) ses.load(Genre_user.class, genre_userid);
		ses.delete(c);
		t.commit();
	}
}
