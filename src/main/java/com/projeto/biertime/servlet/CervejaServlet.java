package com.projeto.biertime.servlet;

import com.projeto.biertime.dao.CervejaDao;
import com.projeto.biertime.model.Cerveja;
import com.projeto.biertime.util.Utils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CervejaServlet", urlPatterns = {"/api/cervejas"})
public class CervejaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CervejaDao cervejaDao = new CervejaDao();

        response.setContentType("application/json");
        if (Utils.isEmpty(request.getParameter("id"))) {
            response.getWriter().append(cervejaDao.findAll().toString());
        } else {
            Long id = Utils.parseLong(request.getParameter("id"));
            response.getWriter().append(cervejaDao.find(id).toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CervejaDao cervejaDao = new CervejaDao();

        Cerveja cerveja = new Cerveja();
        cerveja.parse(Utils.parseReq2Map(request));

        if (cerveja.getId() == null) {
            cerveja = cervejaDao.create(cerveja);
        } else {
            cerveja = cervejaDao.update(cerveja);
        }
        response.setContentType("application/json");
        response.getWriter().append(cerveja.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        if (Utils.isEmpty(req.getParameter("id"))) {
            response.sendError(406, "Identificador do registro não foi informado");
        } else {
            Long id = Utils.parseLong(req.getParameter("id"));
            CervejaDao cervejaDao = new CervejaDao();
            cervejaDao.delete(id);
        }
    }
}
