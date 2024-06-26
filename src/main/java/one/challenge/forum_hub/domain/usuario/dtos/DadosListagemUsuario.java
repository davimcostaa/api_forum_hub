package one.challenge.forum_hub.domain.usuario.dtos;

import one.challenge.forum_hub.domain.usuario.Usuario;

public record DadosListagemUsuario(
        Long id,
        String nome,
        String usuario
) {
    public DadosListagemUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getUsuario());
    }
}
