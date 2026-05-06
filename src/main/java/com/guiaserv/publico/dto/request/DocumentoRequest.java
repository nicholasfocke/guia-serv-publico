package com.guiaserv.publico.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DocumentoRequest(

        @NotBlank(message = "O nome do documento é obrigatório")
        @Size(min = 2, max = 150, message = "O nome deve ter entre 2 e 150 caracteres")
        String nome,

        @NotBlank(message = "A descrição do documento é obrigatória")
        @Size(min = 5, max = 500, message = "A descrição deve ter entre 5 e 500 caracteres")
        String descricao,

        @NotNull(message = "Informe se o documento é obrigatório")
        Boolean obrigatorio,

        @NotNull(message = "O serviço público é obrigatório")
        Long servicoId
) {
}