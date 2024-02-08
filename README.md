# driven-entrega3-aceleracao-java

- **Entrega #03 - API Boardcamp com Spring** do aluno Luiz Cláudio F. Fernandez, Aceleração de Orientação a Objetos (01/2024) com Java da Driven.

- **Deploy**: https://boardcamp-api-9yfe.onrender.com

- **Tecnologias utilizadas**:
    - Java SE Development Kit 17.0.9
    - Spring Boot 3.2.1
    - PostgreSQL 14.10

- **Descrição**: Sistema de gestão de uma locadora de jogos de tabuleiro.

## Requisitos

### Jogos

- Formato de um jogo (tabela `games`)
    
    ```sql
    {
        id: 1,
        name: 'Banco Imobiliário',
        image: 'http://',
        stockTotal: 3,
        pricePerDay: 1500
    }
    ```
    
- Listar jogos.
    - **Rota**: **GET** `/games`

    - **Response:** lista dos jogos encontrados, seguindo o formato abaixo e status `200 (OK)`.
        
        ```sql
        [
            {
                id: 1,
                name: 'Banco Imobiliário',
                image: 'http://',
                stockTotal: 3,
                pricePerDay: 1500
            },
            {
                id: 2,
                name: 'Detetive',
                image: 'http://',
                stockTotal: 1,
                pricePerDay: 2500
            }
        ]
        ```
        
- Inserir um jogo
    - **Rota**: **POST** `/games`

    - **Request**: body no formato abaixo.
        
        ```jsx
        {
            name: 'Banco Imobiliário',
            image: 'http://',
            stockTotal: 3,
            pricePerDay: 1500
        }
        ```
        
    - **Response:** status `201 (CREATED)`, retornando o jogo criado completo (com `id`).
        
        ```sql
        {
            id: 1,
            name: 'Banco Imobiliário',
            image: 'http://',
            stockTotal: 3,
            pricePerDay: 1500
        }
        ```
        
    - **Regras:**
        - Validações ⇒ nesses casos, deve retornar status `400 (BAD REQUEST)`:
            - `name` deve estar presente e não pode ser nulo nem uma string vazia;
            - `stockTotal` e `pricePerDay` devem ser números maiores que 0, não podem ser nulos;
        - `name` não pode ser um nome de jogo já existente ⇒ nesse caso deve retornar status `409 (CONFLICT)`.

### Clientes

- Formato de um cliente (tabela `customers`)
    
    ```sql
    {
        id: 1,
        name: 'Frank Moneybags',
        cpf: '12345678910'
    }
    ```
    
- Buscar um cliente por id
    - **Rota**: **GET** `/customers/:id`

    - **Response:** somente o objeto do usuário com o `id` passado, como mostrado abaixo, e status `200 (OK)`.
        
        ```sql
        {
            id: 1,
            name: 'Frank Moneybags',
            cpf: '12345678910'
        }
        ```
        
    - **Regras:**
        - Se o cliente com `id` dado não existir, deve responder com status `404 (NOT FOUND)`.

- Inserir um cliente
    - **Rota:** **POST** `/customers`

    - **Request:** body no formato abaixo.
        
        ```jsx
        {
            name: 'Frank Moneybags',
            cpf: '12345678910'
        }
        ```
        
    - **Response:** status `201 (CREATED)`, retornando o cliente criado completo (com `id`).
        
        ```sql
        {
            id: 1,
            name: 'Frank Moneybags',
            cpf: '12345678910'
        }
        ```
        
    - **Regras:**
        - Validações ⇒ nesses casos, deve retornar status `400 (BAD REQUEST)`:
            - `cpf` deve ser uma string com 11 caracteres, não pode ser nulo nem string vazia;
            - `name` deve estar presente e não pode ser nulo nem string vazia;
        - `cpf` não pode ser de um cliente já existente ⇒ nesse caso deve retornar status `409 (CONFLICT)`.

### Aluguéis

- Formato de um aluguel (tabela `rentals`)
    
    ```sql
    {
        id: 1,
        customerId: 1,
        gameId: 1,
        rentDate: '2021-06-20',
        daysRented: 3,
        returnDate: null,
        originalPrice: 4500,
        delayFee: 0
    }
    ```

    - sendo:
        - `rentDate` a data em que o aluguel foi feito, formato **LocalDate**
        - `daysRented` por quantos dias o cliente agendou o aluguel
        - `returnDate` a data que o cliente devolveu o jogo (null enquanto não devolvido)
        - `originalPrice` o preço total do aluguel em centavos (dias alugados vezes o preço por dia do jogo)
        - `delayFee` a multa total paga por atraso (dias que passaram do prazo vezes o preço por dia do jogo), começa como 0
    
- Listar aluguéis
    - **Rota**: **GET** `/rentals`

    - **Response:** lista com todos os aluguéis, contendo o `customer` e o `game` do aluguel em questão em cada aluguel, e status `200 (OK)`.
        
        ```sql
        [
            {
                id: 1,
                rentDate: '2021-06-20',
                daysRented: 3,
                returnDate: null,
                originalPrice: 4500,
                delayFee: 0,
                customer: {
                    id: 1,
                    name: 'Frank Moneybags',
                    cpf: '12345678910'
                },
                game: {
                    id: 1,
                    name: 'Banco Imobiliário',
                    image: 'http://',
                    stockTotal: 3,
                    pricePerDay: 1500
                }
            }
        ]
        ```

        - onde:
            - `returnDate` troca pra uma data quando já devolvido
            - `delayFee` troca por outro valor caso tenha devolvido com atraso
        
- Inserir um aluguel
    - **Rota:** **POST** `/rentals`

    - **Request:** body no formato abaixo.
        
        ```jsx
        {
            customerId: 1,
            gameId: 1,
            daysRented: 3
        }
        ```
        
    - **Response:** status `201 (CREATED)`, retornando o aluguel criado completo (com `id`, `customer` e `game`).
        
        ```sql
        {
            id: 1,
            rentDate: '2021-06-20',
            daysRented: 3,
            returnDate: null, 
            originalPrice: 4500,
            delayFee: 0, 
            customer: {
                id: 1,
                name: 'Frank Moneybags',
                cpf: '12345678910'
            },
            game: {
                id: 1,
                name: 'Banco Imobiliário',
                image: 'http://',
                stockTotal: 3,
                pricePerDay: 1500
            }
        }
        ```
        
    - **Regras:**
        - Ao inserir um aluguel, os campos `rentDate` e `originalPrice` devem ser populados automaticamente antes de salvá-lo:
            - `rentDate`: data atual no momento da inserção.
            - `originalPrice`: `daysRented` multiplicado pelo preço por dia do jogo no momento da inserção.
        - Ao inserir um aluguel, o campo `returnDate` deve começar como `null` e a `delayFee` deve começar como 0.
        - Validações ⇒ nesses casos, deve retornar status `400 (BAD REQUEST)`:
            - `daysRented` deve ser um número maior que 0, não pode ser nulo.
            - `gameId` e `customerId` não podem ser nulos.
        - Ao inserir um aluguel, deve verificar se `gameId` se refere a um jogo existente. Se não, deve responder com status `404 (NOT FOUND)`.
        - Ao inserir um aluguel, deve verificar se `customerId` se refere a um cliente existente. Se não, deve responder com status `404 (NOT FOUND)`.
        - Ao inserir um aluguel, deve-se validar que existem jogos disponíveis, ou seja, que não tem aluguéis em aberto acima da quantidade de jogos em estoque. Caso contrário, deve retornar status `422 (UNPROCESSABLE ENTITY)`.

- Finalizar aluguel
    - **Rota:** **PUT** `/rentals/:id/return`

    - **Response:** status `200 (OK)`, retornando o aluguel finalizado completo (com `id`, `customer` e `game`).
        
        ```sql
        {
            id: 1,
            rentDate: '2021-06-20',
            daysRented: 3,
            returnDate: '2021-06-25', 
            originalPrice: 4500,
            delayFee: 3000, 
            customer: {
                id: 1,
                name: 'Frank Moneybags',
                cpf: '12345678910'
            },
            game: {
                id: 1,
                name: 'Banco Imobiliário',
                image: 'http://',
                stockTotal: 3,
                pricePerDay: 1500
            }
        }
        ```
        
    - **Regras:**
        - Deve verificar se o `id` do aluguel fornecido existe. Se não, deve responder com status `404 (NOT FOUND)`.
        - Ao retornar um aluguel, deve verificar se o aluguel já não está finalizado. Se estiver, deve responder com status `422 (UNPROCESSABLE ENTITY)`.
        - Ao retornar um aluguel, o campo `returnDate` deve ser populado com a data atual do momento do retorno.
        - Ao retornar um aluguel, o campo `delayFee` deve ser automaticamente populado com um valor equivalente ao número de dias de atraso vezes o preço por dia do jogo.
            - *Exemplo*: se o cliente alugou no dia **20/06** um jogo por **3 dias**, ele deveria devolver no dia **23/06**. Caso ele devolva somente no dia **25/06**, o sistema deve considerar **2 dias de atraso**. Nesse caso, se o jogo custava **R$ 15,00** por dia, a `delayFee` deve ser de **R$ 30,00** (3000 centavos).

## Instruções para rodar localmente (no [VS Code](https://code.visualstudio.com/download))

- Certifique-se de ter o [Java](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) e [PostgreSQL](https://www.postgresql.org/download/) instalados e configurados.

- Baixe ou clone o projeto na sua máquina local.

- Instale as seguintes extensões no VS Code:
    - [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
    - [XML](https://marketplace.visualstudio.com/items?itemName=redhat.vscode-xml)
    - [SonarLint](https://marketplace.visualstudio.com/items?itemName=SonarSource.sonarlint-vscode)
    - [Checkstyle for Java](https://marketplace.visualstudio.com/items?itemName=shengchen.vscode-checkstyle)
    - [Spring Boot Extension Pack](https://marketplace.visualstudio.com/items?itemName=vmware.vscode-boot-dev-pack)

- Certifique-se de que o servidor do PostgreSQL esteja rodando e crie um banco de dados para desenvolvimento.

- Com base no arquivo `.env.example`, crie um arquivo `.env` na raiz do projeto preenchendo as variáveis de acordo com a configuração padrão do banco criado anteriormente ou com os devidos ajustes baseados na sua configuração local (por exemplo, se o Postgres estiver rodando em outra porta que não 5432). Por padrão, o banco exigirá a seguinte configuração:

    ```
    DB_URL=jdbc:postgresql://localhost:5432/<nome-do-banco>
    DB_USERNAME=<usuario-postgres>
    DB_PASSWORD=<senha-postgres>
    ```

- Execute a aplicação pelo comando `Run` que surgirá no arquivo `src/main/java/com/boardcamp/api/ApiApplication.java` ou através dos botões/atalhos do VS Code.

- Por padrão, a aplicação rodará na porta `8080`.

- Caso queira rodar os testes unitários e de integração:
    - Crie um novo banco de dados para testes.
    - Com base no arquivo `.env.example`, crie um arquivo `.env.test` na raiz do projeto preenchendo as variáveis de maneira semelhante ao que foi feito anteriormente, mas agora com o nome do novo banco.
    - Rode os testes através dos botões que surgirão na declaração das classes:
        - `RentIntegrationTests` em `src/test/java/com/boardcamp/api/RentIntegrationTests.java`; e
        - `RentUnitTests` em `src/test/java/com/boardcamp/api/RentUnitTests.java`.
