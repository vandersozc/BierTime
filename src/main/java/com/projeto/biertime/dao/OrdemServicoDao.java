package com.projeto.biertime.dao;

import com.projeto.biertime.model.OrdemServico;
import com.projeto.biertime.util.ConectionUtil;
import com.projeto.biertime.util.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrdemServicoDao {

    private static final String INSERT = "INSERT INTO ordensservico(idordemservico, emissao, idcliente, liberado, descricao, valortotal, situacao) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE ordensservico SET emissao=?, idcliente=?, liberado=?, descricao=?, valortotal=?, situacao=? ";
    private static final String DELETE = "DELETE FROM ordensservico ";
    private static final String SELECT = "SELECT idordemservico, emissao, idcliente, liberado, descricao, valortotal, situacao FROM ordensservico ";
    private static final String WHEREID = "WHERE idordemservico=?";
    private static final String SEQUENCE = "SELECT NEXTVAL('seqordensservico')";

    public OrdemServico insert(OrdemServico ordem) {
        try {
            ordem.setId(nextVal());
            try (Connection conn = ConectionUtil.getConn()) {
                try (PreparedStatement stm = conn.prepareStatement(INSERT)) {
                    stm.setLong(1, ordem.getId());
                    stm.setTimestamp(2, new Timestamp(ordem.getEmissao().getTime()));
                    stm.setLong(3, ordem.getCliente().getId());
                    stm.setTimestamp(4, Utils.getTimestamp(ordem.getLiberado()));
                    stm.setString(5, ordem.getDescricao());
                    stm.setDouble(6, ordem.getValorTotal());
                    stm.setString(7, ordem.getSituacao());

                    stm.execute();
                }
            }
            return find(ordem.getId());
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public OrdemServico update(OrdemServico ordem) {
        try (Connection conn = ConectionUtil.getConn()) {
            try (PreparedStatement stm = conn.prepareStatement(UPDATE + WHEREID)) {
                stm.setTimestamp(1, new Timestamp(ordem.getEmissao().getTime()));
                stm.setLong(2, ordem.getCliente().getId());
                stm.setTimestamp(3, new Timestamp(ordem.getLiberado().getTime()));
                stm.setString(4, ordem.getDescricao());
                stm.setDouble(5, ordem.getValorTotal());
                stm.setString(6, ordem.getSituacao());

                stm.setLong(7, ordem.getId());

                stm.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return find(ordem.getId());
    }

    public void delete(Long id) {
        try (Connection conn = ConectionUtil.getConn()) {
            try (PreparedStatement stm = conn.prepareStatement(DELETE + WHEREID)) {
                stm.setLong(1, id);
                stm.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public OrdemServico find(Long id) {
        try (Connection conn = ConectionUtil.getConn()) {
            try (PreparedStatement stm = conn.prepareStatement(SELECT + WHEREID)) {
                stm.setLong(1, id);
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        return parse(rs);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<OrdemServico> findAll() {
        List<OrdemServico> registros = new ArrayList<>();
        try (Connection conn = ConectionUtil.getConn()) {
            try (PreparedStatement stm = conn.prepareStatement(SELECT)) {
                try (ResultSet rs = stm.executeQuery()) {
                    while (rs.next()) {
                        registros.add(parse(rs));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return registros;
    }

    private Long nextVal() throws SQLException {
        try (Connection conn = ConectionUtil.getConn()) {
            try (Statement stm = conn.createStatement()) {
                try (ResultSet rs = stm.executeQuery(SEQUENCE)) {
                    rs.next();
                    return rs.getLong(1);
                }
            }
        }
    }

    private OrdemServico parse(ResultSet rs) throws SQLException {
        CervejaDao daoCli = new CervejaDao();
        
        OrdemServico ordem = new OrdemServico();
        ordem.setId(rs.getLong("idordemservico"));
        ordem.setEmissao(rs.getDate("emissao"));
        ordem.setCliente(daoCli.find(rs.getLong("idcliente")));
        ordem.setLiberado(rs.getDate("liberado"));
        ordem.setDescricao(rs.getString("descricao"));
        ordem.setValorTotal(rs.getDouble("valortotal"));
        ordem.setSituacao(rs.getString("situacao"));

        return ordem;
    }

}
