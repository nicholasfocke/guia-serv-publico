package com.guiaserv.publico.repository;

import com.guiaserv.publico.model.Documento;
import com.guiaserv.publico.model.ServicoDocumento;
import com.guiaserv.publico.model.ServicoPublico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServicoDocumentoRepository extends JpaRepository<ServicoDocumento, Long> {

    boolean existsByServicoPublicoAndDocumento(
            ServicoPublico servicoPublico,
            Documento documento
    );

    Optional<ServicoDocumento> findByServicoPublicoAndDocumento(
            ServicoPublico servicoPublico,
            Documento documento
    );

    List<ServicoDocumento> findByServicoPublicoIdAndAtivoTrue(Long servicoId);
}