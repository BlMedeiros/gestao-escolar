package com.weg.gestao_escolar.controller;

import com.weg.gestao_escolar.dto.aula.AulaRequisicaoDto;
import com.weg.gestao_escolar.dto.aula.AulaRespostaDto;
import com.weg.gestao_escolar.service.AulaService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    private final AulaService aulaService;

    public AulaController(AulaService aulaService) {
        this.aulaService = aulaService;
    }

    @GetMapping
    public List<AulaRespostaDto> listarAulas() {
        try {
            return aulaService.listarAulas();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public AulaRespostaDto listarAulaPorId(@PathVariable int id) {
        try {
            return aulaService.listarAulaPorId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public AulaRespostaDto salvarAula(@RequestBody AulaRequisicaoDto aulaRequisicaoDto) {
        try {
            return aulaService.salvarAula(aulaRequisicaoDto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public AulaRespostaDto atualizarAula(@PathVariable int id, @RequestBody AulaRequisicaoDto aulaRequisicaoDto) {
        try {
            return aulaService.atualizarAula(aulaRequisicaoDto, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public void deletarAula(@PathVariable int id) {
        try {
            aulaService.deletarAula(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/turma/{turmaId}")
    public List<AulaRespostaDto> listarAulasPorTurma(@PathVariable int turmaId) {
        try {
            return aulaService.listarAulasPorTurmaId(turmaId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

