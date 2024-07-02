package one.challenge.forum_hub.domain.topico.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosCadastroTopico(

        @NotBlank
        @Size(min = 5, max = 50)
        String titulo,

        @NotBlank
        @Size(min = 10, max = 550)
        String mensagem,

        @NotNull
        Long idUsuario,

        @NotNull
        Long idCurso

) {
}
