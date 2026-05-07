package com.guiaserv.publico.controller;

import com.guiaserv.publico.dto.request.CadastroUsuarioRequest;
import com.guiaserv.publico.dto.request.LoginRequest;
import com.guiaserv.publico.dto.response.LoginResponse;
import com.guiaserv.publico.dto.response.UsuarioResponse;
import com.guiaserv.publico.service.AuthService;
import com.guiaserv.publico.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Autenticação", description = "Endpoints de cadastro, login e usuário autenticado")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    @Operation(summary = "Cadastrar usuário", description = "Cria uma conta de usuário comum no sistema")
    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody @Valid CadastroUsuarioRequest request) {
        UsuarioResponse usuario = authService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @Operation(summary = "Login", description = "Autentica o usuário e retorna um token JWT")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Usuário autenticado", description = "Retorna os dados do usuário autenticado pelo token JWT")
    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> usuarioAutenticado(Authentication authentication) {
        return ResponseEntity.ok(
                usuarioService.buscarUsuarioAutenticado(authentication.getName())
        );
    }
}