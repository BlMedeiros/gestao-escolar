package com.weg.gestao_escolar.mapper;

import com.weg.gestao_escolar.dto.professor.ProfessorRequisicaoDto;
import com.weg.gestao_escolar.dto.professor.ProfessorRespostaDto;
import com.weg.gestao_escolar.model.Professor;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {

    public Professor paraEntidade(ProfessorRequisicaoDto professorRequisicaoDto) {
        return new Professor(
                professorRequisicaoDto.nome(),
                professorRequisicaoDto.email(),
                professorRequisicaoDto.disciplina()
        );
    }

    public Professor paraEntidade(ProfessorRequisicaoDto professorRequisicaoDto, int id) {
        return new Professor(
                id,
                professorRequisicaoDto.nome(),
                professorRequisicaoDto.email(),
                professorRequisicaoDto.disciplina()
        );
    }

    public ProfessorRespostaDto paraResposta(Professor professor) {
        return new ProfessorRespostaDto(
                professor.getId(),
                professor.getNome(),
                professor.getEmail(),
                professor.getDisciplina()
        );
    }
}

