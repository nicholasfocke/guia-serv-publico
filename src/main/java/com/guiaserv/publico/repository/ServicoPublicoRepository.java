package com.guiaserv.publico.repository;

import com.guiaserv.publico.model.ServicoPublico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicoPublicoRepository extends JpaRepository<ServicoPublico, Long> {

    boolean existsByNomeIgnoreCase(String nome);

    List<ServicoPublico> findByAtivoTrue();

    List<ServicoPublico> findByNomeContainingIgnoreCaseOrDescricaoContainingIgnoreCaseOrPalavrasChaveContainingIgnoreCase(
            String nome,
            String descricao,
            String palavrasChave
    );
}