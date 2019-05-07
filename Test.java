package com.demo.javaCrawler;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) throws Exception {
//        createTbTest();
//        System.out.println(htmlTest());
//        LinkedHashSet lhs = new LinkedHashSet();
//        System.out.println(lhs.size());
//        System.out.println(Util.getHtml("http://huaban.com/boards/17375733/?md=newbn&beauty"));
//        test3();
        delete(new File("C:\\Users\\曾涛\\Desktop\\HbCrawler"));

    }

    public static String htmlTest() {
        String last_id = null;
        try {
            StringBuffer html = Hbcrawler.getHtml("https://huaban.com/boards/24169299/");
            Matcher matcher = Pattern.compile("pin_id:'[\\d]+'").matcher(html);
            while (matcher.find()) {
//                System.out.println(matcher.group(matcher.groupCount()));
                last_id = matcher.group();
            }
            System.out.println(last_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String lastId = last_id.substring(last_id.indexOf("'") + 1, last_id.lastIndexOf("'"));
        return lastId;
    }


    public static void createTbTest() {
        Connection conn = Util.getConn();
        try {
            String sql = "create table if not exists hbcrawler(" +
                    "id int AUTO_INCREMENT primary key," +
                    "filename varchar(50)," +
                    "data mediumblob)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            System.out.println("创表成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void test1() throws Exception {
        String suggestsRegex = "app.page\\[\"suggests\"\\] = \\{.*\\}";
        String urlRegex = "\"url\":\".*?\"";
        StringBuffer html = Hbcrawler.getHtml("https://huaban.com/boards/favorite/beauty/");
        Matcher suggestsMatcher = Pattern.compile(suggestsRegex).matcher(html);
        while (suggestsMatcher.find()) {
            Matcher urlMatcher = Pattern.compile(urlRegex).matcher(suggestsMatcher.group());
            while (urlMatcher.find()) {
//                System.out.println(urlMatcher.group());
                String urlStr = urlMatcher.group();
                if (urlStr.contains("boards")) {
                    Matcher matcherId = Pattern.compile("[\\d]+").matcher(urlStr);
                    String id = "";
                    while (matcherId.find()) {
                        id = matcherId.group();
                    }
                    String url = "https://huaban.com/boards/" + id;
                    System.out.println(url);
                }
            }
        }
    }

    public static void test2() throws Exception{
        String  get_img_regex = "<img src=\"//hbimg.*?/>";
        String  get_src_regex = "\"//hbimg.huabanimg.com/.*?\"";
        StringBuffer html = Hbcrawler.getHtml("https://huaban.com/boards/17375733");
        LinkedHashSet<String> srcSet = new LinkedHashSet<>();
        Matcher matcherImg = Pattern.compile(get_img_regex).matcher(html);
        while (matcherImg.find()){
            Matcher matcherSrc = Pattern.compile(get_src_regex).matcher(matcherImg.group());
            while (matcherSrc.find()){
                String srcStr = matcherSrc.group();
                if (srcStr.contains("fw236")){
                    String srcUrl = srcStr.substring(1,srcStr.length()-1).replace("fw236","fw658");
                    System.out.println(srcUrl);
                }
            }
        }
//        System.out.println(srcSet);
    }

    public static void test3() throws Exception{
        URL url = new URL("http://hbimg.huabanimg.com/3bd402c57a727148fce86c193c9d5e93fb48b14b4d760-wF9Y7Z_fw658");
        InputStream is = url.openStream();
        FileOutputStream fos = new FileOutputStream("src/test.png");
        byte buf[] = new byte[1024];
        int length = 0;
        while ((length=is.read(buf))!=-1){
            fos.write(buf,0,length);
        }
        fos.close();
        is.close();
    }

    public static void delete(File file){
        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (File file1:files){
                delete(file1);
            }
        }
        file.delete();
    }


}
