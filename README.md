# Sistema de Cadastro de Usuários

Este projeto é um sistema simples de cadastro de usuários em Java. Ele permite que você cadastre usuários, valide e-mails, e verifique se o e-mail já está cadastrado, além de salvar as informações dos usuários em um arquivo local.

## Funcionalidades
- Cadastro de usuários com nome, idade, e-mail e senha.
- Validação do formato do e-mail.
- Verificação se o e-mail já está cadastrado.
- Salvamento dos dados em um arquivo de texto local.
  
## Pré-requisitos

Antes de rodar o projeto, é necessário ter o seguinte instalado em sua máquina:

- **Java 8 ou superior**: O projeto é desenvolvido em Java, portanto, você precisa do JDK instalado para compilar e executar o código.
  - Para instalar o JDK, siga as instruções da [documentação oficial](https://docs.oracle.com/en/java/javase/).

- **IDE (opcional)**: Recomenda-se utilizar uma IDE como [IntelliJ IDEA](https://www.jetbrains.com/idea/) ou [Eclipse](https://www.eclipse.org/downloads/) para facilitar o desenvolvimento.

## Como Rodar

1. Clone este repositório para a sua máquina local:

   ```bash
   git clone https://github.com/thuzada/Sistema-de-CRUD-em-JAVA.git
Abra o projeto na sua IDE ou editor de texto preferido.

2.Compile e execute o código na sua IDE ou pelo terminal:


    Copiar código
    javac SistemaCadastroGUI.java
                OU
    java Sistema


O programa irá solicitar que você insira o nome, idade, e-mail e senha de um novo usuário. O e-mail será validado, e o cadastro será registrado.

Os dados dos usuários cadastrados serão salvos no arquivo usuarios.txt no mesmo diretório do programa.

Estrutura do Projeto
SistemaCadastro.java: Arquivo principal com a lógica de cadastro e validações.
usuarios.txt: Arquivo onde os dados dos usuários são armazenados.
Contribuições
Sinta-se à vontade para contribuir com melhorias neste projeto! Para contribuir:

Fork o repositório.
Crie uma branch com sua nova funcionalidade (git checkout -b feature/nova-funcionalidade).
Commit suas mudanças (git commit -am 'Adicionando nova funcionalidade').
Faça o push para a sua branch (git push origin feature/nova-funcionalidade).
Crie um Pull Request.
Licença
Este projeto está licenciado sob a Licença MIT - veja o arquivo LICENSE para mais detalhes.
