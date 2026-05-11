package com.guiaserv.publico.service;

import com.guiaserv.publico.dto.request.CategoriaServicoRequest;
import com.guiaserv.publico.dto.response.CategoriaServicoResponse;
import com.guiaserv.publico.exception.DuplicateResourceException;
import com.guiaserv.publico.exception.ResourceNotFoundException;
import com.guiaserv.publico.mapper.CategoriaServicoMapper;
import com.guiaserv.publico.model.CategoriaServico;
import com.guiaserv.publico.repository.CategoriaServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServicoService {

    private final CategoriaServicoRepository categoriaServicoRepository;
    private final CategoriaServicoMapper categoriaServicoMapper;

    public CategoriaServicoResponse cadastrar(CategoriaServicoRequest request) {
        if (categoriaServicoRepository.existsByNomeIgnoreCase(request.nome())) {
            throw new DuplicateResourceException("Já existe uma categoria cadastrada com esse nome");
        }

        CategoriaServico categoria = categoriaServicoMapper.toEntity(request);
        CategoriaServico categoriaSalva = categoriaServicoRepository.save(categoria);
        return categoriaServicoMapper.toResponse(categoriaSalva);
    }

    public List<CategoriaServicoResponse> listarTodas() {
        return categoriaServicoRepository.findAll()
                .stream()
                .map(categoriaServicoMapper::toResponse)
                .toList();
    }

    public CategoriaServicoResponse buscarPorId(Long id) {
        CategoriaServico categoria = buscarCategoriaOuFalhar(id);
        return categoriaServicoMapper.toResponse(categoria);
    }

    public CategoriaServicoResponse atualizar(Long id, CategoriaServicoRequest request) {
        CategoriaServico categoria = buscarCategoriaOuFalhar(id);

        if (!categoria.getNome().equalsIgnoreCase(request.nome())
                && categoriaServicoRepository.existsByNomeIgnoreCase(request.nome())) {
            throw new DuplicateResourceException("Já existe uma categoria cadastrada com esse nome");
        }

        categoriaServicoMapper.updateEntityFromRequest(request, categoria);
        CategoriaServico categoriaAtualizada = categoriaServicoRepository.save(categoria);
        return categoriaServicoMapper.toResponse(categoriaAtualizada);
    }

    public void excluir(Long id) {
        CategoriaServico categoria = buscarCategoriaOuFalhar(id);
        categoriaServicoRepository.delete(categoria);
    }

    private CategoriaServico buscarCategoriaOuFalhar(Long id) {
        return categoriaServicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria de serviço não encontrada"));
    }

}