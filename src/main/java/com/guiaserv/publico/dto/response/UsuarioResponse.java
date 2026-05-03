package com.guiaserv.publico.dto.response;

import com.guiaserv.publico.model.Perfil;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        Perfil perfil
) {
}
