package sqlTest;

public class Main {
    public static void main(String[] args) {
        // データベース初期化
        DatabaseInitializer.initializeDatabase();

        // cars テーブルの検索例
        System.out.println("Search results for cars with price > 70000:");
        DataSearcher.searchData("SELECT * FROM cars WHERE price > 70000.00");

        System.out.println("Search results for cars with year <= 2000:");
        DataSearcher.searchData("SELECT * FROM cars WHERE year <= 2000");

        // users テーブルの検索例
        System.out.println("Search results for users who purchased cars:");
        DataSearcher.searchData("SELECT * FROM users");

        System.out.println("Search results for users who purchased more than 1 car:");
        DataSearcher.searchData("SELECT * FROM users WHERE quantity > 1");
    }
}
