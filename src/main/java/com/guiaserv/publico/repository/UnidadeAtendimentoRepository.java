package com.guiaserv.publico.repository;

import com.guiaserv.publico.model.UnidadeAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnidadeAtendimentoRepository extends JpaRepository<UnidadeAtendimento, Long> {

    boolean existsByNomeIgnoreCaseAndEnderecoIgnoreCase(String nome, String endereco);

    List<UnidadeAtendimento> findByAtivoTrue();

    List<UnidadeAtendimento> findByCidadeContainingIgnoreCaseAndAtivoTrue(String cidade);

    List<UnidadeAtendimento> findByBairroContainingIgnoreCaseAndAtivoTrue(String bairro);

    List<UnidadeAtendimento> findByCidadeContainingIgnoreCaseAndBairroContainingIgnoreCaseAndAtivoTrue(
            String cidade,
            String bairro
    );
}