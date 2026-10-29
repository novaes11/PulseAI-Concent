package com.novaes.pulseAIConcent.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CriarContaDTO(

        @NotBlank(message = "O nome é obrigatório.")
        String nome,
        @NotBlank(message = "O e-mail é obrigatório.")
        String username,
        // 1. Não pode ser vazio
        @NotBlank(message = "A senha não pode estar em branco.")

        // 2. Tamanho mínimo e máximo
        @Size(min = 8, max = 50, message = "A senha deve ter no mínimo 8 caracteres.")

        // 3. Regra de Complexidade (Expressão Regular): maiúscula, número e símbolo
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).*$",
                message = "A senha deve conter ao menos uma letra maiúscula, um número e um caractere especial."
        )
        String password,
        // Campo opcional para confirmação de senha
        String confirmPassword
){
        // Método auxiliar para conferir se as duas senhas são idênticas
        public boolean senhasConferem() {
                return password != null && password.equals(confirmPassword);
        }
}
