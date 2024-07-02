package one.challenge.forum_hub.domain.topico.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosAtualizacaoTopico(
        @NotNull
        Long id,

        @Size(min = 5, max = 50)
        String titulo,

        @Size(min = 10, max = 550)
        String mensagem
) {

}
