package com.weg.gestao_escolar.dto.turma;

import java.util.List;

public record TurmaDto(
    int id,
    String nome,
    String nomeCurso,
    String nomeProfessor,
    List<String> listaAlunos
) { }


