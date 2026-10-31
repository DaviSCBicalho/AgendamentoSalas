package pack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RelatorioDB {

    private static final DateTimeFormatter DATAHORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final String LINHA = "-".repeat(92);

    // Consulta base: junta reservas + salas + usuários (JOIN de 3 tabelas)
    private static final String BASE_RESERVAS =
            "SELECT r.idReserva, s.numSala, s.andar, u.matricula, u.nome, r.dtInicio, r.dtFim "
            + "FROM reservas r "
            + "JOIN salas s ON s.idSala = r.idSala "
            + "JOIN usuarios u ON u.matricula = r.matricula ";

    private static String formatarDuracao(long minutos) {
        return (minutos / 60) + "h" + String.format("%02d", minutos % 60) + "min";
    }

    public int contarRegistros(String tabela) {

        String sql = "select count(1) total_" + tabela + " from " + tabela;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao contar registros!");
            e.printStackTrace();
        }

        return 0;
    }

    // Imprime uma tabela de reservas usando a consulta base + um filtro opcional
    private void relatorioReservas(String titulo, String filtro, Object... parametros) {

        String sql = BASE_RESERVAS + filtro + "ORDER BY r.dtInicio";

        System.out.println("\n-= " + titulo + " =-");

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < parametros.length; i++) {
                stmt.setObject(i + 1, parametros[i]);
            }

            try (ResultSet rs = stmt.executeQuery()) {

                System.out.printf("%-8s %-6s %-6s %-10s %-22s %-17s %-17s%n",
                        "Reserva", "Sala", "Andar", "Matrícula", "Responsável", "Início", "Fim");
                System.out.println(LINHA);

                int total = 0;

                while (rs.next()) {
                    System.out.printf("%-8d %-6d %-6d %-10d %-22.22s %-17s %-17s%n",
                            rs.getInt("idReserva"),
                            rs.getInt("numSala"),
                            rs.getInt("andar"),
                            rs.getInt("matricula"),
                            rs.getString("nome"),
                            rs.getTimestamp("dtInicio").toLocalDateTime().format(DATAHORA),
                            rs.getTimestamp("dtFim").toLocalDateTime().format(DATAHORA));
                    total++;
                }

                if (total == 0) {
                    System.out.println("Nenhuma reserva encontrada.");
                } else {
                    System.out.println(LINHA);
                    System.out.println("Total: " + total + " reserva(s)");
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao gerar relatório!");
            e.printStackTrace();
        }
    }

    // [1] Todas as reservas, com dados da sala e do responsável
    public void reservasDetalhadas() {
        relatorioReservas("Todas as reservas", "");
    }

    // [2] Reservas de uma sala
    public void reservasPorSala(int idSala) {
        relatorioReservas("Reservas da sala (ID " + idSala + ")",
                "WHERE r.idSala = ? ", idSala);
    }

    // [3] Reservas de um usuário
    public void reservasPorUsuario(int matricula) {
        relatorioReservas("Reservas do usuário (matrícula " + matricula + ")",
                "WHERE r.matricula = ? ", matricula);
    }

    // [4] Reservas que começam dentro de um período (datas inclusivas)
    public void reservasPorPeriodo(LocalDate inicio, LocalDate fim) {
        relatorioReservas("Reservas entre "
                + inicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " e "
                + fim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                "WHERE r.dtInicio >= ? AND r.dtInicio < ? ",
                Timestamp.valueOf(inicio.atStartOfDay()),
                Timestamp.valueOf(fim.plusDays(1).atStartOfDay()));
    }

    // [5] Uso das salas: quantidade de reservas e tempo total (GROUP BY + agregações)
    public void usoPorSala() {

        String sql = "SELECT s.numSala, s.andar, COUNT(r.idReserva) AS total, "
                + "COALESCE(SUM(TIMESTAMPDIFF(MINUTE, r.dtInicio, r.dtFim)), 0) AS minutos "
                + "FROM salas s "
                + "LEFT JOIN reservas r ON r.idSala = s.idSala "
                + "GROUP BY s.idSala, s.numSala, s.andar "
                + "ORDER BY total DESC, s.numSala";

        System.out.println("\n-= Uso das salas =-");

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.printf("%-8s %-6s %-10s %-15s%n", "Sala", "Andar", "Reservas", "Tempo reservado");
            System.out.println("-".repeat(44));

            int linhas = 0;

            while (rs.next()) {
                System.out.printf("%-8d %-6d %-10d %-15s%n",
                        rs.getInt("numSala"),
                        rs.getInt("andar"),
                        rs.getInt("total"),
                        formatarDuracao(rs.getLong("minutos")));
                linhas++;
            }

            if (linhas == 0) {
                System.out.println("Nenhuma sala cadastrada.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao gerar relatório!");
            e.printStackTrace();
        }
    }

    // [6] Salas livres em um intervalo (NOT EXISTS com a mesma regra de conflito do ReservaDB)
    public void salasDisponiveis(LocalDateTime inicio, LocalDateTime fim) {

        String sql = "SELECT s.idSala, s.numSala, s.andar, s.qteAssentos, s.tamanho "
                + "FROM salas s "
                + "WHERE NOT EXISTS ("
                + "    SELECT 1 FROM reservas r "
                + "    WHERE r.idSala = s.idSala "
                + "    AND r.dtInicio < ? "
                + "    AND r.dtFim > ?) "
                + "ORDER BY s.numSala";

        System.out.println("\n-= Salas disponíveis de "
                + inicio.format(DATAHORA) + " até " + fim.format(DATAHORA) + " =-");

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(fim));
            stmt.setTimestamp(2, Timestamp.valueOf(inicio));

            try (ResultSet rs = stmt.executeQuery()) {

                int total = 0;

                while (rs.next()) {
                    Sala sala = new Sala(
                            rs.getInt("idSala"),
                            rs.getInt("numSala"),
                            rs.getInt("andar"),
                            rs.getInt("qteAssentos"),
                            rs.getDouble("tamanho"));

                    System.out.println("\n" + sala);
                    total++;
                }

                if (total == 0) {
                    System.out.println("Nenhuma sala disponível nesse horário.");
                } else {
                    System.out.println("\nTotal: " + total + " sala(s) disponível(is)");
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao gerar relatório!");
            e.printStackTrace();
        }
    }

    // [7] Ranking de usuários por número de reservas
    public void rankingUsuarios() {

        String sql = "SELECT u.matricula, u.nome, COUNT(r.idReserva) AS total "
                + "FROM usuarios u "
                + "LEFT JOIN reservas r ON r.matricula = u.matricula "
                + "GROUP BY u.matricula, u.nome "
                + "ORDER BY total DESC, u.nome";

        System.out.println("\n-= Ranking de usuários por reservas =-");

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.printf("%-10s %-30s %-10s%n", "Matrícula", "Nome", "Reservas");
            System.out.println("-".repeat(52));

            int linhas = 0;

            while (rs.next()) {
                System.out.printf("%-10d %-30.30s %-10d%n",
                        rs.getInt("matricula"),
                        rs.getString("nome"),
                        rs.getInt("total"));
                linhas++;
            }

            if (linhas == 0) {
                System.out.println("Nenhum usuário cadastrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao gerar relatório!");
            e.printStackTrace();
        }
    }
}