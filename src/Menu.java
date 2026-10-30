package pack;
import java.util.Scanner;


public class Menu {
    static Scanner scan = new Scanner(System.in);
    
    public static void main (String [] args){
        System.out.println("\n-= Agenda gerenciadora de salas =-"
        + "\n\nCriadores:\nAlécio Pires\nDavi Bicalho"
        + "\n\nPressione ENTER para continuar");
        scan.nextLine();
        int opc;
        do{
                System.out.println("\n-= Menu Principal =-" 
                + "\n[1] - Gerenciar Usuários"
                + "\n[2] - Gerenciar Salas"
                + "\n[3] - Gerenciar Reservas"
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
                    case 0:
                        System.out.println("FIM DO PROGRAMA");
                    break;
                    default:
                        System.out.println("ERRO: Opção inválida");
                }
        } while (opc != 0);
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

    static void cadastrarUsuario(){

    }

    static void editarUsuario(){
        
    }

    static void excluirUsuario(){
        
    }

    static void visualizarUsuarios(){
        
    }

    static void cadastrarSala(){

    }

    static void editarSala(){
        
    }

    static void excluirSala(){
        
    }

    static void visualizarSalas(){
        
    }

    static void realizarReserva(){

    }

    static void alterarReserva(){
        
    }

    static void cancelarReserva(){
        
    }

    static void visualizarReservas(){
        
    }
}