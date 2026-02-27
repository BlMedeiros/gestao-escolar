package com.weg.gestao_escolar.dto.nota;

public record NotaRespostaDto(
        int id,
        String alunoNome,
        String aulaAssunto,
        double valor
) { }
