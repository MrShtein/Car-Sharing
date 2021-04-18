package carsharing;

public class Customer {

    private final int id;
    private final String name;
    private Integer rentedCarId;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
        this.rentedCarId = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getRentedCarId() {
        return rentedCarId;
    }

    public void setRentedCarId(int rentedCarId) {
        this.rentedCarId = rentedCarId;
    }
}
