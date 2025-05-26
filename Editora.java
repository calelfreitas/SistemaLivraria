package sistemalivraria.entidades;
/**
 *
 * @author Calel e Diego (Modificado por Manus)
 */
public class Editora {

    private int id;
    private String nome;
    private String endereco;
    private String telefone;
    private String gerente;
    private String categoria;

    // Construtor para criar uma nova editora (sem ID ainda)
    public Editora(String nome, String endereco, String telefone, String gerente, String categoria) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.gerente = gerente;
        this.categoria = categoria;
    }

    // Construtor para criar uma editora a partir de dados do banco (com ID)
    public Editora(int id, String nome, String endereco, String telefone, String gerente, String categoria) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.gerente = gerente;
        this.categoria = categoria;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getGerente() {
        return gerente;
    }

    public void setGerente(String gerente) {
        this.gerente = gerente;
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
        return nome; // Ou algo mais descritivo se necessário
    }
}

