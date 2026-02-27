package com.weg.gestao_escolar.dto.aula;

import java.time.LocalDateTime;

public record AulaRespostaDto(
    int id,
    String nomeTurma,
    LocalDateTime dataHora,
    String assunto
) { }

