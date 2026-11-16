package vn.garaman.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.garaman.dao.RevenueDao0;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/invoice")
public class InvoiceDetailServlet0 extends HttpServlet {
    private final RevenueDao0 revenueDao = new RevenueDao0();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Optional<Integer> invoiceId = ServletSupport0.positiveInt(request.getParameter("invoiceId"));
        if (invoiceId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            var invoice = revenueDao.findInvoice(invoiceId.get());
            if (invoice.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            request.setAttribute("invoice", invoice.get());
            request.setAttribute("lines", revenueDao.findInvoiceLines(invoiceId.get()));
            request.getRequestDispatcher("/WEB-INF/views/invoice.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Không thể đọc hóa đơn từ cơ sở dữ liệu.", e);
        }
    }
}
