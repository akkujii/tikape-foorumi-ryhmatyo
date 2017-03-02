/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Aihealue;

public class AihealueDao implements Dao<Aihealue, Integer> {

    private Database database;

    public AihealueDao(Database database) {
        this.database = database;
    }

    @Override
    public Aihealue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aihealue WHERE aihe_id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer aihe_id = rs.getInt("aihe_id");
        String nimi = rs.getString("nimi");

        Aihealue o = new Aihealue(aihe_id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Aihealue> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aihealue");

        ResultSet rs = stmt.executeQuery();
        List<Aihealue> aihealueet = new ArrayList<>();
        while (rs.next()) {
            Integer aihe_id = rs.getInt("aihe_id");
            String nimi = rs.getString("nimi");

            aihealueet.add(new Aihealue(aihe_id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return aihealueet;
    }
    
    public int createNew(String otsikko) throws SQLException {
        Connection connection = database.getConnection();
        Statement stmt = connection.createStatement();
        int changes = stmt.executeUpdate("INSERT INTO Aihealue (nimi) VALUES ('" +  otsikko + "');");
        
        stmt.close();
        connection.close();
        
        System.out.println("[AihealueDao] palautettiin " + changes);
        return changes;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}
