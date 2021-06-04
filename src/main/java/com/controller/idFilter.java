package com.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class idFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (canProceed(request.getRequestURI())) {
			HttpSession ses = request.getSession();

			if (ses.getAttribute("mail") != null) {
				chain.doFilter(req, res);
			} else {
				response.sendRedirect("/WritersPen/Login.jsp");
			}
		} else {
			chain.doFilter(req, res);
		}

	}

	private static boolean canProceed(String uri) {
		ArrayList<String> excludeList = new ArrayList<>();
		excludeList.add(".js");
		excludeList.add(".css");
		excludeList.add("Login.jsp");
		excludeList.add("check");
		excludeList.add("createuser");
		excludeList.add("Logout.jsp");

		for (String el : excludeList) {
			if (uri.endsWith(el)) {
				return false;
			}
		}
		return true;
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
