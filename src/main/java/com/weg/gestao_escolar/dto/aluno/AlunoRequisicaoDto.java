package com.weg.gestao_escolar.dto.aluno;

import java.time.LocalDate;

public record AlunoRequisicaoDto(
    String nome,
    String email,
    String matricula,
    LocalDate dataNascimento
) { }
