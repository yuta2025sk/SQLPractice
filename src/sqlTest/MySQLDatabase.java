package sqlTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDatabase {
    // データベース接続情報
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "sample_db";
    private static final String USER = "root";
    private static final String PASSWORD = "tender0831";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); // MySQL に接続
             Statement stmt = conn.createStatement()) {

            // データベースを選択
            stmt.executeUpdate("USE " + DB_NAME);

            // テーブル作成
            String createTableSQL = "CREATE TABLE IF NOT EXISTS cars ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "make VARCHAR(50) NOT NULL, "
                    + "model VARCHAR(50) NOT NULL, "
                    + "year INT NOT NULL, "
                    + "price DECIMAL(10,2) NOT NULL)";
            stmt.executeUpdate(createTableSQL);

            // データ挿入
            String insertSQL = "INSERT INTO cars (make, model, year, price) VALUES "
                    + "('Toyota', 'Corolla', 2018, 15000.00), "
                    + "('Honda', 'Civic', 2020, 18000.00), "
                    + "('Ford', 'Focus', 2019, 17000.00), "
                    + "('Tesla', 'Model 3', 2021, 35000.00), "
                    + "('BMW', '3 Series', 2020, 40000.00), "
                    + "('Audi', 'A4', 2021, 45000.00), "
                    + "('Mercedes', 'C-Class', 2019, 72000.00), "
                    + "('Nissan', 'Altima', 2018, 16000.00), "
                    + "('Chevrolet', 'Malibu', 2020, 20000.00), "
                    + "('Hyundai', 'Elantra', 1998, 19000.00)";
         
            stmt.executeUpdate(insertSQL);
            
            String deleteSQL = "DELETE FROM cars WHERE id NOT IN ("
                    + "SELECT min_id FROM ("
                    + "SELECT MIN(id) AS min_id FROM cars GROUP BY make, model, year, price"
                    + ") AS tmp)";
            stmt.executeUpdate(deleteSQL);

            // 条件付きデータ検索 1回目
            executeQuery(stmt, "SELECT * FROM cars WHERE price > 70000.00");

            // 条件付きデータ検索 2回目
            executeQuery(stmt, "SELECT * FROM cars WHERE year <= 2000");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // データ検索を行い、その後に区切り線を追加するメソッド
    public static void executeQuery(Statement stmt, String query) {
        try (ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id")
                        + ", Make: " + rs.getString("make")
                        + ", Model: " + rs.getString("model")
                        + ", Year: " + rs.getInt("year")
                        + ", Price: " + rs.getBigDecimal("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // クエリの実行が終わったら区切り線を表示
        System.out.println("---------------------------------------------------------");
    }
}
