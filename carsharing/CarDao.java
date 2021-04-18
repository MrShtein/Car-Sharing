package carsharing;

import java.sql.SQLException;
import java.util.List;

public interface CarDao {
    List<Car> getAllCarsFromCompany(int companyId) throws SQLException;
    void updateCars();
    void deleteCar();
    void addCar(String name, int companyId);
}
