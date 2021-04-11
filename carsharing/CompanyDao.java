package carsharing;

public interface CompanyDao {
        List<Company> getAllCompanies();
        void updateCompany();
        void deleteCompany();
        void addCompany();
}
