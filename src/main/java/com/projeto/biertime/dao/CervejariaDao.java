package com.projeto.biertime.dao;

import com.projeto.biertime.model.Cervejaria;
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

public class CervejariaDao {

    private static final String INSERT = "INSERT INTO cervejarias (id, nome, localizacao, estado, pais) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE cervejarias SET nome=?, localizacao=?, estado=?, pais=?";
    private static final String DELETE = "DELETE FROM cervejarias ";
    private static final String SELECT = "SELECT id, nome, localizacao, estado, pais FROM cervejarias ";
    private static final String WHEREID = "WHERE id=? ";
    private static final String SEQUENCE = "SELECT NEXTVAL('seq_cervejarias') ";
    private static final String ORDERBY = "ORDER BY nome asc ";

    public Cervejaria create(Cervejaria cervejaria) {
        try {
            cervejaria.setId(nextVal());
            try (Connection connection = ConectionUtil.getConn()) {
                try (PreparedStatement stm = connection.prepareStatement(INSERT)) {
                    stm.setLong(1, cervejaria.getId());
                    stm.setString(2, cervejaria.getNome());
                    stm.setString(3, cervejaria.getLocalizacao());
                    stm.setString(4, cervejaria.getEstado());
                    stm.setString(5, cervejaria.getPais());

                    stm.execute();
                }
            }
            return find(cervejaria.getId());
        } catch (SQLException ex) {
            Logger.getLogger(CervejariaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Cervejaria update(Cervejaria cervejaria) {
        try (Connection connection = ConectionUtil.getConn()) {
            try (PreparedStatement stm = connection.prepareStatement(UPDATE + WHEREID)) {
                stm.setString(1, cervejaria.getNome());
                stm.setString(2, cervejaria.getLocalizacao());
                stm.setString(3, cervejaria.getEstado());
                stm.setString(4, cervejaria.getPais());
                stm.setLong(5, cervejaria.getId());
                
                stm.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CervejariaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return find(cervejaria.getId());
    }

    public void delete(Long id) {
        try (Connection connection = ConectionUtil.getConn()) {
            try (PreparedStatement stm = connection.prepareStatement(DELETE + WHEREID)) {
                stm.setLong(1, id);
                stm.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CervejariaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Cervejaria find(Long id) {
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
            Logger.getLogger(CervejariaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Cervejaria> findAll() {
        List<Cervejaria> listaCervejarias = new ArrayList<>();
        try (Connection connection = ConectionUtil.getConn()) {
            try (PreparedStatement stm = connection.prepareStatement(SELECT + ORDERBY)) {
                try (ResultSet rs = stm.executeQuery()) {
                    while (rs.next()) {
                        listaCervejarias.add(parse(rs));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CervejariaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaCervejarias;
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

    private Cervejaria parse(ResultSet resultSet) throws SQLException {
        Cervejaria cervejaria = new Cervejaria();
        cervejaria.setId(resultSet.getLong("id"));
        cervejaria.setNome(resultSet.getString("nome"));
        cervejaria.setLocalizacao(resultSet.getString("localizacao"));
        cervejaria.setEstado(resultSet.getString("estado"));
        cervejaria.setPais(resultSet.getString("pais"));
        
        return cervejaria;
    }
}