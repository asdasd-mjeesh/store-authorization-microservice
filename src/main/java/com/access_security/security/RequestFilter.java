package com.access_security.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestFilter extends GenericFilterBean {
    @Value("${http.authorizationKey}")
    private String key;
    @Value("${http-security.authorization.header}")
    private String headerName;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest incomingRequest = (HttpServletRequest) request;
        String requestKey = incomingRequest.getHeader(headerName);

        if (requestKey.equals(key)) {
            chain.doFilter(request, response);
        }
        else {
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) response).sendError(HttpStatus.FORBIDDEN.value());
        }
    }
}
