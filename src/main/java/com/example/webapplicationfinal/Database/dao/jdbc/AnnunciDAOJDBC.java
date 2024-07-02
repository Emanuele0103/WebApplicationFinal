package com.example.webapplicationfinal.Database.dao.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.webapplicationfinal.Database.DBSource;
import com.example.webapplicationfinal.Model.Annuncio;
import com.example.webapplicationfinal.Database.dao.AnnunciDAO;
import com.example.webapplicationfinal.Model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AnnunciDAOJDBC implements AnnunciDAO {
    private DBSource dbSource;

    @Autowired
    public AnnunciDAOJDBC(DBSource dbSource) {
        this.dbSource = dbSource;
    }

    @Override
    public void save(Annuncio annuncio) {
        try (Connection conn = dbSource.getConnection()) {
            String query = "INSERT INTO annuncio (titolo, tipo_di_immobile, descrizione, prezzo, utente_id, images, position) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, annuncio.getTitolo());
            st.setString(2, annuncio.getTipoDiImmobile());
            st.setString(3, annuncio.getDescrizione());
            st.setInt(4, annuncio.getPrezzo());
            st.setLong(5, annuncio.getUtenteId());
            Array imagesArray = conn.createArrayOf("TEXT", annuncio.getImages().toArray());
            st.setArray(6, imagesArray);
            st.setString(7, annuncio.getPosition());
            st.executeUpdate();
            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                annuncio.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Annuncio findByPrimaryKey(Long id) {
        Annuncio annuncio = null;
        try (Connection conn = dbSource.getConnection()) {
            String query = "SELECT * FROM annuncio WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                annuncio = new Annuncio();
                annuncio.setId(rs.getLong("id"));
                annuncio.setTitolo(rs.getString("titolo"));
                annuncio.setTipoDiImmobile(rs.getString("tipo_di_immobile"));
                annuncio.setDescrizione(rs.getString("descrizione"));
                annuncio.setPrezzo(rs.getInt("prezzo"));
                annuncio.setUtenteId(rs.getLong("utente_id"));
                Array imagesArray = rs.getArray("images");
                if (imagesArray != null) {
                    String[] images = (String[]) imagesArray.getArray();
                    annuncio.setImages(new ArrayList<>(List.of(images)));
                }
                annuncio.setPosition(rs.getString("position"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return annuncio;
    }

    @Override
    public List<Annuncio> findAll() {
        List<Annuncio> annunci = new ArrayList<>();
        try (Connection conn = dbSource.getConnection()) {
            String query = "SELECT * FROM annuncio";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Annuncio annuncio = new Annuncio();
                annuncio.setId(rs.getLong("id"));
                annuncio.setTitolo(rs.getString("titolo"));
                annuncio.setTipoDiImmobile(rs.getString("tipo_di_immobile"));
                annuncio.setDescrizione(rs.getString("descrizione"));
                annuncio.setPrezzo(rs.getInt("prezzo"));
                annuncio.setUtenteId(rs.getLong("utente_id"));
                Array imagesArray = rs.getArray("images");
                if (imagesArray != null) {
                    String[] images = (String[]) imagesArray.getArray();
                    annuncio.setImages(new ArrayList<>(List.of(images)));
                }
                annuncio.setPosition(rs.getString("position"));
                annunci.add(annuncio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return annunci;
    }

    @Override
    public void update(Annuncio annuncio) {
        try (Connection conn = dbSource.getConnection()) {
            String query = "UPDATE annuncio SET titolo = ?, tipo_di_immobile = ?, descrizione = ?, prezzo = ?, utente_id = ?, images = ?, position = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, annuncio.getTitolo());
            st.setString(2, annuncio.getTipoDiImmobile());
            st.setString(3, annuncio.getDescrizione());
            st.setInt(4, annuncio.getPrezzo());
            st.setLong(5, annuncio.getUtenteId());
            Array imagesArray = conn.createArrayOf("TEXT", annuncio.getImages().toArray());
            st.setArray(6, imagesArray);
            st.setString(7, annuncio.getPosition());
            st.setLong(8, annuncio.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Annuncio annuncio) {
        try (Connection conn = dbSource.getConnection()) {
            String query = "DELETE FROM annuncio WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, annuncio.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Annuncio> findByUtenteId(Long utenteId) {
        List<Annuncio> annunci = new ArrayList<>();
        try (Connection conn = dbSource.getConnection()) {
            String query = "SELECT * FROM annuncio WHERE utente_id = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, utenteId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Annuncio annuncio = new Annuncio();
                annuncio.setId(rs.getLong("id"));
                annuncio.setTitolo(rs.getString("titolo"));
                annuncio.setTipoDiImmobile(rs.getString("tipo_di_immobile"));
                annuncio.setDescrizione(rs.getString("descrizione"));
                annuncio.setPrezzo(rs.getInt("prezzo"));
                annuncio.setUtenteId(rs.getLong("utente_id"));
                Array imagesArray = rs.getArray("images");
                if (imagesArray != null) {
                    String[] images = (String[]) imagesArray.getArray();
                    annuncio.setImages(new ArrayList<>(List.of(images)));
                }
                annuncio.setPosition(rs.getString("position"));
                annunci.add(annuncio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return annunci;
    }

    // Aggiunta del nuovo metodo
    public Utente findUtenteByAnnuncioId(Long annuncioId) {
        Utente utente = null;
        try (Connection conn = dbSource.getConnection()) {
            String query = "SELECT u.id, u.nome, u.cognome, u.email FROM utenti u JOIN annuncio a ON u.id = a.utente_id WHERE a.id = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, annuncioId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                utente = new Utente();
                utente.setId(rs.getLong("id"));
                utente.setNome(rs.getString("nome"));
                utente.setCognome(rs.getString("cognome"));
                utente.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utente;
    }
}
