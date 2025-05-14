package informix.record;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import informix.utils.ConnectionManager;

public class UpdateRecord {
    private static final Logger log = LoggerFactory.getLogger(UpdateRecord.class);

    public static void main(String[] args) {
        ReadRecord rr = new ReadRecord();
        rr.executeDbOperation("SELECT * FROM manufact ORDER BY manu_code");

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextXmas = LocalDateTime.of(now.getYear(), Month.DECEMBER, 25, 0, 0);
        Duration leadTime = Duration.between(now, nextXmas);

        UpdateRecord ur = new UpdateRecord();
        ur.executeDbOperation("UPDATE manufact SET lead_time='" + leadTime.toDays() + "' WHERE manu_code='SNT'");

        rr.executeDbOperation("SELECT * FROM manufact ORDER BY manu_code");
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
}
