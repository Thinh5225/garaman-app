package vn.garaman.dao;

import vn.garaman.db.Database0;
import vn.garaman.model.CatalogItem0;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CatalogDao0 {
    public List<CatalogItem0> search(String keyword) throws SQLException {
        List<CatalogItem0> items = new ArrayList<>();
        String serviceSql = "SELECT id, name, price, description FROM Service_0 "
                + "WHERE LOWER(name) LIKE LOWER(?) ORDER BY name";
        String partSql = "SELECT id, name, price, stock_quantity FROM Part_0 "
                + "WHERE LOWER(name) LIKE LOWER(?) ORDER BY name";
        try (Connection connection = Database0.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(serviceSql)) {
                statement.setString(1, "%" + keyword.trim() + "%");
                try (ResultSet rows = statement.executeQuery()) {
                    while (rows.next()) {
                        items.add(new CatalogItem0(rows.getInt("id"), "Dịch vụ", rows.getString("name"),
                                rows.getBigDecimal("price"), rows.getString("description"), null));
                    }
                }
            }
            try (PreparedStatement statement = connection.prepareStatement(partSql)) {
                statement.setString(1, "%" + keyword.trim() + "%");
                try (ResultSet rows = statement.executeQuery()) {
                    while (rows.next()) {
                        items.add(new CatalogItem0(rows.getInt("id"), "Phụ tùng", rows.getString("name"),
                                rows.getBigDecimal("price"), null, rows.getInt("stock_quantity")));
                    }
                }
            }
        }
        items.sort((left, right) -> left.getName().compareToIgnoreCase(right.getName()));
        return items;
    }

    public Optional<CatalogItem0> findById(String type, int id) throws SQLException {
        if ("service".equals(type)) {
            String sql = "SELECT id, name, price, description FROM Service_0 WHERE id = ?";
            try (Connection connection = Database0.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet rows = statement.executeQuery()) {
                    if (!rows.next()) return Optional.empty();
                    return Optional.of(new CatalogItem0(rows.getInt("id"), "Dịch vụ", rows.getString("name"),
                            rows.getBigDecimal("price"), rows.getString("description"), null));
                }
            }
        }
        if ("part".equals(type)) {
            String sql = "SELECT id, name, price, stock_quantity FROM Part_0 WHERE id = ?";
            try (Connection connection = Database0.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet rows = statement.executeQuery()) {
                    if (!rows.next()) return Optional.empty();
                    return Optional.of(new CatalogItem0(rows.getInt("id"), "Phụ tùng", rows.getString("name"),
                            rows.getBigDecimal("price"), null, rows.getInt("stock_quantity")));
                }
            }
        }
        return Optional.empty();
    }
}
