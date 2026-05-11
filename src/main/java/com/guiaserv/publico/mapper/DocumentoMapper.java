package com.guiaserv.publico.mapper;

import com.guiaserv.publico.dto.request.DocumentoRequest;
import com.guiaserv.publico.dto.response.DocumentoResponse;
import com.guiaserv.publico.model.Documento;
import com.guiaserv.publico.model.ServicoPublico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DocumentoMapper {

    @Mapping(source = "servicoPublico.id", target = "servicoId")
    @Mapping(source = "servicoPublico.nome", target = "servicoNome")
    DocumentoResponse toResponse(Documento documento);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", constant = "true")
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(source = "request.nome", target = "nome")
    @Mapping(source = "request.descricao", target = "descricao")
    @Mapping(source = "request.obrigatorio", target = "obrigatorio")
    @Mapping(source = "servico", target = "servicoPublico")
    Documento toEntity(DocumentoRequest request, ServicoPublico servico);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(source = "request.nome", target = "nome")
    @Mapping(source = "request.descricao", target = "descricao")
    @Mapping(source = "request.obrigatorio", target = "obrigatorio")
    @Mapping(source = "servico", target = "servicoPublico")
    void updateEntityFromRequest(
            DocumentoRequest request,
            ServicoPublico servico,
            @MappingTarget Documento documento
    );
}