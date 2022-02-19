package com.demo;

import com.ljdsh.bdd.Coordonnes;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


@WebServlet("/Acceuil")
public class Accueil extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Coordonnes tableCoordonnes = new Coordonnes();

        System.out.println(tableCoordonnes.recuperePositions().toString());
            request.setAttribute("positions",tableCoordonnes.recuperePositions());

        this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/accueil.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



            this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/inscription.jsp").forward(request, response);


 }
}
