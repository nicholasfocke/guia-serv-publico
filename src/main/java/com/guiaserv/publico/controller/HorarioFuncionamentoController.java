package com.guiaserv.publico.controller;

import com.guiaserv.publico.dto.request.HorarioFuncionamentoRequest;
import com.guiaserv.publico.dto.response.HorarioFuncionamentoResponse;
import com.guiaserv.publico.service.HorarioFuncionamentoService;
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

@Tag(name = "Horários de Funcionamento", description = "Gerenciamento e consulta de horários de funcionamento por vínculo serviço-unidade")
@RestController
@RequiredArgsConstructor
public class HorarioFuncionamentoController {

    private final HorarioFuncionamentoService horarioFuncionamentoService;

    @PostMapping("/api/horarios")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Cadastrar horário", description = "Cadastra horário de funcionamento para um vínculo serviço-unidade. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Horário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou horário incoerente"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Vínculo serviço-unidade não encontrado"),
            @ApiResponse(responseCode = "409", description = "Horário já cadastrado para este dia")
    })
    public ResponseEntity<HorarioFuncionamentoResponse> cadastrar(
            @RequestBody @Valid HorarioFuncionamentoRequest request
    ) {
        HorarioFuncionamentoResponse response = horarioFuncionamentoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/servicos-unidades/{servicoUnidadeId}/horarios")
    @Operation(summary = "Listar horários", description = "Lista horários de funcionamento de um vínculo serviço-unidade. Endpoint público.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Horários retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vínculo serviço-unidade não encontrado")
    })
    public ResponseEntity<List<HorarioFuncionamentoResponse>> listarPorServicoUnidade(
            @PathVariable Long servicoUnidadeId
    ) {
        return ResponseEntity.ok(horarioFuncionamentoService.listarPorServicoUnidade(servicoUnidadeId));
    }

    @PutMapping("/api/horarios/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Atualizar horário", description = "Atualiza horário de funcionamento. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Horário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou horário incoerente"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Horário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Horário já cadastrado para este dia")
    })
    public ResponseEntity<HorarioFuncionamentoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid HorarioFuncionamentoRequest request
    ) {
        return ResponseEntity.ok(horarioFuncionamentoService.atualizar(id, request));
    }

    @DeleteMapping("/api/horarios/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Excluir horário", description = "Desativa horário de funcionamento. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Horário desativado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Horário não encontrado")
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        horarioFuncionamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}