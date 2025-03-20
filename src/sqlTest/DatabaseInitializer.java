package sqlTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "sample_db";
    private static final String USER = "root";
    private static final String PASSWORD = "tender0831";

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // データベースを選択
            stmt.executeUpdate("USE " + DB_NAME);

            // cars テーブル作成（既存コード）
            String carInfo = "CREATE TABLE IF NOT EXISTS cars ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "make VARCHAR(50) NOT NULL, "
                    + "model VARCHAR(50) NOT NULL, "
                    + "year INT NOT NULL, "
                    + "price DECIMAL(10,2) NOT NULL)";
            stmt.executeUpdate(carInfo);

            // テーブルのデータをリセット
            System.out.println("Clearing existing data in cars table...");
            stmt.executeUpdate("TRUNCATE TABLE cars"); // cars テーブルを初期化
            System.out.println("Cars table cleared successfully.");

            // cars テーブルにデータ挿入
            String insertCarsSQL = "INSERT INTO cars (make, model, year, price) VALUES "
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
            stmt.executeUpdate(insertCarsSQL);

            System.out.println("Cars table initialized successfully.");

            // users テーブル作成
            String userInfo = "CREATE TABLE IF NOT EXISTS users ("
                    + "userid INT AUTO_INCREMENT PRIMARY KEY, " // 主キー
                    + "name VARCHAR(50) NOT NULL, "              // ユーザーの名前
                    + "purchase_date DATE NOT NULL, "            // 購入日
                    + "quantity INT NOT NULL, "                  // 購入台数
                    + "car_id INT NOT NULL, "                    // 外部キー（cars テーブルの id を参照）
                    + "FOREIGN KEY (car_id) REFERENCES cars(id) " // 外部キー制約
                    + ")";
            stmt.executeUpdate(userInfo);

            System.out.println("Users table created successfully.");

            // users テーブルにデータ挿入
            String insertUsersSQL = "INSERT INTO users (name, purchase_date, quantity, car_id) VALUES "
                    + "('Alice', '2023-03-01', 1, 1), "   // Toyota Corolla
                    + "('Bob', '2023-03-02', 2, 4), "     // Tesla Model 3
                    + "('Charlie', '2023-03-03', 1, 6), " // Audi A4
                    + "('David', '2023-03-04', 3, 3), "   // Ford Focus
                    + "('Eve', '2023-03-05', 1, 7)";      // Mercedes C-Class
            stmt.executeUpdate(insertUsersSQL);

            System.out.println("Users table initialized successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
