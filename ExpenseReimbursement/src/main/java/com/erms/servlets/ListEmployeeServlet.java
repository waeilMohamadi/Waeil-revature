package com.erms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.erms.DAO.DBManager;
import com.erms.models.User;
import com.google.gson.Gson;

/**
 * Servlet implementation class ListEmployeeServlet
 */
@WebServlet(urlPatterns = "/listEmployees", name = "listEmployeeServlet")
public class ListEmployeeServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (SessionManager.isLoggedIn(req, resp)) {
			// generateHomePage(resp);
			// resp.sendRedirect("home.html");
			// TODO: Add Authorisation check here
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();

			try {
				if (SessionManager.isManager(req)) {

					User currUser = SessionManager.currLoggedInUser(req);
					List<User> lstUsers = DBManager.getInstance().getAllEmployees(currUser.getId());
					JSONObject users = new JSONObject();
					JSONArray userArray = new JSONArray();
					Gson gson = new Gson();
					// UserList users = new UserList();
					// users.setUsers(lstUsers);
					for (User user : lstUsers) {
						JSONObject userObj = new JSONObject();
						userObj.put("id", user.getId());
						userObj.put("fname", user.getFirstName());
						userObj.put("lname", user.getLastName());
						userObj.put("role", user.getRole());
						userObj.put("email", user.getEmail());
						userObj.put("manager", user.getManagerName());
						userArray.put(userObj);
					}
					// String json = gson.toJson(users);
					users.put("users", userArray);
					out.println(users.toString());
				} else {
					out.println("Error: Not Authorized to perform this operation.");
				}
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
