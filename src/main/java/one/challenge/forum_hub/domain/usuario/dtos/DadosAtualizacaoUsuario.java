package one.challenge.forum_hub.domain.usuario.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosAtualizacaoUsuario(

        @NotNull
        Long id,

        @Pattern(regexp = "^[A-Za-z ]+$", message = "O nome deve conter apenas letras e espaços")
        String nome,

        @Email
        String email,

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial")
        String senha
) {
}
