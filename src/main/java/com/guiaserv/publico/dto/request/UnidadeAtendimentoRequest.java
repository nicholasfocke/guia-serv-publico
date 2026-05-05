package com.guiaserv.publico.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UnidadeAtendimentoRequest(

        @NotBlank(message = "O nome da unidade é obrigatório")
        @Size(min = 3, max = 150, message = "O nome deve ter entre 3 e 150 caracteres")
        String nome,

        @NotBlank(message = "O endereço é obrigatório")
        @Size(min = 5, max = 255, message = "O endereço deve ter entre 5 e 255 caracteres")
        String endereco,

        @NotBlank(message = "O bairro é obrigatório")
        @Size(min = 2, max = 100, message = "O bairro deve ter entre 2 e 100 caracteres")
        String bairro,

        @NotBlank(message = "A cidade é obrigatória")
        @Size(min = 2, max = 100, message = "A cidade deve ter entre 2 e 100 caracteres")
        String cidade,

        @NotBlank(message = "O estado é obrigatório")
        @Pattern(regexp = "^[A-Z]{2}$", message = "O estado deve estar no formato UF, exemplo: AL")
        String estado,

        @NotBlank(message = "O CEP é obrigatório")
        @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "O CEP deve estar no formato 00000-000 ou 00000000")
        String cep,

        @NotBlank(message = "O telefone é obrigatório")
        @Size(min = 8, max = 20, message = "O telefone deve ter entre 8 e 20 caracteres")
        String telefone,

        Double latitude,

        Double longitude
) {
}