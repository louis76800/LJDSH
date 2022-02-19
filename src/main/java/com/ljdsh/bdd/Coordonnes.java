package com.ljdsh.bdd;

import com.ljdsh.beans.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Coordonnes {
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

            resultat = statement.executeQuery("select distinct lattitude, longitude from t_super") ;

            while (resultat.next()) {
                String lattitude = resultat.getString("lattitude");
                String longitude = resultat.getString("longitude");
                Position position = new Position();
                position.setLattitude(lattitude);
                position.setLongitude(longitude);

                 positions.add(position);

            }
        } catch (SQLException e) {
            System.out.println(e);
        }finally {
            try{
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
