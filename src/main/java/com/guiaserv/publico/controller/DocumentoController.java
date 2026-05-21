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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Documentos", description = "Gerenciamento de documentos e vínculo de documentos com serviços públicos")
@RestController
@RequiredArgsConstructor
public class DocumentoController {

    private final DocumentoService documentoService;
    private final ServicoDocumentoService servicoDocumentoService;

    @PostMapping("/api/documentos")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Cadastrar documento", description = "Cria um documento base, como CPF, RG ou comprovante de residência. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Documento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "409", description = "Documento já cadastrado")
    })
    public ResponseEntity<DocumentoResponse> cadastrar(
            @RequestBody @Valid DocumentoRequest request
    ) {
        DocumentoResponse response = documentoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/documentos")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Listar documentos", description = "Lista todos os documentos cadastrados. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Documentos retornados com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<List<DocumentoResponse>> listarTodos() {
        return ResponseEntity.ok(documentoService.listarTodos());
    }

    @GetMapping("/api/documentos/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Buscar documento por ID", description = "Retorna os dados de um documento. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Documento encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado")
    })
    public ResponseEntity<DocumentoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(documentoService.buscarPorId(id));
    }

    @PutMapping("/api/documentos/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Atualizar documento", description = "Atualiza um documento base. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Documento atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado"),
            @ApiResponse(responseCode = "409", description = "Documento já cadastrado")
    })
    public ResponseEntity<DocumentoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DocumentoRequest request
    ) {
        return ResponseEntity.ok(documentoService.atualizar(id, request));
    }

    @DeleteMapping("/api/documentos/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Excluir documento", description = "Desativa um documento base. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Documento excluído/desativado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado")
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        documentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/servicos/{servicoId}/documentos/{documentoId}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Vincular documento a serviço", description = "Vincula um documento existente a um serviço público, informando obrigatoriedade e observações. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Documento vinculado ao serviço com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Serviço ou documento não encontrado"),
            @ApiResponse(responseCode = "409", description = "Documento já vinculado a este serviço")
    })
    public ResponseEntity<ServicoDocumentoResponse> vincularDocumentoAoServico(
            @PathVariable Long servicoId,
            @PathVariable Long documentoId,
            @RequestBody @Valid ServicoDocumentoRequest request
    ) {
        ServicoDocumentoResponse response = servicoDocumentoService.vincular(servicoId, documentoId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/servicos/{servicoId}/documentos")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Listar documentos de um serviço", description = "Lista documentos necessários para um serviço público. Acesso permitido para USER ou ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Documentos do serviço retornados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    public ResponseEntity<List<ServicoDocumentoResponse>> listarDocumentosPorServico(
            @PathVariable Long servicoId
    ) {
        return ResponseEntity.ok(servicoDocumentoService.listarDocumentosPorServico(servicoId));
    }

    @PutMapping("/api/servicos/{servicoId}/documentos/{documentoId}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Atualizar vínculo serviço-documento", description = "Atualiza obrigatoriedade e observações do documento em um serviço. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vínculo atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Vínculo não encontrado")
    })
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
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Desvincular documento de serviço", description = "Desativa o vínculo entre documento e serviço público. Acesso restrito ao ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Documento desvinculado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Vínculo não encontrado")
    })
    public ResponseEntity<Void> desvincularDocumentoDoServico(
            @PathVariable Long servicoId,
            @PathVariable Long documentoId
    ) {
        servicoDocumentoService.desvincular(servicoId, documentoId);
        return ResponseEntity.noContent().build();
    }
}