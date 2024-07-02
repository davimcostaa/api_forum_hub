package one.challenge.forum_hub.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import one.challenge.forum_hub.domain.resposta.RespostaRepository;
import one.challenge.forum_hub.domain.resposta.RespostaService;
import one.challenge.forum_hub.domain.resposta.dtos.DadosAtualizacaoResposta;
import one.challenge.forum_hub.domain.resposta.dtos.DadosCadastroResposta;
import one.challenge.forum_hub.domain.resposta.dtos.DadosDetalhamentoResposta;
import one.challenge.forum_hub.domain.resposta.dtos.DadosListagemResposta;
import one.challenge.forum_hub.domain.usuario.UsuarioRepository;
import one.challenge.forum_hub.infra.exceptions.ForbiddenException;
import one.challenge.forum_hub.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/respostas")
@SecurityRequirement(name = "bearer-key")
public class RespostaController {

    @Autowired
    private RespostaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespostaService service;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ResponseEntity<Page<DadosListagemResposta>> listar(@PageableDefault(size = 10) Pageable paginacao ) {

        var page = repository.findAll(paginacao).map(DadosListagemResposta::new);
        return ResponseEntity.ok(page);
    }

    @PostMapping()
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroResposta dados, UriComponentsBuilder uriBuilder) {

        var dto = service.criarResposta(dados);
        var uri = uriBuilder.path("/respostas/{id}").buildAndExpand(dto.id()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoResposta dados, @RequestHeader("Authorization") String token) {
        var resposta = repository.getReferenceById(dados.id());
        service.verificarPermissao(dados.id(), token);

        resposta.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosListagemResposta(resposta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id, @RequestHeader("Authorization") String token) {

        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("ID não encontrado");
        }

        service.verificarPermissao(id, token);

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var resposta = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoResposta(resposta));
    }

    @PostMapping("/solucao/{id}")
    @Transactional
    public ResponseEntity marcarComoSolucionado(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("ID não encontrado");
        }

        var resposta = repository.getReferenceById(id);
        var autorTopico = repository.getReferenceById(resposta.getTopico().getAutor().getId());
        String jwt = token.substring(7);
        var idRequisicao = tokenService.getClaim(jwt);

        if (!autorTopico.getId().equals(idRequisicao)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        resposta.marcarComoSolucao();

        return ResponseEntity.ok(new DadosDetalhamentoResposta(resposta));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity handleForbiddenException(ForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}
