package one.challenge.forum_hub.domain.usuario.dtos;

import one.challenge.forum_hub.domain.usuario.Usuario;

public record DadosDetalhamentoUsuario(
        Long id,
        String nome,
        String email,
        String usuario
) {

    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getUsuario());

    }
}
