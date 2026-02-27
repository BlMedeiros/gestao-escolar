package com.weg.gestao_escolar.service;

import com.weg.gestao_escolar.dto.aluno.AlunoRequisicaoDto;
import com.weg.gestao_escolar.dto.aluno.AlunoRespostaDto;
import com.weg.gestao_escolar.dto.nota.NotaRespostaDto;
import com.weg.gestao_escolar.mapper.AlunoMapper;
import com.weg.gestao_escolar.mapper.NotaMapper;
import com.weg.gestao_escolar.model.Aluno;
import com.weg.gestao_escolar.model.Nota;
import com.weg.gestao_escolar.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlunoService {

    private final AlunoMapper alunoMapper;
    private final AlunoRepository alunoRepository;
    private final NotaMapper notaMapper;

    public AlunoService(AlunoMapper alunoMapper, AlunoRepository alunoRepository, NotaMapper notaMapper) {
        this.alunoMapper = alunoMapper;
        this.alunoRepository = alunoRepository;
        this.notaMapper = notaMapper;
    }

    public List<AlunoRespostaDto> listarAlunos() throws SQLException {
        List<Aluno> alunos = alunoRepository.listarAlunos();

        return alunos.stream()
                .map(alunoMapper::paraResposta)
                .toList();
    }

    public AlunoRespostaDto listarAlunoPorId(int id) throws SQLException {
        Aluno aluno = alunoRepository.listarAlunoPorId(id);

        return alunoMapper.paraResposta(aluno);
    }

    public AlunoRespostaDto salvarAluno(AlunoRequisicaoDto alunoRequisicaoDto) throws SQLException {
        Aluno aluno = alunoMapper.paraEntidade(alunoRequisicaoDto);

        alunoRepository.salvarAluno(aluno);

        return alunoMapper.paraResposta(aluno);
    }

    public AlunoRespostaDto atualizarAluno(AlunoRequisicaoDto alunoRequisicaoDto, int id) throws SQLException {
        Aluno aluno = alunoMapper.paraEntidade(alunoRequisicaoDto, id);

        alunoRepository.atualizarAluno(aluno);

        return alunoMapper.paraResposta(aluno);
    }

    public void deletarAluno(int id) throws SQLException {
        alunoRepository.deletarAluno(id);
    }

    public List<NotaRespostaDto> listarNotasPorAlunoId(int id) throws SQLException {
        List<Nota> notas = alunoRepository.listarNotasPorAlunoId(id);
        List<NotaRespostaDto> resultado = new ArrayList<>();

        for (Nota nota : notas) {
            resultado.add(notaMapper.paraResposta(nota));
        }

        return resultado;
    }
}
