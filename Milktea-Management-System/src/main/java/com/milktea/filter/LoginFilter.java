package com.milktea.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginFilter implements Filter {
    private List<String> excludePaths = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String excludePathsParam = filterConfig.getInitParameter("excludePaths");
        if (excludePathsParam != null && !excludePathsParam.isEmpty()) {
            String[] paths = excludePathsParam.split(",");
            for (String path : paths) {
                excludePaths.add(path.trim());
            }
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        boolean isExcluded = false;

        for (String path : excludePaths) {
            if (requestURI.equals(path) || requestURI.startsWith(path + "/")) {
                isExcluded = true;
                break;
            }
        }

        if (isExcluded || (session != null && session.getAttribute("employee") != null)) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        }
    }

    @Override
    public void destroy() {
    }
}