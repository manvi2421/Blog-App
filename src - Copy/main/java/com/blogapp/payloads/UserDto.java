package com.blogapp.payloads;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
    @NotNull(message = "Name shouldn't be null")
    @NotEmpty(message = "Name shouldn't be empty")
    private String name;

    @Pattern(regexp = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}" , message = "Invalid Email")
    private String email;

    @NotNull(message = "About shouldn't be null")
    private String about;

    @NotNull(message = "Password shouldn't be null")
    @NotEmpty(message = "Password shouldn't be empty")
    @Size(min = 4 , message = "Password must be minimum of 4 characters")
    private String password;
}
