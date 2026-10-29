package pack;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
//AUTORIA:
//Alécio Pires e Davi Bicalho
//Versão 0.1
public class Pessoa {
    private int matricula;
    private String nome, telefone;
    private LocalDate dataNasc;
    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Pessoa(int matricula, String nome, String telefone, LocalDate dataNasc) {
        this.matricula = matricula;
        this.nome = nome;
        this.telefone = telefone;
        this.dataNasc = dataNasc;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    @Override
    public String toString() {
        return "Nome: " + this.nome
                + "\nMatrícula: " + this.matricula
                + "\nTelefone: " + this.telefone
                + "\nData de nascimento: " + this.dataNasc.format(DATA);
    }
}
