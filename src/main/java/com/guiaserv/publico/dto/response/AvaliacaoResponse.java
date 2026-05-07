package com.guiaserv.publico.dto.response;

import java.time.LocalDateTime;

public record AvaliacaoResponse(
        Long id,
        Integer nota,
        String comentario,
        Long usuarioId,
        String usuarioNome,
        Long servicoId,
        String servicoNome,
        Long unidadeId,
        String unidadeNome,
        Boolean ativo,
        LocalDateTime criadoEm
) {
}