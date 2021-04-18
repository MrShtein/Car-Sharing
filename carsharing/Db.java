package carsharing;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Db extends JdbcDataSource {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String dbUrl = "jdbc:h2:~/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/";
    private String baseName;

    public Db(String baseName) {
        this.baseName = baseName;
        this.setURL(dbUrl + baseName);
    }

    public void start() {
        try {
            Class.forName(Db.JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(this.getURL());
            conn.setAutoCommit(true);
            Statement st = conn.createStatement();

//            st.executeUpdate(
//                    "DROP TABLE IF EXISTS car CASCADE"
//            );
//
//            st.executeUpdate(
//                    "DROP TABLE IF EXISTS company CASCADE"
//            );
//
//            st.executeUpdate(
//                    "DROP TABLE IF EXISTS customer CASCADE"
//            );

            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS company (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT," +
                            "name VARCHAR UNIQUE NOT NULL )" +
                            "");
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS car (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT," +
                            "name VARCHAR(50) UNIQUE NOT NULL," +
                            "company_id INT NOT NULL," +
                            "CONSTRAINT fk_company FOREIGN KEY (company_id)" +
                            "REFERENCES COMPANY(id))"
            );

            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS customer (" +
                            "ID INT PRIMARY KEY AUTO_INCREMENT," +
                            "NAME VARCHAR(100) UNIQUE NOT NULL," +
                            "RENTED_CAR_ID INT DEFAULT NULL," +
                            "CONSTRAINT fk_customer FOREIGN KEY (RENTED_CAR_ID)" +
                            "REFERENCES CAR(ID))"
            );

            st.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}