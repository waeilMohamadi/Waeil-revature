package com.erms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.erms.DAO.DBManager;
import com.erms.models.Receipt;
import com.erms.models.User;
import com.google.gson.Gson;

/**
 * Servlet implementation class ListEmployeesPendingRequests
 */
@WebServlet(urlPatterns = "/listEmployeesPendingRequests", name = "listEmpPendingRequestsServlet")
public class ListEmployeesPendingRequests extends HttpServlet {
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
					List<Receipt> lstUsers = DBManager.getInstance().getEmployeeRequests(currUser.getId(), "PENDING",
							true);
					JSONObject users = new JSONObject();
					JSONArray userArray = new JSONArray();
					Gson gson = new Gson();
					// UserList users = new UserList();
					// users.setUsers(lstUsers);
					for (Receipt user : lstUsers) {
						JSONObject userObj = new JSONObject();
						userObj.put("id", user.getId());
						userObj.put("username", user.getEmployeeName());
						userObj.put("title", user.getTitle());
						userObj.put("description", user.getDescription());
						userObj.put("manager", user.getManager());
						userObj.put("amount", user.getAmount());
						userObj.put("receipt", Base64.getEncoder().encodeToString(user.getReceipt()));
						userObj.put("status", user.getStatus());
						userArray.put(userObj);
					}
					// String json = gson.toJson(users);
					users.put("receipts", userArray);
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
