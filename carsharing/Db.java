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

            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS company (" +
                            "ID INT PRIMARY KEY AUTO_INCREMENT," +
                            "NAME VARCHAR UNIQUE NOT NULL )" +
                            "");

            st.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}