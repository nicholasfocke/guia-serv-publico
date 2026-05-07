package com.guiaserv.publico.controller;

import com.guiaserv.publico.dto.request.UnidadeAtendimentoRequest;
import com.guiaserv.publico.dto.response.UnidadeAtendimentoResponse;
import com.guiaserv.publico.service.UnidadeAtendimentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unidades")
@RequiredArgsConstructor
public class UnidadeAtendimentoController {

    private final UnidadeAtendimentoService unidadeAtendimentoService;

    @PostMapping
    public ResponseEntity<UnidadeAtendimentoResponse> cadastrar(
            @RequestBody @Valid UnidadeAtendimentoRequest request
    ) {
        UnidadeAtendimentoResponse response = unidadeAtendimentoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UnidadeAtendimentoResponse>> listarTodas(
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) String bairro
    ) {
        return ResponseEntity.ok(unidadeAtendimentoService.listarTodas(cidade, bairro));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeAtendimentoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(unidadeAtendimentoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadeAtendimentoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid UnidadeAtendimentoRequest request
    ) {
        return ResponseEntity.ok(unidadeAtendimentoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        unidadeAtendimentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<UnidadeAtendimentoResponse>> buscarPorTermo(
            @RequestParam(required = false) String termo
    ) {
        return ResponseEntity.ok(unidadeAtendimentoService.buscarPorTermo(termo));
    }
}