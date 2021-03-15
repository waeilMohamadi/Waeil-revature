package com.erms.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet(urlPatterns = "/home", name = "homeServlet")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (SessionManager.isLoggedIn(req, resp)) {
			//generateHomePage(resp);
			try {
				if (SessionManager.isManager(req)) {
					//resp.sendRedirect("home_admin.html");
					generateHomePageAdmin(resp);
				} else {
					generateHomePage(resp);
					//resp.sendRedirect("home.html");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				resp.sendRedirect("login.html");
			}
		} else {
			resp.sendRedirect("login.html");
		}
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    
    private void generateHomePage(HttpServletResponse res) throws IOException{
    	res.setContentType("text/html");
    	PrintWriter out = res.getWriter();
    	out.println("<!DOCTYPE html>\n" + 
    			"<html lang=\"en\">\n" + 
    			"<head>\n" + 
    			"<meta charset=\"UTF-8\">\n" + 
    			"<title>Home</title>\n" + 
    			"\n" + 
    			"<script\n" + 
    			"	src=\"//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>\n" + 
    			"\n" + 
    			"<link\n" + 
    			"	href=\"//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css\"\n" + 
    			"	rel=\"stylesheet\" id=\"bootstrap-css\">\n" + 
    			"<link href=\"css/main.css\" rel=\"stylesheet\">\n" + 
    			"\n" + 
    			"<script\n" + 
    			"	src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js\"></script>\n" + 
    			"\n" + 
    			"<script\n" + 
    			"	src=\"//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js\"></script>\n" + 
    			"\n" + 
    			"<script src=\"js/main.js\"></script>\n" + 
    			"\n" + 
    			"</head>\n" + 
    			"<body>\n" + 
    			"\n" + 
    			"	<nav class=\"navbar navbar-expand-sm bg-light fixed-top\">\n" + 
    			"\n" + 
    			"		<!-- Links -->\n" + 
    			"		<ul class=\"navbar-nav\">\n" + 
    			"			<li class=\"nav-item\"><a class=\"navbar-brand\" href=\"#\">Expense\n" + 
    			"					Reimbursement System</a></li>\n" + 
    			"			<div class=\"collapse navbar-collapse\" id=\"navbarNav\">\n" + 
    			"				<ul class=\"nav navbar-nav ml-auto\">\n" + 
    			"					<li class=\"nav-item active\"><a class=\"nav-link\" href=\"logout\">Logout</a>\n" + 
    			"					</li>\n" + 
    			"				</ul>\n" + 
    			"			</div>\n" + 
    			"		</ul>\n" + 
    			"\n" + 
    			"	</nav>\n" + 
    			"\n" + 
    			"	<!-- Side navigation -->\n" + 
    			"	<div class=\"sidenav\">\n" + 
    			"		<a href=\"#\" onclick=\"return onHomeClick();\">Home</a> \n" + 
    			"		<a href=\"#\" onclick=\"return onProfileClick();\">Profile</a>\n" + 
    			"	</div>\n" + 
    			"\n" + 
    			"	<!-- Page content -->\n" + 
    			"	<div class=\"main\">\n" + 
    			"\n" + 
    			"		<!-- alert alert-danger -->\n" + 
    			"		<div id=\"alert_div\" style=\"display: none\">\n" + 
    			"			<div class=\"alert alert-success alert-dismissible\">\n" + 
    			"				<button type=\"button\" class=\"close\" onclick=\"return closeAlert();\">&times;</button>\n" + 
    			"				<strong id=\"alert_msg\"></strong>\n" + 
    			"			</div>\n" + 
    			"		</div>\n" + 
    			"\n" + 
    			"		<div id=\"home\">\n" + 
    			"\n" + 
    			"			<div class=\"container\">\n" + 
    			"	\n" + 
    			"				<div class=\"row\">\n" + 
    			"					<div class=\"col-sm\">\n" + 
    			"						<div class=\"card card_bg\">\n" + 
    			"							<div class=\"card-body text-center\">\n" + 
    			"								<h5 class=\"card-title\">\n" + 
    			"									<a href=\"#\" class=\"card-link\"\n" + 
    			"										onclick=\"return onAddReimbursmentClick();\">New\n" + 
    			"										Reimbursement Form</a>\n" + 
    			"								</h5>\n" + 
    			"							</div>\n" + 
    			"						</div>\n" + 
    			"					</div>\n" + 
    			"					<div class=\"col-sm\">\n" + 
    			"						<div class=\"card card_bg\">\n" + 
    			"							<div class=\"card-body text-center\">\n" + 
    			"								<h5 class=\"card-title\">\n" + 
    			"									<a href=\"#\" class=\"card-link\"\n" + 
    			"										onclick=\"return onViewPendingRequestsClick();\">My Pending\n" + 
    			"										Reimbursement Requests</a>\n" + 
    			"								</h5>\n" + 
    			"							</div>\n" + 
    			"						</div>\n" + 
    			"					</div>\n" + 
    			"				</div>\n" + 
    			"\n" + 
    			"				<br> <br>\n" + 
    			"				<div class=\"row\">\n" + 
    			"					<div class=\"col-sm\">\n" + 
    			"						<div class=\"card card_bg\">\n" + 
    			"							<div class=\"card-body text-center\">\n" + 
    			"								<h5 class=\"card-title\">\n" + 
    			"									<a href=\"#\" class=\"card-link\"\n" + 
    			"										onclick=\"return onViewResolvedRequestsClick();\">My Resolved\n" + 
    			"										Reimbursement Requests</a>\n" + 
    			"								</h5>\n" + 
    			"							</div>\n" + 
    			"						</div>\n" + 
    			"					</div>\n" + 
    			"		\n" + 
    			"				</div>\n" + 
    			"			</div>\n" + 
    			"		</div>\n" + 
    			"\n" + 
    			"		<!-- Add Receipt  -->\n" + 
    			"\n" + 
    			"		<div id=\"addReceipt\" style=\"display: none\">\n" + 
    			"			<h3>Reimbursement Request</h3>\n" + 
    			"			<div>\n" + 
    			"				<form id=\"reimbursementform\" onsubmit=\"return submitReceipt()\"\n" + 
    			"					enctype=\"multipart/form-data\">\n" + 
    			"					<div class=\"form-group\">\n" + 
    			"						<label for=\"titleReceipt\">Title</label> <input type=\"text\"\n" + 
    			"							class=\"form-control\" name=\"titleReceipt\"\n" + 
    			"							aria-describedby=\"titleHelp\" placeholder=\"Enter Receipt Heading\">\n" + 
    			"						<small id=\"titelHelp\" class=\"form-text text-muted\"></small>\n" + 
    			"					</div>\n" + 
    			"					<div class=\"form-group\">\n" + 
    			"						<label for=\"description\">Description</label> <input type=\"text\"\n" + 
    			"							class=\"form-control\" name=\"description\"\n" + 
    			"							aria-describedby=\"descriptionHelp\"\n" + 
    			"							placeholder=\"Enter Description\"> <small\n" + 
    			"							id=\"descriptionHelp\" class=\"form-text text-muted\"></small>\n" + 
    			"					</div>\n" + 
    			"					<div class=\"form-group\">\n" + 
    			"						<label for=\"amount\">Amount</label> <input type=\"number\"\n" + 
    			"							class=\"form-control\" id=\"amount\" name=\"amount\"\n" + 
    			"							aria-describedby=\"emailHelp\" placeholder=\"Enter amount\">\n" + 
    			"						<small id=\"amountHelp\" class=\"form-text text-muted\"></small>\n" + 
    			"					</div>\n" + 
    			"\n" + 
    			"					<div class=\"form-group\">\n" + 
    			"						<label for=\"receipt\">Receipt</label> <input id=\"receipt\"\n" + 
    			"							name=\"receipt\" type=\"file\" class=\"form-control\" id=\"receipt\">\n" + 
    			"					</div>\n" + 
    			"					<button class=\"btn btn-primary\" onclick=\"return onBack();\">Back</button>\n" + 
    			"					<button type=\"submit\" class=\"btn btn-primary\">Submit</button>\n" + 
    			"				</form>\n" + 
    			"			</div>\n" + 
    			"		</div>\n" + 
    			"\n" + 
    			"\n" + 
    			"		<!--  -->\n" + 
    			"		\n" + 
    			"		<!-- View Receipts Starts -->\n" + 
    			"		\n" + 
    			"		<div id=\"viewPendingReceipts\" style=\"display: none\">\n" + 
    			"			<h3>Pending Requests</h3>\n" + 
    			"			<div>\n" + 
    			"				<table class=\"table\">\n" + 
    			"					<thead>\n" + 
    			"						<tr>\n" + 
    			"							<th scope=\"col\">#</th>\n" + 
    			"							<th scope=\"col\">Title</th>\n" + 
    			"							<th scope=\"col\">Description</th>\n" + 
    			"							<th scope=\"col\">Manager Name</th>\n" + 
    			"							<th scope=\"col\">Amount</th>\n" + 
    			"							<th scope=\"col\">Status</th>\n" + 
    			"							<th scope=\"col\">Receipt</th>\n" + 
    			"						</tr>\n" + 
    			"					</thead>\n" + 
    			"					<tbody id=\"pending_receipts_data\">\n" + 
    			"\n" + 
    			"					</tbody>\n" + 
    			"				</table>\n" + 
    			"\n" + 
    			"			</div>\n" + 
    			"		</div>	\n" + 
    			"		\n" + 
    			"		<!-- View Receipts Ends -->\n" + 
    			"		\n" + 
    			"		<div id=\"viewResolvedReceipts\" style=\"display: none\">\n" + 
    			"			<h3>Resolved Requests</h3>\n" + 
    			"			<div>\n" + 
    			"\n" + 
    			"				<table class=\"table\">\n" + 
    			"					<thead>\n" + 
    			"						<tr>\n" + 
    			"							<th scope=\"col\">#</th>\n" + 
    			"							<th scope=\"col\">Title</th>\n" + 
    			"							<th scope=\"col\">Description</th>\n" + 
    			"							<th scope=\"col\">Manager Name</th>\n" + 
    			"							<th scope=\"col\">Amount</th>\n" + 
    			"							<th scope=\"col\">Status</th>\n" + 
    			"							<th scope=\"col\">Receipt</th>\n" + 
    			"						</tr>\n" + 
    			"					</thead>\n" + 
    			"					<tbody id=\"resolved_receipts_data\">\n" + 
    			"\n" + 
    			"					</tbody>\n" + 
    			"				</table>\n" + 
    			"\n" + 
    			"			</div>\n" + 
    			"		</div>	\n" + 
    			"		\n" + 
    			"		<!-- View Receipts Ends -->\n" + 
    			"		\n" + 
    			"		<!-- The Modal -->\n" + 
    			"		<div id=\"modal\" class=\"modal\" style=\"display: none\">\n" + 
    			"			<!-- Modal content -->\n" + 
    			"			<div class=\"modal-content\">\n" + 
    			"				<span class=\"close\" onclick=\"hideModal()\">&times;</span>\n" + 
    			"				<p id=\"modal_data\"></p>\n" + 
    			"			</div>\n" + 
    			"		</div>\n" + 
    			"		\n" + 
    			"		\n" + 
    			"		<div id=\"updateEmployee\" style=\"display: none\">\n" + 
    			"			<h3>Employee Information</h3>\n" + 
    			"			<div>\n" + 
    			"				<form id=\"employeeUpdateForm\" onsubmit=\"return updateEmployee()\"\n" + 
    			"					enctype=\"multipart/form-data\">\n" + 
    			"					<div class=\"form-group\">\n" + 
    			"						<label for=\"fNameUpdate\">First Name</label> <input id= \"fname2\"\n" + 
    			"							type=\"text\" class=\"form-control\" name=\"fname2\"\n" + 
    			"							aria-describedby=\"fNameHelp\" placeholder=\"Enter First Name\">\n" + 
    			"						<small id=\"fNameUpdate\" class=\"form-text text-muted\"></small>\n" + 
    			"					</div>\n" + 
    			"					<div class=\"form-group\">\n" + 
    			"						<label for=\"lNameUpdate\">Last Name</label> <input\n" + 
    			"							id= \"lname2\"\n" + 
    			"							type=\"text\" class=\"form-control\" name=\"lname2\"\n" + 
    			"							aria-describedby=\"lNameUpdate\" placeholder=\"Enter Last Name\">\n" + 
    			"						<small id=\"lNameUpdate\" class=\"form-text text-muted\"></small>\n" + 
    			"					</div>\n" + 
    			"					\n" + 
    			"					<div class=\"form-group\">\n" + 
    			"						<label for=\"password2\">Password</label> <input\n" + 
    			"							name=\"password2\" type=\"password\" class=\"form-control\"\n" + 
    			"							id=\"password2\" placeholder=\"Password\">\n" + 
    			"					</div>\n" + 
    			"\n" + 
    			"					<button type=\"submit\" class=\"btn btn-primary\">Update</button>\n" + 
    			"				</form>\n" + 
    			"			</div>\n" + 
    			"		</div>\n" + 
    			"\n" + 
    			"	</div>\n" + 
    			"\n" + 
    			"</body>\n" + 
    			"</html>");
    	out.close();
    }
    
    private void generateHomePageAdmin(HttpServletResponse res) throws IOException{
    	res.setContentType("text/html");
    	PrintWriter out = res.getWriter();
    	out.println("<!DOCTYPE html>\n" + 
    			"<html lang=\"en\">\n" + 
    			"<head>\n" + 
    			"<meta charset=\"UTF-8\">\n" + 
    			"<title>Home</title>\n" + 
    			"\n" + 
    			"<script\n" + 
    			"	src=\"//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>\n" + 
    			"\n" + 
    			"<link\n" + 
    			"	href=\"//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css\"\n" + 
    			"	rel=\"stylesheet\" id=\"bootstrap-css\">\n" + 
    			"<link href=\"css/main.css\" rel=\"stylesheet\">\n" + 
    			"\n" + 
    			"<script\n" + 
    			"	src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js\"></script>\n" + 
    			"\n" + 
    			"<script\n" + 
    			"	src=\"//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js\"></script>\n" + 
    			"\n" + 
    			"<script src=\"js/main.js\"></script>\n" + 
    			"\n" + 
    			"</head>\n" + 
    			"<body>\n" + 
    			"\n" + 
    			"	<nav class=\"navbar navbar-expand-sm bg-light fixed-top\">\n" + 
    			"\n" + 
    			"		<!-- Links -->\n" + 
    			"		<ul class=\"navbar-nav\">\n" + 
    			"			<li class=\"nav-item\"><a class=\"navbar-brand\" href=\"#\">Expense\n" + 
    			"					Reimbursement System</a></li>\n" + 
    			"			<div class=\"collapse navbar-collapse\" id=\"navbarNav\">\n" + 
    			"				<ul class=\"nav navbar-nav ml-auto\">\n" + 
    			"					<li class=\"nav-item active\"><a class=\"nav-link\" href=\"logout\">Logout</a>\n" + 
    			"					</li>\n" + 
    			"				</ul>\n" + 
    			"			</div>\n" + 
    			"		</ul>\n" + 
    			"\n" + 
    			"	</nav>\n" + 
    			"\n" + 
    			"	<!-- Side navigation -->\n" + 
    			"	<div class=\"sidenav\">\n" + 
    			"		<a href=\"#\" onclick=\"return onHomeClick();\">Home</a> <a href=\"#\"\n" + 
    			"			onclick=\"return onProfileClick();\">Profile</a>\n" + 
    			"	</div>\n" + 
    			"\n" + 
    			"	<!-- Page content -->\n" + 
    			"	<div class=\"main\">\n" + 
    			"\n" + 
    			"		<!-- alert alert-danger -->\n" + 
    			"		<div id=\"alert_div\" style=\"display: none\">\n" + 
    			"			<div class=\"alert alert-success alert-dismissible\">\n" + 
    			"				<button type=\"button\" class=\"close\" onclick=\"return closeAlert();\">&times;</button>\n" + 
    			"				<strong id=\"alert_msg\"></strong>\n" + 
    			"			</div>\n" + 
    			"		</div>\n" + 
    			"\n" + 
    			"		<div id=\"home\">\n" + 
    			"\n" + 
    			"			<div class=\"container\">\n" + 
    			"				<div class=\"row\">\n" + 
    			"					<div class=\"col-sm\">\n" + 
    			"						<div class=\"card card_bg\">\n" + 
    			"							<div class=\"card-body text-center\">\n" + 
    			"								<h5 class=\"card-title\">\n" + 
    			"									<a href=\"#\" class=\"card-link\"\n" + 
    			"										onclick=\"return onAddEmployeeClick();\">New Employee\n" + 
    			"										Registration</a>\n" + 
    			"								</h5>\n" + 
    			"							</div>\n" + 
    			"						</div>\n" + 
    			"					</div>\n" + 
    			"					<div class=\"col-sm\">\n" + 
    			"						<div class=\"card card_bg\">\n" + 
    			"							<div class=\"card-body text-center\">\n" + 
    			"								<h5 class=\"card-title\">\n" + 
    			"									<a href=\"#\" class=\"card-link\"\n" + 
    			"										onclick=\"return onViewEmployeeClick();\">Registered\n" + 
    			"										Employees</a>\n" + 
    			"								</h5>\n" + 
    			"							</div>\n" + 
    			"						</div>\n" + 
    			"					</div>\n" + 
    			"				</div>\n" + 
    			"				<br> \n" + 
    			"				<div class=\"row\">\n" + 
    			"					<div class=\"col-sm\">\n" + 
    			"						<div class=\"card card_bg\">\n" + 
    			"							<div class=\"card-body text-center\">\n" + 
    			"								<h5 class=\"card-title\">\n" + 
    			"									<a href=\"#\" class=\"card-link\"\n" + 
    			"										onclick=\"return onAddReimbursmentClick();\">New\n" + 
    			"										Reimbursement Form</a>\n" + 
    			"								</h5>\n" + 
    			"							</div>\n" + 
    			"						</div>\n" + 
    			"					</div>\n" + 
    			"					<div class=\"col-sm\">\n" + 
    			"						<div class=\"card card_bg\">\n" + 
    			"							<div class=\"card-body text-center\">\n" + 
    			"								<h5 class=\"card-title\">\n" + 
    			"									<a href=\"#\" class=\"card-link\"\n" + 
    			"										onclick=\"return onViewPendingRequestsClick();\">My Pending\n" + 
    			"										Reimbursement Requests</a>\n" + 
    			"								</h5>\n" + 
    			"							</div>\n" + 
    			"						</div>\n" + 
    			"					</div>\n" + 
    			"				</div>\n" + 
    			"\n" + 
    			"				<br> \n" + 
    			"				<div class=\"row\">\n" + 
    			"					<div class=\"col-sm\">\n" + 
    			"						<div class=\"card card_bg\">\n" + 
    			"							<div class=\"card-body text-center\">\n" + 
    			"								<h5 class=\"card-title\">\n" + 
    			"									<a href=\"#\" class=\"card-link\"\n" + 
    			"										onclick=\"return onViewResolvedRequestsClick();\">My\n" + 
    			"										Resolved Reimbursement Requests</a>\n" + 
    			"								</h5>\n" + 
    			"							</div>\n" + 
    			"						</div>\n" + 
    			"					</div>\n" + 
    			"					<div class=\"col-sm\">\n" + 
    			"						<div class=\"card card_bg\">\n" + 
    			"							<div class=\"card-body text-center\">\n" + 
    			"								<h5 class=\"card-title\">\n" + 
    			"									<a href=\"#\" class=\"card-link\"\n" + 
    			"										onclick=\"return onManageRequestsClick();\">Manage Employees Pending\n" + 
    			"									Requests</a>\n" + 
    			"								</h5>\n" + 
    			"							</div>\n" + 
    			"						</div>\n" + 
    			"					</div>\n" + 
    			"				</div>\n" + 
    			"				<br>\n" + 
    			"				<div class=\"row\">\n" + 
    			"					<div class=\"col-sm\">\n" + 
    			"						<div class=\"card card_bg\">\n" + 
    			"							<div class=\"card-body text-center\">\n" + 
    			"								<h5 class=\"card-title\">\n" + 
    			"									<a href=\"#\" class=\"card-link\"\n" + 
    			"										onclick=\"return onViewAllReceiptClick();\">View all \n" + 
    			"										employee's Reimbursement Receipts</a>\n" + 
    			"								</h5>\n" + 
    			"							</div>\n" + 
    			"						</div>\n" + 
    			"					</div>\n" + 
    			"\n" + 
    			"					<div class=\"col-sm\">\n" + 
    			"						<div class=\"card card_bg\">\n" + 
    			"							<div class=\"card-body text-center\">\n" + 
    			"								<h5 class=\"card-title\">\n" + 
    			"									<a href=\"#\" class=\"card-link\"\n" + 
    			"										onclick=\"return onViewAllResolvedClick();\">All employee's\n" + 
    			"										resolved Reimbursement Requests</a>\n" + 
    			"								</h5>\n" + 
    			"							</div>\n" + 
    			"						</div>\n" + 
    			"					</div>\n" + 
    			"				</div>\n" + 
    			"				<br> \n" + 
    			"				<div class=\"row\">\n" + 
    			"					<div class=\"col-sm\">\n" + 
    			"						<div class=\"card card_bg\">\n" + 
    			"							<div class=\"card-body text-center\">\n" + 
    			"								<h5 class=\"card-title\">\n" + 
    			"									<a href=\"#\" class=\"card-link\"\n" + 
    			"										onclick=\"return onViewAllUnderMeClick();\">\n" + 
    			"										Reimbursement Requests from employee's managed by me</a>\n" + 
    			"								</h5>\n" + 
    			"							</div>\n" + 
    			"						</div>\n" + 
    			"					</div>\n" + 
    			"				</div>\n" + 
    			"			</div>\n" + 
    			"\n" + 
    			"\n" + 
    			"		</div>\n" + 
    			"<!-- 	</div> -->\n" + 
    			"\n" + 
    			"\n" + 
    			"	<div id=\"addEmployee\" style=\"display: none\">\n" + 
    			"		<h3>Employee Registration</h3>\n" + 
    			"		<div>\n" + 
    			"			<form id=\"employeeform\" onsubmit=\"return submitEmployee()\"\n" + 
    			"				enctype=\"multipart/form-data\">\n" + 
    			"				<div class=\"form-group\">\n" + 
    			"					<label for=\"exampleInputEmail1\">First Name</label> <input\n" + 
    			"						type=\"text\" class=\"form-control\" name=\"fname\"\n" + 
    			"						aria-describedby=\"fNameHelp\" placeholder=\"Enter First Name\">\n" + 
    			"					<small id=\"fNameHelp\" class=\"form-text text-muted\"></small>\n" + 
    			"				</div>\n" + 
    			"				<div class=\"form-group\">\n" + 
    			"					<label for=\"exampleInputEmail1\">Last Name</label> <input\n" + 
    			"						type=\"text\" class=\"form-control\" name=\"lname\"\n" + 
    			"						aria-describedby=\"fNameHelp\" placeholder=\"Enter Last Name\">\n" + 
    			"					<small id=\"fNameHelp\" class=\"form-text text-muted\"></small>\n" + 
    			"				</div>\n" + 
    			"				<div class=\"form-group\">\n" + 
    			"					<label for=\"exampleInputEmail1\">Email address</label> <input\n" + 
    			"						type=\"email\" class=\"form-control\" id=\"exampleInputEmail1\"\n" + 
    			"						name=\"email\" aria-describedby=\"emailHelp\"\n" + 
    			"						placeholder=\"Enter email\"> <small id=\"emailHelp\"\n" + 
    			"						class=\"form-text text-muted\"></small>\n" + 
    			"				</div>\n" + 
    			"				<div class=\"form-group\">\n" + 
    			"					<label for=\"exampleInputPassword1\">Password</label> <input\n" + 
    			"						name=\"password\" type=\"password\" class=\"form-control\"\n" + 
    			"						id=\"exampleInputPassword1\" placeholder=\"Password\">\n" + 
    			"				</div>\n" + 
    			"\n" + 
    			"				<div class=\"form-group\">\n" + 
    			"					<label for=\"role\">Role</label> <select class=\"form-control\"\n" + 
    			"						name=\"role\" id=\"role\">\n" + 
    			"						<option>EMPLOYEE</option>\n" + 
    			"						<option>MANAGER</option>\n" + 
    			"					</select>\n" + 
    			"				</div>\n" + 
    			"\n" + 
    			"				<!-- <div class=\"form-group\">\n" + 
    			"						<label for=\"receipt\">Receipt</label> <input\n" + 
    			"						   id=\"receipt\"\n" + 
    			"							name=\"receipt\"\n" + 
    			"							type=\"file\" class=\"form-control\" id=\"receipt\">\n" + 
    			"					</div> -->\n" + 
    			"				<button class=\"btn btn-primary\" onclick=\"return onBack();\">Back</button>\n" + 
    			"				<button type=\"submit\" class=\"btn btn-primary\">Register</button>\n" + 
    			"			</form>\n" + 
    			"		</div>\n" + 
    			"	</div>\n" + 
    			"\n" + 
    			"	<!-- Add Receipt  -->\n" + 
    			"\n" + 
    			"	<div id=\"addReceipt\" style=\"display: none\">\n" + 
    			"		<h3>Reimbursement Request</h3>\n" + 
    			"		<div>\n" + 
    			"			<form id=\"reimbursementform\" onsubmit=\"return submitReceipt()\"\n" + 
    			"				enctype=\"multipart/form-data\">\n" + 
    			"				<div class=\"form-group\">\n" + 
    			"					<label for=\"titleReceipt\">Title</label> <input type=\"text\"\n" + 
    			"						class=\"form-control\" name=\"titleReceipt\"\n" + 
    			"						aria-describedby=\"titleHelp\" placeholder=\"Enter Receipt Heading\">\n" + 
    			"					<small id=\"titelHelp\" class=\"form-text text-muted\"></small>\n" + 
    			"				</div>\n" + 
    			"				<div class=\"form-group\">\n" + 
    			"					<label for=\"description\">Description</label> <input type=\"text\"\n" + 
    			"						class=\"form-control\" name=\"description\"\n" + 
    			"						aria-describedby=\"descriptionHelp\" placeholder=\"Enter Description\">\n" + 
    			"					<small id=\"descriptionHelp\" class=\"form-text text-muted\"></small>\n" + 
    			"				</div>\n" + 
    			"				<div class=\"form-group\">\n" + 
    			"					<label for=\"amount\">Amount</label> <input type=\"number\"\n" + 
    			"						class=\"form-control\" id=\"amount\" name=\"amount\"\n" + 
    			"						aria-describedby=\"emailHelp\" placeholder=\"Enter amount\"> <small\n" + 
    			"						id=\"amountHelp\" class=\"form-text text-muted\"></small>\n" + 
    			"				</div>\n" + 
    			"\n" + 
    			"				<div class=\"form-group\">\n" + 
    			"					<label for=\"receipt\">Receipt</label> <input id=\"receipt\"\n" + 
    			"						name=\"receipt\" type=\"file\" class=\"form-control\" id=\"receipt\">\n" + 
    			"				</div>\n" + 
    			"				<button class=\"btn btn-primary\" onclick=\"return onBack();\">Back</button>\n" + 
    			"				<button type=\"submit\" class=\"btn btn-primary\">Submit</button>\n" + 
    			"			</form>\n" + 
    			"		</div>\n" + 
    			"	</div>\n" + 
    			"\n" + 
    			"	<div id=\"viewAllReceipts\" style=\"display: none\">\n" + 
    			"		<h3>All Employee's Reimbursement Receipts</h3>\n" + 
    			"		<div>\n" + 
    			"			<table class=\"table\">\n" + 
    			"				<thead>\n" + 
    			"					<tr>\n" + 
    			"						<th scope=\"col\">#</th>\n" + 
    			"						<th scope=\"col\">Employee Name</th>\n" + 
    			"						<th scope=\"col\">Manager Name</th>\n" + 
    			"						<th scope=\"col\">Title</th>\n" + 
    			"						<th scope=\"col\">Amount</th>\n" + 
    			"						<th scope=\"col\">Status</th>\n" + 
    			"						<th scope=\"col\">Receipt</th>\n" + 
    			"						<th scope=\"col\">Action</th>\n" + 
    			"					</tr>\n" + 
    			"				</thead>\n" + 
    			"				<tbody id=\"all_receipts_data\">\n" + 
    			"\n" + 
    			"				</tbody>\n" + 
    			"			</table>\n" + 
    			"\n" + 
    			"		</div>\n" + 
    			"	</div>\n" + 
    			"	\n" + 
    			"	<div id=\"viewAllCompletedReceipts\" style=\"display: none\">\n" + 
    			"		<h3>All Employee's Resolved Reimbursement Receipts</h3>\n" + 
    			"		<div>\n" + 
    			"			<table class=\"table\">\n" + 
    			"				<thead>\n" + 
    			"					<tr>\n" + 
    			"						<th scope=\"col\">#</th>\n" + 
    			"						<th scope=\"col\">Employee Name</th>\n" + 
    			"						<th scope=\"col\">Manager Name</th>\n" + 
    			"						<th scope=\"col\">Title</th>\n" + 
    			"						<th scope=\"col\">Amount</th>\n" + 
    			"						<th scope=\"col\">Status</th>\n" + 
    			"						<th scope=\"col\">Receipt</th>\n" + 
    			"						<th scope=\"col\">Action</th>\n" + 
    			"					</tr>\n" + 
    			"				</thead>\n" + 
    			"				<tbody id=\"all_completed_receipts_data\">\n" + 
    			"\n" + 
    			"				</tbody>\n" + 
    			"			</table>\n" + 
    			"\n" + 
    			"		</div>\n" + 
    			"	</div>\n" + 
    			"\n" + 
    			"	<div id=\"viewAllReceiptsUnderMe\" style=\"display: none\">\n" + 
    			"		<h3>All Receipts from employee's managed under me</h3>\n" + 
    			"		<div>\n" + 
    			"			<table class=\"table\">\n" + 
    			"				<thead>\n" + 
    			"					<tr>\n" + 
    			"						<th scope=\"col\">#</th>\n" + 
    			"						<th scope=\"col\">Employee Name</th>\n" + 
    			"						<th scope=\"col\">Manager Name</th>\n" + 
    			"						<th scope=\"col\">Title</th>\n" + 
    			"						<th scope=\"col\">Amount</th>\n" + 
    			"						<th scope=\"col\">Status</th>\n" + 
    			"						<th scope=\"col\">Receipt</th>\n" + 
    			"						<th scope=\"col\">Action</th>\n" + 
    			"					</tr>\n" + 
    			"				</thead>\n" + 
    			"				\n" + 
    			"				<tbody id=\"all_receipts_under_me_data\">\n" + 
    			"\n" + 
    			"				</tbody>\n" + 
    			"			</table>\n" + 
    			"\n" + 
    			"		</div>\n" + 
    			"	</div>\n" + 
    			"\n" + 
    			"	<!--  -->\n" + 
    			"	\n" + 
    			"	<div id=\"viewManagePendingReceipts\" style=\"display: none\">\n" + 
    			"		<h3>Pending Employee's Requests</h3>\n" + 
    			"		<div>\n" + 
    			"			<table class=\"table\">\n" + 
    			"				<thead>\n" + 
    			"					<tr>\n" + 
    			"						<th scope=\"col\">#</th>\n" + 
    			"						<th scope=\"col\">Employee Name</th>\n" + 
    			"						<th scope=\"col\">Title</th>\n" + 
    			"						<th scope=\"col\">Description</th>\n" + 
    			"						<th scope=\"col\">Amount</th>\n" + 
    			"						<th scope=\"col\">Status</th>\n" + 
    			"						<th scope=\"col\">Receipt</th>\n" + 
    			"						<th scope=\"col\">Action</th>\n" + 
    			"					</tr>\n" + 
    			"				</thead>\n" + 
    			"				<tbody id=\"manage_pending_receipts_data\">\n" + 
    			"\n" + 
    			"				</tbody>\n" + 
    			"			</table>\n" + 
    			"\n" + 
    			"		</div>\n" + 
    			"	</div>\n" + 
    			"\n" + 
    			"	<!-- View Receipts Starts -->\n" + 
    			"\n" + 
    			"	<div id=\"viewPendingReceipts\" style=\"display: none\">\n" + 
    			"		<h3>Pending Requests</h3>\n" + 
    			"		<div>\n" + 
    			"			<table class=\"table\">\n" + 
    			"				<thead>\n" + 
    			"					<tr>\n" + 
    			"						<th scope=\"col\">#</th>\n" + 
    			"						<th scope=\"col\">Title</th>\n" + 
    			"						<th scope=\"col\">Description</th>\n" + 
    			"						<th scope=\"col\">Manager Name</th>\n" + 
    			"						<th scope=\"col\">Amount</th>\n" + 
    			"						<th scope=\"col\">Status</th>\n" + 
    			"						<th scope=\"col\">Receipt</th>\n" + 
    			"					</tr>\n" + 
    			"				</thead>\n" + 
    			"				<tbody id=\"pending_receipts_data\">\n" + 
    			"\n" + 
    			"				</tbody>\n" + 
    			"			</table>\n" + 
    			"\n" + 
    			"		</div>\n" + 
    			"	</div>\n" + 
    			"\n" + 
    			"	<!-- View Receipts Ends -->\n" + 
    			"\n" + 
    			"	<div id=\"viewResolvedReceipts\" style=\"display: none\">\n" + 
    			"		<h3>Resolved Requests</h3>\n" + 
    			"		<div>\n" + 
    			"\n" + 
    			"			<table class=\"table\">\n" + 
    			"				<thead>\n" + 
    			"					<tr>\n" + 
    			"						<th scope=\"col\">#</th>\n" + 
    			"						<th scope=\"col\">Title</th>\n" + 
    			"						<th scope=\"col\">Description</th>\n" + 
    			"						<th scope=\"col\">Manager Name</th>\n" + 
    			"						<th scope=\"col\">Amount</th>\n" + 
    			"						<th scope=\"col\">Status</th>\n" + 
    			"						<th scope=\"col\">Receipt</th>\n" + 
    			"					</tr>\n" + 
    			"				</thead>\n" + 
    			"				<tbody id=\"resolved_receipts_data\">\n" + 
    			"\n" + 
    			"				</tbody>\n" + 
    			"			</table>\n" + 
    			"\n" + 
    			"		</div>\n" + 
    			"	</div>\n" + 
    			"\n" + 
    			"	<!-- View Receipts Ends -->\n" + 
    			"\n" + 
    			"\n" + 
    			"	<div id=\"updateEmployee\" style=\"display: none\">\n" + 
    			"		<h3>Employee Information</h3>\n" + 
    			"		<div>\n" + 
    			"			<form id=\"employeeUpdateForm\" onsubmit=\"return updateEmployee()\"\n" + 
    			"				enctype=\"multipart/form-data\">\n" + 
    			"				<div class=\"form-group\">\n" + 
    			"					<label for=\"fNameUpdate\">First Name</label> <input id=\"fname2\"\n" + 
    			"						type=\"text\" class=\"form-control\" name=\"fname2\"\n" + 
    			"						aria-describedby=\"fNameHelp\" placeholder=\"Enter First Name\">\n" + 
    			"					<small id=\"fNameUpdate\" class=\"form-text text-muted\"></small>\n" + 
    			"				</div>\n" + 
    			"				<div class=\"form-group\">\n" + 
    			"					<label for=\"lNameUpdate\">Last Name</label> <input id=\"lname2\"\n" + 
    			"						type=\"text\" class=\"form-control\" name=\"lname2\"\n" + 
    			"						aria-describedby=\"lNameUpdate\" placeholder=\"Enter Last Name\">\n" + 
    			"					<small id=\"lNameUpdate\" class=\"form-text text-muted\"></small>\n" + 
    			"				</div>\n" + 
    			"\n" + 
    			"				<div class=\"form-group\">\n" + 
    			"					<label for=\"password2\">Password</label> <input name=\"password2\"\n" + 
    			"						type=\"password\" class=\"form-control\" id=\"password2\"\n" + 
    			"						placeholder=\"Password\">\n" + 
    			"				</div>\n" + 
    			"\n" + 
    			"				<button type=\"submit\" class=\"btn btn-primary\">Update</button>\n" + 
    			"			</form>\n" + 
    			"		</div>\n" + 
    			"	</div>\n" + 
    			"	\n" + 
    			"		<!-- Modal -->\n" + 
    			"\n" + 
    			"		<!-- The Modal -->\n" + 
    			"		<div id=\"modal\" class=\"modal\" style=\"display: none\">\n" + 
    			"			<!-- Modal content -->\n" + 
    			"			<div class=\"modal-content\">\n" + 
    			"				<span class=\"close\" onclick=\"hideModal()\">&times;</span>\n" + 
    			"				<p id=\"modal_data\"></p>\n" + 
    			"			</div>\n" + 
    			"		</div>\n" + 
    			"\n" + 
    			"\n" + 
    			"		<div id=\"viewEmployees\" style=\"display: none\">\n" + 
    			"		<h3>Registered Employees</h3>\n" + 
    			"		<div>\n" + 
    			"\n" + 
    			"			<table class=\"table\">\n" + 
    			"				<thead>\n" + 
    			"					<tr>\n" + 
    			"						<th scope=\"col\">#</th>\n" + 
    			"						<th scope=\"col\">First Name</th>\n" + 
    			"						<th scope=\"col\">Last Name</th>\n" + 
    			"						<th scope=\"col\">Email</th>\n" + 
    			"						<th scope=\"col\">Role</th>\n" + 
    			"						<th scope=\"col\">Manager</th>\n" + 
    			"					</tr>\n" + 
    			"				</thead>\n" + 
    			"				<tbody id=\"employee_data\">\n" + 
    			"\n" + 
    			"				</tbody>\n" + 
    			"			</table>\n" + 
    			"\n" + 
    			"		</div>\n" + 
    			"	</div>\n" + 
    			"	</div>\n" + 
    			"\n" + 
    			"</body>\n" + 
    			"</html>");
    	out.close();
    }

}
