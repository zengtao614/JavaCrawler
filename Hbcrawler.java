package com.demo.javaCrawler;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hbcrawler {
    private static String suggestsRegex = "app.page\\[\"suggests\"\\] = \\{.*\\}";
    private static String urlRegex = "\"url\":\".*?\"";
    private static String folder_name = "C:\\Users\\曾涛\\Desktop\\HbCrawler\\";


    public static void main(String[] args) throws Exception{
        startCrawle();
//        System.out.println(Util.getHtml("https://huaban.com/boards/17375733"));
    }


    public static void startCrawle()throws Exception{
        String html = Util.getHtml("https://huaban.com/boards/favorite/beauty/").toString();
        Matcher matcherSu = Pattern.compile(suggestsRegex).matcher(html);
        while (matcherSu.find()){
            Matcher matcherUrl = Pattern.compile(urlRegex).matcher(matcherSu.group());
            while (matcherUrl.find()){
                String urlStr = matcherUrl.group();
                if (urlStr.contains("boards")){
                    Matcher matcherId = Pattern.compile("[\\d]+").matcher(urlStr);
                    String id = "";
                    while (matcherId.find()){
                        id = matcherId.group();
                    }
                    String url = "https://huaban.com/boards/" + id;
//                    System.out.println("url:" + url + "\tname:"+id);
                    String folderName = folder_name +id;
                    File file = new File(folderName);
                    if (file.exists()){
                        file.delete();
                    }
                    file.mkdirs();
                    new Crawler(url,folderName+"\\").start();
                }
            }
        }
    }
}
