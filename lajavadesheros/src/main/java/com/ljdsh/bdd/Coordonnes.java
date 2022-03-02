package com.ljdsh.bdd;

import com.ljdsh.beans.Position;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Coordonnes {
    /**
     * @return la position des supers pour l'afficher sur la carte
     */
    public List<Position> recuperePositions() {
        List<Position> positions = new ArrayList<Position>();
        //chargement du driver
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e){
        }
        //Connexion a la base
        Connection connection = null;
        Statement statement = null;
        ResultSet resultat = null;
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ljdsh", "root", "");
            //here sonoo is database name, root is username and password
            statement = con.createStatement();
           //recupere les coordonnées depuis la bdd
            resultat = statement.executeQuery("select distinct lattitude, longitude from t_super") ;
            while (resultat.next()) {
                String lattitude = resultat.getString("lattitude");
                String longitude = resultat.getString("longitude");
                //ajout des coordonées dans Position
                Position position = new Position();
                position.setLattitude(lattitude);
                position.setLongitude(longitude);
                 positions.add(position);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }finally {
            try{
                //ferme ; resultat;statement; connection
                if(resultat != null)
                    resultat.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException ignore) {
            }
        }
        return positions;
    }
}
