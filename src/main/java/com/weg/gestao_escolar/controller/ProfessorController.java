package com.weg.gestao_escolar.controller;

import com.weg.gestao_escolar.dto.professor.ProfessorRequisicaoDto;
import com.weg.gestao_escolar.dto.professor.ProfessorRespostaDto;
import com.weg.gestao_escolar.service.ProfessorService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public List<ProfessorRespostaDto> listarProfessores() {
        try {
            return professorService.listarProfessores();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ProfessorRespostaDto listarProfessorPorId(@PathVariable int id) {
        try {
            return professorService.listarProfessorPorId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ProfessorRespostaDto salvarProfessor(@RequestBody ProfessorRequisicaoDto professorRequisicaoDto) {
        try {
            return professorService.salvarProfessor(professorRequisicaoDto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public ProfessorRespostaDto atualizarProfessor(@PathVariable int id, @RequestBody ProfessorRequisicaoDto professorRequisicaoDto) {
        try {
            return professorService.atualizarProfessor(professorRequisicaoDto, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public void deletarProfessor(@PathVariable int id) {
        try {
            professorService.deletarProfessor(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

