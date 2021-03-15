package com.erms.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.erms.models.User;

public class SessionManager {

	public static boolean isLoggedIn(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		return (session != null && session.getAttribute("user") != null);
	}

	public static boolean isManager(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		return session.getAttribute("user") != null && user != null && "MANAGER".equals(user.getRole());
	}

	public static User currLoggedInUser(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		return user;
	}
}
