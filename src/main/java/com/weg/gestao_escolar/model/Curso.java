package com.weg.gestao_escolar.model;

import java.util.List;

public class Curso {
    private int id;
    private String nome;
    private String codigo;
    private List<Integer> listaProfessorIds;

    public Curso(int id, String nome, String codigo, List<Integer> listaProfessorIds) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.listaProfessorIds = listaProfessorIds;
    }

    public Curso(String nome, String codigo, List<Integer> listaProfessorIds) {
        this.nome = nome;
        this.codigo = codigo;
        this.listaProfessorIds = listaProfessorIds;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Integer> getListaProfessorIds() {
        return listaProfessorIds;
    }

    public void setListaProfessorIds(List<Integer> listaProfessorIds) {
        this.listaProfessorIds = listaProfessorIds;
    }
}

