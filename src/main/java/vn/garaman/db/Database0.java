package vn.garaman.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database0 {
    private Database0() { }

    public static Connection getConnection() throws SQLException {
        String url = setting("garaman.db.url", "GARAMAN_DB_URL",
                "jdbc:mysql://localhost:3306/garaman_db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Ho_Chi_Minh");
        String user = setting("garaman.db.user", "GARAMAN_DB_USER", "root");
        String password = setting("garaman.db.password", "GARAMAN_DB_PASSWORD", "");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver is missing from the application.", e);
        }
        return DriverManager.getConnection(url, user, password);
    }

    private static String setting(String property, String environment, String fallback) {
        String value = System.getProperty(property);
        if (value == null || value.isBlank()) value = System.getenv(environment);
        return value == null ? fallback : value;
    }
}
