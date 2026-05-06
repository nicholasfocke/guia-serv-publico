package com.guiaserv.publico.service;

import com.guiaserv.publico.dto.request.ServicoUnidadeRequest;
import com.guiaserv.publico.dto.response.ServicoUnidadeResponse;
import com.guiaserv.publico.exception.DuplicateResourceException;
import com.guiaserv.publico.exception.ResourceNotFoundException;
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

        ServicoUnidade vinculo = ServicoUnidade.builder()
                .servicoPublico(servico)
                .unidadeAtendimento(unidade)
                .observacoes(request != null ? request.observacoes() : null)
                .ativo(true)
                .build();

        ServicoUnidade vinculoSalvo = servicoUnidadeRepository.save(vinculo);

        return toResponse(vinculoSalvo);
    }

    public List<ServicoUnidadeResponse> listarUnidadesPorServico(Long servicoId) {
        buscarServicoOuFalhar(servicoId);

        return servicoUnidadeRepository.findByServicoPublicoIdAndAtivoTrue(servicoId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ServicoUnidadeResponse> listarServicosPorUnidade(Long unidadeId) {
        buscarUnidadeOuFalhar(unidadeId);

        return servicoUnidadeRepository.findByUnidadeAtendimentoIdAndAtivoTrue(unidadeId)
                .stream()
                .map(this::toResponse)
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

    private ServicoUnidadeResponse toResponse(ServicoUnidade vinculo) {
        UnidadeAtendimento unidade = vinculo.getUnidadeAtendimento();

        return new ServicoUnidadeResponse(
                vinculo.getId(),
                vinculo.getServicoPublico().getId(),
                vinculo.getServicoPublico().getNome(),
                unidade.getId(),
                unidade.getNome(),
                unidade.getEndereco(),
                unidade.getBairro(),
                unidade.getCidade(),
                unidade.getEstado(),
                unidade.getCep(),
                unidade.getTelefone(),
                vinculo.getObservacoes(),
                vinculo.getAtivo(),
                vinculo.getCriadoEm()
        );
    }
}