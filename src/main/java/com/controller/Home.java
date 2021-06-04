//$Id$
package com.controller;

import java.io.IOException;import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class Home {
	
	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest req, HttpServletResponse res)
			throws ClassNotFoundException, SQLException, IOException, ServletException {

		System.out.println("home.java");

		ModelAndView mv = new ModelAndView();

			
			mv.setViewName("Home.jsp");
		return mv;
	}

}
