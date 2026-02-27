package com.example.graphql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

@SpringBootTest
@AutoConfigureGraphQlTester
class LibraryGraphQLControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void shouldListBooksWithAuthor() {
        graphQlTester.document("""
                query {
                  books {
                    title
                    author {
                      name
                    }
                  }
                }
                """)
                .execute()
                .path("books[0].title").entity(String.class).isEqualTo("Clean Code")
                .path("books[0].author.name").entity(String.class).isEqualTo("Robert C. Martin");
    }

    @Test
    void shouldCreateBook() {
        graphQlTester.document("""
                mutation {
                  createBook(input: {title: "Domain-Driven Design", pages: 560, category: "Software", authorId: "2"}) {
                    id
                    title
                    author {
                      name
                    }
                  }
                }
                """)
                .execute()
                .path("createBook.title").entity(String.class).isEqualTo("Domain-Driven Design")
                .path("createBook.author.name").entity(String.class).isEqualTo("Martin Fowler");
    }
}
