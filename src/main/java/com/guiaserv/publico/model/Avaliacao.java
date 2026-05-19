package com.guiaserv.publico.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "avaliacoes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_avaliacao_usuario_servico_unidade",
                        columnNames = {"usuario_id", "servico_id", "unidade_id"}
                )
        },
        indexes = {
                @Index(name = "idx_avaliacao_servico", columnList = "servico_id"),
                @Index(name = "idx_avaliacao_unidade", columnList = "unidade_id"),
                @Index(name = "idx_avaliacao_usuario", columnList = "usuario_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nota", nullable = false)
    private Integer nota;

    @Column(name = "comentario", length = 500)
    private String comentario;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "usuario_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_avaliacao_usuario")
    )
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "servico_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_avaliacao_servico")
    )
    private ServicoPublico servicoPublico;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "unidade_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_avaliacao_unidade")
    )
    private UnidadeAtendimento unidadeAtendimento;

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();

        if (this.ativo == null) {
            this.ativo = true;
        }
    }
}