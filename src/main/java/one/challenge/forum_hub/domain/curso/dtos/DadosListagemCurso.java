package one.challenge.forum_hub.domain.curso.dtos;

import one.challenge.forum_hub.domain.curso.Categoria;
import one.challenge.forum_hub.domain.curso.Curso;

public record DadosListagemCurso(
        Long id,
        String nome,
        Categoria categoria
) {
    public DadosListagemCurso(Curso curso) {
        this(curso.getId(), curso.getNome(), curso.getCategoria());
    }
}
