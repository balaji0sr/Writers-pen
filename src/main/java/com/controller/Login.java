package com.controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.object.Users;
import com.service.Verification;

@Controller
public class Login {

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public void login(HttpServletRequest req, HttpServletResponse res) throws ClassNotFoundException, SQLException, IOException, ServletException {
		
		String mail = req.getParameter("mail");
		String pas = req.getParameter("pas");

		Users u = Verification.verifylogin(mail, pas);

		String str = "Home.jsp";

		if (u == null) {
			str = "mailid didn't match , verify mail or create new user";
		} else {				
			if (u.getPas().equals(pas)) {				
				HttpSession ses = req.getSession();
				ses.setAttribute("userid", u.getUserid());
				ses.setAttribute("mail" , u.getMail());
				ses.setAttribute("name" , u.getName());
			}
			else {
				str = "Password didn't match with the E-mail";
			}
		}
		res.setContentType("text/plain");
		res.getWriter().write(str);
		return;
	}
}
