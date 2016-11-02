package com.projeto.biertime.servlet;

import com.projeto.biertime.dao.UsuarioDao;
import com.projeto.biertime.model.Usuario;
import com.projeto.biertime.util.Utils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/api/usuarios"})
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UsuarioDao usuarioDao = new UsuarioDao();

        response.setContentType("application/json");
        if (Utils.isEmpty(request.getParameter("id"))) {
            response.getWriter().append(usuarioDao.findAll().toString());
        } else {
            Long id = Utils.parseLong(request.getParameter("id"));
            response.getWriter().append(usuarioDao.find(id).toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UsuarioDao usuarioDao = new UsuarioDao();

        Usuario usuario = new Usuario();
        usuario.parse(Utils.parseReq2Map(request));

        if (usuario.getId() == null) {
            usuario = usuarioDao.create(usuario);
        } else {
            usuario = usuarioDao.update(usuario);
        }
        response.setContentType("application/json");
        response.getWriter().append(usuario.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        if (Utils.isEmpty(req.getParameter("id"))) {
            response.sendError(406, "Identificador do registro não foi informado");
        } else {
            Long id = Utils.parseLong(req.getParameter("id"));
            UsuarioDao usuarioDao = new UsuarioDao();
            usuarioDao.delete(id);
        }
    }
}
