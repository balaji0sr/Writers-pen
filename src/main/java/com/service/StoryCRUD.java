//$Id$
package com.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.object.Story;

public class StoryCRUD {

	public static final int storyFetchLimit = 5;

	public static void create(long userid, String username, String title, String content, int type, long parentstoryid, String contenttext) {

		long updatetime = System.currentTimeMillis();
		long modifiedtime = System.currentTimeMillis();

		String updatetimestr = String.valueOf(updatetime);
		String modifiedtimestr = String.valueOf(modifiedtime);

		long genreid = 101;
		long likecount = 0;

		Story s = new Story();
		s.setUserid(userid);
		s.setUsername(username);
		s.setTitle(title);
		s.setContent(content);
		s.setType(type);
		s.setParentstoryid(parentstoryid);
		s.setUpdatetime(updatetimestr);
		s.setModifiedtime(modifiedtimestr);
		s.setGenreid(genreid);
		s.setLikecount(likecount);
		s.setContenttext(contenttext);

		Session ses = Hibernate.getSessionFactory().openSession();
		Transaction t = ses.beginTransaction();
		ses.save(s);

		t.commit();
	}

	public static ArrayList<Story> read(int page, int type, long userid, String searchcontent, String likedstories, String commentedstories) {

		int firstelement = (page - 1) * storyFetchLimit;
		int lastelemant = storyFetchLimit + 1;

		Session ses = Hibernate.getSessionFactory().openSession();
		Transaction t = ses.beginTransaction();
		Criteria criteria = ses.createCriteria(Story.class);

		if (likedstories.equals("true") || commentedstories.equals("true")) {

			String querystr = "";
			if (likedstories.equals("true")) {
				querystr = "SELECT storyid, userid, username, title, content, type, parentstoryid, updatetime, modifiedtime, genreid, likecount, viewcount, contenttext FROM Likes l left join Story s where l.storyid = s.storyid and l.userid = " + userid;
			}
			if (commentedstories.equals("true")) {
			}
			Query query = ses.createQuery(querystr);
			query.setFirstResult(firstelement);
			query.setMaxResults(lastelemant);
			ArrayList<Story> slist = (ArrayList<Story>) query.list();
			t.commit();
			return slist;
		}

		if (type == 0) {
			type = 1;
			if (searchcontent != "") {
				Criterion con = Restrictions.like("contenttext", searchcontent, MatchMode.ANYWHERE);
				Criterion tit = Restrictions.like("title", searchcontent, MatchMode.ANYWHERE);
				Criterion typ = Restrictions.eq("type", type);

				Criterion expandor = Restrictions.and(Restrictions.or(con, tit), typ);
				criteria.add(expandor);
			} else {
				criteria.add(Restrictions.eq("type", type));
				criteria.addOrder(Order.desc("updatetime"));
			}
		} else {
			Criterion con = Restrictions.like("contenttext", searchcontent, MatchMode.ANYWHERE);
			Criterion tit = Restrictions.like("title", searchcontent, MatchMode.ANYWHERE);

			Criterion uid = Restrictions.eq("userid", userid);
			Criterion typ = Restrictions.eq("type", type);

			if (searchcontent != null) {
				Criterion expandor = Restrictions.and(Restrictions.or(con, tit), typ, uid);
				criteria.add(expandor);
			} else {
				LogicalExpression andexp = Restrictions.and(uid, typ);
				criteria.add(andexp);
			}
		}

		criteria.setFirstResult(firstelement);
		criteria.setMaxResults(lastelemant);

		ArrayList<Story> slist = (ArrayList<Story>) criteria.list();
		t.commit();
		return slist;

	}

	public static Story read(long storyid) throws ClassNotFoundException, SQLException, IOException {

		Session ses = Hibernate.getSessionFactory().openSession();
		Transaction t = ses.beginTransaction();

		Criteria criteria = ses.createCriteria(Story.class);
		Story s = (Story) criteria.add(Restrictions.eq("storyid", storyid)).uniqueResult();

		long viewcount = s.getViewcount();
		viewcount++;
		s.setViewcount(viewcount);
		// ses.update(s);

		t.commit();
		return s;
	}

	public static void update(long storyid, String title, String content) {

		long modifiedtime = System.currentTimeMillis();
		String modifiedtimestr = String.valueOf(modifiedtime);

		Session ses = Hibernate.getSessionFactory().openSession();
		Transaction t = ses.beginTransaction();

		Story s = (Story) ses.get(Story.class, storyid);

		if (s.getTitle().equals(title) == false)
			s.setTitle(title);

		if (s.getContent().equals(content) == false)
			s.setContent(content);

		s.setModifiedtime(modifiedtimestr);

		ses.update(s);
		t.commit();
	}

	public static void update(long storyid, boolean upordown) {

		Session ses = Hibernate.getSessionFactory().openSession();
		Transaction t = ses.beginTransaction();

		Story s = (Story) ses.get(Story.class, storyid);

		long likecount = s.getLikecount();
		if (upordown) {
			likecount++;
		} else {
			likecount--;
		}
		s.setLikecount(likecount);

		ses.update(s);
		t.commit();
	}

	public static void delete(long storyid) throws ClassNotFoundException, SQLException, IOException {

		Session ses = Hibernate.getSessionFactory().openSession();
		Transaction t = ses.beginTransaction();
		Story s = (Story) ses.load(Story.class, storyid);
		ses.delete(s);
		t.commit();
	}
}
