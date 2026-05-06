package com.guiaserv.publico.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "servicos_unidades",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_servico_unidade",
                        columnNames = {"servico_id", "unidade_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicoUnidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "servico_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_servico_unidade_servico")
    )
    private ServicoPublico servicoPublico;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "unidade_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_servico_unidade_unidade")
    )
    private UnidadeAtendimento unidadeAtendimento;

    @Column(name = "observacoes", length = 500)
    private String observacoes;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();

        if (this.ativo == null) {
            this.ativo = true;
        }
    }
}