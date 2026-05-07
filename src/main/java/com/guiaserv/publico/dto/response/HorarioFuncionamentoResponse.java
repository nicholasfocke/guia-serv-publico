package com.guiaserv.publico.dto.response;

import com.guiaserv.publico.model.DiaSemana;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record HorarioFuncionamentoResponse(
        Long id,
        DiaSemana diaSemana,
        LocalTime horaAbertura,
        LocalTime horaFechamento,
        Boolean ativo,
        LocalDateTime criadoEm,
        Long servicoUnidadeId,
        Long servicoId,
        String servicoNome,
        Long unidadeId,
        String unidadeNome
) {
}