
package pack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;

public class PessoaDB {

    public boolean cadastrarPessoa(Pessoa pessoa) {

        String verificar = "SELECT matricula FROM usuarios WHERE matricula = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(verificar)) {

            stmt.setInt(1, pessoa.getMatricula());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Matrícula já cadastrada!");
                    return false;
                }
            }

            String inserir = "INSERT INTO usuarios "
                    + "(matricula, nome, telefone, dataNasc) "
                    + "VALUES (?, ?, ?, ?)";

            try (PreparedStatement insert = conn.prepareStatement(inserir)) {

                insert.setInt(1, pessoa.getMatricula());
                insert.setString(2, pessoa.getNome());
                insert.setString(3, pessoa.getTelefone());
                insert.setDate(4, Date.valueOf(pessoa.getDataNasc()));

                insert.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Pessoa consultarPessoa(int matricula) {

        String sql = "SELECT * FROM usuarios WHERE matricula = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    int mat = rs.getInt("matricula");
                    String nome = rs.getString("nome");
                    String telefone = rs.getString("telefone");

                    LocalDate dataNasc = rs.getDate("dataNasc").toLocalDate();

                    return new Pessoa(mat, nome, telefone, dataNasc);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Pessoa> listarPessoas() {

        ArrayList<Pessoa> pessoas = new ArrayList<>();

        String sql = "SELECT * FROM usuarios";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                int matricula = rs.getInt("matricula");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");

                LocalDate dataNasc = rs.getDate("dataNasc").toLocalDate();

                Pessoa pessoa = new Pessoa(
                        matricula,
                        nome,
                        telefone,
                        dataNasc
                );

                pessoas.add(pessoa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pessoas;
    }

    public boolean atualizarPessoa(Pessoa pessoa) {

        String sql = "UPDATE usuarios SET "
                + "nome = ?, telefone = ?, dataNasc = ? "
                + "WHERE matricula = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getTelefone());
            stmt.setDate(3, Date.valueOf(pessoa.getDataNasc()));
            stmt.setInt(4, pessoa.getMatricula());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluirPessoa(int matricula) {

        String sql = "DELETE FROM usuarios WHERE matricula = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
