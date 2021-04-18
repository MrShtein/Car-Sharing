package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CompanyDaoImpl implements CompanyDao {

    Db db;

    public CompanyDaoImpl(Db db) {
        this.db = db;
    }

    public String getCompanyName(int companyId) throws SQLException, ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(db.getURL())) {
            Class.forName(Db.JDBC_DRIVER);
            Statement stmt = conn.createStatement();
            String sql = "SELECT name FROM company WHERE ID = " + companyId;
            ResultSet result = stmt.executeQuery(sql);
            if (result.next()) {
                return result.getString("name");
            } else {
                throw new SQLException("Company does not exist");
            }
        }
    }

    @Override
    public ArrayList<Company> getAllCompanies() throws SQLException, ClassNotFoundException {
        ArrayList<Company> companyList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(db.getURL())) {
            Class.forName(Db.JDBC_DRIVER);
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM company");
            while (result.next()) {
                Company company = new Company(result.getInt("id"), result.getString("name"));
                companyList.add(company);
            }
            return companyList;
        }
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
