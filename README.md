# Informix Java/JDBC Examples

Este repositório contém exemplos de conexão e manipulação de dados no banco de dados IBM Informix usando Java e JDBC.

## Como conectar ao Informix

A conexão é realizada utilizando a URL JDBC padrão do Informix. Os parâmetros principais são:

- **HOSTNAME**: `[::1]` (ou `localhost`/`127.0.0.1` conforme seu ambiente)
- **PORT**: `33378`
- **DATABASE**: `stores_demo`
- **USER**: `informix`
- **PASSWORD**: `in4mix`
- **INFORMIXSERVER**: `lo_informix1210`

Exemplo de URL JDBC:
```
jdbc:informix-sqli://[::1]:33378/stores_demo:INFORMIXSERVER=lo_informix1210
```

## Exemplo de código Java

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SimpleInformixExample {
    public static void main(String[] args) throws Exception {
        Class.forName("com.informix.jdbc.IfxDriver");
        String url = "jdbc:informix-sqli://[::1]:33378/stores_demo:INFORMIXSERVER=lo_informix1210";
        String user = "informix";
        String password = "in4mix";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT FIRST 5 * FROM manufact")) {

            while (rs.next()) {
                System.out.println("manu_code: " + rs.getString("manu_code") +
                                   ", manu_name: " + rs.getString("manu_name"));
            }
        }
    }
}
```

## Executando com Docker

Você pode subir um banco Informix local usando o arquivo `docker-compose.yml` incluso neste projeto:

```sh
docker-compose up -d
```

Aguarde o banco inicializar e utilize os exemplos Java para conectar e executar comandos SQL.
