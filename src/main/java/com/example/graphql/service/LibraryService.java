package com.example.graphql.service;

import com.example.graphql.model.Author;
import com.example.graphql.model.Book;
import com.example.graphql.model.CreateBookInput;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LibraryService {

    private final Map<Long, Author> authors = new ConcurrentHashMap<>();
    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong bookIdSequence = new AtomicLong(3);

    public LibraryService() {
        authors.put(1L, new Author(1L, "Robert C. Martin"));
        authors.put(2L, new Author(2L, "Martin Fowler"));

        books.put(1L, new Book(1L, "Clean Code", 464, "Software", 1L));
        books.put(2L, new Book(2L, "Refactoring", 448, "Software", 2L));
    }

    public List<Book> findAllBooks() {
        return books.values().stream()
                .sorted(Comparator.comparing(Book::id))
                .toList();
    }

    public Book findBookById(Long id) {
        return books.get(id);
    }

    public List<Book> findBooksByCategory(String category) {
        return books.values().stream()
                .filter(book -> book.category().equalsIgnoreCase(category))
                .sorted(Comparator.comparing(Book::id))
                .toList();
    }

    public Author findAuthorById(Long authorId) {
        return authors.get(authorId);
    }

    public Book createBook(CreateBookInput input) {
        if (!authors.containsKey(input.authorId())) {
            throw new NoSuchElementException("Autor com id " + input.authorId() + " não foi encontrado.");
        }

        long newId = bookIdSequence.getAndIncrement();
        Book newBook = new Book(newId, input.title(), input.pages(), input.category(), input.authorId());
        books.put(newId, newBook);
        return newBook;
    }

    public List<Author> findAllAuthors() {
        return new ArrayList<>(authors.values()).stream()
                .sorted(Comparator.comparing(Author::id))
                .toList();
    }
}
