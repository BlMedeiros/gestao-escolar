package com.weg.gestao_escolar.controller;


import com.weg.gestao_escolar.dto.AlunoRequisicaoDto;
import com.weg.gestao_escolar.dto.AlunoRespostaDto;
import com.weg.gestao_escolar.service.AlunoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public List<AlunoRespostaDto> listarAlunos() {
        return alunoService.listarAlunos();
    }

    @GetMapping("/{id}")
    public AlunoRespostaDto buscarAlunoPorId(@PathVariable int id) {
        return alunoService.buscarAlunoPorId(id);
    }

    @PostMapping
    public AlunoRespostaDto salvarAluno(@RequestBody AlunoRequisicaoDto alunoRequisicaoDto) {
        return alunoService.salvarAluno(alunoRequisicaoDto);
    }

    @PutMapping("/{id}")
    public AlunoRespostaDto atualizarAluno(@PathVariable int id, @RequestBody AlunoRequisicaoDto alunoRequisicaoDto) {
        return alunoService.atualizarAluno(alunoRequisicaoDto, id);
    }

    @DeleteMapping("/{id}")
    public void deletarAluno(@PathVariable int id) {
        alunoService.deletarAluno(id);
    }

    @GetMapping("/{id}/notas")
    public List<NotaRespostaDto> listarNotasDoAluno(@PathVariable int id) {
        return alunoService.listarNotasDoAluno(id);
    }
}
