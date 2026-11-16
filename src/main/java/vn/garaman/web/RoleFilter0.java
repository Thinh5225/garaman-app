package vn.garaman.web;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/search", "/item", "/statistics", "/history", "/invoice"})
public class RoleFilter0 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/");
            return;
        }

        String role = (String) session.getAttribute("role");
        String path = httpRequest.getServletPath();
        boolean customerPath = "/search".equals(path) || "/item".equals(path);
        boolean allowed = "Customer0".equals(role) ? customerPath
                : "Manager0".equals(role) && !customerPath;
        if (!allowed) {
            String destination = "Customer0".equals(role) ? "/search" : "/statistics";
            httpResponse.sendRedirect(httpRequest.getContextPath() + destination);
            return;
        }
        chain.doFilter(request, response);
    }
}
