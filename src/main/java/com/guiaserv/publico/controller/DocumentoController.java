package com.guiaserv.publico.controller;

import com.guiaserv.publico.dto.request.DocumentoRequest;
import com.guiaserv.publico.dto.response.DocumentoResponse;
import com.guiaserv.publico.service.DocumentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DocumentoController {

    private final DocumentoService documentoService;

    @PostMapping("/api/documentos")
    public ResponseEntity<DocumentoResponse> cadastrar(
            @RequestBody @Valid DocumentoRequest request
    ) {
        DocumentoResponse response = documentoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/servicos/{servicoId}/documentos")
    public ResponseEntity<List<DocumentoResponse>> listarPorServico(
            @PathVariable Long servicoId
    ) {
        return ResponseEntity.ok(documentoService.listarPorServico(servicoId));
    }

    @PutMapping("/api/documentos/{id}")
    public ResponseEntity<DocumentoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DocumentoRequest request
    ) {
        return ResponseEntity.ok(documentoService.atualizar(id, request));
    }

    @DeleteMapping("/api/documentos/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        documentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}