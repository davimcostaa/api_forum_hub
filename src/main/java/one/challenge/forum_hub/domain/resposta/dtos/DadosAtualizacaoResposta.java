package one.challenge.forum_hub.domain.resposta.dtos;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoResposta(

        @NotNull
        Long id,

        String mensagem
) {
}
