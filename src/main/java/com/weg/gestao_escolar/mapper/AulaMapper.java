package com.weg.gestao_escolar.mapper;

import com.weg.gestao_escolar.dto.aula.AulaRequisicaoDto;
import com.weg.gestao_escolar.dto.aula.AulaRespostaDto;
import com.weg.gestao_escolar.model.Aula;
import com.weg.gestao_escolar.repository.TurmaRepository;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class AulaMapper {

    private final TurmaRepository turmaRepository;

    public AulaMapper(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    public Aula paraEntidade(AulaRequisicaoDto aulaRequisicaoDto) {
        return new Aula(
                aulaRequisicaoDto.turmaId(),
                aulaRequisicaoDto.dataHora(),
                aulaRequisicaoDto.assunto()
        );
    }

    public Aula paraEntidade(AulaRequisicaoDto aulaRequisicaoDto, int id) {
        return new Aula(
                id,
                aulaRequisicaoDto.turmaId(),
                aulaRequisicaoDto.dataHora(),
                aulaRequisicaoDto.assunto()
        );
    }

    public AulaRespostaDto paraResposta(Aula aula) throws SQLException {
        String nomeTurma = turmaRepository.listarTurmaPorId(aula.getTurmaId()).getNome();

        return new AulaRespostaDto(
                aula.getId(),
                nomeTurma,
                aula.getDataHora(),
                aula.getAssunto()
        );
    }
}

