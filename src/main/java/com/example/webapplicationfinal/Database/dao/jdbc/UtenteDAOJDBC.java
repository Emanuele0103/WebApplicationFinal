package com.example.webapplicationfinal.Database.dao.jdbc;

import com.example.webapplicationfinal.Database.DBSource;
import com.example.webapplicationfinal.Model.Utente;
import com.example.webapplicationfinal.Database.dao.UtenteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAOJDBC implements UtenteDAO{
    private DBSource dbSource;

    public UtenteDAOJDBC(DBSource dbSource) {
        this.dbSource = dbSource;
    }

    @Override
    public void save(Utente utente) {
        try (Connection conn = dbSource.getConnection()) {
            String query = "INSERT INTO utente (nome, cognome, email, password, tipo) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, utente.getNome());
            st.setString(2, utente.getCognome());
            st.setString(3, utente.getEmail());
            st.setString(4, utente.getPassword());
            st.setString(5, utente.getTipo());
            st.executeUpdate();
            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                utente.setID_Utente(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Utente findByPrimaryKey(Long id) {
        Utente utente = null;
        try (Connection conn = dbSource.getConnection()) {
            String query = "SELECT * FROM utente WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                utente = new Utente();
                utente.setID_Utente(rs.getInt("id"));
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
            String query = "SELECT * FROM utente";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Utente utente = new Utente();
                utente.setID_Utente(rs.getInt("id"));
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
            String query = "UPDATE utente SET nome = ?, cognome = ?, email = ?, password = ?, tipo = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, utente.getNome());
            st.setString(2, utente.getCognome());
            st.setString(3, utente.getEmail());
            st.setString(4, utente.getPassword());
            st.setString(5, utente.getTipo());
            st.setLong(6, utente.getID_Utente());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Utente utente) {
        try (Connection conn = dbSource.getConnection()) {
            String query = "DELETE FROM utente WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, utente.getID_Utente());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Utente> findByNome(String nome) {
        return List.of();
    }
}
