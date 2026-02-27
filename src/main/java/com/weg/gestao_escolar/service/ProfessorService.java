package com.weg.gestao_escolar.service;

import com.weg.gestao_escolar.dto.professor.ProfessorRequisicaoDto;
import com.weg.gestao_escolar.dto.professor.ProfessorRespostaDto;
import com.weg.gestao_escolar.mapper.ProfessorMapper;
import com.weg.gestao_escolar.model.Professor;
import com.weg.gestao_escolar.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorMapper professorMapper;
    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorMapper professorMapper, ProfessorRepository professorRepository) {
        this.professorMapper = professorMapper;
        this.professorRepository = professorRepository;
    }

    public List<ProfessorRespostaDto> listarProfessores() throws SQLException {
        List<Professor> professores = professorRepository.listarProfessores();

        return professores.stream()
                .map(professorMapper::paraResposta)
                .toList();
    }

    public ProfessorRespostaDto listarProfessorPorId(int id) throws SQLException {
        Professor professor = professorRepository.listarProfessorPorId(id);

        return professorMapper.paraResposta(professor);
    }

    public ProfessorRespostaDto salvarProfessor(ProfessorRequisicaoDto professorRequisicaoDto) throws SQLException {
        Professor professor = professorMapper.paraEntidade(professorRequisicaoDto);

        professorRepository.salvarProfessor(professor);

        return professorMapper.paraResposta(professor);
    }

    public ProfessorRespostaDto atualizarProfessor(ProfessorRequisicaoDto professorRequisicaoDto, int id) throws SQLException {
        Professor professor = professorMapper.paraEntidade(professorRequisicaoDto, id);

        professorRepository.atualizarProfessor(professor);

        return professorMapper.paraResposta(professor);
    }

    public void deletarProfessor(int id) throws SQLException {
        professorRepository.deletarProfessor(id);
    }
}

