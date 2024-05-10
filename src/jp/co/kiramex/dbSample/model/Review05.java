package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

	public static void main(String[] args) {
        // 3. データベース接続と結果取得のための変数宣言
            Connection con = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
                // 1. ドライバのクラスをJava上で読み込む
                Class.forName("com.mysql.cj.jdbc.Driver");

                // 2. DBと接続する
                con = DriverManager.getConnection(
                        "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                        "root",
                        "Kiogentyo3r");

                // 4. DBとやりとりする窓口（PreparedStatementオブジェクト）の作成
                // 検索用SQLおよびPreparedStatementオブジェクトの作成
                String selectSql = "SELECT name, age FROM person WHERE id = ?";
                pstmt = con.prepareStatement(selectSql);

                // idをキーボードから入力
                System.out.print("検索キーワードを入力してください > ");
                int id = keyInNum();

                // 入力されたidをPreparedStatementオブジェクトにセット
                pstmt.setInt(1, id);

                // 5. Select文の実行
                rs = pstmt.executeQuery();

                // 6. 結果を表示する
                while (rs.next()) {
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    System.out.println(name);
                    System.out.println(age);
                }
            } catch (ClassNotFoundException e) {
                System.err.println("JDBCドライバのロードに失敗しました。");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("データベースに異常が発生しました。");
                e.printStackTrace();
            } finally {
                // 7. 接続を閉じる
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                        e.printStackTrace();
                    }
                }
                if (pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (SQLException e) {
                        System.err.println("PreparedStatementを閉じるときにエラーが発生しました。");
                        e.printStackTrace();
                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        System.err.println("データベース切断時にエラーが発生しました。");
                        e.printStackTrace();
                    }
                }
            }
        }

        /*
         * キーボードから入力された値をintで返す 引数：なし 戻り値：int
         */
        private static int keyInNum() {
            int result = 0;
            try {
                BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
                String line = key.readLine();
                result = Integer.parseInt(line);
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
