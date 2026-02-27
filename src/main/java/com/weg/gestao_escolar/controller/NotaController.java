package com.weg.gestao_escolar.controller;

import com.weg.gestao_escolar.dto.nota.NotaRequisicaoDto;
import com.weg.gestao_escolar.dto.nota.NotaRespostaDto;
import com.weg.gestao_escolar.model.Nota;
import com.weg.gestao_escolar.service.NotaService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/notas")
public class NotaController {

    NotaService notaService;

    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    @GetMapping
    public List<NotaRespostaDto> listarNotas() {
        try {
            return notaService.listarNotas();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public NotaRespostaDto listarNotaPorId(@PathVariable int id) {
        try {
            return notaService.listarNotaPorId(id);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public NotaRespostaDto salvarNota(@RequestBody NotaRequisicaoDto notaRequisicaoDto) {
        try {
            return notaService.salvarNota(notaRequisicaoDto);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public NotaRespostaDto atualizarNota(@PathVariable int id, @RequestBody NotaRequisicaoDto notaRequisicaoDto) {
        try {
            return notaService.atualizarNota(notaRequisicaoDto, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public void deletarNota(@PathVariable int id) {
        try {
            notaService.deletarNota(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
