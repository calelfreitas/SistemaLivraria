package sistemalivraria.controles;

import sistemalivraria.dados.EditoraDAO;
import sistemalivraria.entidades.Editora;
import java.sql.SQLException;
import java.util.List;

/**
 * Classe de controle para gerenciar operações relacionadas a Editoras.
 * @author Calel e Diego (Modificado por Manus)
 */
public class EditoraControle {

    private EditoraDAO editoraDAO;

    public EditoraControle() {
        this.editoraDAO = new EditoraDAO();
    }

    /**
     * Cadastra uma nova editora.
     *
     * @param editora O objeto Editora a ser cadastrado.
     * @throws SQLException Se ocorrer erro no banco de dados.
     * @throws IllegalArgumentException Se dados inválidos forem fornecidos.
     */
    public void cadastrarEditora(Editora editora) throws SQLException, IllegalArgumentException {
        if (editora == null) {
            throw new IllegalArgumentException("Objeto Editora não pode ser nulo.");
        }
        if (editora.getNome() == null || editora.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da editora não pode ser vazio.");
        }
        // Adicionar mais validações se necessário

        // O DAO agora retorna o ID gerado, atualizamos o objeto
        int idGerado = editoraDAO.inserir(editora);
        if (idGerado > 0) {
            editora.setId(idGerado);
            System.out.println("Editora cadastrada com sucesso: " + editora.getNome() + " (ID: " + idGerado + ")");
        } else {
             System.err.println("Falha ao obter ID gerado para a editora: " + editora.getNome());
             // Considerar lançar exceção se o ID for crucial aqui
        }
    }

    /**
     * Busca uma editora pelo ID.
     *
     * @param id ID da editora.
     * @return A Editora encontrada ou null.
     * @throws SQLException Se ocorrer erro no banco de dados.
     */
    public Editora buscarEditoraPorId(int id) throws SQLException {
        return editoraDAO.buscarPorId(id);
    }

    /**
     * Lista todas as editoras.
     *
     * @return Lista de Editoras.
     * @throws SQLException Se ocorrer erro no banco de dados.
     */
    public List<Editora> listarTodasEditoras() throws SQLException {
        // Precisa garantir que o método listarTodos exista no DAO
        return editoraDAO.listarTodos();
    }

    /**
     * Atualiza os dados de uma editora existente.
     *
     * @param editora Objeto Editora com os dados atualizados (deve incluir o ID).
     * @return true se atualizado com sucesso, false caso contrário.
     * @throws SQLException Se ocorrer erro no banco de dados.
     * @throws IllegalArgumentException Se dados inválidos forem fornecidos.
     */
    public boolean atualizarEditora(Editora editora) throws SQLException, IllegalArgumentException {
        if (editora == null || editora.getId() <= 0) {
            throw new IllegalArgumentException("Dados da editora inválidos para atualização.");
        }
        // Adicionar mais validações
        // Precisa garantir que o método atualizar exista no DAO
        // return editoraDAO.atualizar(editora);
        System.err.println("Método atualizarEditora não implementado completamente (depende do DAO).");
        return false; // Placeholder
    }

    /**
     * Exclui uma editora pelo ID.
     *
     * @param id ID da editora a ser excluída.
     * @return true se excluído com sucesso, false caso contrário.
     * @throws SQLException Se ocorrer erro no banco de dados (ex: editora tem livros associados).
     */
    public boolean excluirEditora(int id) throws SQLException {
         if (id <= 0) {
             throw new IllegalArgumentException("ID da editora inválido para exclusão.");
        }
        // Precisa garantir que o método excluir exista no DAO
        // return editoraDAO.excluir(id);
         System.err.println("Método excluirEditora não implementado completamente (depende do DAO).");
        return false; // Placeholder
    }
}

