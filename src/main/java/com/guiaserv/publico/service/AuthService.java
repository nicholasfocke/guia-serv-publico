package com.guiaserv.publico.service;

import com.guiaserv.publico.dto.request.CadastroUsuarioRequest;
import com.guiaserv.publico.dto.request.LoginRequest;
import com.guiaserv.publico.dto.response.LoginResponse;
import com.guiaserv.publico.dto.response.UsuarioResponse;
import com.guiaserv.publico.exception.EmailAlreadyExistsException;
import com.guiaserv.publico.exception.InvalidCredentialsException;
import com.guiaserv.publico.model.Perfil;
import com.guiaserv.publico.model.Usuario;
import com.guiaserv.publico.repository.UsuarioRepository;
import com.guiaserv.publico.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UsuarioResponse cadastrar(CadastroUsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("E-mail já cadastrado");
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
                .orElseThrow(() -> new InvalidCredentialsException("E-mail ou senha inválidos"));

        boolean senhaCorreta = passwordEncoder.matches(request.senha(), usuario.getSenha());

        if (!senhaCorreta) {
            throw new InvalidCredentialsException("E-mail ou senha inválidos");
        }

        UsuarioResponse usuarioResponse = new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil()
        );

        String token = jwtService.generateToken(usuario);

        return new LoginResponse(
                "Login realizado com sucesso",
                token,
                "Bearer",
                usuarioResponse
        );
    }
}