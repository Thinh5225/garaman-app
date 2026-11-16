package vn.garaman.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.garaman.dao.CatalogDao0;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/item")
public class ItemDetailServlet0 extends HttpServlet {
    private final CatalogDao0 catalogDao = new CatalogDao0();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = request.getParameter("type");
        Optional<Integer> id = ServletSupport0.positiveInt(request.getParameter("id"));
        if (id.isEmpty() || type == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            var item = catalogDao.findById(type, id.get());
            if (item.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            request.setAttribute("item", item.get());
            request.getRequestDispatcher("/WEB-INF/views/item-detail.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Không thể đọc chi tiết từ cơ sở dữ liệu.", e);
        }
    }
}
