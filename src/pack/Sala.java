package pack;

public class Sala {
    private int numSala, andar, qteAssentos, idSala;
    private double tamanho;

    public Sala(int idSala, int numSala, int andar, int qteAssentos, double tamanho) {
        this.idSala = idSala;
        this.numSala = numSala;
        this.andar = andar;
        this.qteAssentos = qteAssentos;
        this.tamanho = tamanho;
    }

    public Sala(int idSala, int numSala, int andar, double tamanho) {
        this.idSala = idSala;
        this.numSala = numSala;
        this.andar = andar;
        this.tamanho = tamanho;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public int getNumSala() {
        return numSala;
    }

    public void setNumSala(int numSala) {
        this.numSala = numSala;
    }

    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }

    public int getQteAssentos() {
        return qteAssentos;
    }

    public void setQteAssentos(int qteAssentos) {
        this.qteAssentos = qteAssentos;
    }

    public double getTamanho() {
        return tamanho;
    }

    public void setTamanho(double tamanho) {
        this.tamanho = tamanho;
    }

    @Override
    public String toString() {
        if (this.qteAssentos != 0){
        return "Número da sala: " + this.numSala
                + "\nAndar: " + this.andar
                + "\nQuantidade de assentos: " + this.qteAssentos
                + "\nID da sala: " + this.idSala
                + "\nTamanho: " + this.tamanho + " m²";
        }else{
        return "Número da sala: " + this.numSala
                + "\nAndar: " + this.andar
                + "\nQuantidade de assentos: Não informado"
                + "\nID da sala: " + this.idSala
                + "\nTamanho: " + this.tamanho + " m²";
        }
    }
}