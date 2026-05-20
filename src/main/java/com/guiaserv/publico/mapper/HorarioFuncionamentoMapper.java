package com.guiaserv.publico.mapper;

import com.guiaserv.publico.dto.request.HorarioFuncionamentoRequest;
import com.guiaserv.publico.dto.response.HorarioFuncionamentoResponse;
import com.guiaserv.publico.model.HorarioFuncionamento;
import com.guiaserv.publico.model.ServicoUnidade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HorarioFuncionamentoMapper {

    @Mapping(source = "servicoUnidade.id", target = "servicoUnidadeId")
    @Mapping(source = "servicoUnidade.servicoPublico.id", target = "servicoId")
    @Mapping(source = "servicoUnidade.servicoPublico.nome", target = "servicoNome")
    @Mapping(source = "servicoUnidade.unidadeAtendimento.id", target = "unidadeId")
    @Mapping(source = "servicoUnidade.unidadeAtendimento.nome", target = "unidadeNome")
    HorarioFuncionamentoResponse toResponse(HorarioFuncionamento horario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", constant = "true")
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(source = "request.diaSemana", target = "diaSemana")
    @Mapping(source = "request.horaAbertura", target = "horaAbertura")
    @Mapping(source = "request.horaFechamento", target = "horaFechamento")
    @Mapping(source = "servicoUnidade", target = "servicoUnidade")
    HorarioFuncionamento toEntity(
            HorarioFuncionamentoRequest request,
            ServicoUnidade servicoUnidade
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(source = "request.diaSemana", target = "diaSemana")
    @Mapping(source = "request.horaAbertura", target = "horaAbertura")
    @Mapping(source = "request.horaFechamento", target = "horaFechamento")
    @Mapping(source = "servicoUnidade", target = "servicoUnidade")
    void updateEntityFromRequest(
            HorarioFuncionamentoRequest request,
            ServicoUnidade servicoUnidade,
            @MappingTarget HorarioFuncionamento horario
    );
}