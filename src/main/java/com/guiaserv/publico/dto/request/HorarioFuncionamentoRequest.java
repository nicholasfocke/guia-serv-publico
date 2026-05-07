package com.guiaserv.publico.dto.request;

import com.guiaserv.publico.model.DiaSemana;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record HorarioFuncionamentoRequest(

        @NotNull(message = "O dia da semana é obrigatório")
        DiaSemana diaSemana,

        @NotNull(message = "A hora de abertura é obrigatória")
        LocalTime horaAbertura,

        @NotNull(message = "A hora de fechamento é obrigatória")
        LocalTime horaFechamento,

        @NotNull(message = "O vínculo entre serviço e unidade é obrigatório")
        Long servicoUnidadeId
) {
}