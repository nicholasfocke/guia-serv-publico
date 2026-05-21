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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Avaliações", description = "Registro e consulta de avaliações de serviços e unidades")
@RestController
@RequiredArgsConstructor
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @PostMapping("/api/avaliacoes")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Criar avaliação", description = "Permite que um usuário autenticado avalie um serviço em uma unidade.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Serviço, unidade ou usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Usuário já avaliou este serviço nesta unidade")
    })
    public ResponseEntity<AvaliacaoResponse> avaliar(
            @RequestBody @Valid AvaliacaoRequest request,
            Authentication authentication
    ) {
        AvaliacaoResponse response = avaliacaoService.avaliar(request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/servicos/{servicoId}/avaliacoes")
    @Operation(summary = "Listar avaliações de serviço", description = "Lista avaliações vinculadas a um serviço público. Endpoint público.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Avaliações do serviço retornadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    public ResponseEntity<List<AvaliacaoResponse>> listarPorServico(
            @PathVariable Long servicoId
    ) {
        return ResponseEntity.ok(avaliacaoService.listarPorServico(servicoId));
    }

    @GetMapping("/api/unidades/{unidadeId}/avaliacoes")
    @Operation(summary = "Listar avaliações de unidade", description = "Lista avaliações vinculadas a uma unidade de atendimento. Endpoint público.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Avaliações da unidade retornadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada")
    })
    public ResponseEntity<List<AvaliacaoResponse>> listarPorUnidade(
            @PathVariable Long unidadeId
    ) {
        return ResponseEntity.ok(avaliacaoService.listarPorUnidade(unidadeId));
    }
}