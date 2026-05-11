package com.guiaserv.publico.mapper;

import com.guiaserv.publico.dto.request.CategoriaServicoRequest;
import com.guiaserv.publico.dto.response.CategoriaServicoResponse;
import com.guiaserv.publico.model.CategoriaServico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoriaServicoMapper {

    CategoriaServicoResponse toResponse(CategoriaServico categoria);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativa", constant = "true")
    @Mapping(target = "criadaEm", ignore = true)
    CategoriaServico toEntity(CategoriaServicoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativa", ignore = true)
    @Mapping(target = "criadaEm", ignore = true)
    void updateEntityFromRequest(CategoriaServicoRequest request, @MappingTarget CategoriaServico categoria);
}