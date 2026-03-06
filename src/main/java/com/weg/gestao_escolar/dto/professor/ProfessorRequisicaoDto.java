package com.weg.gestao_escolar.dto.professor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfessorRequisicaoDto(

    @NotBlank(message = "O nome do professor não pode ser nulo")
    @Size(min = 5, max = 100, message = "O nome do professor deve ser entre 5 a 100 caracteres")
    String nome,

    @Email(message = "O email deve ser valido")
    @NotBlank(message = "O email não pode ser nulo")
    String email,

    @NotBlank(message = "O disciplina não pode ser nulo")
    String disciplina
) { }

