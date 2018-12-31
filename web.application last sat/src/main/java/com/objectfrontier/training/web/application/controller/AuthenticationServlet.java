package com.objectfrontier.training.web.application.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.entity.ContentType;

import com.objectfrontier.training.web.application.model.Person;
import com.objectfrontier.training.web.application.service.AuthenticationService;
import com.objectfrontier.training.web.application.util.JsonUtil;

public class AuthenticationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      AuthenticationService loginService = new AuthenticationService();
      PrintWriter out = response.getWriter();

      String email = request.getParameter("email");
      String password = request.getParameter("password");

      Person person = loginService.login(email, password);
      HttpSession session = request.getSession();
      String sessionId = request.getRequestedSessionId();
      Cookie setCookie = new Cookie("JSESSIONID", sessionId);
      response.addCookie(setCookie);
      response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
      session.setAttribute("person", person);
      out.write(JsonUtil.toJson(sessionId));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
    }
}
