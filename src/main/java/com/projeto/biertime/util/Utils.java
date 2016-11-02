package com.projeto.biertime.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public final class Utils {

    public static Map<String, String> parseReq2Map(HttpServletRequest request) {
        Map<String, String> dados = new HashMap<>();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String key = params.nextElement();
            dados.put(key, request.getParameter(key));
        }
        return dados;
    }
    
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static Long parseLong(String value) {
        return isEmpty(value) ? null : Long.parseLong(value);
    }
    
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
    
    public static Date parseDate(String value) {
        try {
            return isEmpty(value) ? null : SDF.parse(value);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static Double parseDouble(String value) {
        return isEmpty(value) ? null : Double.parseDouble(value);
    }

    public static Timestamp getTimestamp(Date value) {
        return value == null ? null : new Timestamp(value.getTime());
    }
    
}
