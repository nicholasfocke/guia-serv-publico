package com.guiaserv.publico.dto.response;

import java.time.LocalDateTime;

public record UnidadeAtendimentoResponse(
        Long id,
        String nome,
        String endereco,
        String bairro,
        String cidade,
        String estado,
        String cep,
        String telefone,
        Double latitude,
        Double longitude,
        Boolean ativo,
        LocalDateTime criadoEm
) {
}