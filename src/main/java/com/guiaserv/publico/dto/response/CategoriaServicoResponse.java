package com.guiaserv.publico.dto.response;

import java.time.LocalDateTime;

public record CategoriaServicoResponse(
        Long id,
        String nome,
        String descricao,
        Boolean ativa,
        LocalDateTime criadaEm
) {
}
