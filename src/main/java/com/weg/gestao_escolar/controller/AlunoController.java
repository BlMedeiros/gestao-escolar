package com.weg.gestao_escolar.controller;


import com.weg.gestao_escolar.dto.aluno.AlunoRequisicaoDto;
import com.weg.gestao_escolar.dto.aluno.AlunoRespostaDto;
import com.weg.gestao_escolar.dto.nota.NotaRespostaDto;
import com.weg.gestao_escolar.service.AlunoService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public List<AlunoRespostaDto> listarAlunos() {
        try {
            return alunoService.listarAlunos();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public AlunoRespostaDto listarAlunoPorId(@PathVariable int id) {
        try {
            return alunoService.listarAlunoPorId(id);
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public AlunoRespostaDto salvarAluno(@RequestBody AlunoRequisicaoDto alunoRequisicaoDto) {
        try {
            return alunoService.salvarAluno(alunoRequisicaoDto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public AlunoRespostaDto atualizarAluno(@PathVariable int id, @RequestBody AlunoRequisicaoDto alunoRequisicaoDto) {
        try {
            return alunoService.atualizarAluno(alunoRequisicaoDto, id);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping("/{id}")
    public void deletarAluno(@PathVariable int id) {
     try {
         alunoService.deletarAluno(id);
     }catch (SQLException e) {
            throw new RuntimeException(e);
     }
    }

    @GetMapping("/{id}/notas")
    public List<NotaRespostaDto> listarNotasDoAluno(@PathVariable int id) {
        try {
            return alunoService.listarNotasPorAlunoId(id);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
