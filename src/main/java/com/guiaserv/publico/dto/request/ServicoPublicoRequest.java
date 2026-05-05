package com.guiaserv.publico.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ServicoPublicoRequest(

        @NotBlank(message = "O nome do serviço é obrigatório")
        @Size(min = 3, max = 150, message = "O nome deve ter entre 3 e 150 caracteres")
        String nome,

        @NotBlank(message = "A descrição do serviço é obrigatória")
        @Size(min = 10, max = 500, message = "A descrição deve ter entre 10 e 500 caracteres")
        String descricao,

        @NotBlank(message = "As palavras-chave são obrigatórias")
        @Size(min = 3, max = 500, message = "As palavras-chave devem ter entre 3 e 500 caracteres")
        String palavrasChave,

        @NotNull(message = "Informe se o serviço precisa de agendamento")
        Boolean precisaAgendamento,

        @NotBlank(message = "As orientações do serviço são obrigatórias")
        @Size(min = 10, max = 1000, message = "As orientações devem ter entre 10 e 1000 caracteres")
        String orientacoes,

        @NotNull(message = "A categoria é obrigatória")
        Long categoriaId
) {
}