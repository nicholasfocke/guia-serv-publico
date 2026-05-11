package com.guiaserv.publico.service;

import com.guiaserv.publico.dto.response.UsuarioResponse;
import com.guiaserv.publico.exception.ResourceNotFoundException;
import com.guiaserv.publico.mapper.UsuarioMapper;
import com.guiaserv.publico.model.Usuario;
import com.guiaserv.publico.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioResponse buscarUsuarioAutenticado(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário autenticado não encontrado"));

        return usuarioMapper.toResponse(usuario);
    }
}