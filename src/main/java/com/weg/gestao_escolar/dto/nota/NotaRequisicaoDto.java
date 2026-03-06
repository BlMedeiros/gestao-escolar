package com.weg.gestao_escolar.dto.nota;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NotaRequisicaoDto(
    @NotNull(message = "O id não pode ser nulo")
    int id,

    @NotNull(message = "O id do aluno não pode ser nulo")
    int idAluno,

    @NotNull(message = "O id da aula não pode ser nulo")
    int idAula,

    @NotNull(message = "O valor não pode ser nulo")
    @Positive(message = "O valor da nota não pode ser negativa")
    double valor
) { }
