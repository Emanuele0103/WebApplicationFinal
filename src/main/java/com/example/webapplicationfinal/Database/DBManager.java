package com.example.webapplicationfinal.Database;


import com.example.webapplicationfinal.Database.dao.AnnunciDAO;
import com.example.webapplicationfinal.Database.dao.UtenteDAO;
import com.example.webapplicationfinal.Database.dao.jdbc.AnnunciDAOJDBC;
import com.example.webapplicationfinal.Database.dao.jdbc.UtenteDAOJDBC;

public class DBManager {
    private static DBManager instance = null;
    static DBSource dataSource;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            //questi vanno messi in file di configurazione!!!
            dataSource=new DBSource("jdbc:postgresql://localhost:5432/postgres","postgres","admin");
        }
        catch (Exception e) {
            System.err.println("PostgresDAOFactory.class: failed to load MySQL JDBC driver\n"+e);
            e.printStackTrace();
        }
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
    }

    public static DBSource getDataSource() {
        return dataSource;
    }

    public AnnunciDAO annunciDAO() {
        return new AnnunciDAOJDBC(dataSource);
    }

    public UtenteDAO utenteDAO() {
        return new UtenteDAOJDBC(dataSource);
    }


}
