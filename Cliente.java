package sistemalivraria.entidades;

/**
 *
 * @author Calel e Diego (Modificado por Manus)
 * Refatorado para alinhar com o schema do banco (sem email, com tipo PF/PJ).
 */
public class Cliente {

    private int id;
    private String nome;
    private String endereco;
    private String telefone;
    private String cpfcnpj; // Mapeado para 'cpf' no banco
    private String tipo; // Mapeado para 'tipo_pessoa' (PF/PJ) no banco
    // private boolean ativo; // Campo 'ativo' do banco não mapeado diretamente

    // Construtor para criar um novo cliente (sem ID ainda)
    public Cliente(String nome, String cpfcnpj, String telefone, String endereco, String tipo) {
        this.nome = nome;
        this.cpfcnpj = cpfcnpj;
        this.telefone = telefone;
        this.endereco = endereco;
        this.tipo = tipo;
    }

    // Construtor para criar um cliente a partir de dados do banco (com ID)
    public Cliente(int id, String nome, String cpfcnpj, String telefone, String endereco, String tipo) {
        this.id = id;
        this.nome = nome;
        this.cpfcnpj = cpfcnpj;
        this.telefone = telefone;
        this.endereco = endereco;
        this.tipo = tipo;
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

    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        this.cpfcnpj = cpfcnpj;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Método toString para exibição
    @Override
    public String toString() {
        return nome; // Ou algo mais descritivo
    }
}

