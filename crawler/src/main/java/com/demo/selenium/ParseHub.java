package com.demo.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.demo.selenium.SeleniumUtil.path;
import static com.demo.selenium.SeleniumUtil.sleep;

/**
 * @author huwj
 * @date 2023/6/11 16:03
 */
public class ParseHub {

    public static void main(String[] args) throws IOException {

        getWhatIWantFromHub("人妻");

    }

    private static void getWhatIWantFromHub(String keys) throws IOException {
        //将驱动加载到Java的JVM虚拟机中
        System.getProperties().setProperty("webdriver.chrome.driver", "D:\\Demo\\demoNotes\\crawler\\src\\main\\resources\\file\\chromedriver.exe");

        String pathName = path +"hub\\"+ keys;
        File file = new File(pathName);
        if (!file.exists()) {
            file.mkdirs();
        }

        /************************** 方式一：不打开浏览器 **************************/
        //定义浏览器参数
        //ChromeOptions chromeOptions = new ChromeOptions();
        //设置不打开浏览器
        //chromeOptions.addArguments("--headless");
        //初始化驱动
        //WebDriver driver = new ChromeDriver(chromeOptions);

        /************************** 方式二：打开浏览器 **************************/
        //初始化驱动
        WebDriver driver = new ChromeDriver();

        int count = getTotalPageNum(driver);
        for (int i = 1; i <= count; i++) {
            //设置爬取网站
            if (keys != null) {
                driver.get("https://cn.pornhub.com/video/search?search=" + keys + "&page=" + i);
            } else {

                driver.get("https://cn.pornhub.com/video/page=" + i);
            }

            //滚动前先睡眠一会
            sleep(3);
            System.out.println(i + "=======================================================================================");
            getPageInfo(keys, driver, pathName);
        }
    }

    private static void getPageInfo(String keys, WebDriver driver, String pathName) throws IOException {
        //设置滚动条移动到最下面
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
        sleep(2);

        //获取查询页面中的所有商品
        List<WebElement> elements;
        String rightLiName = "pcVideoListItem";
        if (driver.getCurrentUrl().contains("search")) {
            elements = driver.findElements(By.xpath("//*[@id=\"videoSearchResult\"]/li"));
        } else {
            elements = driver.findElements(By.xpath("//*[@class=\"videos full-row-thumbs\"]/li"));
        }

        for (WebElement element : elements) {
            if (!element.getAttribute("class").startsWith(rightLiName)) {
                continue;
            }
            String image = element.findElement(By.tagName("div"))
                    .findElement(By.cssSelector("div.phimage"))
                    .findElement(By.tagName("a"))
                    .findElement(By.tagName("img")).getAttribute("src");
            System.out.println("【" + image + "】");
            if (image != null && !image.equals("null")) {
                String[] split = image.split("/");
                SeleniumUtil.download(image, pathName, split[split.length - 1]);
            }
        }
    }

    private static int getTotalPageNum(WebDriver driver) {
//        WebElement skip = driver.findElement(By.cssSelector("li.page_next_set"));
//        String text = skip.findElement(By.tagName("a")).getText();
//        return Integer.parseInt(text);
        return 2;
    }


}
