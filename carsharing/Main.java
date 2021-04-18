package carsharing;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        Program program = new Program(args);
        program.start();
    }
}