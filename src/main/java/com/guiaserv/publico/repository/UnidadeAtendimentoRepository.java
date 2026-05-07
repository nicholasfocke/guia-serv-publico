package com.guiaserv.publico.repository;

import com.guiaserv.publico.model.UnidadeAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UnidadeAtendimentoRepository extends JpaRepository<UnidadeAtendimento, Long> {

    boolean existsByNomeIgnoreCaseAndEnderecoIgnoreCase(String nome, String endereco);

    List<UnidadeAtendimento> findByAtivoTrue();

    List<UnidadeAtendimento> findByCidadeContainingIgnoreCaseAndAtivoTrue(String cidade);

    List<UnidadeAtendimento> findByBairroContainingIgnoreCaseAndAtivoTrue(String bairro);

    @Query("""
            SELECT u
            FROM UnidadeAtendimento u
            WHERE u.ativo = true
            AND LOWER(u.cidade) LIKE LOWER(CONCAT('%', :cidade, '%'))
            AND LOWER(u.bairro) LIKE LOWER(CONCAT('%', :bairro, '%'))
            """)
    List<UnidadeAtendimento> buscarPorCidadeEBairro(
            @Param("cidade") String cidade,
            @Param("bairro") String bairro
    );

    @Query("""
            SELECT u
            FROM UnidadeAtendimento u
            WHERE u.ativo = true
            AND (
                LOWER(u.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
                OR LOWER(u.endereco) LIKE LOWER(CONCAT('%', :termo, '%'))
                OR LOWER(u.bairro) LIKE LOWER(CONCAT('%', :termo, '%'))
                OR LOWER(u.cidade) LIKE LOWER(CONCAT('%', :termo, '%'))
                OR LOWER(u.cep) LIKE LOWER(CONCAT('%', :termo, '%'))
            )
            """)
    List<UnidadeAtendimento> buscarPorTermo(@Param("termo") String termo);
}