package com.guiaserv.publico.service;

import com.guiaserv.publico.dto.request.ServicoUnidadeRequest;
import com.guiaserv.publico.dto.response.ServicoUnidadeResponse;
import com.guiaserv.publico.exception.DuplicateResourceException;
import com.guiaserv.publico.exception.ResourceNotFoundException;
import com.guiaserv.publico.mapper.ServicoUnidadeMapper;
import com.guiaserv.publico.model.ServicoPublico;
import com.guiaserv.publico.model.ServicoUnidade;
import com.guiaserv.publico.model.UnidadeAtendimento;
import com.guiaserv.publico.repository.ServicoPublicoRepository;
import com.guiaserv.publico.repository.ServicoUnidadeRepository;
import com.guiaserv.publico.repository.UnidadeAtendimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoUnidadeService {

    private final ServicoUnidadeRepository servicoUnidadeRepository;
    private final ServicoPublicoRepository servicoPublicoRepository;
    private final UnidadeAtendimentoRepository unidadeAtendimentoRepository;
    private final ServicoUnidadeMapper servicoUnidadeMapper;

    public ServicoUnidadeResponse vincular(
            Long servicoId,
            Long unidadeId,
            ServicoUnidadeRequest request
    ) {
        ServicoPublico servico = buscarServicoOuFalhar(servicoId);
        UnidadeAtendimento unidade = buscarUnidadeOuFalhar(unidadeId);

        if (servicoUnidadeRepository.existsByServicoPublicoAndUnidadeAtendimento(servico, unidade)) {
            throw new DuplicateResourceException("Este serviço já está vinculado a esta unidade");
        }

        ServicoUnidadeRequest requestFinal = request != null
                ? request
                : new ServicoUnidadeRequest(null);

        ServicoUnidade vinculo = servicoUnidadeMapper.toEntity(requestFinal, servico, unidade);
        ServicoUnidade vinculoSalvo = servicoUnidadeRepository.save(vinculo);

        return servicoUnidadeMapper.toResponse(vinculoSalvo);
    }

    public List<ServicoUnidadeResponse> listarUnidadesPorServico(Long servicoId) {
        buscarServicoOuFalhar(servicoId);

        return servicoUnidadeRepository.findByServicoPublicoIdAndAtivoTrue(servicoId)
                .stream()
                .map(servicoUnidadeMapper::toResponse)
                .toList();
    }

    public List<ServicoUnidadeResponse> listarServicosPorUnidade(Long unidadeId) {
        buscarUnidadeOuFalhar(unidadeId);

        return servicoUnidadeRepository.findByUnidadeAtendimentoIdAndAtivoTrue(unidadeId)
                .stream()
                .map(servicoUnidadeMapper::toResponse)
                .toList();
    }

    public void desvincular(Long servicoId, Long unidadeId) {
        ServicoPublico servico = buscarServicoOuFalhar(servicoId);
        UnidadeAtendimento unidade = buscarUnidadeOuFalhar(unidadeId);

        ServicoUnidade vinculo = servicoUnidadeRepository
                .findByServicoPublicoAndUnidadeAtendimento(servico, unidade)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo entre serviço e unidade não encontrado"));

        vinculo.setAtivo(false);
        servicoUnidadeRepository.save(vinculo);
    }

    private ServicoPublico buscarServicoOuFalhar(Long servicoId) {
        return servicoPublicoRepository.findById(servicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço público não encontrado"));
    }

    private UnidadeAtendimento buscarUnidadeOuFalhar(Long unidadeId) {
        return unidadeAtendimentoRepository.findById(unidadeId)
                .orElseThrow(() -> new ResourceNotFoundException("Unidade de atendimento não encontrada"));
    }

}