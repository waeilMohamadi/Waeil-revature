package com.erms.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.erms.DAO.DBManager;
import com.erms.models.User;

/**
 * Servlet implementation class AddEmployeeServlet
 */
@WebServlet(urlPatterns = "/addEmployee", name = "addEmployeeServlet")
@MultipartConfig
public class AddEmployeeServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (SessionManager.isLoggedIn(req, resp)) {
			// generateHomePage(resp);
			// resp.sendRedirect("home.html");
			// TODO: Add Authorisation check here
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();

			String fname = req.getParameter("fname");
			String lname = req.getParameter("lname");
			String email = req.getParameter("email");
			String role = req.getParameter("role");
			String password = req.getParameter("password");

			// Part receipt = req.getPart("receipt");

			try {
				if (SessionManager.isManager(req)) {
					User currUser = SessionManager.currLoggedInUser(req);
					User user = new User();
					user.setFirstName(fname);
					user.setLastName(lname);
					user.setEmail(email);
					user.setManagerId(currUser.getId());
					user.setManagerName(currUser.getManagerName());
					user.setPassword(password);
					user.setRole(role);
					int res = DBManager.getInstance().registerUser(user, false);
					if (res == 1) {
						out.println("Successfully registered new employee");
					} else {
						out.println("Error in registering new employee");
					}
				} else {
					out.println("Error: Not Authorized to perform this operation.");
				}
			} catch (Exception e) {
				out.println(e.getMessage());
			}
			out.close();

		} else {
			resp.sendRedirect("login");
		}
	}
}
