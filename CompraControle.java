package sistemalivraria.controles;

import sistemalivraria.dados.CompraDAO;
import sistemalivraria.dados.LivroDAO; // Para buscar livro por ID
import sistemalivraria.dados.ClienteDAO; // Para buscar cliente por ID
import sistemalivraria.entidades.Cliente;
import sistemalivraria.entidades.Livro;
import sistemalivraria.entidades.Compra;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Calel e Diego (Modificado por Manus)
 */
public class CompraControle {

    private CompraDAO compraDAO;
    private LivroDAO livroDAO; // Adicionado para buscar livros
    private ClienteDAO clienteDAO; // Adicionado para buscar clientes

    public CompraControle() {
        this.compraDAO = new CompraDAO();
        this.livroDAO = new LivroDAO(); // Instanciar
        this.clienteDAO = new ClienteDAO(); // Instanciar
    }

    /**
     * Registra uma nova compra no banco de dados.
     *
     * @param cliente O cliente que realizou a compra.
     * @param livro O livro comprado.
     * @param dataCompra A data da compra.
     * @throws SQLException Se ocorrer um erro de banco de dados.
     * @throws IllegalArgumentException Se os dados de entrada forem inválidos.
     */
    public void registrarCompra(Cliente cliente, Livro livro, LocalDate dataCompra) throws SQLException, IllegalArgumentException {
        if (cliente == null || livro == null || dataCompra == null) {
            throw new IllegalArgumentException("Cliente, Livro e Data da Compra não podem ser nulos.");
        }
        if (cliente.getId() <= 0 || livro.getId() <= 0) {
             throw new IllegalArgumentException("IDs de Cliente e Livro devem ser válidos.");
        }

        // Validações adicionais podem ser incluídas aqui (ex: verificar estoque do livro)
        // A validação de estoque agora é feita dentro do DAO na transação

        // Chama o DAO para registrar a compra usando os IDs
        compraDAO.registrarCompra(cliente.getId(), livro.getId());

        // A lógica de criar o objeto Compra foi removida daqui, pois o DAO lida com a inserção direta.
        // A atualização de estoque também é feita no DAO.
    }

     /**
     * Lista todas as compras registradas.
     *
     * @return Uma lista de todas as compras.
     * @throws SQLException Se ocorrer um erro de banco de dados.
     */
    public List<Compra> listarTodasCompras() throws SQLException {
        // O DAO agora precisa retornar List<Compra> com objetos Cliente e Livro preenchidos
        return compraDAO.listarTodos();
    }

    // Outros métodos de controle para Compra podem ser adicionados aqui (ex: buscar por cliente, por data, etc.)
    public List<Compra> listarComprasPorCliente(int idCliente) throws SQLException {
         return compraDAO.listarComprasPorCliente(idCliente);
    }

}

