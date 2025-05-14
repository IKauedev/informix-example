package informix.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCVersionInfo {

  private static final Logger LOGGER = LoggerFactory.getLogger(JDBCVersionInfo.class);

  public static void main(String[] args) {
    try {
      Class.forName("com.informix.jdbc.IfxDriver");
    } catch (ClassNotFoundException e) {
      LOGGER.error("Driver JDBC Informix não encontrado no classpath", e);
      return;
    }

    String hostname = "[::1]";
    int port = 33378;
    String user = "informix";
    String password = "in4mix";
    String database = "stores_demo";
    String informixServer = "lo_informix1210";

    String jdbcUrl = String.format(
            "jdbc:informix-sqli://%s:%d/%s:INFORMIXSERVER=%s",
            hostname, port, database, informixServer
    );

    try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password)) {
      DatabaseMetaData dbmd = connection.getMetaData();

      LOGGER.info("Driver name ............ {}", dbmd.getDriverName());
      LOGGER.info("Driver version ......... {}", dbmd.getDriverVersion());
      LOGGER.info("Driver major version ... {}", dbmd.getDriverMajorVersion());
      LOGGER.info("Driver minor version ... {}", dbmd.getDriverMinorVersion());
      LOGGER.info("Database name .......... {}", dbmd.getDatabaseProductName());
      LOGGER.info("Database version ....... {}", dbmd.getDatabaseProductVersion());

    } catch (SQLException e) {
      LOGGER.error("Erro ao conectar ao banco ou extrair metadados JDBC: {}", e.getMessage(), e);
    }
  }
}
