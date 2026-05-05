package com.guiaserv.publico.service;

import com.guiaserv.publico.dto.request.ServicoPublicoRequest;
import com.guiaserv.publico.dto.response.ServicoPublicoResponse;
import com.guiaserv.publico.exception.DuplicateResourceException;
import com.guiaserv.publico.exception.ResourceNotFoundException;
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

    public ServicoPublicoResponse cadastrar(ServicoPublicoRequest request) {
        if (servicoPublicoRepository.existsByNomeIgnoreCase(request.nome())) {
            throw new DuplicateResourceException("Já existe um serviço público cadastrado com esse nome");
        }

        CategoriaServico categoria = buscarCategoriaOuFalhar(request.categoriaId());

        ServicoPublico servico = ServicoPublico.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .palavrasChave(request.palavrasChave())
                .precisaAgendamento(request.precisaAgendamento())
                .orientacoes(request.orientacoes())
                .ativo(true)
                .categoria(categoria)
                .build();

        ServicoPublico servicoSalvo = servicoPublicoRepository.save(servico);

        return toResponse(servicoSalvo);
    }

    public List<ServicoPublicoResponse> listarTodos() {
        return servicoPublicoRepository.findByAtivoTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ServicoPublicoResponse buscarPorId(Long id) {
        ServicoPublico servico = buscarServicoOuFalhar(id);
        return toResponse(servico);
    }

    public List<ServicoPublicoResponse> buscarPorTermo(String termo) {
        return servicoPublicoRepository
                .findByNomeContainingIgnoreCaseOrDescricaoContainingIgnoreCaseOrPalavrasChaveContainingIgnoreCase(
                        termo,
                        termo,
                        termo
                )
                .stream()
                .filter(ServicoPublico::getAtivo)
                .map(this::toResponse)
                .toList();
    }

    public ServicoPublicoResponse atualizar(Long id, ServicoPublicoRequest request) {
        ServicoPublico servico = buscarServicoOuFalhar(id);

        if (!servico.getNome().equalsIgnoreCase(request.nome())
                && servicoPublicoRepository.existsByNomeIgnoreCase(request.nome())) {
            throw new DuplicateResourceException("Já existe um serviço público cadastrado com esse nome");
        }

        CategoriaServico categoria = buscarCategoriaOuFalhar(request.categoriaId());

        servico.setNome(request.nome());
        servico.setDescricao(request.descricao());
        servico.setPalavrasChave(request.palavrasChave());
        servico.setPrecisaAgendamento(request.precisaAgendamento());
        servico.setOrientacoes(request.orientacoes());
        servico.setCategoria(categoria);

        ServicoPublico servicoAtualizado = servicoPublicoRepository.save(servico);

        return toResponse(servicoAtualizado);
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

    private ServicoPublicoResponse toResponse(ServicoPublico servico) {
        return new ServicoPublicoResponse(
                servico.getId(),
                servico.getNome(),
                servico.getDescricao(),
                servico.getPalavrasChave(),
                servico.getPrecisaAgendamento(),
                servico.getOrientacoes(),
                servico.getAtivo(),
                servico.getCriadoEm(),
                servico.getCategoria().getId(),
                servico.getCategoria().getNome()
        );
    }
}