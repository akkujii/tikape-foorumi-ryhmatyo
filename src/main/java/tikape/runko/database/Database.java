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
        lista.add("DROP TABLE Viestiketju;"
                + "CREATE TABLE Viestiketju (ketju_id integer PRIMARY KEY, aihe_id integer, otsikko varchar(40), FOREIGN KEY(aihe_id) REFERENCES Aihealue(aihe_id));"
                + "INSERT INTO Viestiketju (aihe_id, otsikko) VALUES (1, 'Koris on hauskempaa kuin lätkä');"
                + "INSERT INTO Viestiketju (aihe_id, otsikko) VALUES (1, 'Kalastus olympialajiksi');"
                + "INSERT INTO Viestiketju (aihe_id, otsikko) VALUES (2, 'Räppi on paskaa');"
                + "INSERT INTO Viestiketju (aihe_id, otsikko) VALUES (3, 'Nakkiperunat on parasta');"
                + "INSERT INTO Viestiketju (aihe_id, otsikko) VALUES (4, 'Vrum vrum');");
        lista.add("DROP TABLE Viesti;"
                + "CREATE TABLE Viesti (viesti_id integer PRIMARY KEY, lahettaja varchar(20), lahetetty timestamp NOT NULL DEFAULT(datetime('now', '+2 hour')), viestiketju_id integer, sisalto varchar(32768), FOREIGN KEY(viestiketju_id) REFERENCES Viestiketju(ketju_id));"
                + "INSERT INTO Viesti (lahettaja, viestiketju_id, sisalto) VALUES (Matti, 1, 'Niin on');"
                + "INSERT INTO Viesti (lahettaja, viestiketju_id, sisalto) VALUES (Pekka, 1, 'Eipäs ole');"
                + "INSERT INTO Viesti (lahettaja, viestiketju_id, sisalto) VALUES (Pertti, 2, 'JOOO');");
        return lista;
    }
}
