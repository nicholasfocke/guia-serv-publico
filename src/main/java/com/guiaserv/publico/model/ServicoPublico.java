package com.guiaserv.publico.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "servicos_publicos",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_servico_publico_nome", columnNames = "nome")
        },
        indexes = {
                @Index(name = "idx_servico_nome", columnList = "nome"),
                @Index(name = "idx_servico_palavras_chave", columnList = "palavras_chave"),
                @Index(name = "idx_servico_categoria", columnList = "categoria_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicoPublico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;

    @Column(name = "palavras_chave", nullable = false, length = 500)
    private String palavrasChave;

    @Column(name = "precisa_agendamento", nullable = false)
    private Boolean precisaAgendamento;

    @Column(name = "orientacoes", nullable = false, length = 1000)
    private String orientacoes;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "categoria_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_servico_categoria")
    )
    private CategoriaServico categoria;

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();

        if (this.ativo == null) {
            this.ativo = true;
        }

        if (this.precisaAgendamento == null) {
            this.precisaAgendamento = false;
        }
    }
}
