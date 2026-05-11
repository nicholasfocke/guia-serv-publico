package com.guiaserv.publico.mapper;

import com.guiaserv.publico.dto.response.UsuarioResponse;
import com.guiaserv.publico.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioResponse toResponse(Usuario usuario);
}