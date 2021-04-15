package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl implements CompanyDao {

    Db db;

    public CompanyDaoImpl(Db db) {
        this.db = db;
    }

    @Override
    public List<Company> getAllCompanies() throws SQLException {
        ArrayList<Company> companyList = new ArrayList<>();
        try {
            Class.forName(Db.JDBC_DRIVER);
            try (Connection conn = DriverManager.getConnection(db.getURL())) {
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery("SELECT * FROM company");
                while (result.next()) {
                    Company company = new Company(result.getInt("id"), result.getString("name"));
                    companyList.add(company);
                }
                return companyList;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Something wrong with database");
        }
        throw new SQLException("Something wrong with sql result");
    }

    @Override
    public void updateCompany() {

    }

    @Override
    public void deleteCompany() {

    }

    @Override
    public void addCompany(String companyName) {
        String sql = "INSERT INTO company (name) VALUES ('" + companyName + "')";
        try {
            Class.forName(Db.JDBC_DRIVER);
            try (Connection conn = DriverManager.getConnection(db.getURL())) {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Something wrong with database");
            System.out.println(e.getMessage());
        }
    }
}
