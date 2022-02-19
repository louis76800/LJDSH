package com.ljdsh.bdd;
import com.ljdsh.beans.Super;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class Supers {
    public Supers(Object lon, Object lat, String incident_en_question) {

        recupereSupers(lon,lat,incident_en_question);
    }

    public List<Super> recupereSupers(Object lon, Object lat, String incident_en_question) {
        List<Super> supers = new ArrayList<Super>();
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

            resultat = statement.executeQuery("" +
                    "SELECT DISTINCT " +
                    "pseudo,telephone,adresse, lattitude, longitude " +
                    "FROM t_super " +
                    "WHERE lattitude >('"+lat+"'-0.7) && lattitude <("+lat+"+0.7) " +
                    "&& longitude >("+lon+"-0.5) " +
                    "&& longitude <("+lon+"+0.5) " +
                    "&& "+incident_en_question+"='on'") ;
            System.out.println( "SELECT DISTINCT " +
                    "pseudo,telephone,adresse, lattitude, longitude " +
                    "FROM t_super " +
                    "WHERE lattitude >('"+lat+"'-0.7) && lattitude <("+lat+"+0.7) " +
                    "&& longitude >("+lon+"-0.5) " +
                    "&& longitude <("+lon+"+0.5) " +
                    "&& "+incident_en_question+"='on'");
            while (resultat.next()) {
                String pseudo=resultat.getString("pseudo");
                String ville=resultat.getString("adresse");
                String telephone=resultat.getString("telephone");
                String lattitude = resultat.getString("lattitude");
                String longitude = resultat.getString("longitude");
                Super super_h = new Super();
                super_h.setLattitude(lattitude);
                super_h.setLongitude(longitude);
                super_h.setPseudo(pseudo);
                super_h.setVille(ville);
                super_h.setTelephone(telephone);

                supers.add(super_h);

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
        return supers;
    }



}

