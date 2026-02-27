package com.weg.gestao_escolar.repository;

import com.weg.gestao_escolar.connection.Conexao;
import com.weg.gestao_escolar.model.Professor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProfessorRepository {

    public List<Professor> listarProfessores() throws SQLException {
        String selectQuery = """
                SELECT id,
                       nome,
                       email,
                       disciplina
                FROM professor
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(selectQuery)){

                ResultSet rs = ps.executeQuery();
                List<Professor> professores = new ArrayList<>();

                while (rs.next()) {
                    Professor professor = new Professor(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("disciplina")
                    );
                    professores.add(professor);
                }
                return professores;
            }
        }


    public Professor listarProfessorPorId(int id) throws SQLException {
        String selectQuery = """
                SELECT id,
                       nome,
                       email,
                       disciplina
                FROM professor
                WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(selectQuery)){
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return new Professor(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("disciplina")
                    );
                } else {
                    throw new RuntimeException("Professor não encontrado com o ID: " + id);
                }
            }
        }


    public Professor salvarProfessor(Professor professor) throws SQLException {
        String insertQuery = """
                INSERT INTO professor (nome, email, disciplina)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, professor.getNome());
                stmt.setString(2, professor.getEmail());
                stmt.setString(3, professor.getDisciplina());

                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas == 0) {
                    throw new RuntimeException("Falha ao salvar o professor.");
                }

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        professor.setId(rs.getInt(1));
                    } else {
                        throw new RuntimeException("Falha ao obter o ID do professor salvo.");
                    }
                }
            }
        return professor;
    }


    public Professor atualizarProfessor(Professor professor) throws SQLException {
        String updateQuery = """
                UPDATE professor
                SET nome = ?, email = ?, disciplina = ?
                WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                stmt.setString(1, professor.getNome());
                stmt.setString(2, professor.getEmail());
                stmt.setString(3, professor.getDisciplina());
                stmt.setInt(4, professor.getId());

                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas == 0) {
                    throw new RuntimeException("Falha ao atualizar o professor com ID: " + professor.getId());
                }
            }
            return professor;
        }

    public void deletarProfessor(int id) throws SQLException {
        String deleteQuery = "DELETE FROM professor WHERE id = ?";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
                stmt.setInt(1, id);

                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas == 0) {
                    throw new RuntimeException("Falha ao deletar o professor com ID: " + id);
                }
            }
        }

}

