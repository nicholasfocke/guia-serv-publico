package com.guiaserv.publico.service;

import com.guiaserv.publico.dto.request.DocumentoRequest;
import com.guiaserv.publico.dto.response.DocumentoResponse;
import com.guiaserv.publico.exception.DuplicateResourceException;
import com.guiaserv.publico.exception.ResourceNotFoundException;
import com.guiaserv.publico.mapper.DocumentoMapper;
import com.guiaserv.publico.model.Documento;
import com.guiaserv.publico.model.ServicoPublico;
import com.guiaserv.publico.repository.DocumentoRepository;
import com.guiaserv.publico.repository.ServicoPublicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final ServicoPublicoRepository servicoPublicoRepository;
    private final DocumentoMapper documentoMapper;

    public DocumentoResponse cadastrar(DocumentoRequest request) {
        ServicoPublico servico = buscarServicoOuFalhar(request.servicoId());

        if (documentoRepository.existsByNomeIgnoreCaseAndServicoPublico(request.nome(), servico)) {
            throw new DuplicateResourceException("Já existe um documento com esse nome vinculado ao serviço informado");
        }

        Documento documento = documentoMapper.toEntity(request, servico);
        Documento documentoSalvo = documentoRepository.save(documento);
        return documentoMapper.toResponse(documentoSalvo);
    }

    public List<DocumentoResponse> listarPorServico(Long servicoId) {
        buscarServicoOuFalhar(servicoId);

        return documentoRepository.findByServicoPublicoIdAndAtivoTrue(servicoId)
                .stream()
                .map(documentoMapper::toResponse)
                .toList();
    }

    public DocumentoResponse atualizar(Long id, DocumentoRequest request) {
        Documento documento = buscarDocumentoOuFalhar(id);
        ServicoPublico servico = buscarServicoOuFalhar(request.servicoId());

        boolean mudouNomeOuServico =
                !documento.getNome().equalsIgnoreCase(request.nome())
                        || !documento.getServicoPublico().getId().equals(request.servicoId());

        if (mudouNomeOuServico &&
                documentoRepository.existsByNomeIgnoreCaseAndServicoPublico(request.nome(), servico)) {
            throw new DuplicateResourceException("Já existe um documento com esse nome vinculado ao serviço informado");
        }

        documentoMapper.updateEntityFromRequest(request, servico, documento);
        Documento documentoAtualizado = documentoRepository.save(documento);
        return documentoMapper.toResponse(documentoAtualizado);
    }

    public void excluir(Long id) {
        Documento documento = buscarDocumentoOuFalhar(id);
        documento.setAtivo(false);
        documentoRepository.save(documento);
    }

    private ServicoPublico buscarServicoOuFalhar(Long servicoId) {
        return servicoPublicoRepository.findById(servicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço público não encontrado"));
    }

    private Documento buscarDocumentoOuFalhar(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento não encontrado"));
    }

}