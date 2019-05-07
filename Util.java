package com.demo.javaCrawler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Util {

    public static Connection getConn(){
        Connection conn = null;
        try {
            InputStream is = ClassLoader.getSystemResourceAsStream("db.properties");
            Properties properties = new Properties();
            properties.load(is);
            String driverClass = properties.getProperty("driverClass");
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String psw = properties.getProperty("psw");
            Class.forName(driverClass);
            conn = DriverManager.getConnection(url,user,psw);
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }

    /*public static StringBuffer getHtml(String url) throws Exception{
        URL url1 = new URL(url);
        URLConnection connection = url1.openConnection();
        InputStream is = connection.getInputStream();
        BufferedReader bw = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        while (bw.ready()){
            sb.append(bw.readLine()).append("\n");
        }
        bw.close();
        is.close();
        return sb;
    }*/


}
