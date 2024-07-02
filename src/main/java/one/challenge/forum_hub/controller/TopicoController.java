package one.challenge.forum_hub.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import one.challenge.forum_hub.domain.topico.TopicoRepository;
import one.challenge.forum_hub.domain.topico.TopicoService;
import one.challenge.forum_hub.domain.topico.dtos.DadosAtualizacaoTopico;
import one.challenge.forum_hub.domain.topico.dtos.DadosCadastroTopico;
import one.challenge.forum_hub.domain.topico.dtos.DadosDetalhamentoTopico;
import one.challenge.forum_hub.domain.topico.dtos.DadosListagemTopico;
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
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoService service;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(size = 10, sort = {"titulo"}) Pageable paginacao ) {

        var page = repository.findAll(paginacao).map(DadosListagemTopico::new);
        return ResponseEntity.ok(page);
    }

    @PostMapping("/concluir/{id}")
    @Transactional
    public ResponseEntity concluir(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("ID não encontrado");
        }

        var topico = repository.getReferenceById(id);

        service.verificarPermissao(id, token);
        topico.concluirTopico();

        return ResponseEntity.ok(new DadosListagemTopico(topico));
    }

    @PostMapping()
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder) {

        var dto = service.criarTopico(dados);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(dto.id()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoTopico dados, @RequestHeader("Authorization") String token) {
        var topico = repository.getReferenceById(dados.id());
        service.verificarPermissao(dados.id(), token);

        topico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosListagemTopico(topico));
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
        var topico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping("/curso/{id}")
    public ResponseEntity<Page<DadosListagemTopico>> buscarTopicoPorCurso(
            @PageableDefault(size = 10, sort = {"titulo"}) Pageable paginacao,
            @PathVariable Long id) {
        var topicos = repository.findAllByCursoId(id, paginacao).map(DadosListagemTopico::new);;
        return ResponseEntity.ok(topicos);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity handleForbiddenException(ForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

}
