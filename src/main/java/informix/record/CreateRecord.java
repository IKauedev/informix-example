package informix.record;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.informix.jdbc.IfmxStatement;

import informix.utils.ConnectionManager;

public class CreateRecord {
    private static final Logger log = LoggerFactory.getLogger(CreateRecord.class);

    public static void main(String[] args) {
        log.info("Welcome to create a record - {}", new Date());
        CreateRecord cr = new CreateRecord();
        int id = cr.insertNewCustomer("Jean Georges", "Perrin", "JGP.net", "510 Meadowmont Village Circle", "Suite 314",
                "Chapel Hill", "NC", "27517", "919-123-1234");
        if (id > 0) {
            System.out.println("Wonderful, a new customer is in your database! Its unique identifier is '" + id + "'.");
        }
    }

    public boolean executeDbOperation(String sqlStatement) {
        try (Statement statement = ConnectionManager.getStatement()) {
            if (statement == null) return false;
            statement.executeUpdate(sqlStatement);
            return true;
        } catch (SQLException e) {
            log.error("Could not execute the following SQL statement '{}', got: {}", sqlStatement, e.getMessage(), e);
            return false;
        }
    }

    public synchronized int insertNewCustomer(String fname, String lname, String company, String address1,
            String address2, String city, String state, String zipcode, String phone) {
        String sql = "INSERT INTO customer (customer_num, fname, lname, company, address1, address2, city, state, zipcode, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, 0);
            ps.setString(2, fname);
            ps.setString(3, lname);
            ps.setString(4, company);
            ps.setString(5, address1);
            ps.setString(6, address2);
            ps.setString(7, city);
            ps.setString(8, state);
            ps.setString(9, zipcode);
            ps.setString(10, phone);
            int rowCount = ps.executeUpdate();
            log.info("{} row(s) affected", rowCount);
            IfmxStatement ifxps = (IfmxStatement) ps;
            return ifxps.getSerial();
        } catch (SQLException e) {
            log.error("Error inserting new customer: {}", e.getMessage(), e);
            return -1;
        }
    }
}
