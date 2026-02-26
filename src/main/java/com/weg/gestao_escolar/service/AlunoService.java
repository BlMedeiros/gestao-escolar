package com.weg.gestao_escolar.service;

import com.weg.gestao_escolar.dto.AlunoRequisicaoDto;
import com.weg.gestao_escolar.dto.AlunoRespostaDto;
import com.weg.gestao_escolar.mapper.AlunoMapper;
import com.weg.gestao_escolar.model.Aluno;
import com.weg.gestao_escolar.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoMapper alunoMapper;
    private final AlunoRepository alunoRepository;

        public AlunoService(AlunoMapper alunoMapper, AlunoRepository alunoRepository) {
            this.alunoMapper = alunoMapper;
            this.alunoRepository = alunoRepository;
        }

    public List<AlunoRespostaDto> listarAlunos() {
        List<Aluno> alunos = alunoRepository.listarAlunos();

        return alunos.stream()
                .map(alunoMapper::paraResposta)
                .toList();
    }

    public AlunoRespostaDto salvarAluno(AlunoRequisicaoDto alunoRequisicaoDto) {
        Aluno aluno = alunoMapper.paraEntidade(alunoRequisicaoDto);
        alunoRepository.salvarAluno(aluno);
        return alunoMapper.paraResposta(aluno);
    }
}
