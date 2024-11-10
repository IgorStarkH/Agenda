# Sistema de Agenda de Contatos

Este é um sistema simples de **agenda de contatos** desenvolvido em **Java**, com duas interfaces de interação: **Console** e **Swing**. O programa permite ao usuário realizar as operações básicas de gerenciamento de contatos, como **adicionar**, **listar** e **deletar** contatos, através de uma interface de linha de comando ou de uma interface gráfica baseada em Swing.

## Funcionalidades

- **Adicionar Novo Contato**: Permite ao usuário adicionar um novo contato com nome, email e telefone. Validações são feitas para garantir que os campos de nome e telefone estejam no formato correto.
- **Listar Contatos**: Exibe todos os contatos armazenados na agenda, mostrando suas informações (ID, nome, email, telefone).
- **Deletar Contato**: O usuário pode deletar um contato existente informando o ID do contato a ser removido.
- **Interface em Console**: Oferece uma interface de linha de comando para interação, com menus dinâmicos e validação de entradas.
- **Interface Gráfica (Swing)**: Uma versão mais amigável do sistema com interface gráfica, onde o usuário pode realizar as mesmas operações de forma visual.
- **Validação de Entradas**: O programa valida as entradas do usuário, garantindo que apenas dados válidos sejam inseridos (ex: nomes com apenas letras e espaços, telefones com apenas números).
- **Limpeza da Tela**: A tela é limpa a cada novo menu exibido, proporcionando uma experiência de navegação mais limpa e organizada.

## Requisitos

- JDK 8 ou superior.
- Ambiente de desenvolvimento que suporte Java (por exemplo, IntelliJ IDEA, Eclipse, ou Visual Studio Code).

## Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/IgorStarkH/agenda-console-java.git
   ```

2. Compile e execute o programa no seu ambiente Java.

3. Ao iniciar o programa, você poderá escolher entre a interface **Console** ou **Swing** para gerenciar seus contatos.

## Exemplo de Execução

```text
=============================================
        Bem-vindo ao Sistema de Agenda      
=============================================
Escolha uma opcao abaixo:
1. Rodar no Console
2. Rodar no Swing
0. Sair
Digite sua escolha:
```


