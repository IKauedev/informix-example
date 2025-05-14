package informix.record;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import informix.utils.ConnectionManager;
import informix.utils.PrettyFormatter;

public class ReadRecord {
    private static final Logger log = LoggerFactory.getLogger(ReadRecord.class);

    public static void main(String[] args) {
        ReadRecord rr = new ReadRecord();
        rr.executeDbOperation("SELECT * FROM items ORDER BY item_num");
        rr.executeDbOperation("SELECT * FROM manufact ORDER BY manu_code");
        rr.executeDbOperation("SELECT * FROM items, manufact WHERE items.manu_code = manufact.manu_code ORDER BY item_num");
    }

    public boolean executeDbOperation(String query) {
        Statement statement = ConnectionManager.getStatement();
        if (statement == null) return false;
        try (ResultSet resultSet = statement.executeQuery(query)) {
            PrettyFormatter pf = new PrettyFormatter();
            pf.set(resultSet);
            pf.show();
            return true;
        } catch (SQLException e) {
            log.error("Could not get a result set: {}", e.getMessage(), e);
            return false;
        } finally {
            try { statement.close(); } catch (Exception ignore) {}
        }
    }
}
