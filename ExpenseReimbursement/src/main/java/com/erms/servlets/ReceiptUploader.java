package com.erms.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import com.erms.DAO.DBManager;
import com.erms.models.Receipt;
import com.erms.models.User;

/**
 * Servlet implementation class ReceiptUploader
 */
@WebServlet(urlPatterns = "/uploadReceipt", name = "uploadReceiptServlet")
@MultipartConfig
public class ReceiptUploader extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (SessionManager.isLoggedIn(req, resp)) {
			// generateHomePage(resp);
			// resp.sendRedirect("home.html");
			// TODO: Add Authorisation check here
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();

			try {

				float amount = Float.parseFloat(req.getParameter("amount"));
				String description = req.getParameter("description");
				String title = req.getParameter("title");

				Part image = req.getPart("receipt");

				Receipt receipt = new Receipt();

				User user = SessionManager.currLoggedInUser(req);
				receipt.setManagerId(user.getManagerId());
				receipt.setEmployeeId(user.getId());
				receipt.setEmployeeName(user.getManagerName());
				receipt.setManager(user.getManagerName());
				receipt.setTitle(title);
				receipt.setStatus("PENDING");
				receipt.setDescription(description);
				receipt.setAmount(amount);
				receipt.setReceipt(IOUtils.toByteArray(image.getInputStream()));

				int res = DBManager.getInstance().registerReceipt(receipt);
				if (res == 1) {
					out.println("Successfully registered new reimbursement request");
				} else {
					out.println("Error in registering new reimbursement");
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
