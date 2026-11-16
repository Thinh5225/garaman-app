package vn.garaman.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.garaman.dao.CatalogDao0;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/search")
public class SearchServlet0 extends HttpServlet {
    private final CatalogDao0 catalogDao = new CatalogDao0();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String keyword = request.getParameter("keyword");
        boolean searched = keyword != null && !keyword.isBlank();
        request.setAttribute("keyword", keyword == null ? "" : keyword.trim());
        request.setAttribute("searched", searched);
        try {
            request.setAttribute("items", searched ? catalogDao.search(keyword) : List.of());
        } catch (SQLException e) {
            throw new ServletException("Không thể tìm dịch vụ/phụ tùng từ cơ sở dữ liệu.", e);
        }
        request.getRequestDispatcher("/WEB-INF/views/search.jsp").forward(request, response);
    }
}
