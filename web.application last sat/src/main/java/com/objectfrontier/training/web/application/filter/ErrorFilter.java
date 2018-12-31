package com.objectfrontier.training.web.application.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;

import com.objectfrontier.training.web.application.util.AppException;
import com.objectfrontier.training.web.application.util.ErrorCodes;
import com.objectfrontier.training.web.application.util.JsonUtil;

public class ErrorFilter implements Filter{

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        try {
            ((HttpServletResponse) response).setStatus(HttpStatus.SC_ACCEPTED);
            chain.doFilter(request, response);
        } catch (Exception e) {
            if (e instanceof AppException) {
                response.getWriter().write(JsonUtil.toJson(((AppException) e).getErrorCodes()));
            } else {
                ArrayList<ErrorCodes> errors = new ArrayList<>();
                ((HttpServletResponse) response).setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                errors.add(ErrorCodes.INTERNAL_SERVER_ERROR);
                response.getWriter().write(JsonUtil.toJson(errors));
            }
        }
    }
    @Override
    public void destroy() {
    }
}
