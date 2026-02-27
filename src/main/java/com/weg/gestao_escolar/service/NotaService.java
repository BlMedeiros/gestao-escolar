package com.weg.gestao_escolar.service;

import com.weg.gestao_escolar.dto.nota.NotaRequisicaoDto;
import com.weg.gestao_escolar.dto.nota.NotaRespostaDto;
import com.weg.gestao_escolar.mapper.NotaMapper;
import com.weg.gestao_escolar.model.Nota;
import com.weg.gestao_escolar.repository.NotaRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotaService {

    private final NotaMapper notaMapper;
    private final NotaRepository notaRepository;

    public NotaService(NotaMapper notaMapper, NotaRepository notaRepository) {
        this.notaMapper = notaMapper;
        this.notaRepository = notaRepository;
    }

    public List<NotaRespostaDto> listarNotas() throws SQLException {
        List<Nota> notas = notaRepository.listarNotas();
        List<NotaRespostaDto> resultado = new ArrayList<>();

        for (Nota nota : notas) {
            resultado.add(notaMapper.paraResposta(nota));
        }

        return resultado;
    }

    public NotaRespostaDto listarNotaPorId(int id) throws SQLException {
        Nota nota = notaRepository.listarNotaPorId(id);

        return notaMapper.paraResposta(nota);
    }

    public NotaRespostaDto salvarNota(NotaRequisicaoDto notaRequisicaoDto) throws SQLException {
        Nota nota = notaMapper.paraEntidade(notaRequisicaoDto);

        notaRepository.salvarNota(nota);

        return notaMapper.paraResposta(nota);
    }

    public NotaRespostaDto atualizarNota(NotaRequisicaoDto notaRequisicaoDto, int id) throws SQLException {
        Nota nota = notaMapper.paraEntidade(notaRequisicaoDto, id);

        notaRepository.atualizarNota(nota);

        return notaMapper.paraResposta(nota);
    }

    public void deletarNota(int id) throws SQLException {
        notaRepository.deletarNota(id);
    }

    public List<NotaRespostaDto> listarNotasPorAlunoId(int alunoId) throws SQLException {
        List<Nota> notas = notaRepository.listarNotasPorAlunoId(alunoId);
        List<NotaRespostaDto> resultado = new ArrayList<>();

        for (Nota nota : notas) {
            resultado.add(notaMapper.paraResposta(nota));
        }

        return resultado;
    }
}


