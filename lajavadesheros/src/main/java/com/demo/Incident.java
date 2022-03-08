package com.demo;

import com.ljdsh.bdd.Supers;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

@WebServlet("/Incident")
public class Incident extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Incident() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//renvoie vers la page incident
        this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/Incident.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //récupere le post : input ville
        String ville = request.getParameter("ville");
        request.setAttribute("ville", ville);
        //récupere le post : select incident_en_question
        String incident_en_question = request.getParameter("incident_en_question");
        request.setAttribute("incident_en_question", incident_en_question);
        //si ville ou incident = vide : renvoie un message d'erreur
        if (ville != "") {
            if (incident_en_question != "") {
                // les codes erreurs ont été modifié: renvoie un code erreur sinon on continue
                if (incident_en_question.equals("incendie")||incident_en_question.equals("accident_r")||
                        incident_en_question.equals("accident_f")||
                        incident_en_question.equals("accident_a")||
                        incident_en_question.equals("invasion_s")||
                        incident_en_question.equals("fuite_g")||
                        incident_en_question.equals("manifestation")||
                        incident_en_question.equals("eboulement")||
                        incident_en_question.equals("braquage")||
                        incident_en_question.equals("evasion_p")) {
                    try {
                        System.out.println(ville);

                        ville = ville.replace("'","%27");
                        ville = ville.replace(" ","%20");
                        ville = ville.replace("É","E");
                        ville = ville.replace("Î","I");
                        ville = ville.replace("ñ","n");
                        ville = ville.replace("Á","A");
                        ville = ville.replace("ú","u");
                        ville = ville.replace("í","i");
                        System.out.println(ville);
                        // connection a l'api
                        URL url = new URL("http://api.openweathermap.org/geo/1.0/direct?q=" + ville + "&appid=085526ba50eb67b600534682f09dc8dc");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        System.out.println(url);
                        conn.setRequestMethod("GET");

                        conn.connect();
                        int responseCode = conn.getResponseCode();
                        JSONObject cityData;
                        //si le code erreur n'est pas 200 : message d'erreur
                        if (responseCode != 200) {
                            throw new RuntimeException("HttpResponseCode" + responseCode);
                        } else {
                            //sinon on récupère les infos de l'api
                            StringBuilder informationString = new StringBuilder();
                            Scanner scanner = new Scanner(url.openStream());
                            while (scanner.hasNext()) {
                                informationString.append(scanner.nextLine());
                            }
                            scanner.close();
                            //json parser
                            JSONParser parse = new JSONParser();
                            JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));
                            //insertion du résultat dans citydata
                            cityData = (JSONObject) dataObject.get(0);

                        }
                        //insertion de la demande en bdd (pas obligatoire mais j'aime bien voir l'utilisation que l'on fait de l'app posibilité d'utilisre un cron pour delete au fure et a mesure)
                        //ou évolution : mise en place d'un indicateur d'utilisation
                        String request_update = "INSERT INTO t_evenement (ville,type_a, date, longitude,lattitude )" +
                                " VALUES ('" + ville + "','" + incident_en_question + "',NOW(),'" + cityData.get("lon") + "','" + cityData.get("lat") + "')";
                        //connection a la bdd
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/ljdsh", "root", "");
                        //here sonoo is database name, root is username and password
                        Statement stmt = con.createStatement();
                        int rs = stmt.executeUpdate(request_update);

                        //envoie des données récoltées depuis l'api(lon+lat)+incident dans super
                        Supers tableSupers = new Supers(cityData.get("lon"), cityData.get("lat"), incident_en_question);
                        //envoie des données récupérées depuis recupereSupers dans la page d'incident
                        request.setAttribute("supers", tableSupers.recupereSupers(cityData.get("lon"), cityData.get("lat"), incident_en_question));
                        this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/Incident.jsp").forward(request, response);
                    } catch (Exception e) {
                        // la commune n'est pas correcte
                        String messageretour = "1";
                        request.setAttribute("message", messageretour);
                        this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/Incident.jsp").forward(request, response);
                    }
                }else {
                    //l'incident n'est pas correct
                    String messageretour = "4";
                    request.setAttribute("message", messageretour);
                    this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/Incident.jsp").forward(request, response);
                }
            }
            else {
                //l'incident n'a pas été selectionné
                String messageretour = "2";
                request.setAttribute("message", messageretour);
                this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/Incident.jsp").forward(request, response);
            }
        } else {
            //la commune n'a pas été remplie
            String messageretour = "3";
            request.setAttribute("message", messageretour);
            this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/Incident.jsp").forward(request, response);
        }
    }
}
