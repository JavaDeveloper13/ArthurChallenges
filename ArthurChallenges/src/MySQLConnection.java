import java.sql.*;

public class MySQLConnection {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/mydatabase", "username", "password");
            System.out.println("Connected to database successfully...");
            stmt = conn.createStatement();
            String sql = "SELECT * FROM mytable";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String column1 = rs.getString("column1");
                int column2 = rs.getInt("column2");
                System.out.println(column1 + " " + column2);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error in connecting to database: " + e.getMessage());
        }
    }
}
