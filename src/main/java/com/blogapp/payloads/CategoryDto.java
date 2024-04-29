package com.blogapp.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private int id;
    @NotNull(message = "Title cannot be null")
    @NotEmpty(message =  "Title cannot be empty")
    private String title;

    @NotNull(message = "Description cannot be null")
    @NotEmpty(message =  "Description cannot be empty")
    private String description;
}
