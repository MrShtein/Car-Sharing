package carsharing;

import org.h2.jdbcx.JdbcDataSource;

public class Db extends JdbcDataSource {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String dbUrl = "jdbc:h2:~/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/";
    static final String password = "";
    static final String login = "sa";

}