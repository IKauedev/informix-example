package informix.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrettyFormatter {
    private static final Logger log = LoggerFactory.getLogger(PrettyFormatter.class);

    private ResultSet resultSet;
    private boolean updated = false;
    private StringBuilder sb = new StringBuilder();

    public void set(ResultSet resultSet) {
        this.resultSet = resultSet;
        this.updated = true;
    }

    public void show() {
        if (updated) {
            if (!updateBuffer()) return;
        }
        System.out.println(sb.toString());
    }

    private boolean updateBuffer() {
        ResultSetMetaData rsmd;
        try {
            rsmd = resultSet.getMetaData();
        } catch (SQLException e) {
            log.error("Could not extract metadata from result set: {}", e.getMessage(), e);
            return false;
        }

        int columnCount;
        try {
            columnCount = rsmd.getColumnCount();
        } catch (SQLException e) {
            log.error("An exception was raised while trying to get the count of columns of the resultset: {}", e.getMessage(), e);
            return false;
        }

        List<PrettyFormatterColumn> columns = new ArrayList<>();
        try {
            for (int i = 1; i <= columnCount; i++) {
                PrettyFormatterColumn pfc = new PrettyFormatterColumn();
                pfc.setHeading(rsmd.getColumnName(i));
                pfc.setType(rsmd.getColumnType(i));
                pfc.setTypeName(rsmd.getColumnTypeName(i));
                pfc.setColumnWidth(rsmd.getColumnDisplaySize(i));
                columns.add(pfc);
            }
        } catch (SQLException e) {
            log.error("An exception was raised while accessing the metadata of the resultset: {}", e.getMessage(), e);
            return false;
        }

        StringBuilder columnHeading = new StringBuilder();
        StringBuilder rowSeparator = new StringBuilder();
        for (PrettyFormatterColumn pfc : columns) {
            columnHeading.append("|").append(pfc.getColumnName());
            rowSeparator.append("+").append(pfc.getDashes());
        }
        columnHeading.append("|");
        rowSeparator.append("+");

        sb.setLength(0);
        sb.append(rowSeparator).append('\n');
        sb.append(columnHeading).append('\n');
        sb.append(rowSeparator).append('\n');

        try {
            while (resultSet.next()) {
                sb.append('|');
                for (PrettyFormatterColumn pfc : columns) {
                    sb.append(pfc.getFormattedValue(resultSet)).append('|');
                }
                sb.append('\n');
            }
        } catch (SQLException e) {
            log.error("An exception was raised while navigating in the resultset: {}", e.getMessage(), e);
        }

        sb.append(rowSeparator).append('\n');
        return true;
    }
}
