package com.weg.gestao_escolar.repository;

import com.weg.gestao_escolar.connection.Conexao;
import com.weg.gestao_escolar.model.Nota;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotaRepository {

    public List<Nota> listarNotas() throws SQLException {
        String selectQuery = """
                SELECT id,
                       aluno_id,
                       disciplina_id,
                       valor
                FROM nota
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(selectQuery)) {

            ResultSet rs = ps.executeQuery();
            List<Nota> notas = new ArrayList<>();

            while (rs.next()) {
                Nota nota = new Nota(
                        rs.getInt("id"),
                        rs.getInt("aluno_id"),
                        rs.getInt("disciplina_id"),
                        rs.getDouble("valor")
                );
                notas.add(nota);
            }
            return notas;
        }
    }

    public Nota listarNotaPorId(int id) throws SQLException {
        String selectQuery = """
                SELECT id,
                       aluno_id,
                       disciplina_id,
                       valor
                FROM nota
                WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(selectQuery)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Nota(
                        rs.getInt("id"),
                        rs.getInt("aluno_id"),
                        rs.getInt("disciplina_id"),
                        rs.getDouble("valor")
                );
            } else {
                throw new RuntimeException("Nota não encontrada com o ID: " + id);
            }
        }
    }

    public Nota salvarNota(Nota nota) throws SQLException {
        String insertQuery = """
                INSERT INTO nota (aluno_id, disciplina_id, valor)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, nota.getAlunoId());
            stmt.setInt(2, nota.getDisciplinaId());
            stmt.setDouble(3, nota.getValor());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Falha ao salvar a nota.");
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    nota.setId(rs.getInt(1));
                } else {
                    throw new RuntimeException("Falha ao obter o ID da nota salva.");
                }
            }
        }
        return nota;
    }

    public Nota atualizarNota(Nota nota) throws SQLException {
        String updateQuery = """
                UPDATE nota
                SET aluno_id = ?, disciplina_id = ?, valor = ?
                WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setInt(1, nota.getAlunoId());
            stmt.setInt(2, nota.getDisciplinaId());
            stmt.setDouble(3, nota.getValor());
            stmt.setInt(4, nota.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Falha ao atualizar a nota com ID: " + nota.getId());
            }
        }
        return nota;
    }

    public void deletarNota(int id) throws SQLException {
        String deleteQuery = "DELETE FROM nota WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Falha ao deletar a nota com ID: " + id);
            }
        }
    }

    public List<Nota> listarNotasPorAlunoId(int alunoId) throws SQLException {
        String selectQuery = """
                SELECT id,
                       aluno_id,
                       disciplina_id,
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
                        rs.getInt("disciplina_id"),
                        rs.getDouble("valor")
                );
                notas.add(nota);
            }
            return notas;
        }
    }
}

