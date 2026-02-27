package com.weg.gestao_escolar.mapper;

import com.weg.gestao_escolar.dto.curso.CursoRequisicaoDto;
import com.weg.gestao_escolar.dto.curso.CursoRespostaDto;
import com.weg.gestao_escolar.model.Curso;
import com.weg.gestao_escolar.model.Professor;
import com.weg.gestao_escolar.repository.ProfessorRepository;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CursoMapper {

    private final ProfessorRepository professorRepository;

    public CursoMapper(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public Curso paraEntidade(CursoRequisicaoDto cursoRequisicaoDto) {
        return new Curso(
                cursoRequisicaoDto.nome(),
                cursoRequisicaoDto.codigo(),
                cursoRequisicaoDto.listaProfessorIds()
        );
    }

    public Curso paraEntidade(CursoRequisicaoDto cursoRequisicaoDto, int id) {
        return new Curso(
                id,
                cursoRequisicaoDto.nome(),
                cursoRequisicaoDto.codigo(),
                cursoRequisicaoDto.listaProfessorIds()
        );
    }

    public CursoRespostaDto paraResposta(Curso curso) throws SQLException {
        List<String> nomes = new ArrayList<>();

        if (curso.getListaProfessorIds() != null) {
            for (Integer id : curso.getListaProfessorIds()) {
                nomes.add(professorRepository.listarProfessorPorId(id).getNome());
            }
        }

        return new CursoRespostaDto(curso.getId(),
                                    curso.getNome(),
                                    curso.getCodigo(),
                                    nomes);
    }
}

