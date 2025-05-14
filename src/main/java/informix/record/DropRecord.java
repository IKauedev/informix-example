package informix.record;

import java.sql.SQLException;
import java.sql.Statement;

import informix.utils.ConnectionManager;

public class DropRecord {
    public static void main(String[] args) {
        DropRecord cr = new DropRecord();
        cr.executeDbOperation("DELETE FROM manufact WHERE manu_code = 'SNT'");
        cr.executeDbOperation("DELETE FROM customer WHERE lname = 'Perrin'");
    }

    public boolean executeDbOperation(String sqlStatement) {
        try (Statement statement = ConnectionManager.getStatement()) {
            if (statement == null) return false;
            statement.executeUpdate(sqlStatement);
            return true;
        } catch (SQLException e) {
            System.out.println("Could not execute the following SQL statement '" + sqlStatement + "', got: " + e.getMessage());
            return false;
        }
    }
}
