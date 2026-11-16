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
import java.util.Optional;

@WebServlet("/history")
public class RepairHistoryServlet0 extends HttpServlet {
    private final RevenueDao0 revenueDao = new RevenueDao0();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Optional<Integer> customerId = ServletSupport0.positiveInt(request.getParameter("customerId"));
        Optional<LocalDate> start = ServletSupport0.date(request.getParameter("startDate"));
        Optional<LocalDate> end = ServletSupport0.date(request.getParameter("endDate"));
        if (customerId.isEmpty() || start.isEmpty() || end.isEmpty() || start.get().isAfter(end.get())) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu hoặc sai thông tin khách hàng/khoảng ngày.");
            return;
        }
        try {
            var customer = revenueDao.findCustomer(customerId.get());
            if (customer.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            request.setAttribute("customer", customer.get());
            request.setAttribute("invoices", revenueDao.findRepairHistory(customerId.get(), start.get(), end.get()));
            request.setAttribute("startDate", start.get().toString());
            request.setAttribute("endDate", end.get().toString());
            request.getRequestDispatcher("/WEB-INF/views/history.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Không thể đọc lịch sử sửa chữa từ cơ sở dữ liệu.", e);
        }
    }
}
