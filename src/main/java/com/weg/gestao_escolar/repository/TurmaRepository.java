package com.weg.gestao_escolar.repository;

import com.weg.gestao_escolar.connection.Conexao;
import com.weg.gestao_escolar.model.Aluno;
import com.weg.gestao_escolar.model.Turma;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TurmaRepository {

    public List<Turma> listarTurmas() throws SQLException {
        String selectQuery = """
                SELECT id,
                       nome,
                       curso_id,
                       professor_id
                FROM turma
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(selectQuery)) {

            ResultSet rs = ps.executeQuery();
            List<Turma> turmas = new ArrayList<>();

            while (rs.next()) {
                int turmaId = rs.getInt("id");
                List<Integer> alunoIds = listarAlunoIdsPorTurmaId(turmaId);

                Turma turma = new Turma(
                        turmaId,
                        rs.getString("nome"),
                        rs.getInt("curso_id"),
                        rs.getInt("professor_id"),
                        alunoIds
                );
                turmas.add(turma);
            }
            return turmas;
        }
    }

    public Turma listarTurmaPorId(int id) throws SQLException {
        String selectQuery = """
                SELECT id,
                       nome,
                       curso_id,
                       professor_id
                FROM turma
                WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(selectQuery)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                List<Integer> alunoIds = listarAlunoIdsPorTurmaId(id);
                return new Turma(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("curso_id"),
                        rs.getInt("professor_id"),
                        alunoIds
                );
            } else {
                throw new RuntimeException("Turma não encontrada com o ID: " + id);
            }
        }
    }

    public Turma salvarTurma(Turma turma) throws SQLException {
        String insertQuery = """
                INSERT INTO turma (nome, curso_id, professor_id)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, turma.getNome());
            stmt.setInt(2, turma.getCursoId());
            stmt.setInt(3, turma.getProfessorId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Falha ao salvar a turma.");
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    turma.setId(rs.getInt(1));
                } else {
                    throw new RuntimeException("Falha ao obter o ID da turma salva.");
                }
            }

            // Salvar alunos da turma
            if (turma.getListaAlunoIds() != null && !turma.getListaAlunoIds().isEmpty()) {
                salvarAlunosTurma(turma.getId(), turma.getListaAlunoIds());
            }
        }
        return turma;
    }

    public Turma atualizarTurma(Turma turma) throws SQLException {
        String updateQuery = """
                UPDATE turma
                SET nome = ?, curso_id = ?, professor_id = ?
                WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setString(1, turma.getNome());
            stmt.setInt(2, turma.getCursoId());
            stmt.setInt(3, turma.getProfessorId());
            stmt.setInt(4, turma.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Falha ao atualizar a turma com ID: " + turma.getId());
            }
        }
        return turma;
    }

    public void deletarTurma(int id) throws SQLException {
        String deleteQuery = "DELETE FROM turma WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            stmt.setInt(1, id);

            // Deletar alunos da turma
            deletarAlunosTurma(id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Falha ao deletar a turma com ID: " + id);
            }
        }
    }

    private void salvarAlunosTurma(int turmaId, List<Integer> alunoIds) throws SQLException {
        String insertQuery = """
                INSERT INTO turma_aluno (turma_id, aluno_id)
                VALUES (?, ?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            for (Integer alunoId : alunoIds) {
                stmt.setInt(1, turmaId);
                stmt.setInt(2, alunoId);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private List<Integer> listarAlunoIdsPorTurmaId(int turmaId) throws SQLException {
        String selectQuery = """
                SELECT aluno_id
                FROM turma_aluno
                WHERE turma_id = ?
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(selectQuery)) {
            stmt.setInt(1, turmaId);
            ResultSet rs = stmt.executeQuery();

            List<Integer> alunoIds = new ArrayList<>();
            while (rs.next()) {
                alunoIds.add(rs.getInt("aluno_id"));
            }
            return alunoIds;
        }
    }

    private void deletarAlunosTurma(int turmaId) throws SQLException {
        String deleteQuery = "DELETE FROM turma_aluno WHERE turma_id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            stmt.setInt(1, turmaId);
            stmt.executeUpdate();
        }
    }

    public List<Aluno> listarAlunosPorTurmaId(int turmaId) throws SQLException {
        String selectQuery = """
                SELECT a.id,
                       a.nome,
                       a.email,
                       a.matricula,
                       a.data_nascimento
                FROM aluno a
                INNER JOIN turma_aluno ta ON a.id = ta.aluno_id
                WHERE ta.turma_id = ?
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(selectQuery)) {
            stmt.setInt(1, turmaId);
            ResultSet rs = stmt.executeQuery();

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
}

