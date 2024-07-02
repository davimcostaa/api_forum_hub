package one.challenge.forum_hub.domain.resposta.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosCadastroResposta(

        @NotBlank
        @Size(min = 10, max = 550)
        String mensagem,

        @NotNull
        Long idUsuario,

        @NotNull
        Long idTopico
) {

}
