package com.projeto.biertime.servlet;

import com.projeto.biertime.dao.CervejariaDao;
import com.projeto.biertime.model.Cervejaria;
import com.projeto.biertime.util.Utils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CervejariaServlet", urlPatterns = {"/api/cervejarias"})
public class CervejariaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CervejariaDao cervejariaDao = new CervejariaDao();

        response.setContentType("application/json");
        if (Utils.isEmpty(request.getParameter("id"))) {
            response.getWriter().append(cervejariaDao.findAll().toString());
        } else {
            Long id = Utils.parseLong(request.getParameter("id"));
            response.getWriter().append(cervejariaDao.find(id).toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CervejariaDao cervejariaDao = new CervejariaDao();

        Cervejaria cervejaria = new Cervejaria();
        cervejaria.parse(Utils.parseReq2Map(request));

        if (cervejaria.getId() == null) {
            cervejaria = cervejariaDao.create(cervejaria);
        } else {
            cervejaria = cervejariaDao.update(cervejaria);
        }
        response.setContentType("application/json");
        response.getWriter().append(cervejaria.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        if (Utils.isEmpty(req.getParameter("id"))) {
            response.sendError(406, "Identificador do registro não foi informado");
        } else {
            Long id = Utils.parseLong(req.getParameter("id"));
            CervejariaDao cervejariaDao = new CervejariaDao();
            cervejariaDao.delete(id);
        }
    }
}
