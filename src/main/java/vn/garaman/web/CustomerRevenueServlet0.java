package vn.garaman.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.garaman.dao.RevenueDao0;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@WebServlet("/statistics")
public class CustomerRevenueServlet0 extends HttpServlet {
    private final RevenueDao0 revenueDao = new RevenueDao0();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String startText = request.getParameter("startDate");
        String endText = request.getParameter("endDate");
        request.setAttribute("startDate", startText == null ? "" : startText);
        request.setAttribute("endDate", endText == null ? "" : endText);
        request.setAttribute("searched", false);
        request.setAttribute("rows", List.of());

        boolean hasStart = startText != null && !startText.isBlank();
        boolean hasEnd = endText != null && !endText.isBlank();
        if (hasStart || hasEnd) {
            Optional<LocalDate> start = ServletSupport0.date(startText);
            Optional<LocalDate> end = ServletSupport0.date(endText);
            if (start.isEmpty() || end.isEmpty()) {
                request.setAttribute("error", "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc hợp lệ.");
            } else if (start.get().isAfter(end.get())) {
                request.setAttribute("error", "Ngày bắt đầu không được sau ngày kết thúc.");
            } else {
                try {
                    request.setAttribute("rows", revenueDao.findCustomerRevenue(start.get(), end.get()));
                    request.setAttribute("searched", true);
                } catch (SQLException e) {
                    throw new ServletException("Không thể thống kê doanh thu từ cơ sở dữ liệu.", e);
                }
            }
        }
        request.getRequestDispatcher("/WEB-INF/views/statistics.jsp").forward(request, response);
    }
}
