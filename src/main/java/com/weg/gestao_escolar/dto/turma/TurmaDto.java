package com.weg.gestao_escolar.dto.turma;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record TurmaDto(
    @NotNull(message = "O id da turma não pode ser nulo")
    int id,

    @NotBlank(message = "O nome da turma não pode ser nulo")
    @Size(min = 5, max = 20, message = "O nome da turma deve ser entre 5 a 20 caracteres")
    String nome,

    @NotBlank(message = "O nome da curso não pode ser nulo")
    @Size(min = 5, max = 50, message = "O nome da curso deve ser entre 5 a 50 caracteres")
    String nomeCurso,

    @NotBlank(message = "O nome da professor não pode ser nulo")
    @Size(min = 5, max = 100, message = "O nome do professor deve ser entre 5 a 100 caracteres")
    String nomeProfessor,

    @NotNull(message = "A lista de alunos não pode ser nula")
    List<String> listaAlunos
) { }


