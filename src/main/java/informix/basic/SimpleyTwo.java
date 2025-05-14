package informix.basic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import informix.utils.ConnectionManager;

public class SimpleyTwo {
    private static final Logger log = LoggerFactory.getLogger(SimpleyTwo.class);

    public static void main(String[] args) {
        SimpleyTwo rr = new SimpleyTwo();
        rr.executeDbOperation("SELECT * FROM items ORDER BY item_num");
    }

    public boolean executeDbOperation(String query) {
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection != null ? connection.createStatement() : null;
             ResultSet resultSet = statement != null ? statement.executeQuery(query) : null) {

            if (resultSet == null) return false;
            if (resultSet.next()) {
                int itemNum = resultSet.getInt("item_num");
                int orderNum = resultSet.getInt("order_num");
                int stockNum = resultSet.getInt("stock_num");
                String manufacturerCode = resultSet.getString("manu_code");
                int quantity = resultSet.getInt("quantity");
                double totalPrice = resultSet.getDouble("total_price");

                String lineSep = "+--------+---------+---------+---------+--------+-----------+";
                System.out.println(lineSep);
                System.out.println("|item_num|order_num|stock_num|manu_code|quantity|total_price|");
                System.out.println(lineSep);
                System.out.printf("|%8d|%9d|%9d|%-9s|%8d|%11.2f|%n",
                        itemNum, orderNum, stockNum, manufacturerCode, quantity, totalPrice);
                System.out.println(lineSep);
            }
            return true;
        } catch (SQLException e) {
            log.error("Erro durante execução da query [{}]: {}", query, e.getMessage(), e);
            return false;
        }
    }
}
