//$Id$
package com.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.net.ssl.SSLContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.object.Story;
import com.service.StoryCRUD;

@Controller
public class Storycontrol {

	@RequestMapping(value = "/stories/{storyid}", method = RequestMethod.GET)
	public void storyView(@PathVariable("storyid") long storyid, HttpServletRequest req, HttpServletResponse res) throws ClassNotFoundException, SQLException, IOException, ServletException {

		Story s = StoryCRUD.read(storyid);

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(Long.parseLong(s.getUpdatetime()));
		s.setUpdatetime(new SimpleDateFormat("dd-MMM-yy  hh:mm aa").format(cal.getTime()));

		cal.setTimeInMillis(Long.parseLong(s.getModifiedtime()));
		s.setModifiedtime(new SimpleDateFormat("dd-MMM-yy  hh:mm aa").format(cal.getTime()));

		Gson g = new Gson();
		String gsonstring = g.toJson(s);

		res.setContentType("application/json");
		res.getWriter().write(gsonstring);

		return;
	}

	@RequestMapping(value = "/stories", method = RequestMethod.POST)
	@ResponseBody
	public void upload(HttpServletRequest req, HttpServletResponse res) throws ClassNotFoundException, SQLException, IOException, ServletException {

		System.out.println(req.getParameterMap().size());
		long userid = Long.parseLong(req.getParameter("userid"));
		String username = req.getParameter("username");
		String title = req.getParameter("title");
		int type = Integer.parseInt(req.getParameter("type"));
		long parentstoryid = Long.parseLong(req.getParameter("parentstoryid"));

		String story = req.getParameter("encodeedcontent");
		String decodedStory = URLDecoder.decode(story);

		String contenttext = req.getParameter("contenttext");

		StoryCRUD.create(userid, username, title, decodedStory, type, parentstoryid, contenttext);

		String s = "success " + userid;
		res.setContentType("text/plain");
		res.getWriter().write(s);

		return;
	}

	
	

	
	@RequestMapping(value = "/stories", method = RequestMethod.PUT)
	public void storyupdate(HttpServletRequest req, HttpServletResponse res) throws ClassNotFoundException, SQLException, IOException, ServletException {

		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = req.getInputStream();

			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

				char[] charBuffer = new char[128];
				int bytesRead = -1;

				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			System.out.println("Error reading the request body...");
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					System.out.println("Error closing bufferedReader...");
				}
			}
		}
		JSONObject jsonobj = new JSONObject(stringBuilder.toString());
		System.out.println(jsonobj);

		int storyid = jsonobj.getInt("storyid");
		String title = jsonobj.getString("title");
		String draftcontent = jsonobj.getString("encodeedcontent");
		String decodeddraft = URLDecoder.decode(draftcontent);

		StoryCRUD.update(storyid, title, decodeddraft);
		return;
	}

	@RequestMapping(value = "/stories/{storyid}", method = RequestMethod.DELETE)
	public void storydelete(@PathVariable("storyid") long storyid, HttpServletRequest req, HttpServletResponse res) throws ClassNotFoundException, SQLException, IOException, ServletException {

		StoryCRUD.delete(storyid);

		res.setContentType("application/json");
		res.getWriter().write("deleted successfully");

		return;
	}
}
