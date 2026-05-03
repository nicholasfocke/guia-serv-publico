package com.guiaserv.publico.service;

import com.guiaserv.publico.dto.request.CadastroUsuarioRequest;
import com.guiaserv.publico.dto.request.LoginRequest;
import com.guiaserv.publico.dto.response.LoginResponse;
import com.guiaserv.publico.dto.response.UsuarioResponse;
import com.guiaserv.publico.model.Perfil;
import com.guiaserv.publico.model.Usuario;
import com.guiaserv.publico.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioResponse cadastrar(CadastroUsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        Usuario usuario = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(passwordEncoder.encode(request.senha()))
                .perfil(Perfil.USER)
                .build();

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new UsuarioResponse(
                usuarioSalvo.getId(),
                usuarioSalvo.getNome(),
                usuarioSalvo.getEmail(),
                usuarioSalvo.getPerfil()
        );
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("E-mail ou senha inválidos"));

        boolean senhaCorreta = passwordEncoder.matches(request.senha(), usuario.getSenha());

        if (!senhaCorreta) {
            throw new RuntimeException("E-mail ou senha inválidos");
        }

        UsuarioResponse usuarioResponse = new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil()
        );

        return new LoginResponse("Login realizado com sucesso", usuarioResponse);
    }
}