package one.challenge.forum_hub.domain.topico;

import one.challenge.forum_hub.domain.ValidacaoException;
import one.challenge.forum_hub.domain.curso.CursoRepository;
import one.challenge.forum_hub.domain.topico.dtos.DadosCadastroTopico;
import one.challenge.forum_hub.domain.topico.dtos.DadosListagemTopico;
import one.challenge.forum_hub.domain.usuario.UsuarioRepository;
import one.challenge.forum_hub.infra.exceptions.ForbiddenException;
import one.challenge.forum_hub.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    public DadosListagemTopico criarTopico(DadosCadastroTopico dados) {

        if(!usuarioRepository.existsById(dados.idUsuario())) {
            throw new ValidacaoException("Id do usuário informado não existe!");
        }

        if(!cursoRepository.existsById(dados.idCurso())) {
            throw new ValidacaoException("Id do curso informado não existe!");
        }

        var autor = usuarioRepository.getReferenceById(dados.idUsuario());
        var curso = cursoRepository.getReferenceById(dados.idCurso());
        var data = LocalDateTime.now();
        var status = "criado";

        var topico = new Topico(null, dados.titulo(), dados.mensagem(), data, status, autor, curso, null);

        topicoRepository.save(topico);

        return new DadosListagemTopico(topico);
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
