package com.guiaserv.publico.controller;

import com.guiaserv.publico.dto.request.ServicoPublicoRequest;
import com.guiaserv.publico.dto.response.ServicoPublicoResponse;
import com.guiaserv.publico.service.ServicoPublicoService;
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

@Tag(name = "Serviços Públicos", description = "Consulta, busca e gerenciamento de serviços públicos")
@RestController
@RequestMapping("/api/servicos")
@RequiredArgsConstructor
public class ServicoPublicoController {

    private final ServicoPublicoService servicoPublicoService;

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Cadastrar serviço público", description = "Cria um novo serviço público. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Serviço criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "409", description = "Serviço já cadastrado")
    })
    public ResponseEntity<ServicoPublicoResponse> cadastrar(
            @RequestBody @Valid ServicoPublicoRequest request
    ) {
        ServicoPublicoResponse response = servicoPublicoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    @Operation(summary = "Listar serviços públicos", description = "Lista todos os serviços públicos ativos. Endpoint público.")
    @ApiResponse(responseCode = "200", description = "Serviços retornados com sucesso")
    public ResponseEntity<List<ServicoPublicoResponse>> listarTodos() {
        return ResponseEntity.ok(servicoPublicoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar serviço por ID", description = "Retorna detalhes de um serviço público. Endpoint público.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Serviço encontrado"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    public ResponseEntity<ServicoPublicoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicoPublicoService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar serviços por termo", description = "Busca serviços por nome, descrição ou palavras-chave. Endpoint público.")
    @ApiResponse(responseCode = "200", description = "Resultado da busca retornado com sucesso")
    public ResponseEntity<List<ServicoPublicoResponse>> buscarPorTermo(
            @RequestParam(required = false) String termo
    ) {
        return ResponseEntity.ok(servicoPublicoService.buscarPorTermo(termo));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Atualizar serviço público", description = "Atualiza um serviço público existente. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado"),
            @ApiResponse(responseCode = "409", description = "Serviço já cadastrado")
    })
    public ResponseEntity<ServicoPublicoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ServicoPublicoRequest request
    ) {
        return ResponseEntity.ok(servicoPublicoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Excluir serviço público", description = "Desativa um serviço público. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Serviço excluído/desativado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        servicoPublicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }


}