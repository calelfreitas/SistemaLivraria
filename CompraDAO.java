package sistemalivraria.dados;

import sistemalivraria.entidades.Compra;
import sistemalivraria.entidades.Livro;
import sistemalivraria.entidades.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO (Data Access Object) para a entidade Compra (tabela livros_comprados).
 * Modificado por Manus para alinhar com a entidade Compra e o controle.
 */
public class CompraDAO {

    private ClienteDAO clienteDAO; // Para buscar detalhes do cliente
    private LivroDAO livroDAO;     // Para buscar detalhes do livro

    public CompraDAO() {
        // Instanciar DAOs necessários para buscar objetos completos
        this.clienteDAO = new ClienteDAO();
        this.livroDAO = new LivroDAO();
    }

    /**
     * Registra uma nova compra no banco de dados e atualiza o estoque do livro.
     * Operação transacional.
     *
     * @param idCliente O ID do cliente que realizou a compra.
     * @param idLivro O ID do livro comprado.
     * @throws SQLException Se ocorrer um erro durante a operação no banco ou se o livro não tiver estoque.
     */
    public void registrarCompra(int idCliente, int idLivro) throws SQLException {
        String sqlCompra = "INSERT INTO public.livros_comprados (id_cliente, id_livro, data_compra) VALUES (?, ?, CURRENT_TIMESTAMP)";
        String sqlUpdateEstoque = "UPDATE public.livros SET quantidade = quantidade - 1 WHERE id = ? AND quantidade > 0";

        Connection conn = null;
        try {
            conn = ConexaoBD.getConexao();
            conn.setAutoCommit(false); // Inicia transação

            // 1. Verifica e atualiza o estoque
            try (PreparedStatement pstmtEstoque = conn.prepareStatement(sqlUpdateEstoque)) {
                pstmtEstoque.setInt(1, idLivro);
                int affectedRowsEstoque = pstmtEstoque.executeUpdate();
                if (affectedRowsEstoque == 0) {
                    conn.rollback(); // Desfaz antes de lançar exceção
                    throw new SQLException("Falha ao registrar compra: Livro sem estoque ou não encontrado.");
                }
            }

            // 2. Insere o registro da compra
            try (PreparedStatement pstmtCompra = conn.prepareStatement(sqlCompra)) {
                pstmtCompra.setInt(1, idCliente);
                pstmtCompra.setInt(2, idLivro);
                int affectedRowsCompra = pstmtCompra.executeUpdate();
                if (affectedRowsCompra == 0) {
                    conn.rollback(); // Desfaz antes de lançar exceção
                    throw new SQLException("Falha ao registrar compra, nenhuma linha afetada na tabela de compras.");
                }
            }

            conn.commit(); // Confirma a transação

        } catch (SQLException e) {
            System.err.println("Erro ao registrar compra: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback(); // Desfaz a transação em caso de erro
                    System.err.println("Transação revertida.");
                } catch (SQLException ex) {
                    System.err.println("Erro ao reverter transação: " + ex.getMessage());
                }
            }
            throw e; // Relança a exceção para a camada de controle
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Restaura o modo de autocommit
                    // A conexão é fechada pelo try-with-resources do getConexao se implementado assim
                    // ou deve ser fechada explicitamente se não for.
                    // ConexaoBD.fecharConexao(conn); // Exemplo se necessário
                } catch (SQLException ex) {
                    System.err.println("Erro ao restaurar autocommit ou fechar conexão: " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Lista todas as compras registradas no banco de dados.
     *
     * @return Uma lista de objetos Compra, com Cliente e Livro associados.
     * @throws SQLException Se ocorrer um erro durante a operação no banco.
     */
    public List<Compra> listarTodos() throws SQLException {
        String sql = "SELECT id, id_cliente, id_livro, data_compra FROM public.livros_comprados ORDER BY data_compra DESC";
        List<Compra> compras = new ArrayList<>();

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int idCompra = rs.getInt("id");
                int idCliente = rs.getInt("id_cliente");
                int idLivro = rs.getInt("id_livro");
                Timestamp dataCompraTimestamp = rs.getTimestamp("data_compra");

                // Buscar objetos Cliente e Livro completos usando seus DAOs
                Cliente cliente = clienteDAO.buscarPorId(idCliente);
                Livro livro = livroDAO.buscarPorId(idLivro);

                // Criar objeto Compra com os objetos Cliente e Livro
                Compra compra = new Compra(idCompra, cliente, livro, dataCompraTimestamp);
                compras.add(compra);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar todas as compras: " + e.getMessage());
            throw e;
        }
        return compras;
    }


    /**
     * Lista todas as compras realizadas por um cliente específico.
     *
     * @param idCliente O ID do cliente.
     * @return Uma lista de objetos Compra.
     * @throws SQLException Se ocorrer um erro durante a operação no banco.
     */
    public List<Compra> listarComprasPorCliente(int idCliente) throws SQLException {
        String sql = "SELECT id, id_cliente, id_livro, data_compra FROM public.livros_comprados WHERE id_cliente = ? ORDER BY data_compra DESC";
        List<Compra> compras = new ArrayList<>();

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                 int idCompra = rs.getInt("id");
                // int idCliente = rs.getInt("id_cliente"); // Já temos o idCliente
                int idLivro = rs.getInt("id_livro");
                Timestamp dataCompraTimestamp = rs.getTimestamp("data_compra");

                // Buscar objetos Cliente e Livro completos usando seus DAOs
                Cliente cliente = clienteDAO.buscarPorId(idCliente);
                Livro livro = livroDAO.buscarPorId(idLivro);

                // Criar objeto Compra com os objetos Cliente e Livro
                Compra compra = new Compra(idCompra, cliente, livro, dataCompraTimestamp);
                compras.add(compra);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar compras do cliente: " + e.getMessage());
            throw e;
        }
        return compras;
    }

    // Outros métodos podem ser adicionados conforme necessário.
}

