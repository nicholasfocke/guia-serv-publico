package com.guiaserv.publico.service;

import com.guiaserv.publico.dto.request.CategoriaServicoRequest;
import com.guiaserv.publico.dto.response.CategoriaServicoResponse;
import com.guiaserv.publico.exception.DuplicateResourceException;
import com.guiaserv.publico.exception.ResourceNotFoundException;
import com.guiaserv.publico.model.CategoriaServico;
import com.guiaserv.publico.repository.CategoriaServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServicoService {

    private final CategoriaServicoRepository categoriaServicoRepository;

    public CategoriaServicoResponse cadastrar(CategoriaServicoRequest request) {
        if (categoriaServicoRepository.existsByNomeIgnoreCase(request.nome())) {
            throw new DuplicateResourceException("Já existe uma categoria cadastrada com esse nome");
        }

        CategoriaServico categoria = CategoriaServico.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .ativa(true)
                .build();

        CategoriaServico categoriaSalva = categoriaServicoRepository.save(categoria);

        return toResponse(categoriaSalva);
    }

    public List<CategoriaServicoResponse> listarTodas() {
        return categoriaServicoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoriaServicoResponse buscarPorId(Long id) {
        CategoriaServico categoria = buscarCategoriaOuFalhar(id);
        return toResponse(categoria);
    }

    public CategoriaServicoResponse atualizar(Long id, CategoriaServicoRequest request) {
        CategoriaServico categoria = buscarCategoriaOuFalhar(id);

        if (!categoria.getNome().equalsIgnoreCase(request.nome())
                && categoriaServicoRepository.existsByNomeIgnoreCase(request.nome())) {
            throw new DuplicateResourceException("Já existe uma categoria cadastrada com esse nome");
        }

        categoria.setNome(request.nome());
        categoria.setDescricao(request.descricao());

        CategoriaServico categoriaAtualizada = categoriaServicoRepository.save(categoria);

        return toResponse(categoriaAtualizada);
    }

    public void excluir(Long id) {
        CategoriaServico categoria = buscarCategoriaOuFalhar(id);
        categoriaServicoRepository.delete(categoria);
    }

    private CategoriaServico buscarCategoriaOuFalhar(Long id) {
        return categoriaServicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria de serviço não encontrada"));
    }

    private CategoriaServicoResponse toResponse(CategoriaServico categoria) {
        return new CategoriaServicoResponse(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao(),
                categoria.getAtiva(),
                categoria.getCriadaEm()
        );
    }
}