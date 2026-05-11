package com.guiaserv.publico.mapper;

import com.guiaserv.publico.dto.request.UnidadeAtendimentoRequest;
import com.guiaserv.publico.dto.response.UnidadeAtendimentoResponse;
import com.guiaserv.publico.model.UnidadeAtendimento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UnidadeAtendimentoMapper {

    UnidadeAtendimentoResponse toResponse(UnidadeAtendimento unidade);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", constant = "true")
    @Mapping(target = "criadoEm", ignore = true)
    UnidadeAtendimento toEntity(UnidadeAtendimentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    void updateEntityFromRequest(
            UnidadeAtendimentoRequest request,
            @MappingTarget UnidadeAtendimento unidade
    );
}