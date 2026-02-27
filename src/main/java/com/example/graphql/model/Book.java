package com.example.graphql.model;

public record Book(Long id, String title, int pages, String category, Long authorId) {
}
