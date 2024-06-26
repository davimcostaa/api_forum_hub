package one.challenge.forum_hub.domain.curso.dtos;

import jakarta.validation.constraints.NotNull;
import one.challenge.forum_hub.domain.curso.Categoria;

public record DadosAtualizacaoCurso(
        @NotNull
        Long id,
        String nome,
        Categoria categoria
) {
}
