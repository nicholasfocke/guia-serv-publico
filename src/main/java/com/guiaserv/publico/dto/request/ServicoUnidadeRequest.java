package com.guiaserv.publico.dto.request;

import jakarta.validation.constraints.Size;

public record ServicoUnidadeRequest(

        @Size(max = 500, message = "As observações devem ter no máximo 500 caracteres")
        String observacoes
) {
}