package com.projeto.biertime.servlet;

import com.projeto.biertime.dao.AcessoDao;
import com.projeto.biertime.model.Acesso;
import com.projeto.biertime.util.Utils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AcessoServlet", urlPatterns = {"/api/acessos"})
public class AcessoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AcessoDao acessoDao = new AcessoDao();

        response.setContentType("application/json");
        if (Utils.isEmpty(request.getParameter("id"))) {
            response.getWriter().append(acessoDao.findAll().toString());
        } else {
            Long id = Utils.parseLong(request.getParameter("id"));
            response.getWriter().append(acessoDao.find(id).toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AcessoDao acessoDao = new AcessoDao();

        Acesso acesso = new Acesso();
        acesso.parse(Utils.parseReq2Map(request));

        if (acesso.getId() == null) {
            acesso = acessoDao.create(acesso);
        } else {
            acesso = acessoDao.update(acesso);
        }
        response.setContentType("application/json");
        response.getWriter().append(acesso.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        if (Utils.isEmpty(req.getParameter("id"))) {
            response.sendError(406, "Identificador do registro não foi informado");
        } else {
            Long id = Utils.parseLong(req.getParameter("id"));
            AcessoDao acessoDao = new AcessoDao();
            acessoDao.delete(id);
        }
    }
}
