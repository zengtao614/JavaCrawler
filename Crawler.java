package com.demo.javaCrawler;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler extends Thread{

//    private String url = "https://huaban.com/boards/52283153/";
    private String url;
    private String folderName;

    private static String  get_img_regex = "<img src=\"//hbimg.*?/>";
    private static String  get_src_regex = "\"//hbimg.huabanimg.com/.*?\"";

    public Crawler(String url, String folderName) {
        this.url = url;
        this.folderName = folderName;
    }

    @Override
    public void run(){
        try {
            System.out.println("线程启动");
            saveImg(getSrc(Util.getHtml(url)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public LinkedHashSet<String> getSrc(StringBuffer html){
        LinkedHashSet<String> srcSet = new LinkedHashSet<>();
        Matcher matcherImg = Pattern.compile(get_img_regex).matcher(html);
        while (matcherImg.find()){
            Matcher matcherSrc = Pattern.compile(get_src_regex).matcher(matcherImg.group());
            while (matcherSrc.find()){
//                System.out.println(matcherSrc.group());
                srcSet.add("http:"+matcherSrc.group().substring(1,matcherSrc.group().length()-4)+"658");
            }
        }
//        System.out.println(srcSet);
        return srcSet;
    }
    public void saveImg(LinkedHashSet<String> srcSet){
        for (String src:srcSet){
            try {
                URL url = new URL(src);
                InputStream is = url.openStream();
//                String folderName = "C:\\Users\\曾涛\\Desktop\\HbCrawler\\";
                FileOutputStream fos = new FileOutputStream(folderName+src.substring(src.lastIndexOf("/")+1)+".jpg");
                IOUtils.copy(is,fos);
            }catch (Exception e){
                System.out.println(src.substring(src.lastIndexOf("/")+1)+".jpg"+"下载失败");
            }
        }
    }
}
