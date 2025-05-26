package sistemalivraria.dados;

import sistemalivraria.entidades.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO (Data Access Object) para a entidade Cliente.
 * Responsável pelas operações de CRUD (Create, Read, Update, Delete) no banco de dados.
 * Modificado por Manus para alinhar com a entidade Cliente refatorada.
 */
public class ClienteDAO {

    /**
     * Insere um novo cliente no banco de dados.
     *
     * @param cliente O objeto Cliente a ser inserido.
     * @return O ID gerado para o cliente inserido.
     * @throws SQLException Se ocorrer um erro durante a operação no banco.
     */
    public int inserir(Cliente cliente) throws SQLException {
        // Ajustado para usar tipo_pessoa (PF/PJ)
        String sql = "INSERT INTO public.cliente (nome, endereco, identificador, tipo_pessoa, telefone) VALUES (?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getEndereco());
            pstmt.setString(3, cliente.getCpfcnpj());
            pstmt.setString(4, cliente.getTipo()); // Corrigido: Usa getTipo() para PF/PJ
            pstmt.setString(5, cliente.getTelefone());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                        cliente.setId(generatedId); // Atualiza o ID no objeto
                    }
                }
            } else {
                throw new SQLException("Falha ao inserir cliente, nenhuma linha afetada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir cliente: " + e.getMessage());
            throw e;
        }
        return generatedId; // Retorna o ID gerado
    }

    /**
     * Busca um cliente pelo seu ID.
     *
     * @param id O ID do cliente a ser buscado.
     * @return O objeto Cliente encontrado, ou null se não encontrado.
     * @throws SQLException Se ocorrer um erro durante a operação no banco.
     */
    public Cliente buscarPorId(int id) throws SQLException {
        // Ajustado para usar tipo_pessoa (PF/PJ)
        String sql = "SELECT id, nome, endereco, identificador, tipo_pessoa, telefone, ativo FROM public.cliente WHERE id = ? AND ativo = true";
        Cliente cliente = null;

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Usa o construtor que aceita todos os campos, incluindo ID
                cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("identificador"),
                        rs.getString("telefone"),
                        rs.getString("endereco"),
                        rs.getString("tipo_pessoa") // Mapeando tipo_pessoa para tipo (PF/PJ)
                );
                // cliente.setAtivo(rs.getBoolean("ativo")); // Se precisar do campo ativo na entidade
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por ID: " + e.getMessage());
            throw e;
        }
        return cliente;
    }

    /**
     * Lista todos os clientes ativos no banco de dados.
     *
     * @return Uma lista de objetos Cliente.
     * @throws SQLException Se ocorrer um erro durante a operação no banco.
     */
    public List<Cliente> listarTodos() throws SQLException {
        // Ajustado para usar tipo_pessoa (PF/PJ)
        String sql = "SELECT id, nome, endereco, identificador, tipo_pessoa, telefone, ativo FROM public.cliente WHERE ativo = true ORDER BY nome";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = ConexaoBD.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                 // Usa o construtor que aceita todos os campos, incluindo ID
                Cliente cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("identificador"),
                        rs.getString("telefone"),
                        rs.getString("endereco"),
                        rs.getString("tipo_pessoa")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
            throw e;
        }
        return clientes;
    }

    /**
     * Atualiza os dados de um cliente existente no banco.
     *
     * @param cliente O objeto Cliente com os dados atualizados.
     * @return true se a atualização foi bem-sucedida, false caso contrário.
     * @throws SQLException Se ocorrer um erro durante a operação no banco.
     */
    public boolean atualizar(Cliente cliente) throws SQLException {
        // Ajustado para usar tipo_pessoa (PF/PJ)
        String sql = "UPDATE public.cliente SET nome = ?, endereco = ?, identificador = ?, tipo_pessoa = ?, telefone = ? WHERE id = ?";
        boolean atualizado = false;

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getEndereco());
            pstmt.setString(3, cliente.getCpfcnpj());
            pstmt.setString(4, cliente.getTipo()); // Corrigido: Usa getTipo() para PF/PJ
            pstmt.setString(5, cliente.getTelefone());
            pstmt.setInt(6, cliente.getId());

            int affectedRows = pstmt.executeUpdate();
            atualizado = affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
            throw e;
        }
        return atualizado;
    }

    /**
     * Exclui (logicamente) um cliente do banco de dados, marcando-o como inativo.
     *
     * @param id O ID do cliente a ser excluído.
     * @return true se a exclusão lógica foi bem-sucedida, false caso contrário.
     * @throws SQLException Se ocorrer um erro durante a operação no banco.
     */
    public boolean excluir(int id) throws SQLException {
        String sql = "UPDATE public.cliente SET ativo = false WHERE id = ?";
        boolean excluido = false;

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            excluido = affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir cliente: " + e.getMessage());
            throw e;
        }
        return excluido;
    }
}

