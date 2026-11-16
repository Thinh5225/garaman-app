package vn.garaman.dao;

import vn.garaman.db.Database0;
import vn.garaman.model.UserAccount0;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao0 {
    public Optional<UserAccount0> authenticate(String username, String password) throws SQLException {
        String sql = "SELECT u.id, u.username, u.name, u.role FROM User_0 u "
                + "WHERE u.username = ? AND u.password = ? AND (u.role = 'Manager0' "
                + "OR (u.role = 'Customer0' AND EXISTS "
                + "(SELECT 1 FROM Customer_0 c WHERE c.id = u.id)))";
        try (Connection connection = Database0.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet rows = statement.executeQuery()) {
                if (!rows.next()) return Optional.empty();
                return Optional.of(new UserAccount0(rows.getInt("id"), rows.getString("username"),
                        rows.getString("name"), rows.getString("role")));
            }
        }
    }
}
