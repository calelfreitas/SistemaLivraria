package sistemalivraria.dados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por estabelecer a conexão com o banco de dados PostgreSQL.
 */
public class ConexaoBD {

    // --- ATENÇÃO: Configure aqui os dados do seu banco PostgreSQL --- 
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres"; // Substitua 'livraria_db' pelo nome do seu banco
    private static final String USER = "postgres"; // Usuário padrão do PostgreSQL
    private static final String PASSWORD = "postgres"; // Defina a senha do seu usuário postgres
    // ----------------------------------------------------------------

    private static Connection conn = null;

    /**
     * Obtém uma conexão com o banco de dados.
     * Se a conexão ainda não foi estabelecida, ela é criada.
     *
     * @return Uma instância de Connection.
     * @throws SQLException Se ocorrer um erro ao conectar ao banco.
     */
    public static Connection getConexao() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                // Carrega o driver JDBC do PostgreSQL
                Class.forName("org.postgresql.Driver");
                // Estabelece a conexão
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            } catch (ClassNotFoundException e) {
                System.err.println("Erro: Driver JDBC do PostgreSQL não encontrado.");
                throw new SQLException("Driver não encontrado", e);
            } catch (SQLException e) {
                System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
                throw e; // Relança a exceção para ser tratada no nível superior
            }
        }
        return conn;
    }

    /**
     * Fecha a conexão com o banco de dados, se estiver aberta.
     */
    public static void fecharConexao() {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                    System.out.println("Conexão com o banco de dados fechada.");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
            }
        }
    }
}

