package com.guiaserv.publico.controller;

import com.guiaserv.publico.dto.request.DocumentoRequest;
import com.guiaserv.publico.dto.request.ServicoDocumentoRequest;
import com.guiaserv.publico.dto.response.DocumentoResponse;
import com.guiaserv.publico.dto.response.ServicoDocumentoResponse;
import com.guiaserv.publico.service.DocumentoService;
import com.guiaserv.publico.service.ServicoDocumentoService;
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
    private final ServicoDocumentoService servicoDocumentoService;

    @PostMapping("/api/documentos")
    public ResponseEntity<DocumentoResponse> cadastrar(
            @RequestBody @Valid DocumentoRequest request
    ) {
        DocumentoResponse response = documentoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/documentos")
    public ResponseEntity<List<DocumentoResponse>> listarTodos() {
        return ResponseEntity.ok(documentoService.listarTodos());
    }

    @GetMapping("/api/documentos/{id}")
    public ResponseEntity<DocumentoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(documentoService.buscarPorId(id));
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

    @PostMapping("/api/servicos/{servicoId}/documentos/{documentoId}")
    public ResponseEntity<ServicoDocumentoResponse> vincularDocumentoAoServico(
            @PathVariable Long servicoId,
            @PathVariable Long documentoId,
            @RequestBody @Valid ServicoDocumentoRequest request
    ) {
        ServicoDocumentoResponse response = servicoDocumentoService.vincular(servicoId, documentoId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/servicos/{servicoId}/documentos")
    public ResponseEntity<List<ServicoDocumentoResponse>> listarDocumentosPorServico(
            @PathVariable Long servicoId
    ) {
        return ResponseEntity.ok(servicoDocumentoService.listarDocumentosPorServico(servicoId));
    }

    @PutMapping("/api/servicos/{servicoId}/documentos/{documentoId}")
    public ResponseEntity<ServicoDocumentoResponse> atualizarVinculoDocumentoServico(
            @PathVariable Long servicoId,
            @PathVariable Long documentoId,
            @RequestBody @Valid ServicoDocumentoRequest request
    ) {
        return ResponseEntity.ok(
                servicoDocumentoService.atualizarVinculo(servicoId, documentoId, request)
        );
    }

    @DeleteMapping("/api/servicos/{servicoId}/documentos/{documentoId}")
    public ResponseEntity<Void> desvincularDocumentoDoServico(
            @PathVariable Long servicoId,
            @PathVariable Long documentoId
    ) {
        servicoDocumentoService.desvincular(servicoId, documentoId);
        return ResponseEntity.noContent().build();
    }
}