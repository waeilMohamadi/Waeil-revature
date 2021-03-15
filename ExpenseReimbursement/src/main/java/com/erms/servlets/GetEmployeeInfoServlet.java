package com.erms.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.erms.models.User;

/**
 * Servlet implementation class GetEmployeeInfoServlet
 */
@WebServlet(urlPatterns = "/getEmployeeInfo", name = "getEmployeeInfoServlet")
public class GetEmployeeInfoServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (SessionManager.isLoggedIn(req, resp)) {
			// generateHomePage(resp);
			// resp.sendRedirect("home.html");
			// TODO: Add Authorisation check here
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();

			try {
				User currUser = SessionManager.currLoggedInUser(req);
				JSONObject obj = new JSONObject();
				obj.put("fname", currUser.getFirstName());
				obj.put("lname", currUser.getLastName());
				out.println(obj.toString());

			} catch (Exception e) {
				out.println(e.getMessage());
			}
			out.close();

		} else {
			resp.sendRedirect("login.html");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
