package com.example.graphql.controller;

import com.example.graphql.model.Author;
import com.example.graphql.model.Book;
import com.example.graphql.model.CreateBookInput;
import com.example.graphql.service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class LibraryGraphQLController {

    private final LibraryService libraryService;

    public LibraryGraphQLController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @QueryMapping
    public List<Book> books() {
        return libraryService.findAllBooks();
    }

    @QueryMapping
    public Book bookById(@Argument Long id) {
        return libraryService.findBookById(id);
    }

    @QueryMapping
    public List<Book> booksByCategory(@Argument String category) {
        return libraryService.findBooksByCategory(category);
    }

    @QueryMapping
    public List<Author> authors() {
        return libraryService.findAllAuthors();
    }

    @MutationMapping
    public Book createBook(@Argument @Valid CreateBookInput input) {
        return libraryService.createBook(input);
    }

    @SchemaMapping(typeName = "Book", field = "author")
    public Author author(Book book) {
        return libraryService.findAuthorById(book.authorId());
    }
}
