package com.objectfrontier.training.web.application.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;

import com.objectfrontier.training.web.application.model.Person;
import com.objectfrontier.training.web.application.util.AppException;
import com.objectfrontier.training.web.application.util.ErrorCodes;

public class AuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        Person person = (Person) request.getAttribute("person");
        boolean isAdmin = person.isAdmin();
        if (isAdmin) {
            ((HttpServletResponse) response).setStatus(HttpStatus.SC_ACCEPTED);
            chain.doFilter(request, response);
        } else {
            if ((req.getMethod() == "doGet") || (req.getMethod() == "doPost")) {
                ((HttpServletResponse) response).setStatus(HttpStatus.SC_ACCEPTED);
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response).setStatus(HttpStatus.SC_UNAUTHORIZED);
                throw new AppException(ErrorCodes.UNAUTHORIZED_USER);
            }
        }
    }

    @Override
    public void destroy() {
    }
}