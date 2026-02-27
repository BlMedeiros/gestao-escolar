package com.weg.gestao_escolar.service;

import com.weg.gestao_escolar.dto.aluno.AlunoRespostaDto;
import com.weg.gestao_escolar.dto.turma.TurmaDto;
import com.weg.gestao_escolar.dto.turma.TurmaRequisicaoDto;
import com.weg.gestao_escolar.mapper.AlunoMapper;
import com.weg.gestao_escolar.mapper.TurmaMapper;
import com.weg.gestao_escolar.model.Aluno;
import com.weg.gestao_escolar.model.Turma;
import com.weg.gestao_escolar.repository.TurmaRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TurmaService {

    private final TurmaMapper turmaMapper;
    private final TurmaRepository turmaRepository;
    private final AlunoMapper alunoMapper;

    public TurmaService(TurmaMapper turmaMapper, TurmaRepository turmaRepository, AlunoMapper alunoMapper) {
        this.turmaMapper = turmaMapper;
        this.turmaRepository = turmaRepository;
        this.alunoMapper = alunoMapper;
    }

    public List<TurmaDto> listarTurmas() throws SQLException {
        List<Turma> turmas = turmaRepository.listarTurmas();

        return turmas.stream()
                .map(turma -> {
                    try {
                        List<String> nomesAlunos = obterNomesAlunos(turma.getListaAlunoIds());
                        return turmaMapper.paraResposta(turma, nomesAlunos);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    public TurmaDto listarTurmaPorId(int id) throws SQLException {
        Turma turma = turmaRepository.listarTurmaPorId(id);
        List<String> nomesAlunos = obterNomesAlunos(turma.getListaAlunoIds());

        return turmaMapper.paraResposta(turma, nomesAlunos);
    }

    public TurmaDto salvarTurma(TurmaRequisicaoDto turmaRequisicaoDto) throws SQLException {
        Turma turma = turmaMapper.paraEntidade(turmaRequisicaoDto);

        turmaRepository.salvarTurma(turma);

        List<String> nomesAlunos = obterNomesAlunos(turma.getListaAlunoIds());
        return turmaMapper.paraResposta(turma, nomesAlunos);
    }

    public TurmaDto atualizarTurma(TurmaRequisicaoDto turmaRequisicaoDto, int id) throws SQLException {
        Turma turma = turmaMapper.paraEntidade(turmaRequisicaoDto, id);

        turmaRepository.atualizarTurma(turma);

        List<String> nomesAlunos = obterNomesAlunos(turma.getListaAlunoIds());
        return turmaMapper.paraResposta(turma, nomesAlunos);
    }

    public void deletarTurma(int id) throws SQLException {
        turmaRepository.deletarTurma(id);
    }

    public List<AlunoRespostaDto> listarAlunosPorTurmaId(int id) throws SQLException {
        List<Aluno> alunos = turmaRepository.listarAlunosPorTurmaId(id);

        return alunos.stream()
                .map(alunoMapper::paraResposta)
                .toList();
    }

    private List<String> obterNomesAlunos(List<Integer> alunoIds) {
        if (alunoIds == null || alunoIds.isEmpty()) {
            return List.of();
        }
        return alunoIds.stream()
                .map(id -> {
                    try {
                        Aluno aluno = turmaRepository.listarAlunosPorTurmaId(id).stream()
                                .findFirst()
                                .orElse(null);
                        return aluno != null ? aluno.getNome() : "";
                    } catch (SQLException e) {
                        return "";
                    }
                })
                .filter(nome -> !nome.isEmpty())
                .toList();
    }
}

