package com.weg.gestao_escolar.repository;

import com.weg.gestao_escolar.connection.Conexao;
import com.weg.gestao_escolar.model.Curso;
import com.weg.gestao_escolar.model.Turma;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CursoRepository {

    public List<Curso> listarCursos() throws SQLException {
        String selectQuery = """
                SELECT id,
                       nome,
                       codigo
                FROM curso
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(selectQuery)) {

            ResultSet rs = ps.executeQuery();
            List<Curso> cursos = new ArrayList<>();

            while (rs.next()) {
                int cursoId = rs.getInt("id");
                List<Integer> professorIds = listarProfessorIdsPorCursoId(cursoId);

                Curso curso = new Curso(
                        cursoId,
                        rs.getString("nome"),
                        rs.getString("codigo"),
                        professorIds
                );
                cursos.add(curso);
            }
            return cursos;
        }
    }

    public Curso listarCursoPorId(int id) throws SQLException {
        String selectQuery = """
                SELECT id,
                       nome,
                       codigo
                FROM curso
                WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(selectQuery)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                List<Integer> professorIds = listarProfessorIdsPorCursoId(id);
                return new Curso(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("codigo"),
                        professorIds
                );
            } else {
                throw new RuntimeException("Curso não encontrado com o ID: " + id);
            }
        }
    }

    public Curso salvarCurso(Curso curso) throws SQLException {
        String insertQuery = """
                INSERT INTO curso (nome, codigo)
                VALUES (?, ?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getCodigo());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Falha ao salvar o curso.");
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    curso.setId(rs.getInt(1));
                } else {
                    throw new RuntimeException("Falha ao obter o ID do curso salvo.");
                }
            }

            if (curso.getListaProfessorIds() != null && !curso.getListaProfessorIds().isEmpty()) {
                salvarProfessoresCurso(curso.getId(), curso.getListaProfessorIds());
            }
        }
        return curso;
    }

    public Curso atualizarCurso(Curso curso) throws SQLException {
        String updateQuery = """
                UPDATE curso
                SET nome = ?, codigo = ?
                WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getCodigo());
            stmt.setInt(3, curso.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Falha ao atualizar o curso com ID: " + curso.getId());
            }
        }
        return curso;
    }

    public void deletarCurso(int id) throws SQLException {
        String deleteQuery = "DELETE FROM curso WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            stmt.setInt(1, id);

            deletarProfessoresCurso(id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Falha ao deletar o curso com ID: " + id);
            }
        }
    }

    private void salvarProfessoresCurso(int cursoId, List<Integer> professorIds) throws SQLException {
        String insertQuery = """
                INSERT INTO curso_professor (curso_id, professor_id)
                VALUES (?, ?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            for (Integer professorId : professorIds) {
                stmt.setInt(1, cursoId);
                stmt.setInt(2, professorId);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private List<Integer> listarProfessorIdsPorCursoId(int cursoId) throws SQLException {
        String selectQuery = """
                SELECT professor_id
                FROM curso_professor
                WHERE curso_id = ?
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(selectQuery)) {
            stmt.setInt(1, cursoId);
            ResultSet rs = stmt.executeQuery();

            List<Integer> professorIds = new ArrayList<>();
            while (rs.next()) {
                professorIds.add(rs.getInt("professor_id"));
            }
            return professorIds;
        }
    }

    private void deletarProfessoresCurso(int cursoId) throws SQLException {
        String deleteQuery = "DELETE FROM curso_professor WHERE curso_id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            stmt.setInt(1, cursoId);
            stmt.executeUpdate();
        }
    }

    public List<Turma> listarTurmasPorCursoId(int cursoId) throws SQLException {
        String selectQuery = """
                SELECT id,
                       nome,
                       curso_id,
                       professor_id
                FROM turma
                WHERE curso_id = ?
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(selectQuery)) {
            stmt.setInt(1, cursoId);
            ResultSet rs = stmt.executeQuery();

            List<Turma> turmas = new ArrayList<>();
            while (rs.next()) {
                Turma turma = new Turma(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("curso_id"),
                        rs.getInt("professor_id"),
                        null
                );
                turmas.add(turma);
            }
            return turmas;
        }
    }
}


