package com.guiaserv.publico.controller;

import com.guiaserv.publico.dto.request.HorarioFuncionamentoRequest;
import com.guiaserv.publico.dto.response.HorarioFuncionamentoResponse;
import com.guiaserv.publico.service.HorarioFuncionamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HorarioFuncionamentoController {

    private final HorarioFuncionamentoService horarioFuncionamentoService;

    @PostMapping("/api/horarios")
    public ResponseEntity<HorarioFuncionamentoResponse> cadastrar(
            @RequestBody @Valid HorarioFuncionamentoRequest request
    ) {
        HorarioFuncionamentoResponse response = horarioFuncionamentoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/servicos-unidades/{servicoUnidadeId}/horarios")
    public ResponseEntity<List<HorarioFuncionamentoResponse>> listarPorServicoUnidade(
            @PathVariable Long servicoUnidadeId
    ) {
        return ResponseEntity.ok(horarioFuncionamentoService.listarPorServicoUnidade(servicoUnidadeId));
    }

    @PutMapping("/api/horarios/{id}")
    public ResponseEntity<HorarioFuncionamentoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid HorarioFuncionamentoRequest request
    ) {
        return ResponseEntity.ok(horarioFuncionamentoService.atualizar(id, request));
    }

    @DeleteMapping("/api/horarios/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        horarioFuncionamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}