package sistemalivraria.dados;

import sistemalivraria.entidades.Livro;
import sistemalivraria.entidades.Editora;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO (Data Access Object) para a entidade Livro.
 * Modificado por Manus para alinhar com entidade e controle refatorados.
 */
public class LivroDAO {

    private EditoraDAO editoraDAO; // Para buscar a editora associada

    public LivroDAO() {
        this.editoraDAO = new EditoraDAO(); // Instancia o DAO de Editora
    }

    /**
     * Insere um novo livro no banco de dados.
     *
     * @param livro O objeto Livro a ser inserido.
     * @param idEditora O ID da editora associada ao livro.
     * @return O ID gerado para o livro inserido.
     * @throws SQLException Se ocorrer um erro durante a operação no banco.
     */
    public int inserir(Livro livro, int idEditora) throws SQLException {
        String sql = "INSERT INTO public.livros (id_editora, nome, autor, preco, categoria, isbn, quantidade) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, idEditora);
            pstmt.setString(2, livro.getTitulo());
            pstmt.setString(3, livro.getAutor());
            pstmt.setDouble(4, livro.getPreco());
            pstmt.setString(5, livro.getCategoria());
            pstmt.setString(6, livro.getIsbn());
            pstmt.setInt(7, livro.getEstoque());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                        livro.setId(generatedId); // Atualiza o ID no objeto
                    }
                }
            } else {
                throw new SQLException("Falha ao inserir livro, nenhuma linha afetada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir livro: " + e.getMessage());
            throw e;
        }
        return generatedId; // Retorna o ID gerado
    }

    /**
     * Busca um livro pelo seu ID, incluindo a editora associada.
     *
     * @param id O ID do livro a ser buscado.
     * @return O objeto Livro encontrado, ou null se não encontrado.
     * @throws SQLException Se ocorrer um erro durante a operação no banco.
     */
    public Livro buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, id_editora, nome, autor, preco, categoria, isbn, quantidade FROM public.livros WHERE id = ?";
        Livro livro = null;

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int idEditora = rs.getInt("id_editora");
                Editora editora = editoraDAO.buscarPorId(idEditora); // Busca a editora

                // Usa o construtor que aceita ID e objeto Editora
                livro = new Livro(
                        rs.getInt("id"),
                        rs.getString("nome"), // Mapeando nome para titulo
                        rs.getString("autor"),
                        rs.getString("isbn"),
                        editora, // Passa o objeto Editora
                        rs.getInt("quantidade"), // Mapeando quantidade para estoque
                        rs.getDouble("preco"),
                        rs.getString("categoria")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar livro por ID: " + e.getMessage());
            throw e;
        }
        return livro;
    }

    /**
     * Lista todos os livros no banco de dados, incluindo a editora associada.
     *
     * @return Uma lista de objetos Livro.
     * @throws SQLException Se ocorrer um erro durante a operação no banco.
     */
    public List<Livro> listarTodos() throws SQLException {
        String sql = "SELECT id, id_editora, nome, autor, preco, categoria, isbn, quantidade FROM public.livros ORDER BY nome";
        List<Livro> livros = new ArrayList<>();

        try (Connection conn = ConexaoBD.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                 int idEditora = rs.getInt("id_editora");
                 Editora editora = editoraDAO.buscarPorId(idEditora); // Busca a editora

                 // Usa o construtor que aceita ID e objeto Editora
                 Livro livro = new Livro(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("autor"),
                        rs.getString("isbn"),
                        editora,
                        rs.getInt("quantidade"),
                        rs.getDouble("preco"),
                        rs.getString("categoria")
                );
                livros.add(livro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar livros: " + e.getMessage());
            throw e;
        }
        return livros;
    }

    /**
     * Atualiza os dados de um livro existente no banco.
     *
     * @param livro O objeto Livro com os dados atualizados.
     * @param idEditora O ID da editora associada (redundante se já estiver no objeto Livro).
     * @return true se a atualização foi bem-sucedida, false caso contrário.
     * @throws SQLException Se ocorrer um erro durante a operação no banco.
     */
    public boolean atualizar(Livro livro, int idEditora) throws SQLException {
        // idEditora pode ser obtido de livro.getEditoraObj().getId() ou livro.getIdEditora()
        String sql = "UPDATE public.livros SET id_editora = ?, nome = ?, autor = ?, preco = ?, categoria = ?, isbn = ?, quantidade = ? WHERE id = ?";
        boolean atualizado = false;

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, livro.getIdEditora()); // Usa o ID da editora do objeto Livro
            pstmt.setString(2, livro.getTitulo());
            pstmt.setString(3, livro.getAutor());
            pstmt.setDouble(4, livro.getPreco());
            pstmt.setString(5, livro.getCategoria());
            pstmt.setString(6, livro.getIsbn());
            pstmt.setInt(7, livro.getEstoque());
            pstmt.setInt(8, livro.getId());

            int affectedRows = pstmt.executeUpdate();
            atualizado = affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar livro: " + e.getMessage());
            throw e;
        }
        return atualizado;
    }

    /**
     * Exclui um livro do banco de dados.
     *
     * @param id O ID do livro a ser excluído.
     * @return true se a exclusão foi bem-sucedida, false caso contrário.
     * @throws SQLException Se ocorrer um erro durante a operação no banco.
     */
    public boolean excluir(int id) throws SQLException {
        // Considerar verificar se há compras associadas antes de excluir
        String sqlDeleteLivro = "DELETE FROM public.livros WHERE id = ?";
        boolean excluido = false;

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement pstmtLivro = conn.prepareStatement(sqlDeleteLivro)) {

                pstmtLivro.setInt(1, id);
                int affectedRows = pstmtLivro.executeUpdate();
                excluido = affectedRows > 0;

        } catch (SQLException e) {
            // Se for erro de FK (livro em compra), pode lançar uma exceção mais específica
            System.err.println("Erro ao excluir livro: " + e.getMessage());
            throw e;
        }
        return excluido;
    }
}

