package carsharing;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArgsHandler argsHandler = new ArgsHandler(args);
        String dbName = Db.dbUrl + argsHandler.getName();
        JdbcDataSource db = new JdbcDataSource();
        db.setURL(dbName);
        Scanner scanner = new Scanner(System.in);



        try {
            Class.forName(Db.JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(dbName);
            conn.setAutoCommit(true);
            Statement st = conn.createStatement();

//            while (true) {
//                String answer = scanner.nextLine();
//                if ("0".equals(answer)) {
//                    System.exit(9);
//                } else {
//                    break;
//                }
//            }

            st.executeUpdate("CREATE TABLE company (" +
                    "ID INT," +
                    "NAME VARCHAR)");

            st.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}