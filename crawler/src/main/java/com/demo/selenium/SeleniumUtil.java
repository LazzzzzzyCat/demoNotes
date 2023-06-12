package com.demo.selenium;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author huwj
 * @date 2023/6/11 20:13
 */
public class SeleniumUtil {

    public static final String path = "D:\\Picture\\";


    public static void sleep(int num) {
        try {
            Thread.sleep(num * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void download(String urlString, String savePath, String filename) throws IOException {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为20s
        con.setConnectTimeout(20 * 1000);
        //文件路径不存在 则创建
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }
        //jdk 1.7 新特性自动关闭
        try (InputStream in = con.getInputStream();
             OutputStream out = new FileOutputStream(sf.getPath() + "\\" + filename)) {
            //创建缓冲区
            byte[] buff = new byte[1024];
            int n;
            // 开始读取
            while ((n = in.read(buff)) >= 0) {
                out.write(buff, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
