package com.weg.gestao_escolar.controller;

import com.weg.gestao_escolar.dto.aluno.AlunoRespostaDto;
import com.weg.gestao_escolar.dto.turma.TurmaDto;
import com.weg.gestao_escolar.dto.turma.TurmaRequisicaoDto;
import com.weg.gestao_escolar.service.TurmaService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    private final TurmaService turmaService;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @GetMapping
    public List<TurmaDto> listarTurmas() {
        try {
            return turmaService.listarTurmas();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public TurmaDto listarTurmaPorId(@PathVariable int id) {
        try {
            return turmaService.listarTurmaPorId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public TurmaDto salvarTurma(@RequestBody TurmaRequisicaoDto turmaRequisicaoDto) {
        try {
            return turmaService.salvarTurma(turmaRequisicaoDto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public TurmaDto atualizarTurma(@PathVariable int id, @RequestBody TurmaRequisicaoDto turmaRequisicaoDto) {
        try {
            return turmaService.atualizarTurma(turmaRequisicaoDto, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public void deletarTurma(@PathVariable int id) {
        try {
            turmaService.deletarTurma(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}/alunos")
    public List<AlunoRespostaDto> listarAlunosPorTurma(@PathVariable int id) {
        try {
            return turmaService.listarAlunosPorTurmaId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

