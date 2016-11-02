package com.projeto.biertime.dao;

import com.projeto.biertime.model.Favorita;
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

public class FavoritaDao {

    private static final String INSERT = "INSERT INTO favoritas (id, usuario, cerveja, pontuacao, curtida, comentario) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE favoritas SET pontuacao=?, curtida=?, comentario=? ";
    private static final String DELETE = "DELETE FROM favoritas ";
    //private static final String SELECT = "SELECT f.id, f.i_usuario, f.i_cerveja, f.pontuacao, f.curtida, f.comentario, c.nome, c.tipo, c.familia FROM favoritas AS f join cervejas AS c on f.i_cerveja = c.id ";
    private static final String SELECT = "SELECT id, i_usuario, i_cerveja, pontuacao, curtida, comentario FROM favoritas ";
    private static final String WHEREID = "WHERE id=? ";
    private static final String SEQUENCE = "SELECT NEXTVAL('seq_favoritas') ";
    private static final String ORDERBY = "ORDER BY pontuacao desc ";

    public Favorita create(Favorita favorita) {
        try {
            favorita.setId(nextVal());
            try (Connection connection = ConectionUtil.getConn()) {
                try (PreparedStatement stm = connection.prepareStatement(INSERT)) {
                    stm.setLong(1, favorita.getId());
                    stm.setLong(2, favorita.getUsuario());
                    stm.setLong(3, favorita.getCerveja());
                    stm.setLong(4, favorita.getPontuacao());
                    stm.setString(5, favorita.getCurtida());
                    stm.setString(6, favorita.getComentario());

                    stm.execute();
                }
            }
            return find(favorita.getId());
        } catch (SQLException ex) {
            Logger.getLogger(FavoritaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Favorita update(Favorita favorita) {
        try (Connection connection = ConectionUtil.getConn()) {
            try (PreparedStatement stm = connection.prepareStatement(UPDATE + WHEREID)) {
                stm.setLong(1, favorita.getUsuario());
                stm.setLong(2, favorita.getCerveja());
                stm.setLong(3, favorita.getPontuacao());
                stm.setString(4, favorita.getCurtida());
                stm.setString(5, favorita.getComentario());
                stm.setLong(6, favorita.getId());
                
                stm.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FavoritaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return find(favorita.getId());
    }

    public void delete(Long id) {
        try (Connection connection = ConectionUtil.getConn()) {
            try (PreparedStatement stm = connection.prepareStatement(DELETE + WHEREID)) {
                stm.setLong(1, id);
                stm.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FavoritaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Favorita find(Long id) {
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
            Logger.getLogger(FavoritaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Favorita> findAll() {
        List<Favorita> listaFavoritas = new ArrayList<>();
        try (Connection connection = ConectionUtil.getConn()) {
            try (PreparedStatement stm = connection.prepareStatement(SELECT + ORDERBY)) {
                try (ResultSet rs = stm.executeQuery()) {
                    while (rs.next()) {
                        listaFavoritas.add(parse(rs));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FavoritaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaFavoritas;
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

    private Favorita parse(ResultSet resultSet) throws SQLException {
        Favorita favorita = new Favorita();
        favorita.setId(resultSet.getLong("id"));
        favorita.setUsuario(resultSet.getLong("i_usuario"));
        favorita.setCerveja(resultSet.getLong("i_cerveja"));
        favorita.setPontuacao(resultSet.getLong("pontuacao"));
        favorita.setCurtida(resultSet.getString("curtida"));
        favorita.setComentario(resultSet.getString("comentario"));
        
        return favorita;
    }
}