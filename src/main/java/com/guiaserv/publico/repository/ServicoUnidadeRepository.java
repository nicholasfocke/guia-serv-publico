package com.guiaserv.publico.repository;

import com.guiaserv.publico.model.ServicoPublico;
import com.guiaserv.publico.model.ServicoUnidade;
import com.guiaserv.publico.model.UnidadeAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServicoUnidadeRepository extends JpaRepository<ServicoUnidade, Long> {

    boolean existsByServicoPublicoAndUnidadeAtendimento(
            ServicoPublico servicoPublico,
            UnidadeAtendimento unidadeAtendimento
    );

    Optional<ServicoUnidade> findByServicoPublicoAndUnidadeAtendimento(
            ServicoPublico servicoPublico,
            UnidadeAtendimento unidadeAtendimento
    );

    List<ServicoUnidade> findByServicoPublicoIdAndAtivoTrue(Long servicoId);

    List<ServicoUnidade> findByUnidadeAtendimentoIdAndAtivoTrue(Long unidadeId);
}