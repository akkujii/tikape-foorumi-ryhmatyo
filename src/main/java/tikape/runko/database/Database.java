package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        /*lista.add("PRAGMA foreign_keys = ON;");
        lista.add("DROP TABLE Aihealue;");
        lista.add("CREATE TABLE Aihealue (id integer PRIMARY KEY, nimi varchar(20));");
        lista.add("INSERT INTO Aihealue (nimi) VALUES ('Urheilu');");
        lista.add("INSERT INTO Aihealue (nimi) VALUES ('Musiikki');");
        lista.add("INSERT INTO Aihealue (nimi) VALUES ('Ruuanlaitto');");
        lista.add("INSERT INTO Aihealue (nimi) VALUES ('Autot');");*/
        
        lista.add("PRAGMA foreign_keys = ON; DROP TABLE Aihealue; "
                + "CREATE TABLE Aihealue (aihe_id integer PRIMARY KEY, nimi varchar(20));"
                + "INSERT INTO Aihealue (nimi) VALUES ('Urheilu');"
                + "INSERT INTO Aihealue (nimi) VALUES ('Musiikki');"
                + "INSERT INTO Aihealue (nimi) VALUES ('Ruuanlaitto');"
                + "INSERT INTO Aihealue (nimi) VALUES ('Autot');");
        return lista;
    }
}
