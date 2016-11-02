package com.projeto.biertime.dao;

import com.projeto.biertime.model.Acesso;
import com.projeto.biertime.util.ConectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AcessoDao {

    private static final String INSERT = "INSERT INTO acessos (id, i_usuario, login, senha) VALUES (?, ?, ?, ?) ";
    private static final String UPDATE = "UPDATE acessos SET login=?, senha=? ";
    private static final String DELETE = "DELETE FROM acessos ";
    private static final String SELECT = "SELECT id, i_usuario, login, senha FROM acessos ";
    private static final String WHEREID = "WHERE id=? ";
    private static final String SEQUENCE = "SELECT NEXTVAL('seq_acessos') ";
    private static final String ORDERBY = "ORDER BY i_usuario asc ";

    public Acesso create(Acesso acesso) {
        try {
            acesso.setId(nextVal());
            try (Connection connection = ConectionUtil.getConn()) {
                try (PreparedStatement stm = connection.prepareStatement(INSERT)) {
                    stm.setLong(1, acesso.getId());
                    stm.setLong(2, acesso.getUsuario());
                    stm.setString(3, acesso.getLogin());
                    stm.setString(4, acesso.getSenha());
                    stm.execute();
                }
            }
            return find(acesso.getId());
        } catch (SQLException ex) {
            Logger.getLogger(AcessoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Acesso update(Acesso acesso) {
        try (Connection connection = ConectionUtil.getConn()) {
            try (PreparedStatement stm = connection.prepareStatement(UPDATE + WHEREID)) {
                stm.setString(1, acesso.getLogin());
                stm.setString(2, acesso.getSenha());
                stm.setLong(3, acesso.getId());
                stm.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcessoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return find(acesso.getId());
    }

    public void delete(Long id) {
        try (Connection connection = ConectionUtil.getConn()) {
            try (PreparedStatement stm = connection.prepareStatement(DELETE + WHEREID)) {
                stm.setLong(1, id);
                stm.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcessoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Acesso find(Long id) {
        try (Connection connection = ConectionUtil.getConn()) {
            try (PreparedStatement stm = connection.prepareStatement(SELECT + WHEREID)) {
                stm.setLong(1, id);
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        return parse(rs);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcessoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Acesso> findAll() {
        List<Acesso> listaAcessos = new ArrayList<>();
        try (Connection connection = ConectionUtil.getConn()) {
            try (PreparedStatement stm = connection.prepareStatement(SELECT + ORDERBY)) {
                try (ResultSet rs = stm.executeQuery()) {
                    while (rs.next()) {
                        listaAcessos.add(parse(rs));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcessoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaAcessos;
    }

    private Long nextVal() throws SQLException {
        try (Connection connection = ConectionUtil.getConn()) {
            try (Statement stm = connection.createStatement()) {
                try (ResultSet resultSet = stm.executeQuery(SEQUENCE)) {
                    resultSet.next();
                    return resultSet.getLong(1);
                }
            }
        }
    }

    private Acesso parse(ResultSet resultSet) throws SQLException {
        Acesso acesso = new Acesso();
        acesso.setId(resultSet.getLong("id"));
        acesso.setUsuario(resultSet.getLong("usuario"));
        acesso.setLogin(resultSet.getString("login"));
        acesso.setSenha(resultSet.getString("senha"));
        
        return acesso;
    }
}