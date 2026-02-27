package com.weg.gestao_escolar.service;

import com.weg.gestao_escolar.dto.curso.CursoRequisicaoDto;
import com.weg.gestao_escolar.dto.curso.CursoRespostaDto;
import com.weg.gestao_escolar.dto.turma.TurmaDto;
import com.weg.gestao_escolar.mapper.CursoMapper;
import com.weg.gestao_escolar.mapper.TurmaMapper;
import com.weg.gestao_escolar.model.Aluno;
import com.weg.gestao_escolar.model.Curso;
import com.weg.gestao_escolar.model.Turma;
import com.weg.gestao_escolar.repository.CursoRepository;
import com.weg.gestao_escolar.repository.TurmaRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CursoService {

    private final CursoMapper cursoMapper;
    private final CursoRepository cursoRepository;
    private final TurmaMapper turmaMapper;
    private final TurmaRepository turmaRepository;

    public CursoService(CursoMapper cursoMapper, CursoRepository cursoRepository, TurmaMapper turmaMapper, TurmaRepository turmaRepository) {
        this.cursoMapper = cursoMapper;
        this.cursoRepository = cursoRepository;
        this.turmaMapper = turmaMapper;
        this.turmaRepository = turmaRepository;
    }

    public List<CursoRespostaDto> listarCursos() throws SQLException {
        List<Curso> cursos = cursoRepository.listarCursos();
        List<CursoRespostaDto> resultado = new ArrayList<>();

        for (Curso curso : cursos) {
            resultado.add(cursoMapper.paraResposta(curso));
        }

        return resultado;
    }

    public CursoRespostaDto listarCursoPorId(int id) throws SQLException {
        Curso curso = cursoRepository.listarCursoPorId(id);

        return cursoMapper.paraResposta(curso);
    }

    public CursoRespostaDto salvarCurso(CursoRequisicaoDto cursoRequisicaoDto) throws SQLException {
        Curso curso = cursoMapper.paraEntidade(cursoRequisicaoDto);

        cursoRepository.salvarCurso(curso);

        return cursoMapper.paraResposta(curso);
    }

    public CursoRespostaDto atualizarCurso(CursoRequisicaoDto cursoRequisicaoDto, int id) throws SQLException {
        Curso curso = cursoMapper.paraEntidade(cursoRequisicaoDto, id);

        cursoRepository.atualizarCurso(curso);

        return cursoMapper.paraResposta(curso);
    }

    public void deletarCurso(int id) throws SQLException {
        cursoRepository.deletarCurso(id);
    }

    public List<TurmaDto> listarTurmasPorCursoId(int id) throws SQLException {
        List<Turma> turmas = cursoRepository.listarTurmasPorCursoId(id);
        List<TurmaDto> resultado = new ArrayList<>();

        for (Turma turma : turmas) {
            List<Aluno> alunos = turmaRepository.listarAlunosPorTurmaId(turma.getId());
            List<String> nomesAlunos = new ArrayList<>();

            for (Aluno aluno : alunos) {
                nomesAlunos.add(aluno.getNome());
            }

            resultado.add(turmaMapper.paraResposta(turma, nomesAlunos));
        }

        return resultado;
    }
}

