# Agenda Gerenciadora de Salas: Sistema em Java fazendo CRUD no MySQL

Esse sistema é composto por um conjunto de tabelas que representam a reserva de salas, contendo as tabelas: usuarios, salas e reservas. O programa funciona no console e permite gerenciar usuários, salas e reservas (cadastrar, editar, excluir e visualizar), além de gerar relatórios.

- **Disciplina:** Banco de Dados (2026/2)
- **Professor:** Howard Roatti
- **Integrantes:** Alécio Pires, Davi Bicalho
- **Vídeo demonstrativo:** (adicionar o link do YouTube)

Na VM Linux da disciplina (Ubuntu), os bancos de dados rodam em containers Docker, definidos em `~/database_services/docker-compose.yml`. O MySQL já está configurado (banco `maindb`, usuário `root`, senha `mysql`), então basta iniciar o container. **Esse é o único passo feito pelo terminal.** Abra um terminal na área de trabalho da VM e execute:

```shell
~$ cd ~/database_services
~$ docker compose up -d
~$ docker compose ps
```

O serviço `database_services-mysql-1` deve aparecer como `Up`, na porta `3306`.

Todo o restante é feito no **Visual Studio Code**, com as extensões e a conexão descritas em [Executando o sistema no VS Code](#executando-o-sistema-no-vs-code).

## Executando o sistema no VS Code

### 1. Baixar o código na máquina virtual
Não é possível arrastar arquivos para dentro da VM, então o projeto deve ser baixado por ela.
1. Dentro da máquina virtual, abra o navegador e acesse a página de entrega do trabalho (AVA) ou o repositório do projeto no GitHub.
2. Baixe a pasta do projeto (**AgendaSalas**) para a VM. Os arquivos ficam normalmente na pasta **Downloads**.
3. Abra o gerenciador de arquivos e confira se a pasta **AgendaSalas** está completa, com as pastas `src`, `sql`, `lib` e `.vscode`. Se vier compactada, clique com o botão direito e escolha **Extract Here** (Extrair aqui).

### 2. Abrir o projeto no VS Code
1. Abra o Visual Studio Code da máquina virtual.
2. Clique em **File → Open Folder...** e selecione a pasta **AgendaSalas**. Se o VS Code perguntar se confia nos autores da pasta, clique em **Yes, I trust the authors**.

### 3. Instalar as extensões
1. Clique no ícone **Extensions** da barra lateral esquerda (ou pressione `Ctrl+Shift+X`).
2. Pesquise e instale, clicando em **Install**:
    - **Extension Pack for Java** (Microsoft, `vscjava.vscode-java-pack`): permite editar, compilar e executar o projeto Java.
    - **SQLTools** (Matheus Teixeira, `mtxr.sqltools`) e **SQLTools MySQL/MariaDB/TiDB** (`mtxr.sqltools-driver-mysql`): permitem conectar o VS Code ao MySQL.
3. Aguarde o carregamento do Java (o ícone de carregamento na barra inferior desaparece). O driver do MySQL em [lib](lib) e a pasta `src` são reconhecidos automaticamente pelo arquivo [.vscode/settings.json](.vscode/settings.json).

### 4. Criar a conexão com o banco `maindb`
Antes, confirme que o container do MySQL está de pé (`docker compose ps`, no início deste README).

1. Clique no ícone **SQLTools** da barra lateral esquerda (um cilindro).
2. Clique em **Add New Connection** e escolha **MySQL**.
3. Preencha os campos:

    | Campo | Valor |
    |---|---|
    | Connection name | `AgendaSalas` |
    | Server address | `localhost` |
    | Port | `3306` |
    | Database | `maindb` |
    | Username | `root` |
    | Password mode | `Save as plaintext in settings` |
    | Password | `mysql` |

4. Clique em **Test Connection** e, se aparecer a mensagem de sucesso, clique em **Save Connection**.

A conexão `AgendaSalas` já vem configurada no [settings.json](.vscode/settings.json). Se ela já aparecer na lista do SQLTools, basta clicar nela para conectar.

### 5. Criar as tabelas e popular o banco
1. No painel **Explorer** (à esquerda), abra `src/pack/PopularBanco.java`.
2. Clique no botão de rodar (▶ **Run Java**) no canto superior direito do editor.
3. O resultado aparece na parte de baixo da tela. A primeira linha é `Conectado em jdbc:mysql://localhost:3306/maindb`, seguida de uma linha `OK` para cada comando executado e, no final, `total_usuarios: 20`, `total_salas: 10`, `total_reservas: 40` e `Concluído.`

O [PopularBanco.java](src/pack/PopularBanco.java) verifica se a tabela `salas` existe. Se não existir, executa antes o [estrutura.sql](sql/estrutura.sql) (criação das tabelas) e depois executa o [popular.sql](sql/popular.sql). Se as tabelas já existirem, apenas executa o [popular.sql](sql/popular.sql), que apaga o conteúdo das três tabelas antes de inserir os dados de exemplo. Portanto, ele pode ser executado novamente para restaurar os dados.

### 6. Executar o sistema
1. No painel **Explorer**, abra `src/pack/Menu.java`.
2. Clique no botão de rodar (▶ **Run Java**) no canto superior direito do editor.
3. O sistema abre na aba **Terminal** (parte de baixo da tela). Digite as opções do menu diretamente nela e pressione `Enter`.

Para encerrar, escolha a opção **[0] Sair** do menu principal.

## Organização
- [diagrams](diagrams): Nesse diretório está o [diagrama relacional](diagrams/diagrama_relacional.pdf) (lógico) do sistema, também em [código Mermaid](diagrams/AgendaSala.mmd).
    * O sistema possui três entidades: SALAS, USUARIOS e RESERVAS
    * Uma sala e um usuário podem ter várias reservas (relacionamentos 1:N)
- [sql](sql): Nesse diretório estão os scripts para criação das tabelas e inserção de dados fictícios para testes do sistema
    * O usuário `root` do container já possui todos os privilégios, e o banco `maindb` é criado automaticamente na primeira inicialização do container (variável `MYSQL_DATABASE` do `docker-compose.yml`).
    * [estrutura.sql](sql/estrutura.sql): script responsável pela criação das tabelas e dos relacionamentos (chaves primárias e estrangeiras).
    * [popular.sql](sql/popular.sql): script responsável pela inserção dos registros fictícios para testes do sistema.
- [lib](lib): Nesse diretório encontra-se o driver JDBC do MySQL ([mysql-connector-j-26.10.0.jar](lib/mysql-connector-j-26.10.0.jar)).
- [.vscode](.vscode): Nesse diretório estão as configurações do VS Code para o projeto.
    * [settings.json](.vscode/settings.json): define a pasta dos fontes, a pasta de saída (`bin`), o driver em `lib` e a conexão da SQLTools.
- [src/pack](src/pack): Nesse diretório estão os fontes do sistema
    * **Conexão:** [Conexao.java](src/pack/Conexao.java) é o módulo de conexão com o banco de dados MySQL (URL `jdbc:mysql://localhost:3306/maindb`, usuário `root`, senha `mysql`).
      - Exemplo de utilização para consultas simples:

        ```java
        String sql = "SELECT * FROM usuarios WHERE matricula = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                }
            }
        }
        ```
      - Exemplo de utilização para alteração de registros:

        ```java
        String inserir = "INSERT INTO usuarios (matricula, nome, telefone, dataNasc) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement insert = conn.prepareStatement(inserir)) {

            insert.setInt(1, pessoa.getMatricula());
            insert.setString(2, pessoa.getNome());
            insert.setString(3, pessoa.getTelefone());
            insert.setDate(4, Date.valueOf(pessoa.getDataNasc()));

            insert.executeUpdate();
        }
        ```
      - Outros exemplos: [PessoaDB.java](src/pack/PessoaDB.java), [SalaDB.java](src/pack/SalaDB.java) e [ReservaDB.java](src/pack/ReservaDB.java)
    * **Classes das entidades:** [Pessoa.java](src/pack/Pessoa.java) (tabela `usuarios`), [Sala.java](src/pack/Sala.java) (tabela `salas`) e [Reserva.java](src/pack/Reserva.java) (tabela `reservas`), conforme descrito no [diagrama relacional](diagrams/diagrama_relacional.pdf).
    * **Classes controladoras:** [PessoaDB.java](src/pack/PessoaDB.java), [SalaDB.java](src/pack/SalaDB.java) e [ReservaDB.java](src/pack/ReservaDB.java), responsáveis por realizar inserção, alteração, consulta e exclusão dos registros das tabelas. A [ReservaDB.java](src/pack/ReservaDB.java) também impede que uma sala tenha duas reservas com horários sobrepostos.
    * **Relatórios:** [RelatorioDB.java](src/pack/RelatorioDB.java) é a classe responsável por gerar todos os relatórios do sistema e por contar os registros exibidos na tela inicial.
      - Reservas detalhadas (junção de `reservas`, `salas` e `usuarios`)
      - Reservas por sala, por usuário e por período
      - Uso das salas, com quantidade de reservas e tempo total reservado (agrupamento)
      - Salas disponíveis em um horário
      - Ranking de usuários por reservas (agrupamento)
    * [PopularBanco.java](src/pack/PopularBanco.java): Programa responsável por executar um arquivo `.sql` no banco de dados. É utilizado para criar as tabelas ([estrutura.sql](sql/estrutura.sql)) e inserir os registros fictícios ([popular.sql](sql/popular.sql)). Deve ser executado antes do [Menu.java](src/pack/Menu.java). Sem argumentos, cria as tabelas (se não existirem) e popula o banco.
    * [Menu.java](src/pack/Menu.java): Programa principal, responsável por exibir a tela de informações iniciais (nome do sistema, total de registros, integrantes, disciplina, semestre e professor) e por ser a interface entre o usuário e as classes de acesso ao Banco de Dados. Deve ser executado após a criação das tabelas.
    * Regras do sistema:
      - não é possível reservar em data e horário que já passaram;
      - o fim da reserva deve ser depois do início;
      - o telefone do usuário deve ter 13 dígitos, somente números;
      - não é possível excluir usuário ou sala que possua reservas.

### Bibliotecas Utilizadas
- [mysql-connector-j-26.10.0.jar](lib/mysql-connector-j-26.10.0.jar): driver JDBC do MySQL. Já está no diretório [lib](lib) e é incluído automaticamente no projeto pelo VS Code (seção **Referenced Libraries** do painel **JAVA PROJECTS**).
- JDK 11 ou superior, instalado na VM.

### Verificando o banco de dados
Para acessar o MySQL pelo terminal (senha `mysql`):

```shell
~$ docker exec -it database_services-mysql-1 mysql -u root -p
```

```sql
SHOW DATABASES;
USE maindb;
SHOW TABLES;
```

Se por algum motivo o banco `maindb` não existir, crie-o dentro do MySQL com `CREATE DATABASE IF NOT EXISTS maindb;`.

Para criar as tabelas e inserir os registros sem utilizar o Java, também é possível executar os scripts diretamente no container, a partir da pasta do projeto:

```shell
~$ docker exec -i database_services-mysql-1 mysql -u root -pmysql maindb < sql/estrutura.sql
~$ docker exec -i database_services-mysql-1 mysql -u root -pmysql maindb < sql/popular.sql
```

#### Em caso de problemas com a execução do sistema:
- `Communications link failure`: o container do MySQL está parado. Em um terminal, na pasta `~/database_services`, execute `docker compose up -d` e confira com `docker compose ps`.
- `Unknown database 'maindb'`: o banco não foi criado. Entre no MySQL e execute `CREATE DATABASE IF NOT EXISTS maindb;`.
- `Access denied for user 'root'`: a senha é diferente de `mysql`. Confira `MYSQL_ROOT_PASSWORD` no `docker-compose.yml` e os valores do [Conexao.java](src/pack/Conexao.java).
- `No suitable driver found`: o driver não foi carregado. No painel **JAVA PROJECTS**, confira se `mysql-connector-j-26.10.0.jar` aparece em **Referenced Libraries**. Se não aparecer, abra a pasta `AgendaSalas` (e não uma pasta acima dela) no VS Code.
- `Table 'maindb.salas' doesn't exist`: as tabelas não foram criadas. Execute o [PopularBanco.java](src/pack/PopularBanco.java), que cria as tabelas.
- `the attribute version is obsolete` (aviso do `docker compose`): não é um erro e pode ser ignorado.
- Não utilize `docker compose down -v`, pois a opção `-v` apaga os volumes e, com eles, todos os dados do banco. Para parar sem perder os dados, utilize `docker compose stop`.
