package com.guiaserv.publico.service;

import com.guiaserv.publico.dto.request.AvaliacaoRequest;
import com.guiaserv.publico.dto.response.AvaliacaoResponse;
import com.guiaserv.publico.exception.DuplicateResourceException;
import com.guiaserv.publico.exception.ResourceNotFoundException;
import com.guiaserv.publico.model.Avaliacao;
import com.guiaserv.publico.model.ServicoPublico;
import com.guiaserv.publico.model.UnidadeAtendimento;
import com.guiaserv.publico.model.Usuario;
import com.guiaserv.publico.repository.AvaliacaoRepository;
import com.guiaserv.publico.repository.ServicoPublicoRepository;
import com.guiaserv.publico.repository.UnidadeAtendimentoRepository;
import com.guiaserv.publico.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ServicoPublicoRepository servicoPublicoRepository;
    private final UnidadeAtendimentoRepository unidadeAtendimentoRepository;

    public AvaliacaoResponse avaliar(AvaliacaoRequest request, String emailUsuarioAutenticado) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuarioAutenticado)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário autenticado não encontrado"));

        ServicoPublico servico = servicoPublicoRepository.findById(request.servicoId())
                .orElseThrow(() -> new ResourceNotFoundException("Serviço público não encontrado"));

        UnidadeAtendimento unidade = unidadeAtendimentoRepository.findById(request.unidadeId())
                .orElseThrow(() -> new ResourceNotFoundException("Unidade de atendimento não encontrada"));

        if (avaliacaoRepository.existsByUsuarioAndServicoPublicoAndUnidadeAtendimento(
                usuario,
                servico,
                unidade
        )) {
            throw new DuplicateResourceException("Você já avaliou este serviço nesta unidade");
        }

        Avaliacao avaliacao = Avaliacao.builder()
                .nota(request.nota())
                .comentario(request.comentario())
                .usuario(usuario)
                .servicoPublico(servico)
                .unidadeAtendimento(unidade)
                .ativo(true)
                .build();

        Avaliacao avaliacaoSalva = avaliacaoRepository.save(avaliacao);

        return toResponse(avaliacaoSalva);
    }

    public List<AvaliacaoResponse> listarPorServico(Long servicoId) {
        if (!servicoPublicoRepository.existsById(servicoId)) {
            throw new ResourceNotFoundException("Serviço público não encontrado");
        }

        return avaliacaoRepository.findByServicoPublicoIdAndAtivoTrue(servicoId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<AvaliacaoResponse> listarPorUnidade(Long unidadeId) {
        if (!unidadeAtendimentoRepository.existsById(unidadeId)) {
            throw new ResourceNotFoundException("Unidade de atendimento não encontrada");
        }

        return avaliacaoRepository.findByUnidadeAtendimentoIdAndAtivoTrue(unidadeId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private AvaliacaoResponse toResponse(Avaliacao avaliacao) {
        return new AvaliacaoResponse(
                avaliacao.getId(),
                avaliacao.getNota(),
                avaliacao.getComentario(),
                avaliacao.getUsuario().getId(),
                avaliacao.getUsuario().getNome(),
                avaliacao.getServicoPublico().getId(),
                avaliacao.getServicoPublico().getNome(),
                avaliacao.getUnidadeAtendimento().getId(),
                avaliacao.getUnidadeAtendimento().getNome(),
                avaliacao.getAtivo(),
                avaliacao.getCriadoEm()
        );
    }
}