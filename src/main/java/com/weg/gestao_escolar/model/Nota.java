package com.weg.gestao_escolar.model;

public class Nota {
    private int id;
    private int alunoId;
    private int disciplinaId;
    private double valor;

    public Nota(int id, int alunoId, int disciplinaId, double valor) {
        this.id = id;
        this.alunoId = alunoId;
        this.disciplinaId = disciplinaId;
        this.valor = valor;
    }

    public Nota(int alunoId, int disciplinaId, double valor) {
        this.alunoId = alunoId;
        this.disciplinaId = disciplinaId;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public int getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(int disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
