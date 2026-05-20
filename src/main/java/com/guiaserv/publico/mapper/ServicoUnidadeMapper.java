package com.guiaserv.publico.mapper;

import com.guiaserv.publico.dto.request.ServicoUnidadeRequest;
import com.guiaserv.publico.dto.response.ServicoUnidadeResponse;
import com.guiaserv.publico.model.ServicoPublico;
import com.guiaserv.publico.model.ServicoUnidade;
import com.guiaserv.publico.model.UnidadeAtendimento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServicoUnidadeMapper {

    @Mapping(source = "servicoPublico.id", target = "servicoId")
    @Mapping(source = "servicoPublico.nome", target = "servicoNome")
    @Mapping(source = "unidadeAtendimento.id", target = "unidadeId")
    @Mapping(source = "unidadeAtendimento.nome", target = "unidadeNome")
    @Mapping(source = "unidadeAtendimento.endereco", target = "endereco")
    @Mapping(source = "unidadeAtendimento.bairro", target = "bairro")
    @Mapping(source = "unidadeAtendimento.cidade", target = "cidade")
    @Mapping(source = "unidadeAtendimento.estado", target = "estado")
    @Mapping(source = "unidadeAtendimento.cep", target = "cep")
    @Mapping(source = "unidadeAtendimento.telefone", target = "telefone")
    ServicoUnidadeResponse toResponse(ServicoUnidade vinculo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", constant = "true")
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(source = "servico", target = "servicoPublico")
    @Mapping(source = "unidade", target = "unidadeAtendimento")
    @Mapping(source = "request.observacoes", target = "observacoes")
    ServicoUnidade toEntity(
            ServicoUnidadeRequest request,
            ServicoPublico servico,
            UnidadeAtendimento unidade
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "servicoPublico", ignore = true)
    @Mapping(target = "unidadeAtendimento", ignore = true)
    void updateEntityFromRequest(
            ServicoUnidadeRequest request,
            @MappingTarget ServicoUnidade vinculo
    );
}