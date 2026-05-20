package com.guiaserv.publico.service;

import com.guiaserv.publico.dto.request.AvaliacaoRequest;
import com.guiaserv.publico.dto.response.AvaliacaoResponse;
import com.guiaserv.publico.exception.DuplicateResourceException;
import com.guiaserv.publico.exception.ResourceNotFoundException;
import com.guiaserv.publico.mapper.AvaliacaoMapper;
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
    private final AvaliacaoMapper avaliacaoMapper;

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

        Avaliacao avaliacao = avaliacaoMapper.toEntity(request, usuario, servico, unidade);
        Avaliacao avaliacaoSalva = avaliacaoRepository.save(avaliacao);

        return avaliacaoMapper.toResponse(avaliacaoSalva);
    }

    public List<AvaliacaoResponse> listarPorServico(Long servicoId) {
        if (!servicoPublicoRepository.existsById(servicoId)) {
            throw new ResourceNotFoundException("Serviço público não encontrado");
        }

        return avaliacaoRepository.findByServicoPublicoIdAndAtivoTrue(servicoId)
                .stream()
                .map(avaliacaoMapper::toResponse)
                .toList();
    }

    public List<AvaliacaoResponse> listarPorUnidade(Long unidadeId) {
        if (!unidadeAtendimentoRepository.existsById(unidadeId)) {
            throw new ResourceNotFoundException("Unidade de atendimento não encontrada");
        }

        return avaliacaoRepository.findByUnidadeAtendimentoIdAndAtivoTrue(unidadeId)
                .stream()
                .map(avaliacaoMapper::toResponse)
                .toList();
    }

}