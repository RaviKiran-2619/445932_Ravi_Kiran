package todopro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Tododao {

    private String url = "jdbc:mysql://localhost:3306/todo";
    private String username = "root";
    private String password = "root";

    private String INSERT = "INSERT INTO todo_items (description, dateandtime, status) VALUES (?, ?, ?);";
    private String SELECT_BY_ID = "SELECT id, description, dateandtime, status FROM todo_items WHERE id = ?;";
    private String SELECT_ALL = "SELECT * FROM todo_items;";
    private String DELETE = "DELETE FROM todo_items WHERE id = ?;";
    private String UPDATE = "UPDATE todo_items SET description = ?, dateandtime = ?, status = ? WHERE id = ?;";

    // Get DB Connection
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, username, password);
    }

    // Insert a new Todo
    public void insertTodo(Todo todo) throws SQLException {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setString(1, todo.getDescription());
            ps.setTimestamp(2, todo.getDateandtime());
            ps.setString(3, todo.getStatus());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Insert successful!");
            } else {
                System.out.println("Insert failed!");
            }
        }
    }

    // Select Todo by ID
    public Todo selectById(int id) throws SQLException {
        Todo todo = null;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String description = rs.getString("description");
                    Timestamp dateandtime = rs.getTimestamp("dateandtime");
                    String status = rs.getString("status");
                    todo = new Todo(id, description, dateandtime, status);
                }
            }
        }
        return todo;
    }

    // Read all Todos
    public List<Todo> readAll() throws SQLException {
        List<Todo> todolist = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                Timestamp dateandtime = rs.getTimestamp("dateandtime");
                String status = rs.getString("status");
                todolist.add(new Todo(id, description, dateandtime, status));
            }
        }
        return todolist;
    }

    // Delete Todo by ID
    public boolean deleteById(int id) throws SQLException {
        boolean rowDeleted;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, id);
            rowDeleted = ps.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    // Update Todo
    public boolean updateTodo(Todo todo) throws SQLException {
        boolean rowUpdated;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setString(1, todo.getDescription());
            ps.setTimestamp(2, todo.getDateandtime());
            ps.setString(3, todo.getStatus());
            ps.setInt(4, todo.getId());

            rowUpdated = ps.executeUpdate() > 0;
        }
        return rowUpdated;
    }
}
