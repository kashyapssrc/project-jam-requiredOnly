package com.objectfrontier.training.web.application.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;

import com.objectfrontier.training.web.application.util.AppException;
import com.objectfrontier.training.web.application.util.ConnectionManager;
import com.objectfrontier.training.web.application.util.ErrorCodes;

public class TransactionFilter implements Filter{

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            ConnectionManager.initConnection();
            chain.doFilter(request, response);
            ((HttpServletResponse) response).setStatus(HttpStatus.SC_ACCEPTED);
            ConnectionManager.releaseConnection(true);
        } catch (Exception e) {
            ConnectionManager.releaseConnection(false);
            ((HttpServletResponse) response).setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            throw new AppException(ErrorCodes.CONNECTION_ERROR);
        }
    }

    @Override
    public void destroy() {
    }
}
