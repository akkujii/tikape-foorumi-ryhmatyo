/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Viesti;

public class ViestiDao implements Dao<Viesti, Integer> {

    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE viesti_id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        
        Integer viesti_id = rs.getInt("viesti_id");
        Integer ketju_id = rs.getInt("ketju_id");
        String lahettaja = rs.getString("lahettaja");
        String lahetetty = rs.getString("lahetetty");
        String sisalto = rs.getString("sisalto");

        Viesti o = new Viesti(viesti_id, ketju_id, lahettaja, lahetetty, sisalto);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }
    
    public int createNew(Integer ketju_id, String lahettaja, String sisalto) throws SQLException {
        Connection connection = database.getConnection();
        Statement stmt = connection.createStatement();
        int changes = stmt.executeUpdate("INSERT INTO Viesti (lahettaja, viestiketju_id, sisalto) VALUES ('" + lahettaja + "'," +  ketju_id + ",'" + sisalto + "');");
        
        stmt.close();
        connection.close();
        
        System.out.println("[ViestiDao] palautettiin " + changes);
        return changes;
    }

    
    
    @Override
    public List<Viesti> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer viesti_id = rs.getInt("viesti_id");
            Integer ketju_id = rs.getInt("ketju_id");
            String lahettaja = rs.getString("lahettaja");
            String lahetetty = rs.getString("lahetetty");
            String sisalto = rs.getString("sisalto");

            viestit.add(new Viesti(viesti_id, ketju_id, lahettaja, lahetetty, sisalto));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }
    
    public List<Viesti> findByViestiketju(int id) throws SQLException {
        System.out.println("[findByViestiketju] kutsuttu");
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE viestiketju_id = ?");
        stmt.setObject(1, id);

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viesti = new ArrayList<>();
        while (rs.next()) {
            
            Integer viesti_id = rs.getInt("viesti_id");
            Integer viestiketju_id = rs.getInt("viestiketju_id");
            String lahettaja = rs.getString("lahettaja");
            String lahetetty = rs.getString("lahetetty");
            String sisalto = rs.getString("sisalto");
            

            viesti.add(new Viesti(viesti_id, viestiketju_id, lahettaja, lahetetty, sisalto));
        }

        rs.close();
        stmt.close();
        connection.close();
        System.out.println("[findByViestiketju] Viestejä löytyi" + viesti.size());
        return viesti;
    }


    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}
