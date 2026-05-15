package com.guiaserv.publico.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "servicos_documentos",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_servico_documento",
                        columnNames = {"servico_id", "documento_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "servico_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_servico_documento_servico")
    )
    private ServicoPublico servicoPublico;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "documento_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_servico_documento_documento")
    )
    private Documento documento;

    @Column(name = "obrigatorio", nullable = false)
    private Boolean obrigatorio;

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

        if (this.obrigatorio == null) {
            this.obrigatorio = true;
        }
    }
}