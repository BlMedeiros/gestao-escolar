package com.weg.gestao_escolar.dto.curso;

import java.util.List;

public record CursoRequisicaoDto(
    String nome,
    String codigo,
    List<Integer> listaProfessorIds
) { }

