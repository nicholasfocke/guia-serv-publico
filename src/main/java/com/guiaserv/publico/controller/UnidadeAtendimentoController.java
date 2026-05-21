package com.guiaserv.publico.controller;

import com.guiaserv.publico.dto.request.UnidadeAtendimentoRequest;
import com.guiaserv.publico.dto.response.UnidadeAtendimentoResponse;
import com.guiaserv.publico.service.UnidadeAtendimentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Unidades de Atendimento", description = "Consulta, busca e gerenciamento de unidades de atendimento")
@RestController
@RequestMapping("/api/unidades")
@RequiredArgsConstructor
public class UnidadeAtendimentoController {

    private final UnidadeAtendimentoService unidadeAtendimentoService;

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Cadastrar unidade", description = "Cria uma nova unidade de atendimento. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Unidade criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "409", description = "Unidade já cadastrada")
    })
    public ResponseEntity<UnidadeAtendimentoResponse> cadastrar(
            @RequestBody @Valid UnidadeAtendimentoRequest request
    ) {
        UnidadeAtendimentoResponse response = unidadeAtendimentoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar unidades", description = "Lista unidades de atendimento ativas, com filtros opcionais por cidade e bairro. Endpoint público.")
    @ApiResponse(responseCode = "200", description = "Unidades retornadas com sucesso")
    public ResponseEntity<List<UnidadeAtendimentoResponse>> listarTodas(
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) String bairro
    ) {
        return ResponseEntity.ok(unidadeAtendimentoService.listarTodas(cidade, bairro));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar unidade por ID", description = "Retorna os dados de uma unidade de atendimento. Endpoint público.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Unidade encontrada"),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada")
    })
    public ResponseEntity<UnidadeAtendimentoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(unidadeAtendimentoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Atualizar unidade", description = "Atualiza uma unidade de atendimento existente. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Unidade atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada"),
            @ApiResponse(responseCode = "409", description = "Unidade já cadastrada")
    })
    public ResponseEntity<UnidadeAtendimentoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid UnidadeAtendimentoRequest request
    ) {
        return ResponseEntity.ok(unidadeAtendimentoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Excluir unidade", description = "Desativa uma unidade de atendimento. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Unidade excluída/desativada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada")
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        unidadeAtendimentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar unidades por termo", description = "Busca unidades por nome, endereço, bairro, cidade ou CEP. Endpoint público.")
    @ApiResponse(responseCode = "200", description = "Resultado da busca retornado com sucesso")
    public ResponseEntity<List<UnidadeAtendimentoResponse>> buscarPorTermo(
            @RequestParam(required = false) String termo
    ) {
        return ResponseEntity.ok(unidadeAtendimentoService.buscarPorTermo(termo));
    }
}