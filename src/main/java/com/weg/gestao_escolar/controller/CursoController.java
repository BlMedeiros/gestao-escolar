package com.weg.gestao_escolar.controller;

import com.weg.gestao_escolar.dto.curso.CursoRequisicaoDto;
import com.weg.gestao_escolar.dto.curso.CursoRespostaDto;
import com.weg.gestao_escolar.dto.turma.TurmaDto;
import com.weg.gestao_escolar.service.CursoService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public List<CursoRespostaDto> listarCursos() {
        try {
            return cursoService.listarCursos();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public CursoRespostaDto listarCursoPorId(@PathVariable int id) {
        try {
            return cursoService.listarCursoPorId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public CursoRespostaDto salvarCurso(@RequestBody CursoRequisicaoDto cursoRequisicaoDto) {
        try {
            return cursoService.salvarCurso(cursoRequisicaoDto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public CursoRespostaDto atualizarCurso(@PathVariable int id, @RequestBody CursoRequisicaoDto cursoRequisicaoDto) {
        try {
            return cursoService.atualizarCurso(cursoRequisicaoDto, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public void deletarCurso(@PathVariable int id) {
        try {
            cursoService.deletarCurso(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}/turmas")
    public List<TurmaDto> listarTurmasPorCurso(@PathVariable int id) {
        try {
            return cursoService.listarTurmasPorCursoId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

