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
 * Servlet implementation class UpdateEmployeeRequestServlet
 */
@WebServlet(urlPatterns = "/updateEmployeeRequest", name = "updateEmployeeRequestServlet")
@MultipartConfig
public class UpdateEmployeeRequestServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (SessionManager.isLoggedIn(req, resp)) {
			// generateHomePage(resp);
			// resp.sendRedirect("home.html");
			// TODO: Add Authorisation check here
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();

			String idStr = req.getParameter("id");
			String status = req.getParameter("status");

			String action = "DENIED";
			// Part receipt = req.getPart("receipt");
			if ("APPROVED".equalsIgnoreCase(status)) {
				action = "APPROVED";
			}

			try {
				if (SessionManager.isManager(req)) {
					User currUser = SessionManager.currLoggedInUser(req);
					int id = Integer.parseInt(idStr);
					int res = DBManager.getInstance().updateEmployeeRequest(id, currUser.getId(), action);
					if (res == 1) {
						out.println("Successfully updated request status");
					} else {
						out.println("Error in updating request status");
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
