package com.weg.gestao_escolar.mapper;

import com.weg.gestao_escolar.dto.nota.NotaRequisicaoDto;
import com.weg.gestao_escolar.dto.nota.NotaRespostaDto;
import com.weg.gestao_escolar.model.Nota;
import com.weg.gestao_escolar.repository.AlunoRepository;
import com.weg.gestao_escolar.repository.AulaRepository;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class NotaMapper {

    private final AlunoRepository alunoRepository;
    private final AulaRepository aulaRepository;

    public NotaMapper(AlunoRepository alunoRepository, AulaRepository aulaRepository) {
        this.alunoRepository = alunoRepository;
        this.aulaRepository = aulaRepository;
    }

    public Nota paraEntidade(NotaRequisicaoDto notaRequisicaoDto) {
        return new Nota(
                notaRequisicaoDto.idAluno(),
                notaRequisicaoDto.idAula(),
                notaRequisicaoDto.valor()
        );
    }

    public Nota paraEntidade(NotaRequisicaoDto notaRequisicaoDto, int id) {
        return new Nota(
                id,
                notaRequisicaoDto.idAluno(),
                notaRequisicaoDto.idAula(),
                notaRequisicaoDto.valor()
        );
    }

    public NotaRespostaDto paraResposta(Nota nota) throws SQLException {
        String nomeAluno = alunoRepository.listarAlunoPorId(nota.getAlunoId()).getNome();
        String nomeTurma = aulaRepository.listarAulaPorId(nota.getDisciplinaId()).getAssunto();

        return new NotaRespostaDto(
                nota.getId(),
                nomeAluno,
                nomeTurma,
                nota.getValor()
        );
    }
}

