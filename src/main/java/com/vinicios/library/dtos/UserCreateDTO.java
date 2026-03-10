package com.vinicios.library.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "User creation data")
public class UserCreateDTO {

    @Schema(description = "User name", example = "John Doe")
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @Schema(description = "User email", example = "john.doe@example.com")
    @Email(message = "Email inválido")
    private String email;

    @Schema(description = "User password", example = "password123")
    @NotBlank(message = "Senha é obrigatória")
    private String password;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}