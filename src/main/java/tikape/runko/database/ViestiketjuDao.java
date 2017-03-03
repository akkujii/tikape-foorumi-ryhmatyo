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
import tikape.runko.domain.Viestiketju;

public class ViestiketjuDao implements Dao<Viestiketju, Integer> {

    private Database database;

    public ViestiketjuDao(Database database) {
        this.database = database;
    }

    @Override
    public Viestiketju findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju WHERE ketju_id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer ketju_id = rs.getInt("ketju_id");
        Integer aihe_id = rs.getInt("aihe_id");
        String otsikko = rs.getString("otsikko");

        Viestiketju o = new Viestiketju(ketju_id, aihe_id, otsikko);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Viestiketju> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju");

        ResultSet rs = stmt.executeQuery();
        List<Viestiketju> viestiketjut = new ArrayList<>();
        while (rs.next()) {
            Integer ketju_id = rs.getInt("ketju_id");
            Integer aihe_id = rs.getInt("aihe_id");
            String otsikko = rs.getString("otsikko");

            viestiketjut.add(new Viestiketju(ketju_id, aihe_id, otsikko));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestiketjut;
    }
    
    public List<Viestiketju> findByAihealue(int id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju WHERE aihe_id = ? ORDER BY Viestiketju.ketju_id DESC LIMIT 10");
        stmt.setObject(1, id);

        ResultSet rs = stmt.executeQuery();
        List<Viestiketju> viestiketjut = new ArrayList<>();
        while (rs.next()) {
            Integer ketju_id = rs.getInt("ketju_id");
            Integer aihe_id = rs.getInt("aihe_id");
            String otsikko = rs.getString("otsikko");

            viestiketjut.add(new Viestiketju(ketju_id, aihe_id, otsikko));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestiketjut;
    }
    
    public int createNew(Integer aihe_id, String otsikko) throws SQLException {
        Connection connection = database.getConnection();
        Statement stmt = connection.createStatement();
        int changes = stmt.executeUpdate("INSERT INTO Viestiketju (aihe_id, otsikko) VALUES (" + aihe_id + ", '" +  otsikko + "');");
        
        stmt.close();
        connection.close();
        
        System.out.println("[ViestiketjuDao] palautettiin " + changes);
        return changes;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}
