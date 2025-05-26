package sistemalivraria.dados;

import sistemalivraria.entidades.Editora;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO para a entidade Editora.
 * Modificado por Manus para retornar ID gerado.
 */
public class EditoraDAO {

    /**
     * Insere uma nova editora no banco de dados.
     *
     * @param editora O objeto Editora a ser inserido.
     * @return O ID gerado para a editora inserida.
     * @throws SQLException Se ocorrer um erro durante a operação no banco.
     */
    public int inserir(Editora editora) throws SQLException {
        String sql = "INSERT INTO public.editora (nome, endereco, telefone, gerente, categoria) VALUES (?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, editora.getNome());
            pstmt.setString(2, editora.getEndereco());
            pstmt.setString(3, editora.getTelefone());
            pstmt.setString(4, editora.getGerente());
            pstmt.setString(5, editora.getCategoria());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                        editora.setId(generatedId); // Atualiza o ID no objeto
                    }
                }
            } else {
                throw new SQLException("Falha ao inserir editora, nenhuma linha afetada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir editora: " + e.getMessage());
            throw e;
        }
        return generatedId;
    }

    /**
     * Busca uma editora pelo ID.
     *
     * @param id O ID da editora a ser buscada.
     * @return O objeto Editora encontrado, ou null se não encontrado.
     * @throws SQLException Se ocorrer um erro durante a operação no banco.
     */
    public Editora buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nome, endereco, telefone, gerente, categoria FROM public.editora WHERE id = ?";
        Editora editora = null;

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                editora = new Editora(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getString("telefone"),
                    rs.getString("gerente"),
                    rs.getString("categoria")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar editora por ID: " + e.getMessage());
            throw e;
        }
        return editora;
    }

     /**
     * Busca uma editora pelo Nome.
     *
     * @param nome O Nome da editora a ser buscada.
     * @return O objeto Editora encontrado, ou null se não encontrado.
     * @throws SQLException Se ocorrer um erro durante a operação no banco.
     */
    public Editora buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT id, nome, endereco, telefone, gerente, categoria FROM public.editora WHERE nome = ?";
        Editora editora = null;

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                editora = new Editora(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getString("telefone"),
                    rs.getString("gerente"),
                    rs.getString("categoria")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar editora por Nome: " + e.getMessage());
            throw e;
        }
        return editora;
    }

    /**
     * Lista todas as editoras cadastradas.
     *
     * @return Uma lista de objetos Editora.
     * @throws SQLException Se ocorrer um erro durante a operação no banco.
     */
    public List<Editora> listarTodos() throws SQLException {
        String sql = "SELECT id, nome, endereco, telefone, gerente, categoria FROM public.editora ORDER BY nome";
        List<Editora> editoras = new ArrayList<>();

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Editora editora = new Editora(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getString("telefone"),
                    rs.getString("gerente"),
                    rs.getString("categoria")
                );
                editoras.add(editora);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar editoras: " + e.getMessage());
            throw e;
        }
        return editoras;
    }

    // Métodos para atualizar e deletar podem ser adicionados aqui.

}

