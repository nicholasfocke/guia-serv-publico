package com.guiaserv.publico.repository;

import com.guiaserv.publico.model.DiaSemana;
import com.guiaserv.publico.model.HorarioFuncionamento;
import com.guiaserv.publico.model.ServicoUnidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorarioFuncionamentoRepository extends JpaRepository<HorarioFuncionamento, Long> {

    boolean existsByServicoUnidadeAndDiaSemanaAndAtivoTrue(
            ServicoUnidade servicoUnidade,
            DiaSemana diaSemana
    );

    List<HorarioFuncionamento> findByServicoUnidadeIdAndAtivoTrue(Long servicoUnidadeId);
}