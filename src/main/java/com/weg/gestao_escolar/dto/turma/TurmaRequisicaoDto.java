package com.weg.gestao_escolar.dto.turma;

import java.util.List;

public record TurmaRequisicaoDto(
    String nome,
    int cursoId,
    int professorId,
    List<Integer> listaAlunoIds
) { }

