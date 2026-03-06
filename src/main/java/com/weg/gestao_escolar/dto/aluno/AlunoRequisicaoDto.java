package com.weg.gestao_escolar.dto.aluno;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AlunoRequisicaoDto(
    @NotBlank(message = "O nome é obrigatrio")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    String nome,

    @Email(message = "Email Inválido")
    @NotBlank(message = "O Email é Obrigatorio")
    String email,

    @NotBlank(message = "A Matricula é Obrigatoria")
    String matricula,

    @NotNull(message = "A Data de Nascimento é Obrigatoria")
    @Past(message = "A data de nascimento não pode ser no presente e nem no futuro")
    LocalDate dataNascimento
) { }
