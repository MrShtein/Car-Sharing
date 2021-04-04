package carsharing;



public class Db {

    static final String JDBC_DRIVER = "org.h2.Driver";
    private String dbUrl;
    private String password;
    private String login;

    public Db(String dbUrl, String password, String login) {
        this.dbUrl = dbUrl;
        this.password = password;
        this.login = login;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

}