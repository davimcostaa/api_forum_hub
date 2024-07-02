package one.challenge.forum_hub.domain.resposta.dtos;


import one.challenge.forum_hub.domain.resposta.Resposta;

import java.time.LocalDateTime;

public record DadosDetalhamentoResposta(
        Long id,
        String mensagem,
        String nomeAutor,
        String nomeCurso,
        LocalDateTime dataCriacao,
        Boolean solucao
) {
    public DadosDetalhamentoResposta(Resposta resposta) {
        this(resposta.getId(), resposta.getMensagem(),
                resposta.getAutor().getNome(), resposta.getTopico().getTitulo(),
                resposta.getDataCriacao(),resposta.getSolucao());
    }
}
