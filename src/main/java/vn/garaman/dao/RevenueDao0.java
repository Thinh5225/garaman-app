package vn.garaman.dao;

import vn.garaman.db.Database0;
import vn.garaman.model.Customer0;
import vn.garaman.model.CustomerRevenue0;
import vn.garaman.model.Invoice0;
import vn.garaman.model.InvoiceLine0;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RevenueDao0 {
    public List<CustomerRevenue0> findCustomerRevenue(LocalDate startDate, LocalDate endDate) throws SQLException {
        String sql = "SELECT c.id, u.name, u.phone, c.license_plate, COUNT(i.id) AS invoice_count, "
                + "SUM(i.total_amount) AS revenue "
                + "FROM Customer_0 c JOIN User_0 u ON u.id = c.id "
                + "JOIN Invoice_0 i ON i.customer_id = c.id "
                + "WHERE i.payment_date BETWEEN ? AND ? "
                + "GROUP BY c.id, u.name, u.phone, c.license_plate "
                + "ORDER BY revenue DESC, u.name ASC";
        List<CustomerRevenue0> result = new ArrayList<>();
        try (Connection connection = Database0.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));
            try (ResultSet rows = statement.executeQuery()) {
                while (rows.next()) {
                    Customer0 customer = new Customer0(rows.getInt("id"), rows.getString("name"),
                            rows.getString("phone"), rows.getString("license_plate"));
                    result.add(new CustomerRevenue0(customer, rows.getLong("invoice_count"),
                            rows.getBigDecimal("revenue")));
                }
            }
        }
        return result;
    }

    public Optional<Customer0> findCustomer(int customerId) throws SQLException {
        String sql = "SELECT c.id, u.name, u.phone, c.license_plate "
                + "FROM Customer_0 c JOIN User_0 u ON u.id = c.id WHERE c.id = ?";
        try (Connection connection = Database0.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            try (ResultSet rows = statement.executeQuery()) {
                if (!rows.next()) return Optional.empty();
                return Optional.of(new Customer0(rows.getInt("id"), rows.getString("name"),
                        rows.getString("phone"), rows.getString("license_plate")));
            }
        }
    }

    public List<Invoice0> findRepairHistory(int customerId, LocalDate startDate, LocalDate endDate)
            throws SQLException {
        String sql = "SELECT i.id, i.customer_id, u.name, c.license_plate, i.total_amount, i.payment_date "
                + "FROM Invoice_0 i JOIN Customer_0 c ON c.id = i.customer_id "
                + "JOIN User_0 u ON u.id = c.id "
                + "WHERE i.customer_id = ? AND i.payment_date BETWEEN ? AND ? "
                + "ORDER BY i.payment_date DESC, i.id DESC";
        List<Invoice0> result = new ArrayList<>();
        try (Connection connection = Database0.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            statement.setDate(2, Date.valueOf(startDate));
            statement.setDate(3, Date.valueOf(endDate));
            try (ResultSet rows = statement.executeQuery()) {
                while (rows.next()) result.add(readInvoice(rows));
            }
        }
        return result;
    }

    public Optional<Invoice0> findInvoice(int invoiceId) throws SQLException {
        String sql = "SELECT i.id, i.customer_id, u.name, c.license_plate, i.total_amount, i.payment_date "
                + "FROM Invoice_0 i JOIN Customer_0 c ON c.id = i.customer_id "
                + "JOIN User_0 u ON u.id = c.id WHERE i.id = ?";
        try (Connection connection = Database0.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, invoiceId);
            try (ResultSet rows = statement.executeQuery()) {
                if (!rows.next()) return Optional.empty();
                return Optional.of(readInvoice(rows));
            }
        }
    }

    public List<InvoiceLine0> findInvoiceLines(int invoiceId) throws SQLException {
        String sql = "SELECT item_type, item_name, quantity, current_price FROM ("
                + "SELECT 'Dịch vụ' AS item_type, s.name AS item_name, d.quantity, d.current_price "
                + "FROM InvoiceServiceDetail_0 d JOIN Service_0 s ON s.id = d.service_id WHERE d.invoice_id = ? "
                + "UNION ALL "
                + "SELECT 'Phụ tùng' AS item_type, p.name AS item_name, d.quantity, d.current_price "
                + "FROM InvoicePartDetail_0 d JOIN Part_0 p ON p.id = d.part_id WHERE d.invoice_id = ?"
                + ") AS invoice_lines ORDER BY item_type, item_name";
        List<InvoiceLine0> result = new ArrayList<>();
        try (Connection connection = Database0.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, invoiceId);
            statement.setInt(2, invoiceId);
            try (ResultSet rows = statement.executeQuery()) {
                while (rows.next()) result.add(new InvoiceLine0(rows.getString("item_type"),
                        rows.getString("item_name"), rows.getInt("quantity"), rows.getBigDecimal("current_price")));
            }
        }
        return result;
    }

    private Invoice0 readInvoice(ResultSet rows) throws SQLException {
        return new Invoice0(rows.getInt("id"), rows.getInt("customer_id"), rows.getString("name"),
                rows.getString("license_plate"), rows.getBigDecimal("total_amount"),
                rows.getDate("payment_date").toLocalDate());
    }
}
