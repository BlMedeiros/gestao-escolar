package com.weg.gestao_escolar.repository;

import com.weg.gestao_escolar.connection.Conexao;
import com.weg.gestao_escolar.model.Aluno;
import com.weg.gestao_escolar.model.Nota;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AlunoRepository {

    public List<Aluno> listarAlunos() throws SQLException {
        String selectQuery = """
                SELECT id,
                       nome,
                       email,
                       matricula,
                       data_nascimento
                FROM aluno
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(selectQuery)){

                ResultSet rs = ps.executeQuery();
                List<Aluno> alunos = new ArrayList<>();

                while (rs.next()) {
                    Aluno aluno = new Aluno(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("matricula"),
                            rs.getDate("data_nascimento").toLocalDate()
                    );
                    alunos.add(aluno);
                }
                return alunos;
            }
        }


    public Aluno listarAlunoPorId(int id) throws SQLException {
        String selectQuery = """
                SELECT id,
                       nome,
                       email,
                       matricula,
                       data_nascimento
                FROM aluno
                WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(selectQuery)){
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return new Aluno(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("matricula"),
                            rs.getDate("data_nascimento").toLocalDate()
                    );
                } else {
                    throw new RuntimeException("Aluno não encontrado com o ID: " + id);
                }
            }
        }


    public Aluno salvarAluno(Aluno aluno) throws SQLException {
        String insertQuery = """
                INSERT INTO aluno (nome, email, matricula, data_nascimento)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, aluno.getNome());
                stmt.setString(2, aluno.getEmail());
                stmt.setString(3, aluno.getMatricula());
                stmt.setDate(4, java.sql.Date.valueOf(aluno.getDataNascimento()));

                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas == 0) {
                    throw new RuntimeException("Falha ao salvar o aluno.");
                }

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        aluno.setId(rs.getInt(1));
                    } else {
                        throw new RuntimeException("Falha ao obter o ID do aluno salvo.");
                    }
                }
            }
        return aluno;
    }


    public Aluno atualizarAluno(Aluno aluno) throws SQLException {
        String updateQuery = """
                UPDATE aluno
                SET nome = ?, email = ?, matricula = ?, data_nascimento = ?
                WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                stmt.setString(1, aluno.getNome());
                stmt.setString(2, aluno.getEmail());
                stmt.setString(3, aluno.getMatricula());
                stmt.setDate(4, java.sql.Date.valueOf(aluno.getDataNascimento()));
                stmt.setInt(5, aluno.getId());

                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas == 0) {
                    throw new RuntimeException("Falha ao atualizar o aluno com ID: " + aluno.getId());
                }
            }
            return aluno;
        }

    public void deletarAluno(int id) throws SQLException {
        String deleteQuery = "DELETE FROM aluno WHERE id = ?";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
                stmt.setInt(1, id);

                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas == 0) {
                    throw new RuntimeException("Falha ao deletar o aluno com ID: " + id);
                }
            }
        }


    public List<Nota> listarNotasPorAlunoId(int alunoId) throws SQLException {
        String selectQuery = """
                SELECT id,
                       aluno_id,
                       aula_id,
                       valor
                FROM nota
                WHERE aluno_id = ?
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(selectQuery)) {
                stmt.setInt(1, alunoId);
                ResultSet rs = stmt.executeQuery();

                List<Nota> notas = new ArrayList<>();

                while (rs.next()) {
                    Nota nota = new Nota(
                            rs.getInt("id"),
                            rs.getInt("aluno_id"),
                            rs.getInt("aula_id"),
                            rs.getDouble("valor")
                    );
                    notas.add(nota);
                }
                return notas;
            }
        }

}
