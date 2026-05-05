package com.guiaserv.publico.controller;

import com.guiaserv.publico.dto.request.CategoriaServicoRequest;
import com.guiaserv.publico.dto.response.CategoriaServicoResponse;
import com.guiaserv.publico.service.CategoriaServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaServicoController {

    private final CategoriaServicoService categoriaServicoService;

    @PostMapping
    public ResponseEntity<CategoriaServicoResponse> cadastrar(
            @RequestBody @Valid CategoriaServicoRequest request
    ) {
        CategoriaServicoResponse response = categoriaServicoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaServicoResponse>> listarTodas() {
        return ResponseEntity.ok(categoriaServicoService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaServicoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaServicoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaServicoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid CategoriaServicoRequest request
    ) {
        return ResponseEntity.ok(categoriaServicoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        categoriaServicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}