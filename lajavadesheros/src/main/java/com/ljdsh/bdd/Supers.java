package com.ljdsh.bdd;
import com.ljdsh.beans.Super;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class Supers {

    public Supers(Object lon, Object lat, String incident_en_question) {
        recupereSupers(lon,lat,incident_en_question);
    }

    /**
     * @param lon
     * @param lat
     * @param incident_en_question
     * @return la fiche des supers qui sont dans le périmetre
     */
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
            // requete de select à 50 km de distance
            System.out.println(
                    "SELECT DISTINCT " +
                            "pseudo,telephone,adresse, lattitude, longitude " +
                            "FROM t_super " +
                            "WHERE (ACOS(SIN(RADIANS(lattitude))SIN(RADIANS('"+lat+"'))+COS(RADIANS(lattitude))*COS(RADIANS('"+lat+"'))COS(RADIANS(longitude-'"+lon+"')))*6371) < 50"+

                            "&& "+incident_en_question+"='on'"
            );
            resultat = statement.executeQuery("" +
                    "SELECT DISTINCT " +
                    "pseudo,telephone,adresse, lattitude, longitude " +
                    "FROM t_super " +
                    "WHERE (ACOS(SIN(RADIANS(lattitude))*SIN(RADIANS('"+lat+"'))+COS(RADIANS(lattitude))*COS(RADIANS('"+lat+"'))*COS(RADIANS(longitude-'"+lon+"')))*6371) < 50"+

                    " and "+incident_en_question+"='on'") ;

            while (resultat.next()) {
                //récupère les coordonées qui provienne de la requête
                String pseudo=resultat.getString("pseudo");
                String ville=resultat.getString("adresse");
                String telephone=resultat.getString("telephone");
                String lattitude = resultat.getString("lattitude");
                String longitude = resultat.getString("longitude");
                Super super_h = new Super();
                //ajout des coordonées selectionné dans Super
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
                // fermeture de resultat; statement; connection
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