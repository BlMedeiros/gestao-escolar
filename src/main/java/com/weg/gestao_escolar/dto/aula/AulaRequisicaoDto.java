package com.weg.gestao_escolar.dto.aula;

import java.time.LocalDateTime;

public record AulaRequisicaoDto(
    int turmaId,
    LocalDateTime dataHora,
    String assunto
) { }

