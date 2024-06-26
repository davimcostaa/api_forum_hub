package one.challenge.forum_hub.domain.curso.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import one.challenge.forum_hub.domain.curso.Categoria;

public record DadosCadastroCurso(

        @NotBlank
        String nome,

        @NotNull
        Categoria categoria
) {
}
