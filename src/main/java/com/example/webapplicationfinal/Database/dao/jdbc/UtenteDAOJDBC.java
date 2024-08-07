package com.example.webapplicationfinal.Database.dao.jdbc;

import com.example.webapplicationfinal.Database.DBSource;
import com.example.webapplicationfinal.Model.Utente;
import com.example.webapplicationfinal.Database.dao.UtenteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UtenteDAOJDBC implements UtenteDAO {
    private final DBSource dbSource;

    @Autowired
    public UtenteDAOJDBC(DBSource dbSource) {
        this.dbSource = dbSource;
    }

    @Override
    public void save(Utente utente) {
        try (Connection conn = dbSource.getConnection()) {
            String query = "INSERT INTO utenti (nome, cognome, email, password, tipo) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, utente.getNome());
            st.setString(2, utente.getCognome());
            st.setString(3, utente.getEmail());
            st.setString(4, utente.getPassword()); // Salva la password in chiaro (NON raccomandato)
            st.setString(5, utente.getTipo());
            st.executeUpdate();
            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                utente.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Utente findByPrimaryKey(Long id) {
        Utente utente = null;
        try (Connection conn = dbSource.getConnection()) {
            String query = "SELECT * FROM utenti WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                utente = new Utente();
                utente.setId(rs.getLong("id"));
                utente.setNome(rs.getString("nome"));
                utente.setCognome(rs.getString("cognome"));
                utente.setEmail(rs.getString("email"));
                utente.setPassword(rs.getString("password"));
                utente.setTipo(rs.getString("tipo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utente;
    }

    @Override
    public List<Utente> findAll() {
        List<Utente> utenti = new ArrayList<>();
        try (Connection conn = dbSource.getConnection()) {
            String query = "SELECT * FROM utenti";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Utente utente = new Utente();
                utente.setId(rs.getLong("id"));
                utente.setNome(rs.getString("nome"));
                utente.setCognome(rs.getString("cognome"));
                utente.setEmail(rs.getString("email"));
                utente.setPassword(rs.getString("password"));
                utente.setTipo(rs.getString("tipo"));
                utenti.add(utente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utenti;
    }

    @Override
    public void update(Utente utente) {
        try (Connection conn = dbSource.getConnection()) {
            String query = "UPDATE utenti SET nome = ?, cognome = ?, email = ?, password = ?, tipo = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, utente.getNome());
            st.setString(2, utente.getCognome());
            st.setString(3, utente.getEmail());
            st.setString(4, utente.getPassword()); // Salva la password in chiaro (NON raccomandato)
            st.setString(5, utente.getTipo());
            st.setLong(6, utente.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Utente utente) {
        try (Connection conn = dbSource.getConnection()) {
            String query = "DELETE FROM utenti WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, utente.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Utente> findByNome(String nome) {
        return List.of(); // Implementazione di default per questo esempio
    }

    @Override
    public Utente findByEmail(String email) {
        Utente utente = null;
        try (Connection conn = dbSource.getConnection()) {
            String query = "SELECT * FROM utenti WHERE email = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                utente = new Utente();
                utente.setId(rs.getLong("id"));
                utente.setNome(rs.getString("nome"));
                utente.setCognome(rs.getString("cognome"));
                utente.setEmail(rs.getString("email"));
                utente.setPassword(rs.getString("password"));
                utente.setTipo(rs.getString("tipo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utente;
    }

}
