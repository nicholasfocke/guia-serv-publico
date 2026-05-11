package com.guiaserv.publico.service;

import com.guiaserv.publico.dto.request.ServicoPublicoRequest;
import com.guiaserv.publico.dto.response.ServicoPublicoResponse;
import com.guiaserv.publico.exception.DuplicateResourceException;
import com.guiaserv.publico.exception.ResourceNotFoundException;
import com.guiaserv.publico.mapper.ServicoPublicoMapper;
import com.guiaserv.publico.model.CategoriaServico;
import com.guiaserv.publico.model.ServicoPublico;
import com.guiaserv.publico.repository.CategoriaServicoRepository;
import com.guiaserv.publico.repository.ServicoPublicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoPublicoService {

    private final ServicoPublicoRepository servicoPublicoRepository;
    private final CategoriaServicoRepository categoriaServicoRepository;
    private final ServicoPublicoMapper servicoPublicoMapper;

    public ServicoPublicoResponse cadastrar(ServicoPublicoRequest request) {
        if (servicoPublicoRepository.existsByNomeIgnoreCase(request.nome())) {
            throw new DuplicateResourceException("Já existe um serviço público cadastrado com esse nome");
        }

        CategoriaServico categoria = buscarCategoriaOuFalhar(request.categoriaId());

        ServicoPublico servico = servicoPublicoMapper.toEntity(request, categoria);
        ServicoPublico servicoSalvo = servicoPublicoRepository.save(servico);
        return servicoPublicoMapper.toResponse(servicoSalvo);
    }

    public List<ServicoPublicoResponse> listarTodos() {
        return servicoPublicoRepository.findByAtivoTrue()
                .stream()
                .map(servicoPublicoMapper::toResponse)
                .toList();
    }

    public ServicoPublicoResponse buscarPorId(Long id) {
        ServicoPublico servico = buscarServicoOuFalhar(id);
        return servicoPublicoMapper.toResponse(servico);
    }

    public List<ServicoPublicoResponse> buscarPorTermo(String termo) {
        if (termo == null || termo.isBlank()) {
            return listarTodos();
        }

        return servicoPublicoRepository.buscarPorTermo(termo.trim())
                .stream()
                .map(servicoPublicoMapper::toResponse)
                .toList();
    }

    public ServicoPublicoResponse atualizar(Long id, ServicoPublicoRequest request) {
        ServicoPublico servico = buscarServicoOuFalhar(id);

        if (!servico.getNome().equalsIgnoreCase(request.nome())
                && servicoPublicoRepository.existsByNomeIgnoreCase(request.nome())) {
            throw new DuplicateResourceException("Já existe um serviço público cadastrado com esse nome");
        }

        CategoriaServico categoria = buscarCategoriaOuFalhar(request.categoriaId());

        servicoPublicoMapper.updateEntityFromRequest(request, categoria, servico);
        ServicoPublico servicoAtualizado = servicoPublicoRepository.save(servico);
        return servicoPublicoMapper.toResponse(servicoAtualizado);
    }

    public void excluir(Long id) {
        ServicoPublico servico = buscarServicoOuFalhar(id);
        servico.setAtivo(false);
        servicoPublicoRepository.save(servico);
    }

    private CategoriaServico buscarCategoriaOuFalhar(Long categoriaId) {
        return categoriaServicoRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria de serviço não encontrada"));
    }

    private ServicoPublico buscarServicoOuFalhar(Long id) {
        return servicoPublicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço público não encontrado"));
    }

}