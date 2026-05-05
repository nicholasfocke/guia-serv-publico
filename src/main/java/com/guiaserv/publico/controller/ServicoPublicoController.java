package com.guiaserv.publico.controller;

import com.guiaserv.publico.dto.request.ServicoPublicoRequest;
import com.guiaserv.publico.dto.response.ServicoPublicoResponse;
import com.guiaserv.publico.service.ServicoPublicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
@RequiredArgsConstructor
public class ServicoPublicoController {

    private final ServicoPublicoService servicoPublicoService;

    @PostMapping
    public ResponseEntity<ServicoPublicoResponse> cadastrar(
            @RequestBody @Valid ServicoPublicoRequest request
    ) {
        ServicoPublicoResponse response = servicoPublicoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ServicoPublicoResponse>> listarTodos() {
        return ResponseEntity.ok(servicoPublicoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoPublicoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicoPublicoService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ServicoPublicoResponse>> buscarPorTermo(
            @RequestParam String termo
    ) {
        return ResponseEntity.ok(servicoPublicoService.buscarPorTermo(termo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoPublicoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ServicoPublicoRequest request
    ) {
        return ResponseEntity.ok(servicoPublicoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        servicoPublicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}