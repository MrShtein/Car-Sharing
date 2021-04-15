package carsharing;

import java.sql.SQLException;
import java.util.List;

public interface CompanyDao {
        List<Company> getAllCompanies() throws SQLException;
        void updateCompany();
        void deleteCompany();
        void addCompany(String name);
}
