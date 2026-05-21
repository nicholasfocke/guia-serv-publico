package com.guiaserv.publico.controller;

import com.guiaserv.publico.dto.request.ServicoUnidadeRequest;
import com.guiaserv.publico.dto.response.ServicoUnidadeResponse;
import com.guiaserv.publico.service.ServicoUnidadeService;
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

@Tag(name = "Serviço-Unidade", description = "Vínculo entre serviços públicos e unidades de atendimento")
@RestController
@RequiredArgsConstructor
public class ServicoUnidadeController {

    private final ServicoUnidadeService servicoUnidadeService;

    @PostMapping("/api/servicos/{servicoId}/unidades/{unidadeId}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Vincular serviço a unidade", description = "Vincula um serviço público a uma unidade de atendimento. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Vínculo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Serviço ou unidade não encontrado"),
            @ApiResponse(responseCode = "409", description = "Serviço já vinculado a esta unidade")
    })
    public ResponseEntity<ServicoUnidadeResponse> vincular(
            @PathVariable Long servicoId,
            @PathVariable Long unidadeId,
            @RequestBody(required = false) @Valid ServicoUnidadeRequest request
    ) {
        ServicoUnidadeResponse response = servicoUnidadeService.vincular(servicoId, unidadeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/servicos/{servicoId}/unidades")
    @Operation(summary = "Listar unidades de um serviço", description = "Lista unidades onde determinado serviço público está disponível. Endpoint público.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Unidades do serviço retornadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    public ResponseEntity<List<ServicoUnidadeResponse>> listarUnidadesPorServico(
            @PathVariable Long servicoId
    ) {
        return ResponseEntity.ok(servicoUnidadeService.listarUnidadesPorServico(servicoId));
    }

    @GetMapping("/api/unidades/{unidadeId}/servicos")
    @Operation(summary = "Listar serviços de uma unidade", description = "Lista serviços públicos disponíveis em uma unidade de atendimento. Endpoint público.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Serviços da unidade retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada")
    })
    public ResponseEntity<List<ServicoUnidadeResponse>> listarServicosPorUnidade(
            @PathVariable Long unidadeId
    ) {
        return ResponseEntity.ok(servicoUnidadeService.listarServicosPorUnidade(unidadeId));
    }

    @DeleteMapping("/api/servicos/{servicoId}/unidades/{unidadeId}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Desvincular serviço de unidade", description = "Desativa o vínculo entre serviço público e unidade. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Vínculo desativado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Vínculo não encontrado")
    })
    public ResponseEntity<Void> desvincular(
            @PathVariable Long servicoId,
            @PathVariable Long unidadeId
    ) {
        servicoUnidadeService.desvincular(servicoId, unidadeId);
        return ResponseEntity.noContent().build();
    }
}