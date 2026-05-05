package com.guiaserv.publico.repository;

import com.guiaserv.publico.model.CategoriaServico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaServicoRepository extends JpaRepository<CategoriaServico, Long> {
    boolean existsByNomeIgnoreCase(String nome);
}
