package one.challenge.forum_hub.domain.topico.dtos;

import one.challenge.forum_hub.domain.resposta.Resposta;
import one.challenge.forum_hub.domain.resposta.dtos.DadosDetalhamentoResposta;
import one.challenge.forum_hub.domain.topico.Topico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        String status,
        LocalDateTime dataCriacao,
        String nomeAutor,
        String nomeCurso,
        List<DadosDetalhamentoResposta> respostas
) {
    public DadosDetalhamentoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getStatus(),
                topico.getDataCriacao(), topico.getAutor().getNome(), topico.getCurso().getNome(),
                topico.getRespostas().stream().map(DadosDetalhamentoResposta::new).collect(Collectors.toList()));
    }
}
