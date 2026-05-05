package com.guiaserv.publico.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaServicoRequest(
        @NotBlank(message = "O nome da categoria é obrigatório")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String nome,

        @NotBlank(message = "A descrição da categoria é obrigatória")
        @Size(min = 10, max = 255, message = "A descrição deve ter entre 10 e 255 caracteres")
        String descricao
) {
}
