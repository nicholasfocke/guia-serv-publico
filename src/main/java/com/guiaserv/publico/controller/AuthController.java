package com.guiaserv.publico.controller;

import com.guiaserv.publico.dto.request.CadastroUsuarioRequest;
import com.guiaserv.publico.dto.request.LoginRequest;
import com.guiaserv.publico.dto.response.LoginResponse;
import com.guiaserv.publico.dto.response.UsuarioResponse;
import com.guiaserv.publico.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody @Valid CadastroUsuarioRequest request) {
        UsuarioResponse usuario = authService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}

