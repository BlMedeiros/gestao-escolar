package com.weg.gestao_escolar.dto.aula;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record AulaRequisicaoDto(
    @NotNull(message = "O id não pode ser nulo")
    @Positive(message = "O id da turma deve ser positivo")
    int turmaId,

    @NotNull(message = "A data e hora não pode ser nula")
    @FutureOrPresent(message = "A data não pode ser no passado")
    LocalDateTime dataHora,

    @NotBlank(message = "O assunto não pode ser nulo")
    @Size(min = 5, max = 200, message = "O assunto deve ser entre 5 a 200 caracteres")
    String assunto
) { }

