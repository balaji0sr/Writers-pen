//$Id$
package com.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.object.Comment;

public class CommentCRUD {
	public static void create(long storyid, long userid, String name ,  String comment) {

		long updatetime = System.currentTimeMillis();
		String updatetimestr = String.valueOf(updatetime);
		
		 Comment c = new Comment();
		 
		 c.setStoryid(storyid);
		 c.setUserid(userid);
		 c.setName(name);
		 c.setComment(comment);
		 c.setUpdatetime(updatetimestr);

		Session ses = Hibernate.getSessionFactoryOfComment().openSession();
		Transaction t = ses.beginTransaction();
		ses.save(c);

		t.commit();
	}

	public static ArrayList<Comment> read(long storyid) {

		Session ses = Hibernate.getSessionFactoryOfComment().openSession();
		Transaction t = ses.beginTransaction();

		Criteria criteria = ses.createCriteria(Comment.class);
		ArrayList<Comment> clist = (ArrayList<Comment>) criteria.add(Restrictions.eq("storyid", storyid)).list();

		t.commit();
		return clist;
	}
	
	
	public static void delete(long commentid) throws ClassNotFoundException, SQLException, IOException {

		Session ses = Hibernate.getSessionFactoryOfComment().openSession();
		Transaction t = ses.beginTransaction();
		Comment c = (Comment) ses.load(Comment.class, commentid);
		ses.delete(c);
		t.commit();
	}

}
