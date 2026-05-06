package com.guiaserv.publico.repository;

import com.guiaserv.publico.model.Documento;
import com.guiaserv.publico.model.ServicoPublico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    boolean existsByNomeIgnoreCaseAndServicoPublico(String nome, ServicoPublico servicoPublico);

    List<Documento> findByServicoPublicoIdAndAtivoTrue(Long servicoId);
}