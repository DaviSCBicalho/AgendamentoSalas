package pack;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class PopularBanco {

    public static void main(String[] args) {

        Path arquivo = Path.of(args.length > 0 ? args[0] : "sql/popular.sql").toAbsolutePath();
        String conteudo;

        try {
            conteudo = Files.readString(arquivo, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("ERRO: não foi possível ler o arquivo " + arquivo);
            return;
        }

        String comandoAtual = "";

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement()) {

            System.out.println("Conectado em " + conn.getMetaData().getURL());
            System.out.println("Executando " + arquivo + "\n");

            for (String comando : conteudo.split(";")) {

                comandoAtual = comando.trim();

                if (comandoAtual.isEmpty()) {
                    continue;
                }

                if (stmt.execute(comandoAtual)) {
                    try (ResultSet rs = stmt.getResultSet()) {
                        ResultSetMetaData meta = rs.getMetaData();
                        while (rs.next()) {
                            for (int i = 1; i <= meta.getColumnCount(); i++) {
                                System.out.println(meta.getColumnLabel(i) + ": " + rs.getString(i));
                            }
                        }
                    }
                } else {
                    System.out.println("OK (" + stmt.getUpdateCount() + " linha(s)): "
                            + comandoAtual.split("\\R")[0]);
                }
            }

            System.out.println("\nConcluído.");

        } catch (SQLException e) {
            System.out.println("\nERRO no comando: " + comandoAtual.split("\\R")[0]);
            System.out.println("Mensagem: " + e.getMessage());
        }
    }
}