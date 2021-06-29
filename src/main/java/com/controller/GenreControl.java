//$Id$
package com.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.object.Genre;
import com.service.GenreCRUD;
import com.service.Genre_userCRUD;

@Controller
public class GenreControl {
	
	@RequestMapping(value = "stories/{userid}/subscribedgenre", method = RequestMethod.GET)
	@ResponseBody
	public void subscribedgenreget(@PathVariable("userid") long userid, HttpServletRequest req, HttpServletResponse res) throws ClassNotFoundException, SQLException, IOException, ServletException {

		ArrayList<Genre> glist = GenreCRUD.read(userid , false);
		ArrayList<Genre> subglist = GenreCRUD.read(userid , true);

		Gson g = new Gson();
		String glistStr = g.toJson(glist);
		String subglistStr = g.toJson(subglist);
		
		JSONObject json = new JSONObject();
		json.put("genre", new JSONArray(glistStr));
		json.put("subgenre", new JSONArray(subglistStr));
		

		res.setContentType("application/json");
		res.getWriter().write(json.toString());
		return;
	}
	
	@RequestMapping(value = "stories/genre/{genreid}", method = RequestMethod.GET)
	@ResponseBody
	public void genrenameget(@PathVariable("genreid") int genreid, HttpServletRequest req, HttpServletResponse res) throws ClassNotFoundException, SQLException, IOException, ServletException {

		Genre g = GenreCRUD.read(genreid);

		Gson gson = new Gson();
		String gsonstring = gson.toJson(g);

		res.setContentType("application/json");
		res.getWriter().write(gsonstring);
		return;
	}
	
	@RequestMapping(value = "stories/subscribedgenre/{genreid}", method = RequestMethod.POST)
	@ResponseBody
	public void commentpost(@PathVariable("genreid") int genreid, HttpServletRequest req, HttpServletResponse res) throws ClassNotFoundException, SQLException, IOException, ServletException {

		HttpSession ses = req.getSession();
		long userid = (long) ses.getAttribute("userid");

		Genre_userCRUD.create(userid , genreid);
		
		Gson g = new Gson();
		String gsonstring = g.toJson("subscription posted");

		res.setContentType("application/json");
		res.getWriter().write(gsonstring);
		return;
	}
	
	@RequestMapping(value = "stories/subscribedgenre/{genre_userid}", method = RequestMethod.DELETE)
	@ResponseBody
	public void commentdelete(@PathVariable("genre_userid") long genre_userid, HttpServletRequest req, HttpServletResponse res) throws ClassNotFoundException, SQLException, IOException, ServletException {

		Genre_userCRUD.delete(genre_userid);

		res.setContentType("application/json");
		res.getWriter().write("subscription deleted");
		return;
	}
}
