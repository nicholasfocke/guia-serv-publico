package com.guiaserv.publico.controller;

import com.guiaserv.publico.dto.request.ServicoUnidadeRequest;
import com.guiaserv.publico.dto.response.ServicoUnidadeResponse;
import com.guiaserv.publico.service.ServicoUnidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ServicoUnidadeController {

    private final ServicoUnidadeService servicoUnidadeService;

    @PostMapping("/api/servicos/{servicoId}/unidades/{unidadeId}")
    public ResponseEntity<ServicoUnidadeResponse> vincular(
            @PathVariable Long servicoId,
            @PathVariable Long unidadeId,
            @RequestBody(required = false) @Valid ServicoUnidadeRequest request
    ) {
        ServicoUnidadeResponse response = servicoUnidadeService.vincular(servicoId, unidadeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/servicos/{servicoId}/unidades")
    public ResponseEntity<List<ServicoUnidadeResponse>> listarUnidadesPorServico(
            @PathVariable Long servicoId
    ) {
        return ResponseEntity.ok(servicoUnidadeService.listarUnidadesPorServico(servicoId));
    }

    @GetMapping("/api/unidades/{unidadeId}/servicos")
    public ResponseEntity<List<ServicoUnidadeResponse>> listarServicosPorUnidade(
            @PathVariable Long unidadeId
    ) {
        return ResponseEntity.ok(servicoUnidadeService.listarServicosPorUnidade(unidadeId));
    }

    @DeleteMapping("/api/servicos/{servicoId}/unidades/{unidadeId}")
    public ResponseEntity<Void> desvincular(
            @PathVariable Long servicoId,
            @PathVariable Long unidadeId
    ) {
        servicoUnidadeService.desvincular(servicoId, unidadeId);
        return ResponseEntity.noContent().build();
    }
}