package com.projeto.biertime.dao;

import com.projeto.biertime.model.Cerveja;
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

public class CervejaDao {

    private static final String INSERT = "INSERT INTO cervejas (id, nome, tipo, familia, amargor, cor, teor, observacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE cervejas SET nome=?, tipo=?, familia=?, amargor=?, cor=?, teor=?, observacao=?";
    private static final String DELETE = "DELETE FROM cervejas ";
    private static final String SELECT = "SELECT id, nome, tipo, familia, amargor, cor, teor, observacao FROM cervejas ";
    private static final String WHEREID = "WHERE id=? ";
    private static final String SEQUENCE = "SELECT NEXTVAL('seq_cervejas') ";
    private static final String ORDERBY = "ORDER BY nome asc ";

    public Cerveja create(Cerveja cerveja) {
        try {
            cerveja.setId(nextVal());
            try (Connection connection = ConectionUtil.getConn()) {
                try (PreparedStatement stm = connection.prepareStatement(INSERT)) {
                    stm.setLong(1, cerveja.getId());
                    stm.setString(2, cerveja.getNome());
                    stm.setString(3, cerveja.getTipo());
                    stm.setString(4, cerveja.getFamilia());
                    stm.setLong(5, cerveja.getAmargor());
                    stm.setLong(6, cerveja.getCor());
                    stm.setLong(7, cerveja.getTeor());
                    stm.setString(8, cerveja.getObservacao());

                    stm.execute();
                }
            }
            return find(cerveja.getId());
        } catch (SQLException ex) {
            Logger.getLogger(CervejaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Cerveja update(Cerveja cerveja) {
        try (Connection connection = ConectionUtil.getConn()) {
            try (PreparedStatement stm = connection.prepareStatement(UPDATE + WHEREID)) {
                stm.setString(1, cerveja.getNome());
                stm.setString(2, cerveja.getTipo());
                stm.setString(3, cerveja.getFamilia());
                stm.setLong(4, cerveja.getAmargor());
                stm.setLong(5, cerveja.getCor());
                stm.setLong(6, cerveja.getTeor());
                stm.setString(7, cerveja.getObservacao());
                stm.setLong(8, cerveja.getId());
                
                stm.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CervejaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return find(cerveja.getId());
    }

    public void delete(Long id) {
        try (Connection connection = ConectionUtil.getConn()) {
            try (PreparedStatement stm = connection.prepareStatement(DELETE + WHEREID)) {
                stm.setLong(1, id);
                stm.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CervejaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Cerveja find(Long id) {
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
            Logger.getLogger(CervejaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Cerveja> findAll() {
        List<Cerveja> listaCervejas = new ArrayList<>();
        try (Connection connection = ConectionUtil.getConn()) {
            try (PreparedStatement stm = connection.prepareStatement(SELECT + ORDERBY)) {
                try (ResultSet rs = stm.executeQuery()) {
                    while (rs.next()) {
                        listaCervejas.add(parse(rs));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CervejaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaCervejas;
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

    private Cerveja parse(ResultSet resultSet) throws SQLException {
        Cerveja cerveja = new Cerveja();
        cerveja.setId(resultSet.getLong("id"));
        cerveja.setNome(resultSet.getString("nome"));
        cerveja.setTipo(resultSet.getString("tipo"));
        cerveja.setFamilia(resultSet.getString("familia"));
        cerveja.setAmargor(resultSet.getLong("amargor"));
        cerveja.setCor(resultSet.getLong("cor"));
        cerveja.setTeor(resultSet.getLong("teor"));
        cerveja.setObservacao(resultSet.getString("observacao"));
        
        return cerveja;
    }
}