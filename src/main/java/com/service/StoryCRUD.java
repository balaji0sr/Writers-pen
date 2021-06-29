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
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.object.Comment;
import com.object.Genre;
import com.object.Genre_user;
import com.object.Likes;
import com.object.Story;

public class StoryCRUD {

	public static final int storyFetchLimit = 5;

	public static void create(long userid, String username, String title, String content, int type, long parentstoryid, String contenttext, String genrename) {

		long updatetime = System.currentTimeMillis();
		long modifiedtime = System.currentTimeMillis();

		String updatetimestr = String.valueOf(updatetime);
		String modifiedtimestr = String.valueOf(modifiedtime);

		int genreid = getgenreid(genrename);
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

	public static ArrayList<Story> read(int page, int type, long userid, String searchcontent, String likedstories, String commentedstories, String subscribedstories) {

		int firstelement = (page - 1) * storyFetchLimit;
		int lastelemant = storyFetchLimit + 1;

		Session ses = Hibernate.getSessionFactory().openSession();
		Transaction t = ses.beginTransaction();

		if (subscribedstories.equals("true")) {
			ArrayList<Genre_user> gulist = getsubscribedstorys(userid);
			int listsize = gulist.size();
			if (listsize > 0) {
				String hql = "Select storyid, userid, username, title, content, type, parentstoryid, updatetime, modifiedtime, genreid, likecount, viewcount, contenttext FROM Story WHERE ";
				for (int i = 0; i < listsize; i++) {
					int genreid = gulist.get(i).getGenreid();
					hql = hql + "genreid = " + genreid;
					if (i == listsize - 1) {
						break;
					}
					hql = hql + " or ";
				}
				hql = hql + " ORDER BY updatetime DESC limit " + firstelement + "," + lastelemant;

				System.out.println(hql);
				Query query = ses.createQuery(hql);
				List<Object[]> list =(List<Object[]>) query.list();
				t.commit();
				ArrayList<Story> slist = ObjectListToStoryList.change(list);
				return slist;
			}
		}

		Criteria criteria = ses.createCriteria(Story.class);

		if (likedstories.equals("true")) {

			ArrayList<Likes> llist = getlikedstorys(userid);
			int listsize = llist.size();
			if (listsize > 0) {
				String hql = "Select storyid, userid, username, title, content, type, parentstoryid, updatetime, modifiedtime, genreid, likecount, viewcount, contenttext FROM Story WHERE ";
				for (int i = 0; i < listsize; i++) {
					long storyid = llist.get(i).getStoryid();
					hql = hql + "storyid = " + storyid;
					if (i == listsize - 1) {
						break;
					}
					hql = hql + " or ";
				}
				hql = hql + " ORDER BY updatetime DESC limit " + firstelement + "," + lastelemant;

				System.out.println(hql);
				Query query = ses.createQuery(hql);
				List<Object[]> list =(List<Object[]>) query.list();
				t.commit();
				ArrayList<Story> slist = ObjectListToStoryList.change(list);
				return slist;
			}
		}
		
		if (commentedstories.equals("true")) {

			ArrayList<Comment> clist = getcommentedstorys(userid);
			int listsize = clist.size();
			if (listsize > 0) {
				String hql = "Select storyid, userid, username, title, content, type, parentstoryid, updatetime, modifiedtime, genreid, likecount, viewcount, contenttext FROM Story WHERE ";
				for (int i = 0; i < listsize; i++) {
					long storyid = clist.get(i).getStoryid();
					hql = hql + "storyid = " + storyid;
					if (i == listsize - 1) {
						break;
					}
					hql = hql + " or ";
				}
				hql = hql + " ORDER BY updatetime DESC limit " + firstelement + "," + lastelemant;

				System.out.println(hql);
				Query query = ses.createQuery(hql);
				List<Object[]> list =(List<Object[]>) query.list();
				t.commit();
				ArrayList<Story> slist = ObjectListToStoryList.change(list);
				return slist;
			}
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

	public static int getgenreid(String genrename) {
		System.out.println(genrename);

		Session ses = Hibernate.getSessionFactoryOfGenre().openSession();
		Transaction t = ses.beginTransaction();

		Criteria criteria = ses.createCriteria(Genre.class);
		Genre g = (Genre) criteria.add(Restrictions.eq("genrename", genrename)).uniqueResult();
		t.commit();
		System.out.println(g.getGenreid());

		return g.getGenreid();
	}

	public static ArrayList<Genre_user> getsubscribedstorys(long userid) {

		Session ses = Hibernate.getSessionFactoryOfGenre_user().openSession();
		Transaction t = ses.beginTransaction();

		Criteria criteria = ses.createCriteria(Genre_user.class);
		ArrayList<Genre_user> gulist = (ArrayList<Genre_user>) criteria.add(Restrictions.eq("userid", userid)).list();
		t.commit();

		return gulist;
	}
	
	public static ArrayList<Likes> getlikedstorys(long userid) {

		System.out.println("userid " + userid);
		Session ses = Hibernate.getSessionFactoryOfLike().openSession();
		Transaction t = ses.beginTransaction();

		Criteria criteria = ses.createCriteria(Likes.class);
		ArrayList<Likes> llist = (ArrayList<Likes>) criteria.add(Restrictions.eq("userid", userid)).list();
		t.commit();

		return llist;
	}
	
	public static ArrayList<Comment> getcommentedstorys(long userid) {

		System.out.println("userid " + userid);
		Session ses = Hibernate.getSessionFactoryOfComment().openSession();
		Transaction t = ses.beginTransaction();

		Criteria criteria = ses.createCriteria(Comment.class);
		ArrayList<Comment> clist = (ArrayList<Comment>) criteria.add(Restrictions.eq("userid", userid)).list();
		t.commit();

		return clist;
	}

}
