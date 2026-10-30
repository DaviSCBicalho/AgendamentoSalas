
package pack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ReservaDB {

    private boolean horarioDisponivel(Connection conn, Reserva reserva,
            Integer idReservaIgnorada) throws SQLException {

        String sql = "SELECT idReserva FROM reservas "
                + "WHERE idSala = ? "
                + "AND dtInicio < ? "
                + "AND dtFim > ?";

        if (idReservaIgnorada != null) {
            sql += " AND idReserva <> ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reserva.getIdSala());
            stmt.setTimestamp(2, Timestamp.valueOf(reserva.getDtFim()));
            stmt.setTimestamp(3, Timestamp.valueOf(reserva.getDtInicio()));

            if (idReservaIgnorada != null) {
                stmt.setInt(4, idReservaIgnorada);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                return !rs.next();
            }
        }
    }

    public boolean realizarReserva(Reserva reserva) {

        if (reserva.getDtInicio() == null || reserva.getDtFim() == null
                || !reserva.getDtFim().isAfter(reserva.getDtInicio())) {
            System.out.println("Horário de reserva inválido!");
            return false;
        }

        String sql = "INSERT INTO reservas "
                + "(idReserva, idSala, matricula, dtInicio, dtFim, dtReserva) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar()) {

            if (!horarioDisponivel(conn, reserva, null)) {
                System.out.println("A sala já está reservada nesse horário!");
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, reserva.getIdReserva());
                stmt.setInt(2, reserva.getIdSala());
                stmt.setInt(3, reserva.getMatricula());
                stmt.setTimestamp(4, Timestamp.valueOf(reserva.getDtInicio()));
                stmt.setTimestamp(5, Timestamp.valueOf(reserva.getDtFim()));
                stmt.setDate(6, Date.valueOf(reserva.getDtReserva()));

                stmt.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterarReserva(Reserva reserva) {

        if (reserva.getDtInicio() == null || reserva.getDtFim() == null
                || !reserva.getDtFim().isAfter(reserva.getDtInicio())) {
            System.out.println("Horário de reserva inválido!");
            return false;
        }

        String sql = "UPDATE reservas SET "
                + "idSala = ?, matricula = ?, dtInicio = ?, "
                + "dtFim = ?, dtReserva = ? "
                + "WHERE idReserva = ?";

        try (Connection conn = Conexao.conectar()) {

            if (!horarioDisponivel(conn, reserva, reserva.getIdReserva())) {
                System.out.println("A sala já está reservada nesse horário!");
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, reserva.getIdSala());
                stmt.setInt(2, reserva.getMatricula());
                stmt.setTimestamp(3, Timestamp.valueOf(reserva.getDtInicio()));
                stmt.setTimestamp(4, Timestamp.valueOf(reserva.getDtFim()));
                stmt.setDate(5, Date.valueOf(reserva.getDtReserva()));
                stmt.setInt(6, reserva.getIdReserva());

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelarReserva(int idReserva) {

        String sql = "DELETE FROM reservas WHERE idReserva = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idReserva);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Reserva> visualizarReservas() {

        ArrayList<Reserva> reservas = new ArrayList<>();

        String sql = "SELECT * FROM reservas ORDER BY dtInicio";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Reserva reserva = new Reserva(
                        rs.getInt("idReserva"),
                        rs.getInt("idSala"),
                        rs.getInt("matricula"),
                        rs.getDate("dtReserva").toLocalDate(),
                        rs.getTimestamp("dtInicio").toLocalDateTime(),
                        rs.getTimestamp("dtFim").toLocalDateTime()
                );

                reservas.add(reserva);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservas;
    }

    public ArrayList<Sala> visualizarSalas() {

        SalaDB salaDB = new SalaDB();

        return salaDB.listarSalas();
    }
}
