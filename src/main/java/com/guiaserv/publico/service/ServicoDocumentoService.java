package com.guiaserv.publico.service;

import com.guiaserv.publico.dto.request.ServicoDocumentoRequest;
import com.guiaserv.publico.dto.response.ServicoDocumentoResponse;
import com.guiaserv.publico.exception.DuplicateResourceException;
import com.guiaserv.publico.exception.ResourceNotFoundException;
import com.guiaserv.publico.mapper.ServicoDocumentoMapper;
import com.guiaserv.publico.model.Documento;
import com.guiaserv.publico.model.ServicoDocumento;
import com.guiaserv.publico.model.ServicoPublico;
import com.guiaserv.publico.repository.DocumentoRepository;
import com.guiaserv.publico.repository.ServicoDocumentoRepository;
import com.guiaserv.publico.repository.ServicoPublicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoDocumentoService {

    private final ServicoDocumentoRepository servicoDocumentoRepository;
    private final ServicoPublicoRepository servicoPublicoRepository;
    private final DocumentoRepository documentoRepository;
    private final ServicoDocumentoMapper servicoDocumentoMapper;

    public ServicoDocumentoResponse vincular(
            Long servicoId,
            Long documentoId,
            ServicoDocumentoRequest request
    ) {
        ServicoPublico servico = buscarServicoOuFalhar(servicoId);
        Documento documento = buscarDocumentoOuFalhar(documentoId);

        if (servicoDocumentoRepository.existsByServicoPublicoAndDocumento(servico, documento)) {
            throw new DuplicateResourceException("Este documento já está vinculado a este serviço");
        }

        ServicoDocumento vinculo = servicoDocumentoMapper.toEntity(request, servico, documento);
        ServicoDocumento vinculoSalvo = servicoDocumentoRepository.save(vinculo);

        return servicoDocumentoMapper.toResponse(vinculoSalvo);
    }

    public List<ServicoDocumentoResponse> listarDocumentosPorServico(Long servicoId) {
        buscarServicoOuFalhar(servicoId);

        return servicoDocumentoRepository.findByServicoPublicoIdAndAtivoTrue(servicoId)
                .stream()
                .map(servicoDocumentoMapper::toResponse)
                .toList();
    }

    public ServicoDocumentoResponse atualizarVinculo(
            Long servicoId,
            Long documentoId,
            ServicoDocumentoRequest request
    ) {
        ServicoPublico servico = buscarServicoOuFalhar(servicoId);
        Documento documento = buscarDocumentoOuFalhar(documentoId);

        ServicoDocumento vinculo = servicoDocumentoRepository
                .findByServicoPublicoAndDocumento(servico, documento)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo entre serviço e documento não encontrado"));

        servicoDocumentoMapper.updateEntityFromRequest(request, vinculo);
        ServicoDocumento vinculoAtualizado = servicoDocumentoRepository.save(vinculo);

        return servicoDocumentoMapper.toResponse(vinculoAtualizado);
    }

    public void desvincular(Long servicoId, Long documentoId) {
        ServicoPublico servico = buscarServicoOuFalhar(servicoId);
        Documento documento = buscarDocumentoOuFalhar(documentoId);

        ServicoDocumento vinculo = servicoDocumentoRepository
                .findByServicoPublicoAndDocumento(servico, documento)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo entre serviço e documento não encontrado"));

        vinculo.setAtivo(false);
        servicoDocumentoRepository.save(vinculo);
    }

    private ServicoPublico buscarServicoOuFalhar(Long servicoId) {
        return servicoPublicoRepository.findById(servicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço público não encontrado"));
    }

    private Documento buscarDocumentoOuFalhar(Long documentoId) {
        return documentoRepository.findById(documentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Documento não encontrado"));
    }
}