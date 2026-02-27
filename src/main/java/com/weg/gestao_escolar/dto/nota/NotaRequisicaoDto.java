package com.weg.gestao_escolar.dto.nota;

public record NotaRequisicaoDto(
    int id,
    int idAluno,
    int idAula,
    double valor
) { }
