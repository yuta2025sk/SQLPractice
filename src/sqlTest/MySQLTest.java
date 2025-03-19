package sqlTest;


import java.sql.Connection;
import java.sql.DriverManager;


public class MySQLTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/sample_db";
        String user = "root";
        String password = "tender0831";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("MySQL に接続成功！");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}