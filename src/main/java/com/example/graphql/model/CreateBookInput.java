package com.example.graphql.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateBookInput(
        @NotBlank String title,
        @Min(1) int pages,
        @NotBlank String category,
        @NotNull Long authorId
) {
}
