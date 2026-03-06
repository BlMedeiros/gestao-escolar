package com.weg.gestao_escolar.dto.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CursoRequisicaoDto(
    @NotBlank(message = "O nome não pode ser nulo")
    @Size(min = 5, max = 200, message = "A mensagem deve ser entre 5 a 200 caracteres")
    String nome,

    @NotBlank(message = "o codigo não pode ser nulo")
    @Size(min = 2, max = 20, message = "o código deve ser entre 2 a 20 caracteres")
    String codigo,

    @NotNull(message = "A lista de professores não pode ser nula")
    List<Integer> listaProfessorIds
) { }

