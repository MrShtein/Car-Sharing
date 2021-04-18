package carsharing;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Program {

    String[] args;
    Db db;
    HashSet<Integer> unavailableCars = new HashSet<>();

    public Program(String[] args) {
        this.args = args;
    }

    public void start() throws SQLException {
        boolean work = true;
        ArgsHandler argsHandler = new ArgsHandler(args);
        String tableName = argsHandler.getName();
        db = new Db(tableName);
        db.start();

        while (work) {
            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");
            int item = 0;
            try {
                item = new Scanner(System.in).nextInt();
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal argument");
            }

            switch (item) {
                case 0:
                    work = false;
                    break;
                case 1:
                    companyMenu();
                    break;
                case 2:
                    customerMenu();
                    break;
                case 3:
                    createCustomer();
                    break;
                default:
                    throw new IllegalArgumentException("Choose from 0 to 3");
            }
        }
    }

    public void companyMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean companyMenuWork = true;

        while (companyMenuWork) {
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");
            int companyMenuItem = 0;
            try {
                companyMenuItem = scanner.nextInt();
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal argument");
            }

            switch (companyMenuItem) {
                case 1:
                    chooseCompany(db);
                    break;
                case 2:
                    addNewCompany();
                    break;
                case 0:
                    companyMenuWork = false;
                    break;
                default:
                    throw new IllegalArgumentException("Choose correct menu item");
            }
        }

    }

    public ArrayList<Company> getCompanyList(Db db) throws SQLException, ClassNotFoundException {
        CompanyDaoImpl companyDao = new CompanyDaoImpl(db);
        return companyDao.getAllCompanies();
    }

    public void addNewCompany() {
        System.out.println("Enter the company name:");
        String companyName = new Scanner(System.in).nextLine();
        CompanyDaoImpl companyDao = new CompanyDaoImpl(db);
        companyDao.addCompany(companyName);
        System.out.println("The company was created");
    }

    public void chooseCompany(Db db) {
        boolean companyMenu = true;
        try {
            ArrayList<Company> companies = getCompanyList(db);
            if (companies.isEmpty()) {
                System.out.println("The company list is empty!");
            } else {
                while (companyMenu) {
                    try {
                        System.out.println("Choose the company:");
                        printCompanies(companies);
                        System.out.println("0. Back");
                        int companyMenuItem = new Scanner(System.in).nextInt();

                        if (companyMenuItem == 0) {
                            companyMenu = false;
                        } else {
                            int result = showCompanyMenu(companies.get(companyMenuItem - 1).getId());
                            if (result == 0) {
                                companyMenu = false;
                            }
                        }
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                    };
                }

            }
        } catch (SQLException e) {
            System.out.println("Something wrong with database");
        } catch (ClassNotFoundException e) {
            System.out.println("Class for db not found");
        }

    }

    public int showCompanyMenu(int companyId) {
        try {
            while (true) {
                System.out.printf("'%s' company:\n" +
                        "1. Car list\n" +
                        "2. Create a car\n" +
                        "0. Back\n", getCompanyName(companyId));
                int menuItem = new Scanner(System.in).nextInt();
                switch (menuItem) {
                    case 1:
                        showCarList(db, companyId);
                        break;
                    case 2:
                        addNewCar(db, companyId);
                        break;
                    case 0:
                        return 0;
                    default:
                        System.out.println("Wrong menu item");
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }

    public void showCarList(Db db, int companyId) {
        CarDaoImpl carDao = new CarDaoImpl(db);
        try {
            ArrayList<Car> cars = carDao.getAllCarsFromCompany(companyId);
            if (cars.isEmpty()) {
                System.out.println("The car list is empty!");
            } else {
                System.out.printf("'%s' cars:\n", getCompanyName(companyId));
                printCarsList(cars);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printCarsList(ArrayList<Car> cars) {
        int carNum = 1;
        for (Car item : cars) {
            if (unavailableCars.contains(item.getId())) {
                continue;
            }
            System.out.printf("%d. %s\n", carNum, item.getName());
            carNum++;
        }
    }

    public void addNewCar(Db db, int company_id) {
        CarDaoImpl carDao = new CarDaoImpl(db);
        System.out.println("Enter the car name:");
        String carName = new Scanner(System.in).nextLine();

        carDao.addCar(carName, company_id);
        System.out.println("The car was added!");
    }

    public String getCompanyName(int companyId) throws SQLException, ClassNotFoundException {
        return new CompanyDaoImpl(db).getCompanyName(companyId);
    }

    public void customerMenu() {
        ArrayList<Customer> customerList = null;
        try {
            customerList = new CustomerDaoImpl(db).getAllCustomers();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        if (customerList == null || customerList.isEmpty()) {
            System.out.println(" The customer list is empty!");
        } else {
            boolean customerMenuWork = true;
            while (customerMenuWork) {
                showCustomersAndBack(customerList);
                int customerNum = 0;
                try {
                    customerNum = new Scanner(System.in).nextInt();
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                if (customerNum == 0) {
                    customerMenuWork = false;
                } else {
                    int result = showCustomerMenu(customerNum);
                    if (result == 0) {
                        customerMenuWork = false;
                    }
                }
            }
        }



    }

    public void createCustomer() {
        System.out.println("Enter the customer name:");
        String customerName = "";
        try {
            customerName = new Scanner(System.in).nextLine();
        } catch (Exception e) {
            System.out.println("Something wrong with customer name");
        }

        try {
            CustomerDaoImpl customerDao = new CustomerDaoImpl(db);
            customerDao.createCustomer(customerName);
            System.out.println("The customer was added!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showCustomersAndBack(ArrayList<Customer> customersList) {
        for (Customer item : customersList) {
            System.out.printf("%d. %s\n", item.getId(), item.getName());
        }
        System.out.println("0. Back\n");
    }

    public int showCustomerMenu(int customerNum) {
        while (true) {
            System.out.println("1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back");

            int menuItem = 0;
            try {
                menuItem = new Scanner(System.in).nextInt();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            switch (menuItem) {
                case 0:
                    return 0;
                case 1:
                    rentACar(customerNum);
                    break;
                case 2:
                    returnCar(customerNum);
                    break;
                case 3:
                    showRentedCarInfo(customerNum);
                    break;
            }
        }
    }

    public void showRentedCarInfo(int customerId) {
        Customer curCustomer = null;
        Car car = null;
        try {
           curCustomer =  new CustomerDaoImpl(db).getCustomer(customerId);

            if (0 == curCustomer.getRentedCarId()) {
                System.out.println("You didn't rent a car!.");
            } else {
                car = new CarDaoImpl(db).getCar(curCustomer.getRentedCarId());
                String companyName = new CompanyDaoImpl(db).getCompanyName(car.getCompany_id());
                printRentedCar(car, companyName);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void printRentedCar(Car car, String compName) {
        System.out.printf("Your rented car:\n%s\nCompany:\n%s\n", car.getName(), compName);
    }

    public void returnCar(int customerId) {
        try {
            Customer customer = new CustomerDaoImpl(db).getCustomer(customerId);
            if (customer.getRentedCarId() == 0) {
                System.out.println("You didn't rent a car!");
            } else {
                unavailableCars.remove(customer.getRentedCarId());
                new CustomerDaoImpl(db).returnCar(customerId);
                System.out.println("You've returned a rented car!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    public void rentACar(int customerId) {
        try {
            Customer customer = new CustomerDaoImpl(db).getCustomer(customerId);
            if (customer.getRentedCarId() != 0) {
                System.out.println("You've already rented a car!");
            } else {
                ArrayList<Company> companies = new CompanyDaoImpl(db).getAllCompanies();
                if (companies.isEmpty()) {
                    System.out.println("The company list is empty!");
                    return;
                }
                printCompanies(companies);
                System.out.println("0. Back");
                int itemMenu = 0;
                try {
                    itemMenu = new Scanner(System.in).nextInt();
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                if (itemMenu == 0) {
                    return;
                }
                showCarsInCompany(companies.get(itemMenu - 1).getId(), customerId);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printCompanies(ArrayList<Company> companies) {
        int index = 1;
        for (Company company : companies) {
            System.out.printf("%d. %s\n", index, company.getName());
            index++;
        }
    }

    public void showCarsInCompany(int companyId, int customerID) {
        try {
            ArrayList<Car> cars = new CarDaoImpl(db).getAllCarsFromCompany(companyId);
            if (cars.isEmpty()) {
                String companyName = new CompanyDaoImpl(db).getCompanyName(companyId);
                System.out.printf("No available cars in the %s company\n", companyName);
                return;
            }
            System.out.println("Choose a car:");
            printCars(cars);
            int carNum = new Scanner(System.in).nextInt();
            if (carNum == 0) return;
            Car car = cars.get(carNum - 1);
            System.out.printf("You rented '%s'\n", car.getName());
            new CustomerDaoImpl(db).updateRentCarId(customerID, car.getId());
            unavailableCars.add(car.getId());
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    public void printCars(ArrayList<Car> cars) {
        int index = 1;
        for (Car car : cars) {
            if (unavailableCars.contains(car.getId())) {
                continue;
            }
            System.out.printf("%d. %s\n", index, car.getName());
            index++;
        }
    }
}
