package com.guiaserv.publico.dto.response;

import java.time.LocalDateTime;

public record DocumentoResponse(
        Long id,
        String nome,
        String descricao,
        Boolean ativo,
        LocalDateTime criadoEm
) {
}