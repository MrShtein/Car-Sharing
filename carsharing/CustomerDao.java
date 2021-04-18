package carsharing;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDao {

    ArrayList<Customer> getAllCustomers() throws SQLException, ClassNotFoundException;
    void createCustomer(String name) throws SQLException, ClassNotFoundException;

}
