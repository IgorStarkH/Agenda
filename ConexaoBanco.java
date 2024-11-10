package brincandocombanco1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {

    // Informações de conexão com o banco
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/agenda";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "marcelogomesrp";

    // Método para obter a conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
        }
    }
}
