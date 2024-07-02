package one.challenge.forum_hub.domain.resposta;

import one.challenge.forum_hub.domain.ValidacaoException;
import one.challenge.forum_hub.domain.curso.CursoRepository;
import one.challenge.forum_hub.domain.resposta.dtos.DadosCadastroResposta;
import one.challenge.forum_hub.domain.resposta.dtos.DadosListagemResposta;
import one.challenge.forum_hub.domain.topico.TopicoRepository;
import one.challenge.forum_hub.domain.usuario.UsuarioRepository;
import one.challenge.forum_hub.infra.exceptions.ForbiddenException;
import one.challenge.forum_hub.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RespostaService {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private TokenService tokenService;

    public DadosListagemResposta criarResposta(DadosCadastroResposta dados) {
        if(!usuarioRepository.existsById(dados.idUsuario())) {
            throw new ValidacaoException("Id do usuário informado não existe!");
        }

        if(!topicoRepository.existsById(dados.idTopico())) {
            throw new ValidacaoException("Id do tópico informado não existe!");
        }
        var topicoSolucionado = topicoRepository.topicoJaSolucionado(dados.idTopico());
        var topicoFechado = topicoRepository.topicoJaFechado(dados.idTopico());

        if(topicoSolucionado || topicoFechado) {
            throw new ForbiddenException("Tópico já solucionado ou concluído!");
        }

        var autor = usuarioRepository.getReferenceById(dados.idUsuario());
        var topico = topicoRepository.getReferenceById(dados.idTopico());

        var data = LocalDateTime.now();
        var solucao = false;

        var resposta = new Resposta(null, dados.mensagem(), data, solucao, autor, topico);

        respostaRepository.save(resposta);

        return new DadosListagemResposta(resposta);
    }

    public void verificarPermissao(Long topicoId, String token) {
        var topico = topicoRepository.getReferenceById(topicoId);
        String jwt = token.substring(7);
        var idRequisicao = tokenService.getClaim(jwt);

        if (!topico.getAutor().getId().equals(idRequisicao)) {
            throw new ForbiddenException("Usuário não autorizado para esta operação");
        }
    }
}
