import java.util.Scanner;
import java.io.Console;
import java.util.ArrayList;

public class Sistema {
    public static boolean autenticarUsuario(ArrayList<Usuario> usuarios, String email, String senha) {
        for (Usuario u : usuarios) {
            if (u.email.equals(email) && u.senha.equals(senha)) {
                return true;
            }
        }
        return false;
    }
    
    static class Usuario {
        String nome;
        String email;
        String senha;
        int idade;

        public Usuario(String nome, String email, int idade, String senha) {
            this.nome = nome;
            this.email = email;
            this.idade = idade;
            this.senha = senha;
        }

        @Override
        public String toString() {
            return "Nome: " + this.nome + "\nEmail: " + this.email + "\nIdade: " + this.idade;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Usuario> usuarios = new ArrayList<>();
        int opcao;

        do {
            System.out.println("\n--- Sistema de Cadastro ---");
            System.out.println("1. Cadastrar Usuário");
            System.out.println("2. Listar Usuários");
            System.out.println("3. Remoção de usuários");
            System.out.println("4. Editar dados");
            System.out.println("5. Logar");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");    
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer do teclado

            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Email: ");
                    String email;
                    while (true) {
                        email = scanner.nextLine();
                        if (email.contains("@gmail.com") || email.contains("@outlook.com")||email.contains("@yahoo.com") ||email.contains("@icloud.com")) {
                            break;
                        }
                        System.out.println("E-mail inválido. Digite novamente:");
                    }

                    System.out.print("Idade: ");
                    int idade;
                    while (true) {
                        idade = scanner.nextInt();

                        if (idade > 0) {
                            break;
                        }
                        System.out.println("Idade inválida. Digite novamente:");
                    }

                    scanner.nextLine(); // Limpar buffer
                    System.out.print("Senha: ");
                    String senha = scanner.nextLine();
                     

                    usuarios.add(new Usuario(nome, email, idade, senha));
                    System.out.println("Usuário cadastrado com sucesso!");
                    break;

                case 2:
                    System.out.println("\n--- Lista de Usuários ---");
                    if (usuarios.isEmpty()) {
                        System.out.println("Nenhum usuário cadastrado.");
                    } else {
                        for (Usuario u : usuarios) {
                            System.out.println(u);
                        }
                    }
                    break;
                    
                    case 3:
                    System.out.print("Digite o e-mail do usuário a ser removido: ");
                    String emailRemover = scanner.nextLine();
                    boolean removido = usuarios.removeIf(u -> u.email.equals(emailRemover));
                    if (removido) {
                        System.out.println("Usuário removido com sucesso!");
                    } else {
                        System.out.println("E-mail não encontrado.");
                    }
                    break;
                    case 4:
                    System.out.print("Digite o e-mail do usuário para atualizar: ");
                    String emailAtualizar = scanner.nextLine();
                    boolean encontrado = false;

                                for (Usuario u : usuarios) {
                                    if (u.email.equals(emailAtualizar)) {
                                        encontrado = true;

                                        int opcaoEditar;
                                        do {
                                            System.out.println("\n--- Editar Dados ---");
                                            System.out.println("1. Editar Nome");
                                            System.out.println("2. Editar Idade");
                                            System.out.println("3. Editar Senha");
                                            System.out.println("4. Sair");
                                            System.out.print("Escolha uma opção: ");
                                            opcaoEditar = scanner.nextInt();
                                            scanner.nextLine(); // Limpar o buffer

                                            switch (opcaoEditar) {
                                                case 1:
                                                    System.out.print("Novo nome: ");
                                                    u.nome = scanner.nextLine();
                                                    System.out.println("Nome atualizado com sucesso!");
                                                    break;
                                                case 2:
                                                    System.out.print("Nova idade: ");
                                                    u.idade = scanner.nextInt();
                                                    scanner.nextLine(); // Limpar o buffer
                                                    System.out.println("Idade atualizada com sucesso!");
                                                    break;
                                                case 3:
                                                    System.out.print("Nova senha: ");
                                                    u.senha = scanner.nextLine();
                                                    System.out.println("Senha atualizada com sucesso!");
                                                    break;
                                                case 4:
                                                    System.out.println("Saindo da edição...");
                                                    break;
                                                default:
                                                    System.out.println("Opção inválida. Tente novamente.");
                                            }
                                        } while (opcaoEditar != 4);
                                        break;
                                    }
                                }

                                            if (!encontrado) {
                                                System.out.println("E-mail não encontrado.");
                                            }
    break;

                    case 5:
                    System.out.print("E-mail: ");
                    String emailLogin = scanner.nextLine();
                    System.out.print("Senha: ");
                    String senhaLogin = scanner.nextLine();

                    if (autenticarUsuario(usuarios, emailLogin, senhaLogin)) {
                        System.out.println("Login bem-sucedido!");
                    } else {
                        System.out.println("E-mail ou senha incorretos.");
                    }
                    break;

                case 6:
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 6);

        scanner.close();
    }
}
