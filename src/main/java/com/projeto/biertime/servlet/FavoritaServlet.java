package com.projeto.biertime.servlet;

import com.projeto.biertime.dao.FavoritaDao;
import com.projeto.biertime.model.Favorita;
import com.projeto.biertime.util.Utils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "FavoritasServlet", urlPatterns = {"/api/favoritas"})
public class FavoritaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        FavoritaDao favoritaDao = new FavoritaDao();

        response.setContentType("application/json");
        if (Utils.isEmpty(request.getParameter("id"))) {
            response.getWriter().append(favoritaDao.findAll().toString());
        } else {
            Long id = Utils.parseLong(request.getParameter("id"));
            response.getWriter().append(favoritaDao.find(id).toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        FavoritaDao favoritaDao = new FavoritaDao();

        Favorita favorita = new Favorita();
        favorita.parse(Utils.parseReq2Map(request));

        if (favorita.getId() == null) {
            favorita = favoritaDao.create(favorita);
        } else {
            favorita = favoritaDao.update(favorita);
        }
        response.setContentType("application/json");
        response.getWriter().append(favorita.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        if (Utils.isEmpty(req.getParameter("id"))) {
            response.sendError(406, "Identificador do registro não foi informado");
        } else {
            Long id = Utils.parseLong(req.getParameter("id"));
            FavoritaDao favoritaDao = new FavoritaDao();
            favoritaDao.delete(id);
        }
    }
}
