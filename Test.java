package com.demo.javaCrawler;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Test {
    public static void main(String[] args) {
        createTbTest();
    }
    public static void createTbTest(){
        Connection conn = Util.getConn();
        try {
            String sql = "create table if not exists hbcrawler(" +
                    "id int AUTO_INCREMENT primary key," +
                    "filename varchar(50)," +
                    "data mediumblob)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            System.out.println("创表成功");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
