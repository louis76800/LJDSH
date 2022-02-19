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


        this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/Incident.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ville = request.getParameter("ville");
        request.setAttribute("ville", ville);

        String incident_en_question = request.getParameter("incident_en_question");
        request.setAttribute("incident_en_question", incident_en_question);
        if (ville != "") {
            if (incident_en_question != "") {
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

                        URL url = new URL("http://api.openweathermap.org/geo/1.0/direct?q=" + ville + "&appid=085526ba50eb67b600534682f09dc8dc");


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


                        String request_update = "INSERT INTO t_evenement (ville,type_a, date, longitude,lattitude )" +
                                " VALUES ('" + ville + "','" + incident_en_question + "',NOW(),'" + cityData.get("lon") + "','" + cityData.get("lat") + "')";


                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/ljdsh", "root", "");
                        //here sonoo is database name, root is username and password
                        Statement stmt = con.createStatement();
                        int rs = stmt.executeUpdate(request_update);
                        Supers tableSupers = new Supers(cityData.get("lon"), cityData.get("lat"), incident_en_question);

                        request.setAttribute("supers", tableSupers.recupereSupers(cityData.get("lon"), cityData.get("lat"), incident_en_question));
                        this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/Incident.jsp").forward(request, response);

                    } catch (Exception e) {

                        String messageretour = "1";
                        request.setAttribute("message", messageretour);
                        this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/Incident.jsp").forward(request, response);
                    }
                }else {
                    String messageretour = "4";
                    request.setAttribute("message", messageretour);
                    this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/Incident.jsp").forward(request, response);

                }
            }
            else {
                String messageretour = "2";
                request.setAttribute("message", messageretour);
                this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/Incident.jsp").forward(request, response);

            }
        } else {

            String messageretour = "3";
            request.setAttribute("message", messageretour);
            this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/Incident.jsp").forward(request, response);

        }

    }
}
