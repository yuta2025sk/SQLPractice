package sqlTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSearcher {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "sample_db";
    private static final String USER = "root";
    private static final String PASSWORD = "tender0831";

    public static void searchData(String query) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // データベースを選択
            stmt.executeUpdate("USE " + DB_NAME);

            // クエリ実行
            try (ResultSet rs = stmt.executeQuery(query)) {

                // 結果メタデータを取得して列名を動的に表示
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // 列名を表示
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(metaData.getColumnName(i) + "\t");
                }
                System.out.println();

                // データ行を表示
                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(rs.getObject(i) + "\t");
                    }
                    System.out.println();
                }
            }
            System.out.println("---------------------------------------------------------");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

