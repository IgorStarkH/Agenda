package brincandocombanco1;

import java.util.List;
import java.util.Scanner;

public class AgendaConsole {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            exibirMenuPrincipal();
            int escolha = lerEscolhaValida(scanner, 0, 2);  // Opções válidas: 0, 1, 2

            switch (escolha) {
                case 1:
                    rodarNoConsole();
                    break;
                case 2:
                    rodarNoSwing();
                    return;  // Sai do menu principal quando rodar no Swing
                case 0:
                    System.out.println("\nObrigado por usar o Sistema de Agenda! Ate logo!");
                    System.exit(0);
                    break;
                default:
                    // Este caso nunca será alcançado devido à validação anterior
                    break;
            }
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("=============================================");
        System.out.println("        Bem-vindo ao Sistema de Agenda      ");
        System.out.println("=============================================");
        System.out.println("Escolha uma opcao abaixo:");
        System.out.println("1. Rodar no Console");
        System.out.println("2. Rodar no Swing");
        System.out.println("0. Sair");
        System.out.print("Digite sua escolha: ");
    }

    private static int lerEscolhaValida(Scanner scanner, int min, int max) {
        int escolha = -1;
        while (escolha < min || escolha > max) {
            if (scanner.hasNextInt()) {
                escolha = scanner.nextInt();
                scanner.nextLine(); // Limpar buffer após o nextInt
                if (escolha < min || escolha > max) {
                    System.out.println("\n[ERRO] Opcao invalida! Escolha uma opcao entre " + min + " e " + max + ".");
                }
            } else {
                System.out.println("\n[ERRO] Entrada invalida! Por favor, digite um numero.");
                scanner.nextLine(); // Limpar buffer se entrada não for um número
            }
        }
        return escolha;
    }

    private static void rodarNoConsole() {
        ContatoDAO contatoDAO = new ContatoDAO();
        Scanner scanner = new Scanner(System.in);
        int escolha;

        do {
            exibirMenuConsole();
            escolha = lerEscolhaValida(scanner, 0, 3);  // Opções válidas: 0, 1, 2, 3

            switch (escolha) {
                case 1:
                    adicionarContatoConsole(contatoDAO, scanner);
                    break;
                case 2:
                    listarContatosConsole(contatoDAO);
                    break;
                case 3:
                    deletarContatoConsole(contatoDAO, scanner);
                    break;
                case 0:
                    System.out.println("\nVoltando ao menu principal...\n");
                    break;
                default:
                    // Este caso nunca será alcançado devido à validação anterior
                    break;
            }
        } while (escolha != 0);
    }

    private static void exibirMenuConsole() {
        System.out.println("=============================================");
        System.out.println("                 Menu - Console              ");
        System.out.println("=============================================");
        System.out.println("1. Adicionar Novo Contato");
        System.out.println("2. Listar Contatos");
        System.out.println("3. Deletar Contato");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opcao: ");
    }

    private static void adicionarContatoConsole(ContatoDAO contatoDAO, Scanner scanner) {
        System.out.println("\n===========================================");
        System.out.println("           Adicionando Novo Contato         ");
        System.out.println("===========================================");

        String nome = obterNomeValido(scanner);
        String email = obterEmailValido(scanner);
        String telefone = obterTelefoneValido(scanner);

        // Verifica se algum campo está vazio
        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            System.out.println("\n[ERRO] Todos os campos devem ser preenchidos! Tente novamente.");
            return;
        }

        Contato novoContato = new Contato();
        novoContato.setNome(nome);
        novoContato.setEmail(email);
        novoContato.setTelefone(telefone);

        contatoDAO.adicionarContato(novoContato);
        System.out.println("\nContato adicionado com sucesso!");
    }

    private static String obterNomeValido(Scanner scanner) {
        String nome = "";
        while (true) {
            System.out.print("Nome (apenas letras e espacos): ");
            nome = scanner.nextLine();
            if (!nome.matches("[a-zA-Z ]+")) {
                System.out.println("\n[ERRO] O nome so pode conter letras e espacos.");
            } else {
                break;
            }
        }
        return nome;
    }

    private static String obterEmailValido(Scanner scanner) {
        System.out.print("Email: ");
        return scanner.nextLine();
    }

    private static String obterTelefoneValido(Scanner scanner) {
        String telefone = "";
        while (true) {
            System.out.print("Telefone (apenas numeros): ");
            telefone = scanner.nextLine();
            if (!telefone.matches("[0-9]+")) {
                System.out.println("\n[ERRO] O telefone deve conter apenas numeros.");
            } else {
                break;
            }
        }
        return telefone;
    }

    private static void listarContatosConsole(ContatoDAO contatoDAO) {
        System.out.println("\n===========================================");
        System.out.println("             Listando Contatos             ");
        System.out.println("===========================================");

        List<Contato> contatos = contatoDAO.listarContatos();
        if (contatos.isEmpty()) {
            System.out.println("[INFO] Nenhum contato encontrado.\n");
        } else {
            for (Contato contato : contatos) {
                System.out.println("ID: " + contato.getId() + ", Nome: " + contato.getNome() +
                                   ", Email: " + contato.getEmail() + ", Telefone: " + contato.getTelefone());
            }
        }
    }

    private static void deletarContatoConsole(ContatoDAO contatoDAO, Scanner scanner) {
        // Exibe a lista de contatos antes de deletar
        listarContatosConsole(contatoDAO);

        int id;
        boolean encontrado = false;

        while (true) {
            System.out.print("\nDigite o ID do contato a ser deletado (ou 0 para cancelar): ");
            String idInput = scanner.nextLine();

            if (!idInput.matches("[0-9]+")) {
                System.out.println("\n[ERRO] O ID deve ser um numero.");
            } else {
                id = Integer.parseInt(idInput);

                // Se o usuário digitar '0', cancela a exclusão
                if (id == 0) {
                    System.out.println("\nExclusao cancelada.");
                    break;
                }

                // Verifica se o contato com o ID existe
                for (Contato contato : contatoDAO.listarContatos()) {
                    if (contato.getId() == id) {
                        encontrado = true;
                        break;
                    }
                }

                if (encontrado) {
                    contatoDAO.deletarContato(id);
                    System.out.println("\nContato deletado com sucesso!");
                    break;
                } else {
                    System.out.println("\n[ERRO] O ID do contato nao existe. Tente novamente.");
                }
            }
        }
    }

    private static void rodarNoSwing() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            AgendaSwing frame = new AgendaSwing();
            frame.setVisible(true);
        });
    }
}
