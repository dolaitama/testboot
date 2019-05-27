package com.sugon.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ResourceBundle;

/**
 * Created by lsx on 15/6/3.
 */
public class PropertiesUtil {

    private static ResourceBundle resources = null;

    static {

        String env = "DEV";
        try {
            Context context = new InitialContext();
            context = (Context) context.lookup("java:comp/env");
            env= (String) context.lookup("app.env");
        } catch (NamingException e) {
        }
        resources = ResourceBundle.getBundle("conf/app-" + env);
    }

    public static String getString(String key){
        return resources!=null && resources.containsKey(key) ? resources.getString(key):null;
    }

    public static String getString(String key, String defaultValue){
        return resources!=null && resources.containsKey(key) ? resources.getString(key):defaultValue;
    }
}
