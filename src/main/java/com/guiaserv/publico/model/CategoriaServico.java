package com.guiaserv.publico.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "categorias_servico",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_categoria_servico_nome", columnNames = "nome")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Column(name = "ativa", nullable = false)
    private Boolean ativa;

    @Column(name = "criada_em", nullable = false, updatable = false)
    private LocalDateTime criadaEm;

    @PrePersist
    public void prePersist() {
        this.criadaEm = LocalDateTime.now();

        if (this.ativa == null) {
            this.ativa = true;
        }
    }
}
