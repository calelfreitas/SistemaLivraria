package sistemalivraria.entidades;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter; // Import adicionado na posição correta

/**
 *
 * @author Calel e Diego (Modificado por Manus)
 */
public class Compra {
    private int id;
    private Cliente cliente; // Usar objeto Cliente
    private Livro livro;     // Usar objeto Livro
    private LocalDate dataCompra; // Usar LocalDate
    private Timestamp dataCompraTimestamp; // Para compatibilidade com BD

    // Construtor principal usado pelo Controle
    public Compra(int id, Cliente cliente, Livro livro, LocalDate dataCompra) {
        this.id = id;
        this.cliente = cliente;
        this.livro = livro;
        this.dataCompra = dataCompra;
        // Converter LocalDate para Timestamp (considerando início do dia)
        if (dataCompra != null) {
            this.dataCompraTimestamp = Timestamp.valueOf(dataCompra.atStartOfDay());
        } else {
             this.dataCompraTimestamp = null;
        }
    }

    // Construtor para uso pelo DAO ao recuperar do banco
    // Assume que o DAO recuperará Cliente e Livro completos ou seus IDs
    public Compra(int id, Cliente cliente, Livro livro, Timestamp dataCompraTimestamp) {
        this.id = id;
        this.cliente = cliente;
        this.livro = livro;
        this.dataCompraTimestamp = dataCompraTimestamp;
        // Converter Timestamp para LocalDate
        if (dataCompraTimestamp != null) {
            this.dataCompra = dataCompraTimestamp.toLocalDateTime().toLocalDate();
        } else {
            this.dataCompra = null;
        }
    }

    // Getters
    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Livro getLivro() {
        return livro;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public Timestamp getDataCompraTimestamp() {
        // Garante que o timestamp esteja sincronizado com LocalDate se este for alterado
        if (this.dataCompra != null && (this.dataCompraTimestamp == null || !this.dataCompra.equals(this.dataCompraTimestamp.toLocalDateTime().toLocalDate()))) {
             this.dataCompraTimestamp = Timestamp.valueOf(this.dataCompra.atStartOfDay());
        }
        return dataCompraTimestamp;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
        // Atualiza o Timestamp quando LocalDate é alterado
        if (dataCompra != null) {
            this.dataCompraTimestamp = Timestamp.valueOf(dataCompra.atStartOfDay());
        } else {
             this.dataCompraTimestamp = null;
        }
    }

     public void setDataCompraTimestamp(Timestamp dataCompraTimestamp) {
        this.dataCompraTimestamp = dataCompraTimestamp;
        // Atualiza o LocalDate quando Timestamp é alterado
        if (dataCompraTimestamp != null) {
            this.dataCompra = dataCompraTimestamp.toLocalDateTime().toLocalDate();
        } else {
            this.dataCompra = null;
        }
    }

    // Método toString para exibição
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = (dataCompra != null) ? dataCompra.format(formatter) : "N/A";
        String nomeCliente = (cliente != null) ? cliente.getNome() : "N/A";
        String tituloLivro = (livro != null) ? livro.getTitulo() : "N/A";
        return "Compra [ID=" + id + ", Cliente=" + nomeCliente + ", Livro=" + tituloLivro + ", Data=" + dataFormatada + "]";
    }
}
// Removido import duplicado/incorreto do final do arquivo

