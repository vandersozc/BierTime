package com.projeto.biertime.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ConectionUtil {

    private static final String URL = "jdbc:postgresql://localhost:5432/Biertime";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";
    
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static final Connection getConn() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
    
}
