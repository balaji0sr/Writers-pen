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
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.object.Inform;
import com.object.Story;
import com.service.StoryCRUD;

@Controller
public class StoriesGet {

	@RequestMapping(value = "/stories", method = RequestMethod.GET)
	@ResponseBody
	public void getstories(HttpServletRequest req, HttpServletResponse res) throws ClassNotFoundException, SQLException, IOException, ServletException {
		boolean moredata = true;
		
		HttpSession ses = req.getSession();

		long useridint = (long) ses.getAttribute("userid");
		long userid = Long.valueOf(useridint);

		int page = Integer.parseInt(req.getParameter("page"));
		int type = Integer.parseInt(req.getParameter("type"));
		
		String searchcontent = req.getParameter("searchcontent");
		String likedstories = req.getParameter("likedstories");
		String commentedstories = req.getParameter("searchcontent");
		String subscribedstories = req.getParameter("subscribedstories");
		

		ArrayList<Story> slist = StoryCRUD.read(page ,type, userid , searchcontent , likedstories , commentedstories , subscribedstories);

		if (slist.size() < (StoryCRUD.storyFetchLimit + 1) )
			moredata = false;
		else
			slist.remove(StoryCRUD.storyFetchLimit);
		
		for (Story s : slist) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(Long.parseLong(s.getUpdatetime()));
			s.setUpdatetime( new SimpleDateFormat("dd-MMM-yy  hh:mm aa" ).format(cal.getTime()));
		}

		Inform info = new Inform();
		info.setMoredata(moredata);
		info.setPage(page);

		Gson g = new Gson();
		String storyStr = g.toJson(slist);
		String infoStr = g.toJson(info);
		
		JSONObject json = new JSONObject();
		json.put("story", new JSONArray(storyStr));
		json.put("info", new JSONObject(infoStr));

		res.setContentType("application/json");
		res.getWriter().write(json.toString());
		return;

	}
}
