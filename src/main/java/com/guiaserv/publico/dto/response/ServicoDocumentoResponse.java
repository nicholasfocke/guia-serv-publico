package com.guiaserv.publico.dto.response;

import java.time.LocalDateTime;

public record ServicoDocumentoResponse(
        Long id,
        Long servicoId,
        String servicoNome,
        Long documentoId,
        String documentoNome,
        String documentoDescricao,
        Boolean obrigatorio,
        String observacoes,
        Boolean ativo,
        LocalDateTime criadoEm
) {
}