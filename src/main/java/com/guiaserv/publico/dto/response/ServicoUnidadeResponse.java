package com.guiaserv.publico.dto.response;

import java.time.LocalDateTime;

public record ServicoUnidadeResponse(
        Long id,
        Long servicoId,
        String servicoNome,
        Long unidadeId,
        String unidadeNome,
        String endereco,
        String bairro,
        String cidade,
        String estado,
        String cep,
        String telefone,
        String observacoes,
        Boolean ativo,
        LocalDateTime criadoEm
) {
}