package com.guiaserv.publico.dto.response;

public record LoginResponse(
        String mensagem,
        UsuarioResponse usuario
) {
}
