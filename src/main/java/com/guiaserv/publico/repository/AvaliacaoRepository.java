package com.guiaserv.publico.repository;

import com.guiaserv.publico.model.Avaliacao;
import com.guiaserv.publico.model.ServicoPublico;
import com.guiaserv.publico.model.UnidadeAtendimento;
import com.guiaserv.publico.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    boolean existsByUsuarioAndServicoPublicoAndUnidadeAtendimento(
            Usuario usuario,
            ServicoPublico servicoPublico,
            UnidadeAtendimento unidadeAtendimento
    );

    List<Avaliacao> findByServicoPublicoIdAndAtivoTrue(Long servicoId);

    List<Avaliacao> findByUnidadeAtendimentoIdAndAtivoTrue(Long unidadeId);
}