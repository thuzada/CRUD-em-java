import java.util.Scanner;
import java.io.BufferedReader;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Sistema {
    public static void salvarDados(ArrayList<Usuario> usuarios, String nomeArquivo) {
    try (FileWriter writer = new FileWriter(nomeArquivo)) {
        for (Usuario u : usuarios) {
            writer.write(u.nome + ";" + u.email + ";" + u.idade + ";" + u.senha + "\n");
        }
        System.out.println("Dados salvos com sucesso no arquivo: " + nomeArquivo);
    } catch (IOException e) {
        System.out.println("Erro ao salvar os dados: " + e.getMessage());
    }
}

public static ArrayList<Usuario> carregarDados(String nomeArquivo) {
    ArrayList<Usuario> usuarios = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
        String linha;
        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(";");
            if (dados.length == 4) {
                String nome = dados[0];
                String email = dados[1];
                int idade = Integer.parseInt(dados[2]);
                String senha = dados[3];
                usuarios.add(new Usuario(nome, email, idade, senha));
            }
        }
        System.out.println("Dados carregados com sucesso do arquivo: " + nomeArquivo);
    } catch (FileNotFoundException e) {
        System.out.println("Arquivo não encontrado. Nenhum dado carregado.");
    } catch (IOException e) {
        System.out.println("Erro ao carregar os dados: " + e.getMessage());
    }
    return usuarios;
}
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
        ArrayList<Usuario> usuarios = carregarDados("usuarios.txt");

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

                    scanner.nextLine(); 
                    System.out.print("Senha: ");
                    String senha = scanner.nextLine();
                     

                    usuarios.add(new Usuario(nome, email, idade, senha));
                    System.out.println("Usuário cadastrado com sucesso!");
                    salvarDados(usuarios, "usuarios.txt");

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
                    salvarDados(usuarios, "usuarios.txt");

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
                                            scanner.nextLine(); 

                                            switch (opcaoEditar) {
                                                case 1:
                                                    System.out.print("Novo nome: ");
                                                    u.nome = scanner.nextLine();
                                                    System.out.println("Nome atualizado com sucesso!");
                                                    break;
                                                case 2:
                                                    System.out.print("Nova idade: ");
                                                    u.idade = scanner.nextInt();
                                                    scanner.nextLine(); 
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
                                            salvarDados(usuarios, "usuarios.txt");

    break;

    case 5:
    System.out.print("E-mail: ");
    String emailLogin = scanner.nextLine();
    System.out.print("Senha: (DIGITE 0 SE VOCÊ NÃO LEMBRA DA SENHA): ");
    String senhaLogin = scanner.nextLine();

    if (senhaLogin.equals("0")) {
        System.out.print("Digite o e-mail do usuário para redefinir a senha: ");
        String emailRedefinir = scanner.nextLine();
        boolean encontradoRedefinir = false;

        for (Usuario u : usuarios) {
            if (u.email.equals(emailRedefinir)) {
                encontradoRedefinir = true;
                System.out.print("Nova senha: ");
                u.senha = scanner.nextLine();
                System.out.println("Senha redefinida com sucesso!");
                break;
            }
        }

        if (!encontradoRedefinir) {
            System.out.println("E-mail não encontrado.");
        }
        break; 
    }

    Usuario usuarioLogado = null;
    for (Usuario u : usuarios) {
        if (u.email.equals(emailLogin) && u.senha.equals(senhaLogin)) {
            usuarioLogado = u;
            break;
        }
    }

    if (usuarioLogado != null) {
        System.out.println("Login bem-sucedido!");
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n--- Menu do Usuário ---");
            System.out.println("1. Ver Perfil");
            System.out.println("2. Atualizar Informações");
            System.out.println("3. Excluir Conta");
            System.out.println("4. Logout");
            System.out.print("Escolha uma opção: ");
            int escolhaUsuario = scanner.nextInt();
            scanner.nextLine(); 

            switch (escolhaUsuario) {
                case 1:
                    System.out.println("\n--- Perfil do Usuário ---");
                    System.out.println("Nome: " + usuarioLogado.nome);
                    System.out.println("E-mail: " + usuarioLogado.email);
                    break;

                case 2:
                    System.out.print("\nDigite o novo nome: ");
                    usuarioLogado.nome = scanner.nextLine();
                    System.out.print("Digite o novo e-mail: ");
                    usuarioLogado.email = scanner.nextLine();
                    System.out.print("Digite a nova senha: ");
                    usuarioLogado.senha = scanner.nextLine();
                    System.out.println("Informações atualizadas com sucesso!");
                    break;

                case 3:
                    System.out.println("\nTem certeza que deseja excluir sua conta? (s/n)");
                    String confirmacao = scanner.nextLine();
                    if (confirmacao.equalsIgnoreCase("s")) {
                        usuarios.remove(usuarioLogado);
                        System.out.println("Conta excluída com sucesso!");
                        usuarioLogado = null;
                        continuar = false;
                    } else {
                        System.out.println("Ação cancelada.");
                    }
                    break;

                case 4:
                    System.out.println("Logout realizado com sucesso!");
                    usuarioLogado = null;
                    continuar = false;
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    } else {
        System.out.println("E-mail ou senha incorretos.");
    }
    salvarDados(usuarios, "usuarios.txt");

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
