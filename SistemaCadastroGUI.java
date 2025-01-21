import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SistemaCadastroGUI {
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
            return "Nome: " + nome + "\nEmail: " + email + "\nIdade: " + idade;
        }
    }

    private static ArrayList<Usuario> usuarios = new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sistema de Cadastro");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        usuarios = carregarDados("usuarios.txt");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JButton btnCadastrar = new JButton("Cadastrar Usuário");
        JButton btnListar = new JButton("Listar Usuários");
        JButton btnRemover = new JButton("Remover Usuário");
        JButton btnEditar = new JButton("Editar Usuário");
        JButton btnLogar = new JButton("Logar");
        JButton btnSair = new JButton("Sair");

        panel.add(btnCadastrar);
        panel.add(btnListar);
        panel.add(btnRemover);
        panel.add(btnEditar);
        panel.add(btnLogar);
        panel.add(btnSair);

        frame.add(panel);
        frame.setVisible(true);

        // Ações dos botões
        btnCadastrar.addActionListener(e -> cadastrarUsuario());
        btnListar.addActionListener(e -> listarUsuarios());
        btnRemover.addActionListener(e -> removerUsuario());
        btnEditar.addActionListener(e -> editarUsuario());
        btnLogar.addActionListener(e -> logarUsuario());
        btnSair.addActionListener(e -> System.exit(0));
    }
    private static boolean isEmailValido(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);
    }
    
    private static boolean emailJaCadastrado(String email) {
        for (Usuario u : usuarios) {
            if (u.email.equals(email)) {
                return true; // Email já existe
            }
        }
        return false; // Email não existe
    }
    private static void cadastrarUsuario() {
        JTextField nomeField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField idadeField = new JTextField();
        JPasswordField senhaField = new JPasswordField();

        Object[] message = {
            "Nome:", nomeField,
            "Email:", emailField,
            "Idade:", idadeField,
            "Senha:", senhaField

        };

        int option = JOptionPane.showConfirmDialog(null, message, "Cadastrar Usuário", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String nome = nomeField.getText();
                String email = emailField.getText();
                int idade = Integer.parseInt(idadeField.getText());
                String senha = new String(senhaField.getPassword());
                
                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || idade <= 0) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente!");
                    return;
                }
                if (!isEmailValido(email)) {
                    JOptionPane.showMessageDialog(null, "E-mail inválido! Por favor, insira um e-mail válido.");
                    return;
                }
        
                // Verificar se o e-mail já está cadastrado
                if (emailJaCadastrado(email)) {
                    JOptionPane.showMessageDialog(null, "Este e-mail já está cadastrado.");
                    return;
                }
                usuarios.add(new Usuario(nome, email, idade, senha));
                JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Idade inválida!");
            }
        }
        salvarDados(usuarios, "usuarios.txt");

    }

    private static void listarUsuarios() {
        if (usuarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum usuário cadastrado.");
        } else {
            StringBuilder lista = new StringBuilder();
            for (Usuario u : usuarios) {
                lista.append(u).append("\n\n");
            }
            JTextArea textArea = new JTextArea(lista.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);

            JOptionPane.showMessageDialog(null, scrollPane, "Lista de Usuários", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void removerUsuario() {
        String email = JOptionPane.showInputDialog("Digite o e-mail do usuário a ser removido:");
        if (email != null) {
            boolean removido = usuarios.removeIf(u -> u.email.equals(email));
            if (removido) {
                JOptionPane.showMessageDialog(null, "Usuário removido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "E-mail não encontrado.");
            }
        }
        salvarDados(usuarios, "usuarios.txt");

    }

    private static void editarUsuario() {
        String email = JOptionPane.showInputDialog("Digite o e-mail do usuário para editar:");
        if (email != null) {
            for (Usuario u : usuarios) {
                if (u.email.equals(email)) {
                    JTextField nomeField = new JTextField(u.nome);
                    JTextField idadeField = new JTextField(String.valueOf(u.idade));
                    JPasswordField senhaField = new JPasswordField(u.senha);

                    Object[] message = {
                        "Nome:", nomeField,
                        "Idade:", idadeField,
                        "Senha:", senhaField
                    };

                    int option = JOptionPane.showConfirmDialog(null, message, "Editar Usuário", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        try {
                            u.nome = nomeField.getText();
                            u.idade = Integer.parseInt(idadeField.getText());
                            u.senha = new String(senhaField.getPassword());
                            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
                            salvarDados(usuarios, "usuarios.txt");

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Idade inválida!");
                        }
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "E-mail não encontrado.");
        }
    }

    private static void logarUsuario() {
        JTextField emailField = new JTextField();
        JPasswordField senhaField = new JPasswordField();
    
        Object[] message = {
            "Email:", emailField,
            "Senha:", senhaField
        };
    
        int option = JOptionPane.showConfirmDialog(null, message, "Logar", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String email = emailField.getText();
            String senha = new String(senhaField.getPassword());
    
            for (Usuario u : usuarios) {
                if (u.email.equals(email) && u.senha.equals(senha)) {
                    JOptionPane.showMessageDialog(null, "Login bem-sucedido!\n\n" + u);
    
                    while (true) {
                        String[] opcoes = { "Ver Perfil", "Atualizar Informações", "Excluir Conta", "Logout" };
                        int escolha = JOptionPane.showOptionDialog(
                            null,
                            "Escolha uma opção:",
                            "Menu do Usuário",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            opcoes,
                            opcoes[0]
                        );
    
                        if (escolha == 0) { // Ver Perfil
                            JOptionPane.showMessageDialog(null, u.toString(), "Perfil", JOptionPane.INFORMATION_MESSAGE);
                        } else if (escolha == 1) { // Atualizar Informações
                            JTextField nomeField = new JTextField(u.nome);
                            JTextField idadeField = new JTextField(String.valueOf(u.idade));
                            JPasswordField senhaFieldNova = new JPasswordField(u.senha);
    
                            Object[] campos = {
                                "Nome:", nomeField,
                                "Idade:", idadeField,
                                "Senha:", senhaFieldNova
                            };
    
                            int opcaoAtualizar = JOptionPane.showConfirmDialog(null, campos, "Atualizar Informações", JOptionPane.OK_CANCEL_OPTION);
                            if (opcaoAtualizar == JOptionPane.OK_OPTION) {
                                try {
                                    u.nome = nomeField.getText();
                                    u.idade = Integer.parseInt(idadeField.getText());
                                    u.senha = new String(senhaFieldNova.getPassword());
                                    JOptionPane.showMessageDialog(null, "Informações atualizadas com sucesso!");
                                    salvarDados(usuarios, "usuarios.txt");
                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(null, "Idade inválida!");
                                }
                            }
                        } else if (escolha == 2) { // Excluir Conta
                            int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja excluir sua conta?", "Excluir Conta", JOptionPane.YES_NO_OPTION);
                            if (confirmacao == JOptionPane.YES_OPTION) {
                                usuarios.remove(u);
                                JOptionPane.showMessageDialog(null, "Conta excluída com sucesso!");
                                salvarDados(usuarios, "usuarios.txt");

                                return;
                            }
                        } else if (escolha == 3) { // Logout
                            JOptionPane.showMessageDialog(null, "Logout realizado com sucesso!");
                            return;
                        }
                    }
                }
            }
    
            JOptionPane.showMessageDialog(null, "E-mail ou senha incorretos.");
        }        
    }
}
    