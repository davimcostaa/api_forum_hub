package one.challenge.forum_hub.domain.usuario.dtos;

import one.challenge.forum_hub.domain.curso.Categoria;
import one.challenge.forum_hub.domain.curso.Curso;

public record DadosDetalhamentoCurso(
        Long id,
        String nome,
        Categoria categoria
) {
    public DadosDetalhamentoCurso(Curso curso) {
        this(curso.getId(), curso.getNome(), curso.getCategoria());
    }
}
