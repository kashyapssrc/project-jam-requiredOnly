package com.objectfrontier.training.web.application.filter;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpStatus;

import com.objectfrontier.training.web.application.model.Person;
import com.objectfrontier.training.web.application.util.AppException;
import com.objectfrontier.training.web.application.util.ErrorCodes;

public class AuthenticationFilter implements Filter{

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }


    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
        String sessionId = session.getId();
        Person person = (Person) httpRequest.getAttribute("person");
        if (! Objects.isNull(sessionId) && ! Objects.isNull(person)) {
            ((HttpServletResponse) response).setStatus(HttpStatus.SC_ACCEPTED);
            chain.doFilter(httpRequest, response);
        } else {
            ((HttpServletResponse) response).setStatus(HttpStatus.SC_FORBIDDEN);
            throw new AppException(ErrorCodes.UNAUTHENTICATED_USER);
        }
    }


}
