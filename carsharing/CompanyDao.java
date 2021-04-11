package carsharing;

import java.util.List;

public interface CompanyDao {
        List<Company> getAllCompanies();
        void updateCompany();
        void deleteCompany();
        void addCompany();
}
