package pack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalaDB {

    public boolean cadastrarSala(Sala sala) {

        String verificar = 
            "SELECT idSala FROM salas WHERE idSala = ? OR numSala = ?";

        String inserir =
            "INSERT INTO salas (idSala, numSala, andar, qteAssentos, tamanho) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar()) {

            try (PreparedStatement stmt = conexao.prepareStatement(verificar)) {

                stmt.setInt(1, sala.getIdSala());
                stmt.setInt(2, sala.getNumSala());

                try (ResultSet resultado = stmt.executeQuery()) {
                    if (resultado.next()) {
                        System.out.println("ID ou número da sala já cadastrado!");
                        return false;
                    }
                }
            }

            try (PreparedStatement stmt = conexao.prepareStatement(inserir)) {

                stmt.setInt(1, sala.getIdSala());
                stmt.setInt(2, sala.getNumSala());
                stmt.setInt(3, sala.getAndar());
                stmt.setInt(4, sala.getQteAssentos());
                stmt.setDouble(5, sala.getTamanho());

                stmt.executeUpdate();

                System.out.println("Sala cadastrada com sucesso!");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar sala!");
            e.printStackTrace();
            return false;
        }
    }


    public Sala consultarSala(int idSala) {

        String sql = "SELECT * FROM salas WHERE idSala = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idSala);

            try (ResultSet resultado = stmt.executeQuery()) {

                if (resultado.next()) {

                    return new Sala(
                        resultado.getInt("idSala"),
                        resultado.getInt("numSala"),
                        resultado.getInt("andar"),
                        resultado.getInt("qteAssentos"),
                        resultado.getDouble("tamanho")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar sala!");
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<Sala> listarSalas() {

        ArrayList<Sala> lista = new ArrayList<>();

        String sql = "SELECT * FROM salas";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet resultado = stmt.executeQuery()) {

            while (resultado.next()) {

                Sala sala = new Sala(
                    resultado.getInt("idSala"),
                    resultado.getInt("numSala"),
                    resultado.getInt("andar"),
                    resultado.getInt("qteAssentos"),
                    resultado.getDouble("tamanho")
                );

                lista.add(sala);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar salas!");
            e.printStackTrace();
        }

        return lista;
    }


    public boolean atualizarSala(Sala sala) {

        String verificar =
            "SELECT idSala FROM salas WHERE numSala = ? AND idSala <> ?";

        String sql =
            "UPDATE salas SET numSala = ?, andar = ?, qteAssentos = ?, tamanho = ? WHERE idSala = ?";

        try (Connection conexao = Conexao.conectar()) {

            try (PreparedStatement stmt = conexao.prepareStatement(verificar)) {

                stmt.setInt(1, sala.getNumSala());
                stmt.setInt(2, sala.getIdSala());

                try (ResultSet resultado = stmt.executeQuery()) {
                    if (resultado.next()) {
                        System.out.println("Esse número já pertence a outra sala!");
                        return false;
                    }
                }
            }

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

                stmt.setInt(1, sala.getNumSala());
                stmt.setInt(2, sala.getAndar());
                stmt.setInt(3, sala.getQteAssentos());
                stmt.setDouble(4, sala.getTamanho());
                stmt.setInt(5, sala.getIdSala());

                int linhasAlteradas = stmt.executeUpdate();

                if (linhasAlteradas > 0) {
                    System.out.println("Sala atualizada com sucesso!");
                    return true;
                }

                System.out.println("Sala não encontrada!");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar sala!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluirSala(int idSala) {

        String sql = "DELETE FROM salas WHERE idSala = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idSala);

            int linhasAlteradas = stmt.executeUpdate();

            if (linhasAlteradas > 0) {
                System.out.println("Sala excluída com sucesso!");
                return true;
            }

            System.out.println("Sala não encontrada!");
            return false;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir sala!");
            e.printStackTrace();
            return false;
        }
    }
}