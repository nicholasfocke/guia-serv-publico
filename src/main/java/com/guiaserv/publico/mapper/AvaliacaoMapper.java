package com.guiaserv.publico.mapper;

import com.guiaserv.publico.dto.request.AvaliacaoRequest;
import com.guiaserv.publico.dto.response.AvaliacaoResponse;
import com.guiaserv.publico.model.Avaliacao;
import com.guiaserv.publico.model.ServicoPublico;
import com.guiaserv.publico.model.UnidadeAtendimento;
import com.guiaserv.publico.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AvaliacaoMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.nome", target = "usuarioNome")
    @Mapping(source = "servicoPublico.id", target = "servicoId")
    @Mapping(source = "servicoPublico.nome", target = "servicoNome")
    @Mapping(source = "unidadeAtendimento.id", target = "unidadeId")
    @Mapping(source = "unidadeAtendimento.nome", target = "unidadeNome")
    AvaliacaoResponse toResponse(Avaliacao avaliacao);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", constant = "true")
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(source = "request.nota", target = "nota")
    @Mapping(source = "request.comentario", target = "comentario")
    @Mapping(source = "usuario", target = "usuario")
    @Mapping(source = "servico", target = "servicoPublico")
    @Mapping(source = "unidade", target = "unidadeAtendimento")
    Avaliacao toEntity(
            AvaliacaoRequest request,
            Usuario usuario,
            ServicoPublico servico,
            UnidadeAtendimento unidade
    );
}