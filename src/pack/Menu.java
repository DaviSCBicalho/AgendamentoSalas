package pack;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    static Scanner scan = new Scanner(System.in);

    static final PessoaDB pessoaDB = new PessoaDB();
    static final SalaDB salaDB = new SalaDB();
    static final ReservaDB reservaDB = new ReservaDB();
    static final RelatorioDB relatorioDB = new RelatorioDB();

    static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static final DateTimeFormatter FORMATO_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

    static final String SEPARADOR = "----------------------------------------";

    public static void main (String [] args){
        splash();
        int opc;
        do{
                System.out.println("\n-= Menu Principal =-"
                + "\n[1] - Gerenciar Usuários"
                + "\n[2] - Gerenciar Salas"
                + "\n[3] - Gerenciar Reservas"
                + "\n[4] - Ver Relatórios"
                + "\n[0] - Sair"
                + "\n\nSelecione uma opção:");
                opc = scan.nextInt();
                switch (opc){
                    case 1:
                        MenuGerenciaUsuarios();
                    break;
                    case 2:
                        MenuGerenciaSalas();
                    break;
                    case 3:
                        MenuGerenciaReservas();
                    break;
                    case 4:
                        MenuRelatorios();
                    break;
                    case 0:
                        System.out.println("FIM DO PROGRAMA");
                    break;
                    default:
                        System.out.println("ERRO: Opção inválida");
                }
        } while (opc != 0);
    }

    static void splash(){
        int usuarios = relatorioDB.contarRegistros("usuarios");
        int salas = relatorioDB.contarRegistros("salas");
        int reservas = relatorioDB.contarRegistros("reservas");

        System.out.println("#".repeat(45));
        linhaSplash("");
        linhaSplash("AGENDA GERENCIADORA DE SALAS");
        linhaSplash("");
        linhaSplash("TOTAL DE REGISTROS EXISTENTES");
        linhaSplash(String.format("  1 - %-12s%6d", "USUÁRIOS:", usuarios));
        linhaSplash(String.format("  2 - %-12s%6d", "SALAS:", salas));
        linhaSplash(String.format("  3 - %-12s%6d", "RESERVAS:", reservas));
        linhaSplash("");
        linhaSplash("CRIADO POR: ALÉCIO PIRES");
        linhaSplash("            DAVI BICALHO");
        linhaSplash("");
        linhaSplash("DISCIPLINA: BANCO DE DADOS");
        linhaSplash("            2026/2");
        linhaSplash("PROFESSOR: HOWARD ROATTI");
        System.out.println("#".repeat(45));
        System.out.println("\nPressione ENTER para continuar");
        scan.nextLine();
    }

    static void linhaSplash(String texto){
        System.out.printf("#  %-40s #%n", texto);
    }

    static void MenuGerenciaUsuarios(){
        int opc;
        do{
                System.out.println("\n-= Gerência de Usuários =-"
                + "\n[1] - Cadastrar Usuário"
                + "\n[2] - Editar Usuário"
                + "\n[3] - Excluir Usuário"
                + "\n[4] - Visualizar Usuários"
                + "\n[0] - Sair"
                + "\n\nSelecione uma opção:");
                opc = scan.nextInt();
                switch (opc){
                    case 1:
                        cadastrarUsuario();
                    break;
                    case 2:
                        editarUsuario();
                    break;
                    case 3:
                        excluirUsuario();
                    break;
                    case 4:
                        visualizarUsuarios();
                    break;
                    case 0:
                    System.out.println("Voltando...");
                    break;
                    default:
                        System.out.println("ERRO: Opção inválida");
                }
        } while (opc != 0);
    }

    static void MenuGerenciaSalas(){
        int opc;
        do{
                System.out.println("\n-= Gerência de Salas =-"
                + "\n[1] - Cadastrar Sala"
                + "\n[2] - Editar Sala"
                + "\n[3] - Excluir Sala"
                + "\n[4] - Visualizar Salas"
                + "\n[0] - Sair"
                + "\n\nSelecione uma opção:");
                opc = scan.nextInt();
                switch (opc){
                    case 1:
                        cadastrarSala();
                    break;
                    case 2:
                        editarSala();
                    break;
                    case 3:
                        excluirSala();
                    break;
                    case 4:
                        visualizarSalas();
                    break;
                    case 0:
                    System.out.println("Voltando...");
                    break;
                    default:
                        System.out.println("ERRO: Opção inválida");
                }
        } while (opc != 0);
    }

    static void MenuGerenciaReservas(){
        int opc;
        do{
                System.out.println("\n-= Gerência de Reservas =-"
                + "\n[1] - Realizar Reserva"
                + "\n[2] - Alterar Reserva"
                + "\n[3] - Cancelar Reserva"
                + "\n[4] - Visualizar Reservas"
                + "\n[5] - Visualizar Salas"
                + "\n[0] - Sair"
                + "\n\nSelecione uma opção:");
                opc = scan.nextInt();
                switch (opc){
                    case 1:
                        realizarReserva();
                    break;
                    case 2:
                        alterarReserva();
                    break;
                    case 3:
                        cancelarReserva();
                    break;
                    case 4:
                        visualizarReservas();
                    break;
                    case 5:
                        visualizarSalas();
                    break;
                    case 0:
                    System.out.println("Voltando...");
                    break;
                    default:
                        System.out.println("ERRO: Opção inválida");
                }
        } while (opc != 0);
    }

    static void MenuRelatorios(){
        int opc;
        do{
                System.out.println("\n-= Relatórios =-"
                + "\n[1] - Todas as reservas (detalhado)"
                + "\n[2] - Reservas por sala"
                + "\n[3] - Reservas por usuário"
                + "\n[4] - Reservas por período"
                + "\n[5] - Uso das salas (quantidade e tempo reservado)"
                + "\n[6] - Salas disponíveis em um horário"
                + "\n[7] - Ranking de usuários por reservas"
                + "\n[0] - Voltar"
                + "\n\nSelecione uma opção:");
                opc = scan.nextInt();
                switch (opc){
                    case 1:
                        relatorioDB.reservasDetalhadas();
                    break;
                    case 2:
                        relatorioReservasPorSala();
                    break;
                    case 3:
                        relatorioReservasPorUsuario();
                    break;
                    case 4:
                        relatorioReservasPorPeriodo();
                    break;
                    case 5:
                        relatorioDB.usoPorSala();
                    break;
                    case 6:
                        relatorioSalasDisponiveis();
                    break;
                    case 7:
                        relatorioDB.rankingUsuarios();
                    break;
                    case 0:
                        System.out.println("Voltando...");
                    break;
                    default:
                        System.out.println("ERRO: Opção inválida");
                }
        } while (opc != 0);
    }

    static void cadastrarUsuario(){
        System.out.println("\n-= Cadastrar Usuário =-");

        System.out.print("Matrícula: ");
        int matricula = scan.nextInt();
        scan.nextLine();
        System.out.print("Nome: ");
        String nome = scan.nextLine();
        String telefone;
        do{
            System.out.print("Telefone (13 dígitos, somente números): ");
            telefone = scan.nextLine().trim();
            if (!telefone.matches("\\d{13}")) {
                System.out.println("ERRO: o telefone deve ter 13 dígitos, somente números");
            }
        } while (!telefone.matches("\\d{13}"));
        LocalDate dataNasc = lerData("Data de nascimento (dd/mm/aaaa): ");

        if (pessoaDB.cadastrarPessoa(new Pessoa(matricula, nome, telefone, dataNasc))) {
            System.out.println("Usuário cadastrado com sucesso!");
        } else {
            System.out.println("Não foi possível cadastrar o usuário.");
        }
    }

    static void editarUsuario(){
        System.out.println("\n-= Editar Usuário =-");

        ArrayList<Pessoa> pessoas = pessoaDB.listarPessoas();

        if (pessoas.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }

        for (Pessoa p : pessoas) {
            System.out.println(p.getMatricula() + " - " + p.getNome());
        }

        System.out.print("\nMatrícula do usuário a editar: ");
        int matricula = scan.nextInt();

        if (pessoaDB.consultarPessoa(matricula) == null) {
            System.out.println("ERRO: usuário não encontrado.");
            return;
        }

        scan.nextLine();
        System.out.print("Novo nome: ");
        String nome = scan.nextLine();
        String telefone;
        do{
            System.out.print("Novo telefone (13 dígitos, somente números): ");
            telefone = scan.nextLine().trim();
            if (!telefone.matches("\\d{13}")) {
                System.out.println("ERRO: o telefone deve ter 13 dígitos, somente números");
            }
        } while (!telefone.matches("\\d{13}"));
        LocalDate dataNasc = lerData("Nova data de nascimento (dd/mm/aaaa): ");

        if (pessoaDB.atualizarPessoa(new Pessoa(matricula, nome, telefone, dataNasc))) {
            System.out.println("\nUsuário atualizado:\n" + pessoaDB.consultarPessoa(matricula));
        } else {
            System.out.println("Não foi possível atualizar o usuário.");
        }
    }

    static void excluirUsuario(){
        System.out.println("\n-= Excluir Usuário =-");

        ArrayList<Pessoa> pessoas = pessoaDB.listarPessoas();

        if (pessoas.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }

        for (Pessoa p : pessoas) {
            System.out.println(p.getMatricula() + " - " + p.getNome());
        }

        System.out.print("\nMatrícula do usuário a excluir: ");
        int matricula = scan.nextInt();
        Pessoa pessoa = pessoaDB.consultarPessoa(matricula);

        if (pessoa == null) {
            System.out.println("ERRO: usuário não encontrado.");
            return;
        }

        System.out.print("Deseja realmente excluir " + pessoa.getNome() + "? (S/N): ");

        if (!scan.next().equalsIgnoreCase("S")) {
            System.out.println("Exclusão cancelada.");
            return;
        }

        boolean temReservas = reservaDB.visualizarReservas().stream()
                .anyMatch(r -> r.getMatricula() == matricula);

        if (temReservas) {
            System.out.println("Este usuário possui reservas e não pode ser excluído. Cancele as reservas antes.");
            return;
        }

        if (pessoaDB.excluirPessoa(matricula)) {
            System.out.println("Usuário excluído com sucesso!");
        } else {
            System.out.println("Não foi possível excluir o usuário.");
        }
    }

    static void visualizarUsuarios(){
        System.out.println("\n-= Usuários cadastrados =-");

        ArrayList<Pessoa> pessoas = pessoaDB.listarPessoas();

        if (pessoas.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }

        for (Pessoa p : pessoas) {
            System.out.println(SEPARADOR + "\n" + p);
        }
        System.out.println(SEPARADOR + "\nTotal: " + pessoas.size() + " usuário(s)");
    }

    static void cadastrarSala(){
        System.out.println("\n-= Cadastrar Sala =-");

        System.out.print("ID da sala: ");
        int idSala = scan.nextInt();
        System.out.print("Número da sala: ");
        int numSala = scan.nextInt();
        System.out.print("Andar: ");
        int andar = scan.nextInt();
        System.out.print("Quantidade de assentos (0 para não informar): ");
        int assentos = scan.nextInt();
        System.out.print("Tamanho em m²: ");
        double tamanho = scan.nextDouble();

        salaDB.cadastrarSala(new Sala(idSala, numSala, andar, assentos, tamanho));
    }

    static void editarSala(){
        System.out.println("\n-= Editar Sala =-");

        ArrayList<Sala> salas = salaDB.listarSalas();

        if (salas.isEmpty()) {
            System.out.println("Nenhuma sala cadastrada.");
            return;
        }

        for (Sala s : salas) {
            System.out.println(s.getIdSala() + " - Sala " + s.getNumSala() + " (andar " + s.getAndar() + ")");
        }

        System.out.print("\nID da sala a editar: ");
        int idSala = scan.nextInt();

        if (salaDB.consultarSala(idSala) == null) {
            System.out.println("ERRO: sala não encontrada.");
            return;
        }

        System.out.print("Novo número da sala: ");
        int numSala = scan.nextInt();
        System.out.print("Novo andar: ");
        int andar = scan.nextInt();
        System.out.print("Nova quantidade de assentos (0 para não informar): ");
        int assentos = scan.nextInt();
        System.out.print("Novo tamanho em m²: ");
        double tamanho = scan.nextDouble();

        if (salaDB.atualizarSala(new Sala(idSala, numSala, andar, assentos, tamanho))) {
            System.out.println("\nSala atualizada:\n" + salaDB.consultarSala(idSala));
        }
    }

    static void excluirSala(){
        System.out.println("\n-= Excluir Sala =-");

        ArrayList<Sala> salas = salaDB.listarSalas();

        if (salas.isEmpty()) {
            System.out.println("Nenhuma sala cadastrada.");
            return;
        }

        for (Sala s : salas) {
            System.out.println(s.getIdSala() + " - Sala " + s.getNumSala() + " (andar " + s.getAndar() + ")");
        }

        System.out.print("\nID da sala a excluir: ");
        int idSala = scan.nextInt();
        Sala sala = salaDB.consultarSala(idSala);

        if (sala == null) {
            System.out.println("ERRO: sala não encontrada.");
            return;
        }

        System.out.print("Deseja realmente excluir a sala " + sala.getNumSala() + "? (S/N): ");

        if (!scan.next().equalsIgnoreCase("S")) {
            System.out.println("Exclusão cancelada.");
            return;
        }

        boolean temReservas = reservaDB.visualizarReservas().stream()
                .anyMatch(r -> r.getIdSala() == idSala);

        if (temReservas) {
            System.out.println("Esta sala possui reservas e não pode ser excluída. Cancele as reservas antes.");
            return;
        }

        salaDB.excluirSala(idSala);
    }

    static void visualizarSalas(){
        System.out.println("\n-= Salas cadastradas =-");

        ArrayList<Sala> salas = salaDB.listarSalas();

        if (salas.isEmpty()) {
            System.out.println("Nenhuma sala cadastrada.");
            return;
        }

        for (Sala s : salas) {
            System.out.println(SEPARADOR + "\n" + s);
        }
        System.out.println(SEPARADOR + "\nTotal: " + salas.size() + " sala(s)");
    }

    static void realizarReserva(){
        System.out.println("\n-= Realizar Reserva =-");

        System.out.print("ID da reserva: ");
        int idReserva = scan.nextInt();

        if (buscarReserva(idReserva) != null) {
            System.out.println("ERRO: já existe uma reserva com esse ID.");
            return;
        }

        System.out.print("ID da sala: ");
        int idSala = scan.nextInt();

        if (salaDB.consultarSala(idSala) == null) {
            System.out.println("ERRO: sala não encontrada.");
            return;
        }

        System.out.print("Matrícula do responsável: ");
        int matricula = scan.nextInt();

        if (pessoaDB.consultarPessoa(matricula) == null) {
            System.out.println("ERRO: usuário não encontrado.");
            return;
        }

        scan.nextLine();
        LocalDateTime inicio;
        do{
            inicio = lerDataHora("Início");
            if (inicio.isBefore(LocalDateTime.now())) {
                System.out.println("ERRO: não é possível reservar em uma data/horário que já passou");
            }
        } while (inicio.isBefore(LocalDateTime.now()));
        LocalDateTime fim;
        do{
            fim = lerDataHora("Fim");
            if (!fim.isAfter(inicio)) {
                System.out.println("ERRO: o fim da reserva deve ser depois do início");
            }
        } while (!fim.isAfter(inicio));

        Reserva reserva = new Reserva(idReserva, idSala, matricula, LocalDate.now(), inicio, fim);

        if (reservaDB.realizarReserva(reserva)) {
            System.out.println("Reserva realizada com sucesso!");
        } else {
            System.out.println("Reserva não realizada.");
        }
    }

    static void alterarReserva(){
        System.out.println("\n-= Alterar Reserva =-");

        ArrayList<Reserva> reservas = reservaDB.visualizarReservas();

        if (reservas.isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada.");
            return;
        }

        for (Reserva r : reservas) {
            System.out.println(r.getIdReserva() + " - Sala (ID " + r.getIdSala() + ") - Matrícula "
                    + r.getMatricula() + " - " + r.getDtInicio().format(FORMATO_DATA_HORA));
        }

        System.out.print("\nID da reserva a alterar: ");
        int idReserva = scan.nextInt();
        Reserva atual = buscarReserva(idReserva);

        if (atual == null) {
            System.out.println("ERRO: reserva não encontrada.");
            return;
        }

        System.out.print("Novo ID da sala: ");
        int idSala = scan.nextInt();

        if (salaDB.consultarSala(idSala) == null) {
            System.out.println("ERRO: sala não encontrada.");
            return;
        }

        System.out.print("Nova matrícula do responsável: ");
        int matricula = scan.nextInt();

        if (pessoaDB.consultarPessoa(matricula) == null) {
            System.out.println("ERRO: usuário não encontrado.");
            return;
        }

        scan.nextLine();
        LocalDateTime inicio;
        do{
            inicio = lerDataHora("Novo início");
            if (inicio.isBefore(LocalDateTime.now())) {
                System.out.println("ERRO: não é possível reservar em uma data/horário que já passou");
            }
        } while (inicio.isBefore(LocalDateTime.now()));
        LocalDateTime fim;
        do{
            fim = lerDataHora("Novo fim");
            if (!fim.isAfter(inicio)) {
                System.out.println("ERRO: o fim da reserva deve ser depois do início");
            }
        } while (!fim.isAfter(inicio));

        Reserva nova = new Reserva(idReserva, idSala, matricula, atual.getDtReserva(), inicio, fim);

        if (reservaDB.alterarReserva(nova)) {
            System.out.println("\nReserva atualizada:\n" + buscarReserva(idReserva));
        } else {
            System.out.println("Reserva não alterada.");
        }
    }

    static void cancelarReserva(){
        System.out.println("\n-= Cancelar Reserva =-");

        ArrayList<Reserva> reservas = reservaDB.visualizarReservas();

        if (reservas.isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada.");
            return;
        }

        for (Reserva r : reservas) {
            System.out.println(r.getIdReserva() + " - Sala (ID " + r.getIdSala() + ") - Matrícula "
                    + r.getMatricula() + " - " + r.getDtInicio().format(FORMATO_DATA_HORA));
        }

        System.out.print("\nID da reserva a cancelar: ");
        int idReserva = scan.nextInt();

        if (buscarReserva(idReserva) == null) {
            System.out.println("ERRO: reserva não encontrada.");
            return;
        }

        System.out.print("Deseja realmente cancelar a reserva " + idReserva + "? (S/N): ");

        if (!scan.next().equalsIgnoreCase("S")) {
            System.out.println("Cancelamento abortado.");
            return;
        }

        if (reservaDB.cancelarReserva(idReserva)) {
            System.out.println("Reserva cancelada com sucesso!");
        } else {
            System.out.println("Não foi possível cancelar a reserva.");
        }
    }

    static void visualizarReservas(){
        System.out.println("\n-= Reservas realizadas =-");

        ArrayList<Reserva> reservas = reservaDB.visualizarReservas();

        if (reservas.isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada.");
            return;
        }

        for (Reserva r : reservas) {
            System.out.println(SEPARADOR + "\n" + r);
        }
        System.out.println(SEPARADOR + "\nTotal: " + reservas.size() + " reserva(s)");
    }

    static Reserva buscarReserva(int idReserva){
        for (Reserva r : reservaDB.visualizarReservas()) {
            if (r.getIdReserva() == idReserva) {
                return r;
            }
        }
        return null;
    }

    static void relatorioReservasPorSala(){
        System.out.print("ID da sala: ");
        int idSala = scan.nextInt();

        relatorioDB.reservasPorSala(idSala);
    }

    static void relatorioReservasPorUsuario(){
        System.out.print("Matrícula do usuário: ");
        int matricula = scan.nextInt();

        relatorioDB.reservasPorUsuario(matricula);
    }

    static void relatorioReservasPorPeriodo(){
        scan.nextLine();
        LocalDate inicio = lerData("Data inicial (dd/mm/aaaa): ");
        LocalDate fim;
        do{
            fim = lerData("Data final (dd/mm/aaaa): ");
            if (fim.isBefore(inicio)) {
                System.out.println("ERRO: a data final não pode ser antes da data inicial");
            }
        } while (fim.isBefore(inicio));

        relatorioDB.reservasPorPeriodo(inicio, fim);
    }

    static void relatorioSalasDisponiveis(){
        scan.nextLine();
        LocalDateTime inicio = lerDataHora("Início");
        LocalDateTime fim;
        do{
            fim = lerDataHora("Fim");
            if (!fim.isAfter(inicio)) {
                System.out.println("ERRO: o fim deve ser depois do início");
            }
        } while (!fim.isAfter(inicio));

        relatorioDB.salasDisponiveis(inicio, fim);
    }

    static LocalDate lerData(String mensagem){
        String texto;
        boolean valido;
        do{
            System.out.print(mensagem);
            texto = scan.nextLine().trim();
            valido = texto.matches("(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/[1-9]\\d{3}");
            if (valido) {
                int dia = Integer.parseInt(texto.substring(0, 2));
                int mes = Integer.parseInt(texto.substring(3, 5));
                int ano = Integer.parseInt(texto.substring(6, 10));
                valido = dia <= YearMonth.of(ano, mes).lengthOfMonth();
            }
            if (!valido) {
                System.out.println("ERRO: data inexistente ou fora do formato dd/MM/yyyy");
            }
        } while (!valido);
        return LocalDate.parse(texto, FORMATO_DATA);
    }

    static String lerHora(String mensagem){
        String texto;
        boolean valido;
        do{
            System.out.print(mensagem);
            texto = scan.nextLine().trim();
            valido = texto.matches("([01]\\d|2[0-3]):[0-5]\\d");
            if (!valido) {
                System.out.println("ERRO: use o formato HH:mm (de 00:00 a 23:59)");
            }
        } while (!valido);
        return texto;
    }

    static LocalDateTime lerDataHora(String rotulo){
        LocalDate data = lerData(rotulo + " - data (dd/mm/aaaa): ");
        String hora = lerHora(rotulo + " - hora (hh:mm): ");
        return LocalDateTime.parse(data.format(FORMATO_DATA) + " - " + hora, FORMATO_DATA_HORA);
    }
}