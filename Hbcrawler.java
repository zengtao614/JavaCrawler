package com.demo.javaCrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hbcrawler {

    private static String suggestsRegex = "app.page\\[\"suggests\"\\] = \\{.*\\}";
    private static String urlRegex = "\"url\":\".*?\"";
    private static String folder_name = "C:\\Users\\曾涛\\Desktop\\HbCrawler\\";


    public static void main(String[] args) throws Exception {
        String url = "https://huaban.com/boards/favorite/beauty/";
        startCrawle(url);
    }

    /**
     * 获取每个画板的url并启动爬虫
     *
     * @param originUrl
     * @throws Exception
     */
    public static void startCrawle(String originUrl) throws Exception {
        StringBuffer html = getHtml(originUrl);
        Matcher matcherSu = Pattern.compile(suggestsRegex).matcher(html);
        while (matcherSu.find()) {
            Matcher matcherUrl = Pattern.compile(urlRegex).matcher(matcherSu.group());
            while (matcherUrl.find()) {
                String urlStr = matcherUrl.group();
                if (urlStr.contains("boards")) {
                    Matcher matcherId = Pattern.compile("[\\d]+").matcher(urlStr);
                    String id = "";
                    while (matcherId.find()) {
                        id = matcherId.group();
                    }
                    String url = "https://huaban.com/boards/" + id;
                    String folderName = folder_name + id;
                    File file = new File(folderName);
                    if (file.exists()) {
                        delete(file);
                    }
                    file.mkdirs();
                    new Crawler(url, folderName + "\\").start();
                }
            }
        }
    }

    /**
     * 传入url返回html
     *
     * @param url 传入的url参数
     * @return 返回html
     * @throws Exception
     */
    public static StringBuffer getHtml(String url) throws Exception {
        URL url1 = new URL(url);
        URLConnection connection = url1.openConnection();
        InputStream is = connection.getInputStream();
        BufferedReader bw = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        while (bw.ready()) {
            sb.append(bw.readLine()).append("\n");
        }
        bw.close();
        is.close();
        return sb;
    }

    /**
     * 删除目录下所有文件包括该目录，如果该目录下有不为空的目录则会删除失败
     *
     * @param file 传入的File对象
     */
    public static void delete(File file) {
        File[] files = file.listFiles();
        for (File file1 : files) {
            file1.delete();
        }
        file.delete();
    }
}
