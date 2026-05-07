package com.guiaserv.publico.service;

import com.guiaserv.publico.dto.request.HorarioFuncionamentoRequest;
import com.guiaserv.publico.dto.response.HorarioFuncionamentoResponse;
import com.guiaserv.publico.exception.DuplicateResourceException;
import com.guiaserv.publico.exception.ResourceNotFoundException;
import com.guiaserv.publico.model.HorarioFuncionamento;
import com.guiaserv.publico.model.ServicoUnidade;
import com.guiaserv.publico.repository.HorarioFuncionamentoRepository;
import com.guiaserv.publico.repository.ServicoUnidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HorarioFuncionamentoService {

    private final HorarioFuncionamentoRepository horarioFuncionamentoRepository;
    private final ServicoUnidadeRepository servicoUnidadeRepository;

    public HorarioFuncionamentoResponse cadastrar(HorarioFuncionamentoRequest request) {
        validarHorario(request);

        ServicoUnidade servicoUnidade = buscarServicoUnidadeOuFalhar(request.servicoUnidadeId());

        if (horarioFuncionamentoRepository.existsByServicoUnidadeAndDiaSemanaAndAtivoTrue(
                servicoUnidade,
                request.diaSemana()
        )) {
            throw new DuplicateResourceException("Já existe horário ativo cadastrado para este serviço nesta unidade nesse dia da semana");
        }

        HorarioFuncionamento horario = HorarioFuncionamento.builder()
                .diaSemana(request.diaSemana())
                .horaAbertura(request.horaAbertura())
                .horaFechamento(request.horaFechamento())
                .servicoUnidade(servicoUnidade)
                .ativo(true)
                .build();

        HorarioFuncionamento horarioSalvo = horarioFuncionamentoRepository.save(horario);

        return toResponse(horarioSalvo);
    }

    public List<HorarioFuncionamentoResponse> listarPorServicoUnidade(Long servicoUnidadeId) {
        buscarServicoUnidadeOuFalhar(servicoUnidadeId);

        return horarioFuncionamentoRepository.findByServicoUnidadeIdAndAtivoTrue(servicoUnidadeId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public HorarioFuncionamentoResponse atualizar(Long id, HorarioFuncionamentoRequest request) {
        validarHorario(request);

        HorarioFuncionamento horario = buscarHorarioOuFalhar(id);
        ServicoUnidade servicoUnidade = buscarServicoUnidadeOuFalhar(request.servicoUnidadeId());

        boolean mudouVinculoOuDia =
                !horario.getServicoUnidade().getId().equals(request.servicoUnidadeId())
                        || !horario.getDiaSemana().equals(request.diaSemana());

        if (mudouVinculoOuDia &&
                horarioFuncionamentoRepository.existsByServicoUnidadeAndDiaSemanaAndAtivoTrue(
                        servicoUnidade,
                        request.diaSemana()
                )) {
            throw new DuplicateResourceException("Já existe horário ativo cadastrado para este serviço nesta unidade nesse dia da semana");
        }

        horario.setDiaSemana(request.diaSemana());
        horario.setHoraAbertura(request.horaAbertura());
        horario.setHoraFechamento(request.horaFechamento());
        horario.setServicoUnidade(servicoUnidade);

        HorarioFuncionamento horarioAtualizado = horarioFuncionamentoRepository.save(horario);

        return toResponse(horarioAtualizado);
    }

    public void excluir(Long id) {
        HorarioFuncionamento horario = buscarHorarioOuFalhar(id);
        horario.setAtivo(false);
        horarioFuncionamentoRepository.save(horario);
    }

    private void validarHorario(HorarioFuncionamentoRequest request) {
        if (request.horaAbertura() != null
                && request.horaFechamento() != null
                && !request.horaAbertura().isBefore(request.horaFechamento())) {
            throw new IllegalArgumentException("A hora de abertura deve ser anterior à hora de fechamento");
        }
    }

    private ServicoUnidade buscarServicoUnidadeOuFalhar(Long servicoUnidadeId) {
        return servicoUnidadeRepository.findById(servicoUnidadeId)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo entre serviço e unidade não encontrado"));
    }

    private HorarioFuncionamento buscarHorarioOuFalhar(Long id) {
        return horarioFuncionamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Horário de funcionamento não encontrado"));
    }

    private HorarioFuncionamentoResponse toResponse(HorarioFuncionamento horario) {
        ServicoUnidade vinculo = horario.getServicoUnidade();

        return new HorarioFuncionamentoResponse(
                horario.getId(),
                horario.getDiaSemana(),
                horario.getHoraAbertura(),
                horario.getHoraFechamento(),
                horario.getAtivo(),
                horario.getCriadoEm(),
                vinculo.getId(),
                vinculo.getServicoPublico().getId(),
                vinculo.getServicoPublico().getNome(),
                vinculo.getUnidadeAtendimento().getId(),
                vinculo.getUnidadeAtendimento().getNome()
        );
    }
}