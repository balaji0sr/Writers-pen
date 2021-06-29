//$Id$
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
import com.service.UsersCRUD;
import com.service.Verification;

@Controller
public class Signup {
	@RequestMapping(value = "/createuser", method = RequestMethod.POST)
	@ResponseBody
	public void signup(HttpServletRequest req, HttpServletResponse res) throws ClassNotFoundException, SQLException, IOException, ServletException {
		String str = "Home.jsp";

		String name = req.getParameter("name");
		String mail = req.getParameter("mail");
		String pas = req.getParameter("pas");

		if (Verification.verifylogin(mail, pas) == null) {
			Users u = UsersCRUD.create(name, mail, pas);
			long userid  = UsersCRUD.read(mail);

			System.out.println("session get attribute");
			HttpSession ses = req.getSession();
			ses.setAttribute("mail", mail);
			ses.setAttribute("name", name);
			ses.setAttribute("userid", u.getUserid());
			
		} else {
			str = "account already exist in this e-mail";
		}
		res.setContentType("text/plain");
		res.getWriter().write(str);
		return;
	}
}
