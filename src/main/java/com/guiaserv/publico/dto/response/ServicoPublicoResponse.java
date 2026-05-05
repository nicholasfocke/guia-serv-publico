package com.guiaserv.publico.dto.response;

import java.time.LocalDateTime;

public record ServicoPublicoResponse(
        Long id,
        String nome,
        String descricao,
        String palavrasChave,
        Boolean precisaAgendamento,
        String orientacoes,
        Boolean ativo,
        LocalDateTime criadoEm,
        Long categoriaId,
        String categoriaNome
) {
}