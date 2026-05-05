package com.guiaserv.publico.dto.response;

public record LoginResponse(
        String mensagem,
        String token,
        String tipo,
        UsuarioResponse usuario
) {
}
