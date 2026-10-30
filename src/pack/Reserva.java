package pack;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reserva {
    private int idReserva, idSala, matricula;
    private LocalDate dtReserva;
    private LocalDateTime dtInicio, dtFim;

    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy"), DATAHORA = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

    public Reserva(int idReserva, int idSala, int matricula, LocalDate dtReserva, LocalDateTime dtInicio, LocalDateTime dtFim) {
        this.idReserva = idReserva;
        this.idSala = idSala;
        this.matricula = matricula;
        this.dtReserva = dtReserva;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public LocalDate getDtReserva() {
        return dtReserva;
    }

    public void setDtReserva(LocalDate dtReserva) {
        this.dtReserva = dtReserva;
    }

    public LocalDateTime getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(LocalDateTime dtInicio) {
        this.dtInicio = dtInicio;
    }

    public LocalDateTime getDtFim() {
        return dtFim;
    }

    public void setDtFim(LocalDateTime dtFim) {
        this.dtFim = dtFim;
    }

    @Override
    public String toString() {
        return "ID da reserva: " + this.idReserva
                + "\nID da sala: " + this.idSala
                + "\nMatrícula do responsável: " + this.matricula
                + "\nData e horário de início: " + this.dtInicio.format(DATAHORA)
                + "\nData e horário de fim: " + this.dtFim.format(DATAHORA)
                + "\nData da reserva: " + this.dtReserva.format(DATA);
    }
}
