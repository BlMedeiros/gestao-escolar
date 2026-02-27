package com.weg.gestao_escolar.repository;

import com.weg.gestao_escolar.connection.Conexao;
import com.weg.gestao_escolar.model.Aula;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class AulaRepository {

    public List<Aula> listarAulas() throws SQLException {
        String selectQuery = """
                SELECT id,
                       turma_id,
                       data_hora,
                       assunto
                FROM aula
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(selectQuery)) {

            ResultSet rs = ps.executeQuery();
            List<Aula> aulas = new ArrayList<>();

            while (rs.next()) {
                Aula aula = new Aula(
                        rs.getInt("id"),
                        rs.getInt("turma_id"),
                        rs.getTimestamp("data_hora").toLocalDateTime(),
                        rs.getString("assunto")
                );
                aulas.add(aula);
            }
            return aulas;
        }
    }

    public Aula listarAulaPorId(int id) throws SQLException {
        String selectQuery = """
                SELECT id,
                       turma_id,
                       data_hora,
                       assunto
                FROM aula
                WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(selectQuery)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Aula(
                        rs.getInt("id"),
                        rs.getInt("turma_id"),
                        rs.getTimestamp("data_hora").toLocalDateTime(),
                        rs.getString("assunto")
                );
            } else {
                throw new RuntimeException("Aula não encontrada com o ID: " + id);
            }
        }
    }

    public Aula salvarAula(Aula aula) throws SQLException {
        String insertQuery = """
                INSERT INTO aula (turma_id, data_hora, assunto)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, aula.getTurmaId());
            stmt.setTimestamp(2, Timestamp.valueOf(aula.getDataHora()));
            stmt.setString(3, aula.getAssunto());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Falha ao salvar a aula.");
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    aula.setId(rs.getInt(1));
                } else {
                    throw new RuntimeException("Falha ao obter o ID da aula salva.");
                }
            }
        }
        return aula;
    }

    public Aula atualizarAula(Aula aula) throws SQLException {
        String updateQuery = """
                UPDATE aula
                SET turma_id = ?, data_hora = ?, assunto = ?
                WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setInt(1, aula.getTurmaId());
            stmt.setTimestamp(2, Timestamp.valueOf(aula.getDataHora()));
            stmt.setString(3, aula.getAssunto());
            stmt.setInt(4, aula.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Falha ao atualizar a aula com ID: " + aula.getId());
            }
        }
        return aula;
    }

    public void deletarAula(int id) throws SQLException {
        String deleteQuery = "DELETE FROM aula WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Falha ao deletar a aula com ID: " + id);
            }
        }
    }

    public List<Aula> listarAulasPorTurmaId(int turmaId) throws SQLException {
        String selectQuery = """
                SELECT id,
                       turma_id,
                       data_hora,
                       assunto
                FROM aula
                WHERE turma_id = ?
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(selectQuery)) {
            stmt.setInt(1, turmaId);
            ResultSet rs = stmt.executeQuery();

            List<Aula> aulas = new ArrayList<>();
            while (rs.next()) {
                Aula aula = new Aula(
                        rs.getInt("id"),
                        rs.getInt("turma_id"),
                        rs.getTimestamp("data_hora").toLocalDateTime(),
                        rs.getString("assunto")
                );
                aulas.add(aula);
            }
            return aulas;
        }
    }
}

