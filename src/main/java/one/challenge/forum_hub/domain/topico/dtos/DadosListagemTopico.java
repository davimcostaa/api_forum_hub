package one.challenge.forum_hub.domain.topico.dtos;

import one.challenge.forum_hub.domain.topico.Topico;

import java.time.LocalDateTime;

public record DadosListagemTopico(
        Long id,
        String titulo,
        String mensagem,
        String status,
        LocalDateTime dataCriacao,
        Long idAutor,
        Long idCurso
) {
    public DadosListagemTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getStatus(),
                topico.getDataCriacao(), topico.getAutor().getId(), topico.getCurso().getId());
    }
}
