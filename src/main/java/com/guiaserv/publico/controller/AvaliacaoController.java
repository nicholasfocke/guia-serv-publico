package com.guiaserv.publico.controller;

import com.guiaserv.publico.dto.request.AvaliacaoRequest;
import com.guiaserv.publico.dto.response.AvaliacaoResponse;
import com.guiaserv.publico.service.AvaliacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @PostMapping("/api/avaliacoes")
    public ResponseEntity<AvaliacaoResponse> avaliar(
            @RequestBody @Valid AvaliacaoRequest request,
            Authentication authentication
    ) {
        AvaliacaoResponse response = avaliacaoService.avaliar(request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/servicos/{servicoId}/avaliacoes")
    public ResponseEntity<List<AvaliacaoResponse>> listarPorServico(
            @PathVariable Long servicoId
    ) {
        return ResponseEntity.ok(avaliacaoService.listarPorServico(servicoId));
    }

    @GetMapping("/api/unidades/{unidadeId}/avaliacoes")
    public ResponseEntity<List<AvaliacaoResponse>> listarPorUnidade(
            @PathVariable Long unidadeId
    ) {
        return ResponseEntity.ok(avaliacaoService.listarPorUnidade(unidadeId));
    }
}