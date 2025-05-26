package sistemalivraria.controles;

import sistemalivraria.dados.LivroDAO;
import sistemalivraria.entidades.Livro;
import sistemalivraria.entidades.Editora;
import java.sql.SQLException;
import java.util.List;

/**
 * Classe de controle para gerenciar operações relacionadas a Livros.
 * @author Calel e Diego (Modificado por Manus)
 */
public class LivroControle {

    private LivroDAO livroDAO;

    public LivroControle() {
        this.livroDAO = new LivroDAO();
    }

    /**
     * Cadastra um novo livro.
     *
     * @param livro O objeto Livro a ser cadastrado.
     * @throws SQLException Se ocorrer erro no banco de dados.
     * @throws IllegalArgumentException Se dados inválidos forem fornecidos.
     */
    public void cadastrarLivro(Livro livro) throws SQLException, IllegalArgumentException {
        if (livro == null) {
            throw new IllegalArgumentException("Objeto Livro não pode ser nulo.");
        }
        if (livro.getTitulo() == null || livro.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("Título do livro não pode ser vazio.");
        }
        if (livro.getIsbn() == null || livro.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN não pode ser vazio.");
        }
        if (livro.getEditoraObj() == null || livro.getEditoraObj().getId() <= 0) {
            throw new IllegalArgumentException("Editora associada ao livro é inválida.");
        }
        if (livro.getEstoque() < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo.");
        }
        if (livro.getPreco() < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo.");
        }

        // O DAO agora retorna o ID gerado, atualizamos o objeto
        int idGerado = livroDAO.inserir(livro, livro.getEditoraObj().getId());
        if (idGerado > 0) {
            livro.setId(idGerado);
            System.out.println("Livro cadastrado com sucesso: " + livro.getTitulo() + " (ID: " + idGerado + ")");
        } else {
             System.err.println("Falha ao obter ID gerado para o livro: " + livro.getTitulo());
             // Considerar lançar exceção se o ID for crucial aqui
        }
    }

    /**
     * Busca um livro pelo ID.
     *
     * @param id ID do livro.
     * @return O Livro encontrado ou null.
     * @throws SQLException Se ocorrer erro no banco de dados.
     */
    public Livro buscarLivroPorId(int id) throws SQLException {
        // O DAO buscarPorId precisa carregar a Editora associada
        return livroDAO.buscarPorId(id);
    }

    /**
     * Lista todos os livros.
     *
     * @return Lista de Livros.
     * @throws SQLException Se ocorrer erro no banco de dados.
     */
    public List<Livro> listarTodosLivros() throws SQLException {
        // O DAO listarTodos precisa carregar a Editora associada
        return livroDAO.listarTodos();
    }

    /**
     * Atualiza os dados de um livro existente.
     *
     * @param livro Objeto Livro com os dados atualizados (deve incluir o ID).
     * @return true se atualizado com sucesso, false caso contrário.
     * @throws SQLException Se ocorrer erro no banco de dados.
     * @throws IllegalArgumentException Se dados inválidos forem fornecidos.
     */
    public boolean atualizarLivro(Livro livro) throws SQLException, IllegalArgumentException {
        if (livro == null || livro.getId() <= 0) {
            throw new IllegalArgumentException("Dados do livro inválidos para atualização.");
        }
        if (livro.getEditoraObj() == null || livro.getEditoraObj().getId() <= 0) {
             throw new IllegalArgumentException("Editora associada inválida para atualização do livro.");
        }
        // Adicionar mais validações

        // Precisa garantir que o método atualizar exista no DAO e aceite o objeto Livro
        // return livroDAO.atualizar(livro, livro.getEditoraObj().getId());
        System.err.println("Método atualizarLivro não implementado completamente (depende do DAO).");
        return false; // Placeholder
    }

    /**
     * Exclui um livro pelo ID.
     *
     * @param id ID do livro a ser excluído.
     * @return true se excluído com sucesso, false caso contrário.
     * @throws SQLException Se ocorrer erro no banco de dados (ex: livro está em compras).
     */
    public boolean excluirLivro(int id) throws SQLException {
         if (id <= 0) {
             throw new IllegalArgumentException("ID do livro inválido para exclusão.");
        }
        // Precisa garantir que o método excluir exista no DAO
        // return livroDAO.excluir(id);
         System.err.println("Método excluirLivro não implementado completamente (depende do DAO).");
        return false; // Placeholder
    }
}

