package com.weg.gestao_escolar.dto;

import java.time.LocalDate;

public record AlunoRespostaDto(
        int id,
        String nome,
        String email,
        String matricula,
        LocalDate dataNascimento
) {
}
