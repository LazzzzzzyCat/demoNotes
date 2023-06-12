package com.demo;

import org.junit.Test;

import java.io.*;

public class InputOutputStream {


    private void copy(String srcPath, String destPath) {
        //zao wenjian
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            File srcFile = new File(srcPath);
            File destFile = new File(destPath);

            //zao liu
            FileInputStream fis = new FileInputStream(srcFile);
            FileOutputStream fos = new FileOutputStream(destFile);

            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);


            //du xie
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                for (int i = 0; i < buffer.length; i++) {
                    buffer[i] = (byte) (buffer[i] ^ 5);
                }
                bos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // shifang
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }


    private void cycle(String srcPath, String descPath) {
        File file = new File(srcPath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File item : files) {
                if (item.isDirectory()) {
                    cycle(srcPath + "/" + item.getName(), descPath);
                } else {
                    String name = item.getName();
                    if (name.contains("mp4")) {
                        copy(srcPath + "/" + name, descPath + "/" + name);
                        System.out.println(name + "     complete");
                    }
                }
            }
        } else {
            String name = file.getName();
            if (name.contains("mp4")) {
                copy(srcPath + "/" + name, descPath + "/" + name);
                System.out.println(name + "     complete");
            }
        }
    }

    @Test
    public void cycleTest() {
        String srcPath = "/Users/Messi/Downloads/movie/Taare.Zameen.Par.2007.地球上的星星.双语字幕.HR-HDTV.AC3.1024X576.x264-人人影视制作.mkv/[Airota＆DHR][Asa_Made_Jugyou_Chu！][OVA][1280x720][x264_AAC][BIG5].mp4";
        String descPath = "/Users/Messi/Desktop/Asa_Made_Jugyou_Chu.mp4";

//        String srcPath = "/Users/Messi/Desktop/des";
//        String descPath = "/Users/Messi/Desktop/src";
//        cycle(srcPath, descPath);
        copy(srcPath, descPath);
    }


    @Test
    public void singleTest() {
        String srcPath = "/Users/Messi/Desktop/sgs_mira_sunset1^5.mp4";
        String destPath = "/Users/Messi/Desktop/sgs_mira_sunset2.mp4";
//        String srcPath = "/Users/Messi/Desktop/gywf^5.mp4";
//        String destPath = "/Users/Messi/Desktop/gywf.mp4";
        copy(srcPath, destPath);
        System.out.println("拷贝完成");
    }
}
