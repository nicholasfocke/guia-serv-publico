package com.guiaserv.publico.service;

import com.guiaserv.publico.dto.request.UnidadeAtendimentoRequest;
import com.guiaserv.publico.dto.response.UnidadeAtendimentoResponse;
import com.guiaserv.publico.exception.DuplicateResourceException;
import com.guiaserv.publico.exception.ResourceNotFoundException;
import com.guiaserv.publico.mapper.UnidadeAtendimentoMapper;
import com.guiaserv.publico.model.UnidadeAtendimento;
import com.guiaserv.publico.repository.UnidadeAtendimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnidadeAtendimentoService {

    private final UnidadeAtendimentoRepository unidadeAtendimentoRepository;
    private final UnidadeAtendimentoMapper unidadeAtendimentoMapper;

    public UnidadeAtendimentoResponse cadastrar(UnidadeAtendimentoRequest request) {
        if (unidadeAtendimentoRepository.existsByNomeIgnoreCaseAndEnderecoIgnoreCase(
                request.nome(),
                request.endereco()
        )) {
            throw new DuplicateResourceException("Já existe uma unidade cadastrada com esse nome e endereço");
        }

        UnidadeAtendimento unidade = unidadeAtendimentoMapper.toEntity(request);
        UnidadeAtendimento unidadeSalva = unidadeAtendimentoRepository.save(unidade);
        return unidadeAtendimentoMapper.toResponse(unidadeSalva);
    }

    public List<UnidadeAtendimentoResponse> listarTodas(String cidade, String bairro) {
        List<UnidadeAtendimento> unidades;

        if (cidade != null && !cidade.isBlank() && bairro != null && !bairro.isBlank()) {
            unidades = unidadeAtendimentoRepository
                    .buscarPorCidadeEBairro(cidade, bairro);
        } else if (cidade != null && !cidade.isBlank()) {
            unidades = unidadeAtendimentoRepository.findByCidadeContainingIgnoreCaseAndAtivoTrue(cidade);
        } else if (bairro != null && !bairro.isBlank()) {
            unidades = unidadeAtendimentoRepository.findByBairroContainingIgnoreCaseAndAtivoTrue(bairro);
        } else {
            unidades = unidadeAtendimentoRepository.findByAtivoTrue();
        }

        return unidades.stream()
                .map(unidadeAtendimentoMapper::toResponse)
                .toList();
    }

    public UnidadeAtendimentoResponse buscarPorId(Long id) {
        UnidadeAtendimento unidade = buscarUnidadeOuFalhar(id);
        return unidadeAtendimentoMapper.toResponse(unidade);
    }

    public UnidadeAtendimentoResponse atualizar(Long id, UnidadeAtendimentoRequest request) {
        UnidadeAtendimento unidade = buscarUnidadeOuFalhar(id);

        boolean mudouNomeOuEndereco =
                !unidade.getNome().equalsIgnoreCase(request.nome())
                        || !unidade.getEndereco().equalsIgnoreCase(request.endereco());

        if (mudouNomeOuEndereco &&
                unidadeAtendimentoRepository.existsByNomeIgnoreCaseAndEnderecoIgnoreCase(
                        request.nome(),
                        request.endereco()
                )) {
            throw new DuplicateResourceException("Já existe uma unidade cadastrada com esse nome e endereço");
        }

        unidadeAtendimentoMapper.updateEntityFromRequest(request, unidade);
        UnidadeAtendimento unidadeAtualizada = unidadeAtendimentoRepository.save(unidade);
        return unidadeAtendimentoMapper.toResponse(unidadeAtualizada);
    }

    public void excluir(Long id) {
        UnidadeAtendimento unidade = buscarUnidadeOuFalhar(id);
        unidade.setAtivo(false);
        unidadeAtendimentoRepository.save(unidade);
    }

    public List<UnidadeAtendimentoResponse> buscarPorTermo(String termo) {
        if (termo == null || termo.isBlank()) {
            return listarTodas(null, null);
        }

        return unidadeAtendimentoRepository.buscarPorTermo(termo.trim())
                .stream()
                .map(unidadeAtendimentoMapper::toResponse)
                .toList();
    }

    private UnidadeAtendimento buscarUnidadeOuFalhar(Long id) {
        return unidadeAtendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidade de atendimento não encontrada"));
    }

}