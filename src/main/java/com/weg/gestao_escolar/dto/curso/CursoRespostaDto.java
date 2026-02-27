package com.weg.gestao_escolar.dto.curso;

import java.util.List;

public record CursoRespostaDto(
    int id,
    String nome,
    String codigo,
    List<String> listaProfessores
) { }

