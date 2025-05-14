package informix.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class JDBCVersionInfoWithConnectionManager {

  public static void main(String[] args) {
    try (Connection connection = ConnectionManager.getConnection()) {
      if (connection == null) {
        System.out.println("Could not get a connection to the database.");
        return;
      }
      DatabaseMetaData dbmd = connection.getMetaData();

      System.out.println("Driver name ............ " + dbmd.getDriverName());
      System.out.println("Driver version ......... " + dbmd.getDriverVersion());
      System.out.println("Driver major version ... " + dbmd.getDriverMajorVersion());
      System.out.println("Driver minor version ... " + dbmd.getDriverMinorVersion());
      System.out.println("Database name .......... " + dbmd.getDatabaseProductName());
      System.out.println("Database version ....... " + dbmd.getDatabaseProductVersion());
    } catch (SQLException e) {
      System.out.println("An error occurred while accessing metadata: " + e.getMessage());
    }
  }
}
