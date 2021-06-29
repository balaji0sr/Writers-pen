//$Id$
package com.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.object.Genre;
import com.object.Genre_user;

public class GenreCRUD {
	public static ArrayList<Genre> read(long userid , boolean subglist) {
		Session ses = Hibernate.getSessionFactoryOfGenre().openSession();
		Transaction t = ses.beginTransaction();

		ArrayList<Genre> glist = new ArrayList<Genre>();
		ArrayList<Genre_user> gulist = Genre_userCRUD.get(userid);
		int listsize = gulist.size();
		if (listsize > 0) {
			String hql = "Select genreid , genrename FROM Genre WHERE ";
			for (int i = 0; i < listsize; i++) {
				int genreid = gulist.get(i).getGenreid();
				if(subglist) {
					hql = hql + "genreid = " + genreid;
					if (i == listsize - 1) {
						break;
					}
					hql = hql + " or ";
				}
				else {
					hql = hql + "genreid <> " + genreid;
					if (i == listsize - 1) {
						break;
					}
					hql = hql + " and ";
				}
			}

			System.out.println(hql);
			Query query = ses.createQuery(hql);
			List<Object[]> list = (List<Object[]>) query.list();
			t.commit();

			for (Object[] o : list) {
				Genre g = new Genre();
				g.setGenreid((int) o[0]);
				g.setGenrename((String) o[1]);
				glist.add(g);
			}
		}
		return glist;
	}
	
	public static Genre read(int genreid) throws ClassNotFoundException, SQLException, IOException {

		Session ses = Hibernate.getSessionFactoryOfGenre().openSession();
		Transaction t = ses.beginTransaction();

		Criteria criteria = ses.createCriteria(Genre.class);
		Genre s = (Genre) criteria.add(Restrictions.eq("genreid", genreid)).uniqueResult();

		t.commit();
		return s;
	}
}
