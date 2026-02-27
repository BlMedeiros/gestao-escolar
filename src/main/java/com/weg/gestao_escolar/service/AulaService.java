package com.weg.gestao_escolar.service;

import com.weg.gestao_escolar.dto.aula.AulaRequisicaoDto;
import com.weg.gestao_escolar.dto.aula.AulaRespostaDto;
import com.weg.gestao_escolar.mapper.AulaMapper;
import com.weg.gestao_escolar.model.Aula;
import com.weg.gestao_escolar.repository.AulaRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AulaService {

    private final AulaMapper aulaMapper;
    private final AulaRepository aulaRepository;

    public AulaService(AulaMapper aulaMapper, AulaRepository aulaRepository) {
        this.aulaMapper = aulaMapper;
        this.aulaRepository = aulaRepository;
    }

    public List<AulaRespostaDto> listarAulas() throws SQLException {
        List<Aula> aulas = aulaRepository.listarAulas();
        List<AulaRespostaDto> resultado = new ArrayList<>();

        for (Aula aula : aulas) {
            resultado.add(aulaMapper.paraResposta(aula));
        }

        return resultado;
    }

    public AulaRespostaDto listarAulaPorId(int id) throws SQLException {
        Aula aula = aulaRepository.listarAulaPorId(id);

        return aulaMapper.paraResposta(aula);
    }

    public AulaRespostaDto salvarAula(AulaRequisicaoDto aulaRequisicaoDto) throws SQLException {
        Aula aula = aulaMapper.paraEntidade(aulaRequisicaoDto);

        aulaRepository.salvarAula(aula);

        return aulaMapper.paraResposta(aula);
    }

    public AulaRespostaDto atualizarAula(AulaRequisicaoDto aulaRequisicaoDto, int id) throws SQLException {
        Aula aula = aulaMapper.paraEntidade(aulaRequisicaoDto, id);

        aulaRepository.atualizarAula(aula);

        return aulaMapper.paraResposta(aula);
    }

    public void deletarAula(int id) throws SQLException {
        aulaRepository.deletarAula(id);
    }

    public List<AulaRespostaDto> listarAulasPorTurmaId(int turmaId) throws SQLException {
        List<Aula> aulas = aulaRepository.listarAulasPorTurmaId(turmaId);
        List<AulaRespostaDto> resultado = new ArrayList<>();

        for (Aula aula : aulas) {
            resultado.add(aulaMapper.paraResposta(aula));
        }

        return resultado;
    }
}

