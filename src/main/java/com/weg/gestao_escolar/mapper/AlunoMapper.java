package com.weg.gestao_escolar.mapper;

import com.weg.gestao_escolar.dto.aluno.AlunoRequisicaoDto;
import com.weg.gestao_escolar.dto.aluno.AlunoRespostaDto;
import com.weg.gestao_escolar.model.Aluno;
import org.springframework.stereotype.Component;

@Component
public class AlunoMapper {

    public Aluno paraEntidade(AlunoRequisicaoDto alunoRequisicaoDto) {
        return new Aluno(
                alunoRequisicaoDto.nome(),
                alunoRequisicaoDto.email(),
                alunoRequisicaoDto.matricula(),
                alunoRequisicaoDto.dataNascimento()
        );
    }

    public Aluno paraEntidade(AlunoRequisicaoDto alunoRequisicaoDto, int id) {
        return new Aluno(
                id,
                alunoRequisicaoDto.nome(),
                alunoRequisicaoDto.email(),
                alunoRequisicaoDto.matricula(),
                alunoRequisicaoDto.dataNascimento()
        );
    }

    public AlunoRespostaDto paraResposta(Aluno aluno) {
        return new AlunoRespostaDto(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getMatricula(),
                aluno.getDataNascimento()
        );
    }
}
