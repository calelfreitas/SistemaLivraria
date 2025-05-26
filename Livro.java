package sistemalivraria.entidades;

/**
 *
 * @author Calel e Diego (Modificado por Manus)
 */
public class Livro {

    private int id;
    private String titulo; // Mapeado de 'nome' no banco
    private String autor;
    private String isbn;
    private int idEditora;
    private Editora editoraObj; // Objeto Editora completo
    private int estoque; // Mapeado de 'quantidade' no banco
    private double preco;
    private String categoria; // Mapeado de 'categoria'/'assunto'

    // Construtor para criar um novo livro (sem ID ainda)
    // Usado antes de inserir no banco
    public Livro(String titulo, String autor, String isbn, Editora editoraObj, int estoque, double preco, String categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.editoraObj = editoraObj;
        if (editoraObj != null) {
            this.idEditora = editoraObj.getId();
        } else {
            this.idEditora = 0; // Ou lançar exceção se editora for obrigatória
        }
        this.estoque = estoque;
        this.preco = preco;
        this.categoria = categoria;
    }

    // Construtor para criar um livro a partir de dados do banco (com ID)
    // Usado pelo DAO ao recuperar dados
    public Livro(int id, String titulo, String autor, String isbn, Editora editoraObj, int estoque, double preco, String categoria) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.editoraObj = editoraObj;
         if (editoraObj != null) {
            this.idEditora = editoraObj.getId();
        } else {
            this.idEditora = 0; // Considerar como tratar editora nula vinda do banco
        }
        this.estoque = estoque;
        this.preco = preco;
        this.categoria = categoria;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getIdEditora() {
        // Retorna o ID do objeto Editora se ele existir, senão o idEditora armazenado
        return (editoraObj != null) ? editoraObj.getId() : idEditora;
    }

    public void setIdEditora(int idEditora) {
        this.idEditora = idEditora;
        // Se o ID for setado manualmente, o objeto Editora pode ficar dessincronizado
        if (this.editoraObj != null && this.editoraObj.getId() != idEditora) {
            this.editoraObj = null; // Invalida o objeto para forçar recarregamento se necessário
        }
    }

    public Editora getEditoraObj() {
        // Idealmente, carregar sob demanda se for null e idEditora > 0
        // Ex: if (editoraObj == null && idEditora > 0) { editoraObj = editoraDAO.buscarPorId(idEditora); }
        return editoraObj;
    }

    public void setEditoraObj(Editora editoraObj) {
        this.editoraObj = editoraObj;
        if (editoraObj != null) {
            this.idEditora = editoraObj.getId();
        } else {
            this.idEditora = 0; // Ou manter o ID anterior?
        }
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // Método toString para facilitar a exibição (ex: em ComboBoxes)
    @Override
    public String toString() {
        return titulo + " (" + autor + ")";
    }
}

