package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class CarDaoImpl implements CarDao {
    Db db;

    public CarDaoImpl(Db db) {
        this.db = db;
    }

    @Override
    public ArrayList<Car> getAllCarsFromCompany(int companyId) throws SQLException {
        ArrayList<Car> carList = new ArrayList<>();
        try {
            Class.forName(Db.JDBC_DRIVER);
            try (Connection conn = DriverManager.getConnection(db.getURL())) {
                Statement stmt = conn.createStatement();

            ResultSet result = stmt.executeQuery("SELECT * FROM car WHERE COMPANY_ID = " + companyId);
            while (result.next()) {
                Car car = new Car(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("company_id"));
                carList.add(car);
            }
            return carList;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Something wrong with database");
        }
        throw new SQLException("Something wrong with sql result");
    }

    @Override
    public void updateCars() {

    }

    @Override
    public void deleteCar() {

    }

    @Override
    public void addCar(String name, int companyId) {
        try {
            Class.forName(Db.JDBC_DRIVER);
            try (Connection conn = DriverManager.getConnection(db.getURL())) {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(
                        "INSERT INTO car (name, company_id) VALUES ('" + name + "', '" + companyId + "')"
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Something wrong with database");
        }
    }

    public Car getCar(int carId) throws SQLException, ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(db.getURL())) {
            Class.forName(Db.JDBC_DRIVER);
            Statement stmt = conn.createStatement();
            //language=sql
            String sql = "SELECT * FROM car WHERE id = " + carId;
            ResultSet res = stmt.executeQuery(sql);
            Car car = null;
            if (res.next()) {
                car = new Car(res.getInt("id"), res.getString("name"), res.getInt("company_id"));
            } else {
                throw new SQLException("No car with current id");
            }
            return car;
        }
    }
}
