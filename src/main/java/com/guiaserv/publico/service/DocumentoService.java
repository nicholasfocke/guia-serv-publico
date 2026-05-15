package com.guiaserv.publico.service;

import com.guiaserv.publico.dto.request.DocumentoRequest;
import com.guiaserv.publico.dto.response.DocumentoResponse;
import com.guiaserv.publico.exception.DuplicateResourceException;
import com.guiaserv.publico.exception.ResourceNotFoundException;
import com.guiaserv.publico.mapper.DocumentoMapper;
import com.guiaserv.publico.model.Documento;
import com.guiaserv.publico.repository.DocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final DocumentoMapper documentoMapper;

    public DocumentoResponse cadastrar(DocumentoRequest request) {
        if (documentoRepository.existsByNomeIgnoreCase(request.nome())) {
            throw new DuplicateResourceException("Já existe um documento cadastrado com esse nome");
        }

        Documento documento = documentoMapper.toEntity(request);
        Documento documentoSalvo = documentoRepository.save(documento);

        return documentoMapper.toResponse(documentoSalvo);
    }

    public List<DocumentoResponse> listarTodos() {
        return documentoRepository.findByAtivoTrue()
                .stream()
                .map(documentoMapper::toResponse)
                .toList();
    }

    public DocumentoResponse buscarPorId(Long id) {
        Documento documento = buscarDocumentoOuFalhar(id);
        return documentoMapper.toResponse(documento);
    }

    public DocumentoResponse atualizar(Long id, DocumentoRequest request) {
        Documento documento = buscarDocumentoOuFalhar(id);

        if (!documento.getNome().equalsIgnoreCase(request.nome())
                && documentoRepository.existsByNomeIgnoreCase(request.nome())) {
            throw new DuplicateResourceException("Já existe um documento cadastrado com esse nome");
        }

        documentoMapper.updateEntityFromRequest(request, documento);
        Documento documentoAtualizado = documentoRepository.save(documento);

        return documentoMapper.toResponse(documentoAtualizado);
    }

    public void excluir(Long id) {
        Documento documento = buscarDocumentoOuFalhar(id);
        documento.setAtivo(false);
        documentoRepository.save(documento);
    }

    private Documento buscarDocumentoOuFalhar(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento não encontrado"));
    }
}