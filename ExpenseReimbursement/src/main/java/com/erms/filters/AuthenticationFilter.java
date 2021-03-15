package com.erms.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter {

	private static final String[] loginNotRequiredUrl = { "/login", "/logout", "/login.html" };

	/// @Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession(false);
		String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");

		boolean isLoggedIn = (session != null && session.getAttribute("email") != null);

		String loginURI = request.getContextPath() + "/login";
		boolean isLoginRequest = request.getRequestURI().equals(loginURI);
		boolean isLoginPage = request.getRequestURI().endsWith("login.html");

		String logoutURI = request.getContextPath() + "/logout";
		boolean isLogoutRequest = request.getRequestURI().equals(logoutURI);

		if (isLoggedIn && (isLoginRequest || isLoginPage || isLogoutRequest)) {
			request.getRequestDispatcher("/").forward(request, response);
		} else if (!isLoggedIn && !(isLoginRequest || isLoginPage || isLogoutRequest)) {
			String loginPage = "login.html";
			RequestDispatcher dispatcher = request.getRequestDispatcher(loginPage);
			dispatcher.forward(request, response);
		} else {
			filterChain.doFilter(request, response);
		}
	}

	// @Override
	public void init(FilterConfig filterConfig) {

	}

	// @Override
	public void destroy() {

    }
}