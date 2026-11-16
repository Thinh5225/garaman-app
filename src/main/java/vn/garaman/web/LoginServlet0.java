package vn.garaman.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.garaman.dao.UserDao0;
import vn.garaman.model.UserAccount0;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet0 extends HttpServlet {
    private final UserDao0 userDao = new UserDao0();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            showLoginError(request, response, "Vui lòng nhập tên đăng nhập và mật khẩu.");
            return;
        }

        try {
            Optional<UserAccount0> account = userDao.authenticate(username.trim(), password);
            if (account.isEmpty()) {
                showLoginError(request, response, "Thông tin đăng nhập không đúng hoặc tài khoản không có quyền dùng hai chức năng này.");
                return;
            }

            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) oldSession.invalidate();
            HttpSession session = request.getSession(true);
            UserAccount0 user = account.get();
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userName", user.getName());
            session.setAttribute("role", user.getRole());

            String destination = "Customer0".equals(user.getRole()) ? "/search" : "/statistics";
            response.sendRedirect(request.getContextPath() + destination);
        } catch (SQLException e) {
            throw new ServletException("Không thể xác thực tài khoản từ cơ sở dữ liệu.", e);
        }
    }

    private void showLoginError(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("loginError", message);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
