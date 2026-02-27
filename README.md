# Spring Boot + GraphQL (guia prático)

Sim, eu já tenho acesso ao seu repositório neste ambiente, e deixei uma implementação base para você estudar e usar como material de aula.

## Objetivo do projeto

Este projeto mostra como usar GraphQL com Spring Boot para:

- consultar listas e itens por ID;
- filtrar dados por argumento;
- resolver relacionamento (`Book -> Author`) com `@SchemaMapping`;
- criar dados via mutation;
- tratar erros de domínio em GraphQL.

## Stack usada

- Java 17
- Spring Boot 3
- Spring for GraphQL
- JUnit 5 + GraphQlTester

## Como rodar

```bash
mvn spring-boot:run
```

Depois abra:

- GraphiQL: `http://localhost:8080/graphiql`
- Endpoint GraphQL: `http://localhost:8080/graphql`

## Estrutura principal

- `schema.graphqls`: contrato GraphQL (types, Query, Mutation).
- `LibraryGraphQLController`: resolvers com `@QueryMapping`, `@MutationMapping` e `@SchemaMapping`.
- `LibraryService`: camada de regra de negócio (aqui em memória para estudo).
- `GraphQlExceptionHandler`: converte exceções de domínio para erros GraphQL amigáveis.

## Exemplos para testar no GraphiQL

### Query: listar livros com autor

```graphql
query {
  books {
    id
    title
    pages
    category
    author {
      id
      name
    }
  }
}
```

### Query: buscar por ID

```graphql
query {
  bookById(id: "1") {
    title
    author {
      name
    }
  }
}
```

### Query: filtrar por categoria

```graphql
query {
  booksByCategory(category: "Software") {
    title
  }
}
```

### Mutation: criar livro

```graphql
mutation {
  createBook(input: {
    title: "Building Microservices"
    pages: 352
    category: "Software"
    authorId: "2"
  }) {
    id
    title
    author {
      name
    }
  }
}
```

## Como explicar isso em uma aula (roteiro)

### 1) Conceito rápido

- REST expõe múltiplos endpoints.
- GraphQL expõe um endpoint único com um schema fortemente tipado.
- O cliente escolhe exatamente os campos que quer.

### 2) Leitura do schema

Mostre `type Query`, `type Mutation`, `type Book`, `type Author` e `input CreateBookInput`.

### 3) Mapeamento no Spring

- `@QueryMapping` para operações de leitura.
- `@MutationMapping` para escrita.
- `@SchemaMapping` para resolver campos derivados/relacionados.

### 4) Fluxo completo

1. Cliente envia query/mutation para `/graphql`.
2. Spring valida contra o schema.
3. Resolver chama service.
4. Resultado volta com apenas os campos pedidos.

### 5) Tratamento de erros

Explique que exceções de negócio (como autor inexistente) são convertidas em erro GraphQL via `DataFetcherExceptionResolverAdapter`.

### 6) Encerramento (prova de entendimento)

Você pode justificar domínio do tema mostrando que:

- desenhou um schema consistente com o domínio;
- implementou resolvers e relacionamento entre entidades;
- criou mutation com validação;
- escreveu teste automatizado com `GraphQlTester`;
- conhece diferenças práticas entre REST e GraphQL (overfetching/underfetching, flexibilidade de consulta).

## Próximos passos sugeridos

- Persistência com Spring Data JPA.
- Paginação e ordenação.
- Autenticação/autorização por campo.
- DataLoader para evitar N+1 em relacionamentos mais complexos.
