package com.weg.gestao_escolar.model;

import java.util.List;

public class Turma {
    private int id;
    private String nome;
    private int cursoId;
    private int professorId;
    private List<Integer> listaAlunoIds;

    public Turma(int id, String nome, int cursoId, int professorId, List<Integer> listaAlunoIds) {
        this.id = id;
        this.nome = nome;
        this.cursoId = cursoId;
        this.professorId = professorId;
        this.listaAlunoIds = listaAlunoIds;
    }

    public Turma(String nome, int cursoId, int professorId, List<Integer> listaAlunoIds) {
        this.nome = nome;
        this.cursoId = cursoId;
        this.professorId = professorId;
        this.listaAlunoIds = listaAlunoIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public List<Integer> getListaAlunoIds() {
        return listaAlunoIds;
    }

    public void setListaAlunoIds(List<Integer> listaAlunoIds) {
        this.listaAlunoIds = listaAlunoIds;
    }
}


