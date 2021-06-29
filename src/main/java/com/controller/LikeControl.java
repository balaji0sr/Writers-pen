//$Id$
package com.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.object.Likes;
import com.service.LikeCRUD;
import com.service.StoryCRUD;

@Controller
public class LikeControl {

	@RequestMapping(value = "/stories/{storyid}/like", method = RequestMethod.GET)
	public void likeget(@PathVariable("storyid") long storyid, HttpServletRequest req, HttpServletResponse res) throws ClassNotFoundException, SQLException, IOException, ServletException {

		HttpSession ses = req.getSession();
		long useridint = (long) ses.getAttribute("userid");
		long userid = Long.valueOf(useridint);

		Likes l = LikeCRUD.read(userid, storyid);

		Gson g = new Gson();
		String gsonstring = g.toJson(l);

		res.setContentType("application/json");
		res.getWriter().write(gsonstring);
		return;
	}

	@RequestMapping(value = "/stories/{storyid}/like", method = RequestMethod.POST)
	public void likepost(@PathVariable("storyid") long storyid, HttpServletRequest req, HttpServletResponse res) throws ClassNotFoundException, SQLException, IOException, ServletException {

		HttpSession ses = req.getSession();
		int useridint = (int) ses.getAttribute("userid");
		long userid = Long.valueOf(useridint);

		LikeCRUD.create(userid, storyid);
		StoryCRUD.update(storyid, true);

		res.setContentType("application/json");
		res.getWriter().write("success");
		return;
	}

	@RequestMapping(value = "/stories/{storyid}/like", method = RequestMethod.DELETE)
	public void likedelete(@PathVariable("storyid") long storyid, HttpServletRequest req, HttpServletResponse res) throws ClassNotFoundException, SQLException, IOException, ServletException {

		HttpSession ses = req.getSession();
		int useridint = (int) ses.getAttribute("userid");
		long userid = Long.valueOf(useridint);

		LikeCRUD.delete(userid, storyid);
		StoryCRUD.update(storyid, false);

		res.setContentType("application/json");
		res.getWriter().write("successfully deleted");
		return;
	}
}
