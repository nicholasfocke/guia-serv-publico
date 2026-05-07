package com.guiaserv.publico.repository;

import com.guiaserv.publico.model.ServicoPublico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicoPublicoRepository extends JpaRepository<ServicoPublico, Long> {

    boolean existsByNomeIgnoreCase(String nome);

    List<ServicoPublico> findByAtivoTrue();

    @Query("""
            SELECT s
            FROM ServicoPublico s
            WHERE s.ativo = true
            AND (
                LOWER(s.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
                OR LOWER(s.descricao) LIKE LOWER(CONCAT('%', :termo, '%'))
                OR LOWER(s.palavrasChave) LIKE LOWER(CONCAT('%', :termo, '%'))
            )
            """)
    List<ServicoPublico> buscarPorTermo(@Param("termo") String termo);
}