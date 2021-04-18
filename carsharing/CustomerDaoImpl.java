package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerDaoImpl implements CustomerDao {

    Db db;

    public CustomerDaoImpl(Db db) {
        this.db = db;
    }

    @Override
    public ArrayList<Customer> getAllCustomers() throws SQLException, ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(db.getURL())) {
            ArrayList<Customer> customerList = new ArrayList<>();
            Class.forName(Db.JDBC_DRIVER);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM customer";
            ResultSet result = stmt.executeQuery(sql);
            int customerNum = 1;
            while (result.next()) {
                Customer customer = new Customer(customerNum, result.getString("name"));
                Integer rentedCarId = result.getInt("rented_car_id");
                if (!result.wasNull()) {
                    customer.setRentedCarId(rentedCarId);
                }
                customerList.add(customer);
                customerNum++;
            }
            return customerList;
        }
    }

    @Override
    public void createCustomer(String name) throws SQLException, ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(db.getURL())) {
            Class.forName(Db.JDBC_DRIVER);
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO customer (name) VALUES ( " + String.format("'%s')", name);
            stmt.executeUpdate(sql);
        }
    }

    public Customer getCustomer(int customerId) throws SQLException, ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(db.getURL())) {
            Class.forName(Db.JDBC_DRIVER);
            Statement stmt = conn.createStatement();
            // language=sql
            String sql = "SELECT * FROM customer WHERE id = " + customerId;
            ResultSet res = stmt.executeQuery(sql);

            if (res.next()) {
                Customer customer = new Customer(res.getInt("id"), res.getString("name"));
                Integer carId = res.getInt("rented_car_id");
                if (!res.wasNull()) {
                    customer.setRentedCarId(carId);
                }
                return customer;
            }
        }
        throw new SQLException("Something wrong");
    }

    public void returnCar(int customerId) throws SQLException, ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(db.getURL())) {
            Class.forName(Db.JDBC_DRIVER);
            Statement stmt = conn.createStatement();

            Customer customer = new CustomerDaoImpl(db).getCustomer(customerId);
            if (customer.getRentedCarId() == 0) {
                System.out.println("You didn't rent a car!");
                return;
            }
            //language=sql
        String sql = "UPDATE customer SET rented_car_id = NULL WHERE id = " + customerId;
            stmt.executeUpdate(sql);
        }
    }

    public void updateRentCarId(int customerId, int rentCarId) throws SQLException, ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(db.getURL())) {
            Class.forName(Db.JDBC_DRIVER);
            Statement stmt = conn.createStatement();
            //language=sql
            String sql = "UPDATE customer SET rented_car_id = " + rentCarId + " WHERE id = " + customerId;
            stmt.executeUpdate(sql);
        }
    }
}
