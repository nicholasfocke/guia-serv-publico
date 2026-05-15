package com.guiaserv.publico.mapper;

import com.guiaserv.publico.dto.request.ServicoDocumentoRequest;
import com.guiaserv.publico.dto.response.ServicoDocumentoResponse;
import com.guiaserv.publico.model.Documento;
import com.guiaserv.publico.model.ServicoDocumento;
import com.guiaserv.publico.model.ServicoPublico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServicoDocumentoMapper {

    @Mapping(source = "servicoPublico.id", target = "servicoId")
    @Mapping(source = "servicoPublico.nome", target = "servicoNome")
    @Mapping(source = "documento.id", target = "documentoId")
    @Mapping(source = "documento.nome", target = "documentoNome")
    @Mapping(source = "documento.descricao", target = "documentoDescricao")
    ServicoDocumentoResponse toResponse(ServicoDocumento servicoDocumento);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", constant = "true")
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(source = "servico", target = "servicoPublico")
    @Mapping(source = "documento", target = "documento")
    @Mapping(source = "request.obrigatorio", target = "obrigatorio")
    @Mapping(source = "request.observacoes", target = "observacoes")
    ServicoDocumento toEntity(
            ServicoDocumentoRequest request,
            ServicoPublico servico,
            Documento documento
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "servicoPublico", ignore = true)
    @Mapping(target = "documento", ignore = true)
    void updateEntityFromRequest(
            ServicoDocumentoRequest request,
            @MappingTarget ServicoDocumento servicoDocumento
    );
}