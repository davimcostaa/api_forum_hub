package one.challenge.forum_hub.domain.usuario.dtos;
import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(

        @NotBlank
        String login,

        @NotBlank
        String senha
) {
}
