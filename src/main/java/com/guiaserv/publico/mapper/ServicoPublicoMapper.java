package com.guiaserv.publico.mapper;

import com.guiaserv.publico.dto.request.ServicoPublicoRequest;
import com.guiaserv.publico.dto.response.ServicoPublicoResponse;
import com.guiaserv.publico.model.CategoriaServico;
import com.guiaserv.publico.model.ServicoPublico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServicoPublicoMapper {

    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "categoria.nome", target = "categoriaNome")
    ServicoPublicoResponse toResponse(ServicoPublico servico);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", constant = "true")
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(source = "request.nome", target = "nome")
    @Mapping(source = "request.descricao", target = "descricao")
    @Mapping(source = "request.palavrasChave", target = "palavrasChave")
    @Mapping(source = "request.precisaAgendamento", target = "precisaAgendamento")
    @Mapping(source = "request.orientacoes", target = "orientacoes")
    @Mapping(source = "categoria", target = "categoria")
    ServicoPublico toEntity(ServicoPublicoRequest request, CategoriaServico categoria);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(source = "request.nome", target = "nome")
    @Mapping(source = "request.descricao", target = "descricao")
    @Mapping(source = "request.palavrasChave", target = "palavrasChave")
    @Mapping(source = "request.precisaAgendamento", target = "precisaAgendamento")
    @Mapping(source = "request.orientacoes", target = "orientacoes")
    @Mapping(source = "categoria", target = "categoria")
    void updateEntityFromRequest(
            ServicoPublicoRequest request,
            CategoriaServico categoria,
            @MappingTarget ServicoPublico servico
    );
}