package com.erms.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.erms.DAO.DBManager;
import com.erms.models.User;

@WebServlet(urlPatterns = "/login", name = "loginServlet")
public class LoginServlet extends HttpServlet {

	private DBManager db = DBManager.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.sendRedirect("login.html");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String email = req.getParameter("email");
		String password = req.getParameter("password");

		try {
			User user = db.isValidUser(email, password);
			if (user != null) {
				HttpSession session = req.getSession(true);
				session.setAttribute("user", user);
				session.setMaxInactiveInterval(3000);
				resp.sendRedirect("home");
				// RequestDispatcher dispatcher = req.getRequestDispatcher("/home");
				// dispatcher.forward(req, resp);
			} else {
				resp.sendRedirect("login.html");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resp.sendRedirect("login.html");
		}
	}

}
