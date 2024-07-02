package one.challenge.forum_hub.domain.resposta.dtos;


import one.challenge.forum_hub.domain.resposta.Resposta;

public record DadosListagemResposta(
        Long id,
        String mensagem,
        Long idUsuario,
        Long idTopico,
        Boolean solucao
) {
    public DadosListagemResposta(Resposta resposta) {
        this(resposta.getId(), resposta.getMensagem(),
                resposta.getAutor().getId(), resposta.getTopico().getId(), resposta.getSolucao());
    }
}
