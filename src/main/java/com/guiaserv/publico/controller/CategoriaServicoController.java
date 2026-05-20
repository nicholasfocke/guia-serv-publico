package com.guiaserv.publico.controller;

import com.guiaserv.publico.dto.request.CategoriaServicoRequest;
import com.guiaserv.publico.dto.response.CategoriaServicoResponse;
import com.guiaserv.publico.service.CategoriaServicoService;
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

@Tag(name = "Categorias", description = "Gerenciamento e consulta de categorias de serviços públicos")
@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaServicoController {

    private final CategoriaServicoService categoriaServicoService;

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Cadastrar categoria", description = "Cria uma nova categoria de serviço público. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "409", description = "Categoria já cadastrada")
    })
    public ResponseEntity<CategoriaServicoResponse> cadastrar(
            @RequestBody @Valid CategoriaServicoRequest request
    ) {
        CategoriaServicoResponse response = categoriaServicoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar categorias", description = "Lista todas as categorias cadastradas. Endpoint público.")
    @ApiResponse(responseCode = "200", description = "Categorias retornadas com sucesso")
    public ResponseEntity<List<CategoriaServicoResponse>> listarTodas() {
        return ResponseEntity.ok(categoriaServicoService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID", description = "Retorna os dados de uma categoria específica. Endpoint público.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public ResponseEntity<CategoriaServicoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaServicoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Atualizar categoria", description = "Atualiza uma categoria existente. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "409", description = "Categoria já cadastrada")
    })
    public ResponseEntity<CategoriaServicoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid CategoriaServicoRequest request
    ) {
        return ResponseEntity.ok(categoriaServicoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Excluir categoria", description = "Remove uma categoria do sistema. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoria excluída com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        categoriaServicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}