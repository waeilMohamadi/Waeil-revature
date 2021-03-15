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
 * Servlet implementation class UpdateEmployeeServlet
 */
@WebServlet(urlPatterns = "/updateEmployee", name = "updateEmployeeServlet")
@MultipartConfig
public class UpdateEmployeeServlet extends HttpServlet {
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
			String password = req.getParameter("password");

			// Part receipt = req.getPart("receipt");

			try {
				User currUser = SessionManager.currLoggedInUser(req);
				User user = new User();
				user.setId(currUser.getId());
				user.setFirstName(fname);
				user.setLastName(lname);
				user.setPassword(password);
				int res = DBManager.getInstance().updateUser(user);
				if (res == 1) {
					out.println("Successfully updated information");
				} else {
					out.println("Error in updating employee's information");
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
