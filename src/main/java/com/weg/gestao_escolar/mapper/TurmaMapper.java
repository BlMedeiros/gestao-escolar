package com.weg.gestao_escolar.mapper;

import com.weg.gestao_escolar.dto.turma.TurmaDto;
import com.weg.gestao_escolar.dto.turma.TurmaRequisicaoDto;
import com.weg.gestao_escolar.model.Turma;
import com.weg.gestao_escolar.repository.CursoRepository;
import com.weg.gestao_escolar.repository.ProfessorRepository;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TurmaMapper {

    private final CursoRepository cursoRepository;
    private final ProfessorRepository professorRepository;

    public TurmaMapper(CursoRepository cursoRepository, ProfessorRepository professorRepository) {
        this.cursoRepository = cursoRepository;
        this.professorRepository = professorRepository;
    }

    public Turma paraEntidade(TurmaRequisicaoDto turmaRequisicaoDto) {
        return new Turma(
                turmaRequisicaoDto.nome(),
                turmaRequisicaoDto.cursoId(),
                turmaRequisicaoDto.professorId(),
                turmaRequisicaoDto.listaAlunoIds()
        );
    }

    public Turma paraEntidade(TurmaRequisicaoDto turmaRequisicaoDto, int id) {
        return new Turma(
                id,
                turmaRequisicaoDto.nome(),
                turmaRequisicaoDto.cursoId(),
                turmaRequisicaoDto.professorId(),
                turmaRequisicaoDto.listaAlunoIds()
        );
    }

    public TurmaDto paraResposta(Turma turma, List<String> nomesAlunos) throws SQLException {
        String nomeCurso = cursoRepository.listarCursoPorId(turma.getCursoId()).getNome();
        String nomeProfessor = professorRepository.listarProfessorPorId(turma.getProfessorId()).getNome();

        return new TurmaDto(
                turma.getId(),
                turma.getNome(),
                nomeCurso,
                nomeProfessor,
                nomesAlunos
        );
    }
}


