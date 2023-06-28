package com.demo.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.demo.selenium.SeleniumUtil.*;

/**
 * @author huwj
 * @date 2023/6/11 16:00
 */
public class ParseJinDong {


    public static void main(String[] args) throws IOException {
        getWhatIWantFromHub("头盔");

    }

    private static void getWhatIWantFromHub(String keys) throws IOException {
        //将驱动加载到Java的JVM虚拟机中
        System.getProperties().setProperty("webdriver.chrome.driver", "D:\\Demo\\demoNotes\\crawler\\src\\main\\resources\\file\\chromedriver.exe");

        String pathName = path +"jingdong\\" + keys;
        File file = new File(pathName);
        if (!file.exists()) {
            file.mkdirs();
        }

        /************************** 方式一：不打开浏览器 **************************/
        //定义浏览器参数
        ChromeOptions chromeOptions = new ChromeOptions();
        //设置不打开浏览器
        chromeOptions.addArguments("--headless");
        //初始化驱动
        WebDriver driver = new ChromeDriver(chromeOptions);

        /************************** 方式二：打开浏览器 **************************/
        //初始化驱动
//        WebDriver driver = new ChromeDriver();
        //设置爬取网站
        driver.get("https://www.jd.com/");
        //获取京东网站首页查询条件输入框
        WebElement key = driver.findElement(By.id("key"));
        key.sendKeys(keys);
        //获取京东网站首页查询按钮并完成点击事件
        WebElement button = driver.findElement(By.cssSelector("button.button"));
        button.click();
        //滚动前先睡眠一会
        sleep(3);
        int count = getTotalPageNum(driver);
        for (int i = 0; i < count; i++) {
            System.out.println(i + "=======================================================================================");
            getPageInfo(keys, driver, pathName);
            nextPage(driver);
        }
    }

    private static void getPageInfo(String keys, WebDriver driver, String pathName) throws IOException {
        //设置滚动条移动到最下面
//        Object height = ((JavascriptExecutor) driver).executeScript("document.body.scrollHeight");
        int intHeight = 11644;

        for (int i = 0, j = 0; i <= intHeight; j = i, i = i + 600) {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(" + j + "," + i + ")");
            sleep(1);
        }

        sleep(2);

//        *[@id="J_goodsList"]/ul/li[3]
        //获取查询页面中的所有商品
        List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"J_goodsList\"]/ul/li"));
        for (WebElement element : elements) {
            String price = element.findElement(By.className("p-price")).getText().replaceAll("\\s*", "");
            String name = element.findElement(By.className("p-name")).getText();
            String image = element.findElement(By.className("p-img"))
                    .findElement(By.tagName("a"))
                    .findElement(By.tagName("img")).getAttribute("src");
            System.out.println("【" + price + "】-" + "【" + name + "】-" + "【" + image + "】");
            if (image != null && !image.equals("null")) {
                String[] split = image.split("/");
                download(image, pathName, split[split.length - 1]);
            }
        }
    }

    private static int getTotalPageNum(WebDriver driver) {
        WebElement skip = driver.findElement(By.cssSelector("span.p-skip"));
        String text = skip.findElement(By.tagName("em")).findElement(By.tagName("b")).getText();
        return Integer.parseInt(text);
    }

    private static void nextPage(WebDriver driver) {
        WebElement button = driver.findElement(By.cssSelector("a.pn-next"));
        button.click();
        //滚动前先睡眠一会
        sleep(3);
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0)");
    }

}
