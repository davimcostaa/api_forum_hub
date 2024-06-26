package one.challenge.forum_hub.domain.usuario.dtos;

import jakarta.validation.constraints.*;

public record DadosCadastroUsuario(

        @NotBlank
        @Pattern(regexp = "^[A-Za-z ]+$", message = "O nome deve conter apenas letras e espaços")
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size (min = 5, max = 20)
        String usuario,

        @NotBlank
        @Size(min = 5, max = 20)
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial")
        String senha

) {
}
