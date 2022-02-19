package com.demo;

import com.ljdsh.bdd.Coordonnes;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

//redirect ver accueil
@WebServlet("/Accueil")
public class Accueil extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Récupère la position des super et la renvoie à la page accueil
        Coordonnes tableCoordonnes = new Coordonnes();
        request.setAttribute("positions",tableCoordonnes.recuperePositions());
        this.getServletContext().getRequestDispatcher("/WEB-INF/Pages/accueil.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //il n'y a pas de post pour cette page
    }
}
