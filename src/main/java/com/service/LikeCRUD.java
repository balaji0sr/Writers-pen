//$Id$
package com.service;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import com.object.Likes;

public class LikeCRUD {
	public static Likes read(long userid, long storyid) {

		Session ses = Hibernate.getSessionFactoryOfLike().openSession();
		Transaction t = ses.beginTransaction();


		Criteria criteria = ses.createCriteria(Likes.class);
		
		Criterion uid = Restrictions.eq("userid", userid);
		Criterion sid = Restrictions.eq("storyid", storyid);
		LogicalExpression andexp = Restrictions.and(uid, sid);

		Likes l = (Likes) criteria.add(andexp).uniqueResult();

		t.commit();
		return l;
	}

	public static void create(long userid, long storyid) {
		int status = 1;

		Session ses = Hibernate.getSessionFactoryOfLike().openSession();
		Transaction t = ses.beginTransaction();

		Likes l = new Likes();
		l.setStoryid(storyid);
		l.setUserid(userid);
		l.setStatus(status);

		ses.save(l);
		t.commit();
	}
	
	 public static void delete(long userid, long storyid) {

		Session ses = Hibernate.getSessionFactoryOfLike().openSession();
		Transaction t = ses.beginTransaction();

		Criteria criteria = ses.createCriteria(Likes.class);
		Criterion sid = Restrictions.eq("storyid", storyid);
		Criterion uid = Restrictions.eq("userid", userid);
		
		LogicalExpression andexp = Restrictions.and(uid, sid);

		Likes l = (Likes) criteria.add(andexp).uniqueResult();
		
		ses.delete(l);
		t.commit();
	}

}
