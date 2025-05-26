package sistemalivraria.controles;

import sistemalivraria.dados.ClienteDAO;
import sistemalivraria.entidades.Cliente;
import java.sql.SQLException;
import java.util.List;

/**
 * Classe de controle para gerenciar operações relacionadas a Clientes.
 * Interage com a camada de dados (DAO) e a camada de apresentação (Telas).
 * @author Calel e Diego (Modificado por Manus)
 */
public class ClienteControle {

    private ClienteDAO clienteDAO;

    public ClienteControle() {
        this.clienteDAO = new ClienteDAO();
    }

    /**
     * Cadastra um novo cliente.
     *
     * @param cliente O objeto Cliente a ser cadastrado.
     * @throws SQLException Se ocorrer erro no banco de dados.
     * @throws IllegalArgumentException Se dados inválidos forem fornecidos.
     */
    public void cadastrarCliente(Cliente cliente) throws SQLException, IllegalArgumentException {
        // Validações básicas (podem ser expandidas)
        if (cliente == null) {
             throw new IllegalArgumentException("Objeto Cliente não pode ser nulo.");
        }
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente não pode ser vazio.");
        }
        if (cliente.getCpfcnpj() == null || cliente.getCpfcnpj().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF/CNPJ não pode ser vazio.");
        }
        // Adicionar mais validações (formato CPF/CNPJ, email, etc.)

        // O DAO agora retorna o ID gerado, atualizamos o objeto
        int idGerado = clienteDAO.inserir(cliente);
        if (idGerado > 0) {
             cliente.setId(idGerado);
             System.out.println("Cliente cadastrado com sucesso: " + cliente.getNome() + " (ID: " + idGerado + ")");
        } else {
             System.err.println("Falha ao obter ID gerado para o cliente: " + cliente.getNome());
             // Considerar lançar exceção se o ID for crucial aqui
        }
    }

    /**
     * Busca um cliente pelo ID.
     *
     * @param id ID do cliente.
     * @return O Cliente encontrado ou null.
     * @throws SQLException Se ocorrer erro no banco de dados.
     */
    public Cliente buscarClientePorId(int id) throws SQLException {
        return clienteDAO.buscarPorId(id);
    }

    /**
     * Lista todos os clientes ativos.
     *
     * @return Lista de Clientes.
     * @throws SQLException Se ocorrer erro no banco de dados.
     */
    public List<Cliente> listarTodosClientes() throws SQLException {
        return clienteDAO.listarTodos();
    }

    /**
     * Atualiza os dados de um cliente existente.
     *
     * @param cliente Objeto Cliente com os dados atualizados (deve incluir o ID).
     * @return true se atualizado com sucesso, false caso contrário.
     * @throws SQLException Se ocorrer erro no banco de dados.
     * @throws IllegalArgumentException Se dados inválidos forem fornecidos.
     */
    public boolean atualizarCliente(Cliente cliente) throws SQLException, IllegalArgumentException {
        if (cliente == null || cliente.getId() <= 0) {
            throw new IllegalArgumentException("Dados do cliente inválidos para atualização.");
        }
        // Adicionar mais validações
        return clienteDAO.atualizar(cliente);
    }

    /**
     * Exclui (logicamente) um cliente pelo ID.
     *
     * @param id ID do cliente a ser excluído.
     * @return true se excluído com sucesso, false caso contrário.
     * @throws SQLException Se ocorrer erro no banco de dados.
     */
    public boolean excluirCliente(int id) throws SQLException {
        if (id <= 0) {
             throw new IllegalArgumentException("ID do cliente inválido para exclusão.");
        }
        return clienteDAO.excluir(id);
    }
}

