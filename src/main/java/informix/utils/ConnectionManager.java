package informix.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);

    private static final String HOSTNAME = "[::1]";
    private static final int PORT = 33378;
    private static final String USER = "informix";
    private static final String PASSWORD = "in4mix";
    private static final String DATABASE = "stores_demo";
    private static final String INFORMIX_SERVER = "lo_informix1210";

    private ConnectionManager() {}

    public static Connection getConnection() {
        try {
            Class.forName("com.informix.jdbc.IfxDriver");
            String jdbcUrl = String.format(
                "jdbc:informix-sqli://%s:%d/%s:INFORMIXSERVER=%s",
                HOSTNAME, PORT, DATABASE, INFORMIX_SERVER
            );
            return DriverManager.getConnection(jdbcUrl, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error("Erro ao estabelecer conexão com o banco de dados", e);
            return null;
        }
    }

    public static Statement getStatement() {
        Connection conn = getConnection();
        if (conn == null) {
            return null;
        }
        try {
            return conn.createStatement();
        } catch (SQLException e) {
            LOGGER.error("Não foi possível criar Statement", e);
            try { conn.close(); } catch (SQLException ex) { LOGGER.warn("Erro ao fechar conexão", ex); }
            return null;
        }
    }
}
