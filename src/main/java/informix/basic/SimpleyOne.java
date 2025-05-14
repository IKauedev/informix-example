package informix.basic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import informix.utils.ConnectionManager;

public class SimpleyOne {
    private static final Logger log = LoggerFactory.getLogger(SimpleyOne.class);

    public static void main(String[] args) {
        SimpleyOne app = new SimpleyOne();
        app.executeDbOperation("SELECT * FROM items ORDER BY item_num");
        app.executeDbOperation("SELECT * FROM items WHERE total_price < 50 ORDER BY total_price");
    }

    public boolean executeDbOperation(String query) {
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection != null ? connection.createStatement() : null;
             ResultSet resultSet = statement != null ? statement.executeQuery(query) : null) {

            if (resultSet == null) return false;
            printResultSet(resultSet);
            return true;

        } catch (SQLException e) {
            log.error("Erro durante execução da query [{}]: {}", query, e.getMessage(), e);
            return false;
        }
    }

    private void printResultSet(ResultSet resultSet) throws SQLException {
        String lineSep = "+--------+---------+---------+---------+--------+-----------+";
        System.out.println(lineSep);
        System.out.println("|item_num|order_num|stock_num|manu_code|quantity|total_price|");
        System.out.println(lineSep);

        while (resultSet.next()) {
            int itemNum = resultSet.getInt("item_num");
            int orderNum = resultSet.getInt("order_num");
            int stockNum = resultSet.getInt("stock_num");
            String manufacturerCode = resultSet.getString("manu_code");
            int quantity = resultSet.getInt("quantity");
            double totalPrice = resultSet.getDouble("total_price");

            System.out.printf("|%8d|%9d|%9d|%-9s|%8d|%11.2f|%n",
                    itemNum, orderNum, stockNum, manufacturerCode, quantity, totalPrice);
        }
        System.out.println(lineSep);
    }
}
