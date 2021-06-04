//$Id$
package com.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.object.Comment;
import com.service.CommentCRUD;

@Controller
public class CommentControl {

	@RequestMapping(value = "stories/{storyid}/comment", method = RequestMethod.GET)
	@ResponseBody
	public void commentget(@PathVariable("storyid") long storyid, HttpServletRequest req, HttpServletResponse res) throws ClassNotFoundException, SQLException, IOException, ServletException {

		ArrayList<Comment> clist = CommentCRUD.read(storyid);
		for (Comment c : clist) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(Long.parseLong(c.getUpdatetime()));
			c.setUpdatetime(new SimpleDateFormat("dd-MMM-yyyy  hh:mm aa").format(cal.getTime()));
		}
		Gson g = new Gson();
		String gsonstring = g.toJson(clist);

		res.setContentType("application/json");
		res.getWriter().write(gsonstring);
		return;
	}

	@RequestMapping(value = "stories/{storyid}/comment", method = RequestMethod.POST)
	@ResponseBody
	public void commentpost(@PathVariable("storyid") long storyid, HttpServletRequest req, HttpServletResponse res) throws ClassNotFoundException, SQLException, IOException, ServletException {

		long userid = Long.parseLong(req.getParameter("userid"));
		String name = req.getParameter("name");
		String comment = req.getParameter("comment");

		CommentCRUD.create(storyid, userid, name, comment);
		
		Gson g = new Gson();
		String gsonstring = g.toJson("comment posted");

		res.setContentType("application/json");
		res.getWriter().write(gsonstring);
		return;
	}

	@RequestMapping(value = "stories/{storyid}/comment", method = RequestMethod.DELETE)
	@ResponseBody
	public void commentdelete(@PathVariable("storyid") long storyid, HttpServletRequest req, HttpServletResponse res) throws ClassNotFoundException, SQLException, IOException, ServletException {

		CommentCRUD.delete(storyid);

		res.setContentType("application/json");
		res.getWriter().write("comment deleted");
		return;
	}
}
