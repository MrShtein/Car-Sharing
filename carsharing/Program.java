package carsharing;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {

    String[] args;
    Db db;

    public Program(String[] args) {
        this.args = args;
    }

    public void start() {
        boolean work = true;
        ArgsHandler argsHandler = new ArgsHandler(args);
        String tableName = argsHandler.getName();
        db = new Db(tableName);
        db.start();

        while (work) {
            System.out.println("1. Log in as a manager");
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
                default:
                    throw new IllegalArgumentException("Choose 0 or 1");
            }
        }
    }

    public void companyMenu() {
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

    public ArrayList<Company> getCompanyList(Db db) {
        List<Company> companies = null;
        CompanyDaoImpl companyDao = new CompanyDaoImpl(db);
        try {
            companies = companyDao.getAllCompanies();
        } catch (SQLException e) {
            System.out.println("Something wrong with data");
        }
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            for (Company item : companies) {
                System.out.printf("%d. %s\n", item.getId(), item.getName());
            }
        }
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
        ArrayList<Company> companies = getCompanyList(db);
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println("Choose a company:");
            printCompanyList(companies);
            int company_id = new Scanner(System.in).nextInt();

            while (companyMenu) {
                printCompanyMenu();
                int companyMenuItem = new Scanner(System.in).nextInt();

                switch (companyMenuItem) {
                    case 1:
                        showCarList(db, company_id);
                        break;
                    case 2:
                        addNewCar(db, company_id);
                        break;
                    case 0:
                        companyMenu = false;
                        break;
                    default:
                        throw new IllegalArgumentException("Menu item is wrong");
                }
            }

        }
    }

    public void printCompanyList(ArrayList<Company> companies) {
        for (Company item : companies) {
            System.out.printf("%d. %s\n", item.getId(), item.getName());
        }
    }

    public void printCompanyMenu() {
        System.out.println("'Company name' company:\n" +
                "1. Car list\n" +
                "2. Create a car\n" +
                "0. Back");
    }

    public void showCarList() {

    }
}
