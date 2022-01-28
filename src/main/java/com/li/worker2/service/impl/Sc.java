package com.li.worker2.service.impl;

import com.li.worker2.entity.User;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author li
 */
public class Sc {

    String path = "/users/li/Desktop/";

    String pathImpl;

    public void Screen(User user) {

        pathImpl = path + user.getStudentId() + ".png";

        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--hide-scrollbars");
        options.addArguments("--window-size=450,800");
        Cookie cookie = new Cookie(user.getCookieName(), user.getCookieValue());

        WebDriver driver = new ChromeDriver(options);

        driver.get("http://htu.g8n.cn");
        driver.manage().addCookie(cookie);
        driver.get(user.getHost());
        driver.switchTo().alert().accept();
        driver.findElement(By.xpath("/html/body/div/div/div[3]/div/div[2]/div/div/ul/li[2]")).click();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //指定了OutputType.FILE做为参数传递给getScreenshotAs()方法，其含义是将截取的屏幕以文件形式返回。
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        //利用FileUtils工具类的copyFile()方法保存getScreenshotAs()返回的文件对象。
        try {
            FileUtils.copyFile(srcFile, new File(pathImpl));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //关闭浏览器
        driver.quit();
        Font font = new Font("微软雅黑", Font.PLAIN, 35);
        String srcImgPath = pathImpl;
        String tarImgPath = pathImpl;
        String waterMarkContent = user.getName() + user.getStudentId();
        Color color = new Color(253, 135, 53, 255);
        try {
            // 读取原图片信息
            File srcImgFile = new File(srcImgPath);
            Image srcImg = ImageIO.read(srcImgFile);
            int srcImgWidth = srcImg.getWidth(null);
            int srcImgHeight = srcImg.getHeight(null);
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(color);
            g.setFont(font);

            //设置水印的坐标
            int x = srcImgWidth - 2 * getWatermarkLength(waterMarkContent, g) + 250;
            int y = srcImgHeight - 2 * getWatermarkLength(waterMarkContent, g) + 150;
            g.drawString(waterMarkContent, x, y);
            g.dispose();
            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
            ImageIO.write(bufImg, "png", outImgStream);
            outImgStream.flush();
            outImgStream.close();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }
}
