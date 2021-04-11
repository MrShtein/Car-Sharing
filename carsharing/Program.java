package carsharing;

import java.util.Scanner;

public class Program {
    Scanner scanner = new Scanner(System.in);


    public void start() {
        boolean work = true;

        while (work) {
            System.out.println("1. Log in as a manager");
            System.out.println("0. Exit");
            int item = 0;
            try {
                item = scanner.nextInt();
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
                    getCompanyList();
                    break;
                case 2:
                    createNewCompany();
                    break;
                case 0:
                    companyMenuWork = false;
                    break;
                default:
                    throw new IllegalArgumentException("Choose correct menu item");
            }
        }

    }

}
