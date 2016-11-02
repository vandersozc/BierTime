package com.projeto.biertime.servlet;

import com.projeto.biertime.dao.OrdemServicoDao;
import com.projeto.biertime.model.OrdemServico;
import com.projeto.biertime.util.Utils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "OrdemServicoServlet", urlPatterns = {"/api/ordens"})
public class OrdemServicoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrdemServicoDao dao = new OrdemServicoDao();
        response.setContentType("application/json");

        if (Utils.isEmpty(request.getParameter("id"))) {
            response.getWriter().append(dao.findAll().toString());
        } else {
            response.getWriter().append(dao.find(Utils.parseLong(request.getParameter("id"))).toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrdemServicoDao dao = new OrdemServicoDao();
        OrdemServico bean = new OrdemServico();
        bean.parse(Utils.parseReq2Map(request));
        response.setContentType("application/json");
        if (bean.getId() == null) {
            response.getWriter().append(dao.insert(bean).toString());
        } else {
            response.getWriter().append(dao.update(bean).toString());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Utils.isEmpty(req.getParameter("id"))) {
            resp.sendError(406, "Parâmetro ID obrigatório");
        } else {
            Long id = Utils.parseLong(req.getParameter("id"));
            OrdemServicoDao dao = new OrdemServicoDao();
            dao.delete(id);
        }
    }

}
