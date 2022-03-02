package com.demo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.Scanner;
@WebServlet("/Inscription")
public class Inscription extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public Inscription() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/inscription.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Récupération des infos du formulaire
        String pseudo = request.getParameter("pseudo");
        request.setAttribute("pseudo", pseudo);

        String Incendie = request.getParameter("Incendie");
        request.setAttribute("Incendie", Incendie);

        String Accident_routier = request.getParameter("Accident_routier");
        request.setAttribute("Accident_routier", Accident_routier);

        String Accident_fluviale = request.getParameter("Accident_fluviale");
        request.setAttribute("Accident_fluviale", Accident_fluviale);

        String Accident_aerien = request.getParameter("Accident_aerien");
        request.setAttribute("Accident_aerien", Accident_aerien);

        String Eboulement = request.getParameter("Eboulement");
        request.setAttribute("Eboulement", Eboulement);

        String Invasion_serpent = request.getParameter("Invasion_serpent");
        request.setAttribute("Invasion_serpent", Invasion_serpent);

        String Fuite_gaz = request.getParameter("Fuite_gaz");
        request.setAttribute("Fuite_gaz", Fuite_gaz);

        String Manifestation = request.getParameter("Manifestation");
        request.setAttribute("Manifestation", Manifestation);

        String Braquage = request.getParameter("Braquage");
        request.setAttribute("Braquage", Braquage);

        String Evasion_prisonnier = request.getParameter("Evasion_prisonnier");
        request.setAttribute("Evasion_prisonnier", Evasion_prisonnier);

        String adresse = request.getParameter("adresse");
        request.setAttribute("adresse", adresse);

        String tel = request.getParameter("tel");
        request.setAttribute("tel", tel);

        try {
            // Vérification que le pseuod n'existe pas déjà
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ljdsh", "root", "");
            //here sonoo is database name, root is username and password
            Statement statement = con.createStatement();
            ResultSet resultat = statement.executeQuery("" +
                    "SELECT " +
                    "count(*) as pseudo  " +
                    "FROM t_super " +
                    "WHERE pseudo ='" + pseudo + "' ;");
            int pseudoverif = 0;
            while (resultat.next()) {
                pseudoverif = resultat.getInt("pseudo");
            }
            //si le pseudo n'existe pas encore :
            if (pseudoverif == 0) {
                if (pseudo != "" && adresse != "" && tel != "") {
                    int i = 0;
                    if (Incendie != null && Incendie.equals("on")) {
                        i++;
                    }
                    if (Accident_routier != null && Incendie.equals("on")) {
                        i++;
                    }
                    if (Accident_fluviale != null && Accident_fluviale.equals("on")) {
                        i++;
                    }
                    if (Accident_aerien != null && Accident_aerien.equals("on")) {
                        i++;
                    }
                    if (Eboulement != null && Eboulement.equals("on")) {
                        i++;
                    }
                    if (Invasion_serpent != null && Invasion_serpent.equals("on")) {
                        i++;
                    }
                    if (Fuite_gaz != null && Fuite_gaz.equals("on")) {
                        i++;
                    }
                    if (Manifestation != null && Manifestation.equals("on")) {
                        i++;
                    }
                    if (Braquage != null && Braquage.equals("on")) {
                        i++;
                    }
                    if (Evasion_prisonnier != null && Evasion_prisonnier.equals("on")) {
                        i++;
                    }
                    //Vérification du nombre d'incident choisi
                    if ((i >= 1) && (i <= 4)) {
                        String message = (pseudo + " " + Incendie + " " + Accident_routier + " " + Accident_fluviale + " " + Accident_aerien + " " + Eboulement + " " + Invasion_serpent + " " + Manifestation + " " + Braquage + " " +
                                Evasion_prisonnier + " " + adresse + " " + tel);
                        // if si != evasion braquage ...
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                        } catch (ClassNotFoundException e) {
                        }
                        try {
                           String adresse1 = adresse.replace("'","%27");
                            adresse1 = adresse1.replace(" ","%20");
                            URL url = new URL("http://api.openweathermap.org/geo/1.0/direct?q='" + adresse1 + "'&appid=085526ba50eb67b600534682f09dc8dc");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.connect();
                            int responseCode = conn.getResponseCode();
                            JSONObject cityData;
                            if (responseCode != 200) {
                                throw new RuntimeException("HttpResponseCode" + responseCode);
                            } else {
                                StringBuilder informationString = new StringBuilder();
                                Scanner scanner = new Scanner(url.openStream());
                                while (scanner.hasNext()) {
                                    informationString.append(scanner.nextLine());
                                }
                                scanner.close();
                                JSONParser parse = new JSONParser();
                                JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));
                                cityData = (JSONObject) dataObject.get(0);
                            }
                            //insertion du super en bdd avec sa ville est les coordonnées qui lui sont accordées
                            String request_update = "INSERT INTO t_super (pseudo,adresse, telephone, incendie, accident_r, accident_f, accident_a, eboulement, invasion_s, fuite_g, manifestation, braquage, evasion_p , longitude,lattitude)" +
                                    " VALUES ('" + pseudo + "','" + adresse + "','" + tel + "' ,'" + Incendie + "','" + Accident_routier + "','" + Accident_fluviale + "','" + Accident_aerien + "','" + Eboulement + "','" + Invasion_serpent + "','" + Manifestation + "','" + Invasion_serpent + "','" + Braquage + "','" + Evasion_prisonnier + "','" + cityData.get("lon") + "','" + cityData.get("lat") + "')";
                            Class.forName("com.mysql.jdbc.Driver");
                            //here sonoo is database name, root is username and password
                            Statement stmt = con.createStatement();
                            int rs = stmt.executeUpdate(request_update);
                            int  messageretour = 0;
                            request.setAttribute("message", messageretour);
                            this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/inscription.jsp").forward(request, response);
                        } catch (Exception e) {
                            //la ville n'est pas connue
                            int messageretour = 4;
                            request.setAttribute("message", messageretour);
                            this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/inscription.jsp").forward(request, response);
                        }
                    } else {
                        //il n'y pas  ou trop de case(s) cochée(s)
                        int messageretour = 3;
                        request.setAttribute("message", messageretour);
                        this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/inscription.jsp").forward(request, response);
                    }
                } else {
                    //Le pseudo ou le lieu d'habitation ou le numéros de téléphone n'est pas remplis
                    int messageretour = 1;
                    request.setAttribute("message", messageretour);
                    this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/inscription.jsp").forward(request, response);
                }
            }else{
                //Le pseudo existe déjà
                int messageretour = 2;
                request.setAttribute("message", messageretour);
                this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/inscription.jsp").forward(request, response);
            }
        } catch (SQLException e) {
        }
    }
}
