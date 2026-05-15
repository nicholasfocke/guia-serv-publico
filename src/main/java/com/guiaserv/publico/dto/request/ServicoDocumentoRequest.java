package com.guiaserv.publico.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ServicoDocumentoRequest(

        @NotNull(message = "Informe se o documento é obrigatório")
        Boolean obrigatorio,

        @Size(max = 500, message = "As observações devem ter no máximo 500 caracteres")
        String observacoes
) {
}