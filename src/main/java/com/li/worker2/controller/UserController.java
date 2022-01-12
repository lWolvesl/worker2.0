package com.li.worker2.controller;


import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.li.worker2.entity.User;
import com.li.worker2.service.MasterService;
import com.li.worker2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author li
 * @since 2021-12-31
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MasterService masterService;

    private Thread thread;

    @RequestMapping("/start")
    public String start() {
        if (thread != null) {
            stop();
        }
        System.out.println(getTimes() + "  " + "服务启动成功");
        thread = new Thread(this::run);
        thread.start();
        return getTimes() + "  " + "服务启动成功";
    }

    @RequestMapping("/stop")
    public String stop() {
        if (thread == null) {
            return getTimes() + "  " + "服务尚未启动";
        }
        try {
            thread.interrupt();
            thread = null;
            System.out.println(getTimes() + "  " + "服务停止成功");
        } catch (Exception e) {
            System.out.println(getTimes() + "  " + "服务停止失败");
        }
        return getTimes() + "  " + "服务停止成功";
    }

    HtmlPage home = null;

    public void run() {
        while (true) {
            int time = getTime();
            if (time >= 6 && time < 15) {
                List<User> all = userService.getAll();
                for (User user : all) {
                    implement(user);
                    System.out.println();
                }
                try {
                    TimeUnit.HOURS.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("---------------------------------");
            try {
                TimeUnit.MINUTES.sleep(30);
            } catch (InterruptedException ignored) {

            }
        }
    }

    public void implement(User user) {
        WebClient webClient = createWebClient(user);
        if (confirm(webClient, user)) {
            System.out.println(getTimes() + "  " + user.getName() + "今日已打卡");
        } else {
            submit(user, webClient);
            if (confirm(webClient, user)) {
                masterService.sendMail(user.getMail(), "Punch in information", "Punch success");
            } else {
                masterService.sendMail(user.getMail(), "Punch in information", "Punch fail");
            }
        }
    }

    public void submit(User user, WebClient webClient) {
        System.out.println(getTimes() + "  " + user.getName() + "打卡");
        try {
            home = webClient.getPage("https://htu.g8n.cn/student/course/31030/profiles/6099");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert home != null;
        try {
            try {
                List<HtmlTextInput> location = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[1]/div/input");
                location.get(0).setText(user.getLocation());

                String isInSchool = 1 == user.getIsInschool() ? "/html/body/div[1]/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[2]/div/label[1]/input" : "/html/body/div[1]/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[2]/div/label[2]/input";
                List<HtmlRadioButtonInput> isInSchoolList = home.getByXPath(isInSchool);
                isInSchoolList.get(0).setAttribute("checked", "checked");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                String pecialPersonnel = "/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[5]/div/label[2]/input";
                List<HtmlRadioButtonInput> pecialPersonnelList = home.getByXPath(pecialPersonnel);
                pecialPersonnelList.get(0).setAttribute("checked", "checked");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                String locationDenger = "/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[6]/div/label[2]/input";
                List<HtmlRadioButtonInput> locationDengerList = home.getByXPath(locationDenger);
                locationDengerList.get(0).setAttribute("checked", "checked");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                String isAdult = "/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[7]/div/label[1]/input";
                List<HtmlRadioButtonInput> adultList = home.getByXPath(isAdult);
                adultList.get(0).setAttribute("checked", "checked");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                String isVaccines = "/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[8]/div/label[1]/input";
                List<HtmlRadioButtonInput> vaccinesList = home.getByXPath(isVaccines);
                vaccinesList.get(0).setAttribute("checked", "checked");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                List<HtmlRadioButtonInput> twice = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[11]/div/label[2]/input");
                twice.get(0).setAttribute("checked", "checked");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                List<HtmlRadioButtonInput> mie = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[12]/div/label[1]/input");
                mie.get(0).setAttribute("checked", "checked");
            } catch (Exception e) {
                try {
                    e.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            try {
                List<HtmlRadioButtonInput> jia = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[13]/div/label[1]/input");
                jia.get(0).setAttribute("checked", "checked");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                List<HtmlTextInput> temperature = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[9]/div/input");
                temperature.get(0).setText("36.5");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                List<HtmlRadioButtonInput> symptomList = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[10]/div/label[2]/input");
                symptomList.get(0).setAttribute("checked", "checked");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                List<HtmlRadioButtonInput> feverList = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[11]/div/label[2]/input");
                feverList.get(0).setAttribute("checked", "checked");

                List<HtmlRadioButtonInput> diagnosisList = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[12]/div/label[2]/input");
                diagnosisList.get(0).setAttribute("checked", "checked");

                List<HtmlRadioButtonInput> suspectedList = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[13]/div/label[2]/input");
                suspectedList.get(0).setAttribute("checked", "checked");

                List<HtmlRadioButtonInput> beenList = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[15]/div/label[2]/input");
                beenList.get(0).setAttribute("checked", "checked");

                List<HtmlRadioButtonInput> closeContactList = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[16]/div/label[2]/input");
                closeContactList.get(0).setAttribute("checked", "checked");

                List<HtmlRadioButtonInput> stayList = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[17]/div/label[2]/input");
                stayList.get(0).setAttribute("checked", "checked");

                List<HtmlRadioButtonInput> parentsList = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[18]/div/label[2]/input");
                parentsList.get(0).setAttribute("checked", "checked");

                List<HtmlRadioButtonInput> relativesList = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[19]/div/label[2]/input");
                relativesList.get(0).setAttribute("checked", "checked");

                String dayStateX = "/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[20]/div/label[1]/input";
                List<HtmlRadioButtonInput> dayStateList = home.getByXPath(dayStateX);
                dayStateList.get(0).setAttribute("checked", "checked");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                List<HtmlTextInput> telephone = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[21]/div/input");
                telephone.get(0).setText(user.getPersonalPhone());

                List<HtmlTextInput> parentName = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[22]/div/input");
                parentName.get(0).setText(user.getEmergency());

                List<HtmlTextInput> parentPhone = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[23]/div/input");
                parentPhone.get(0).setText(user.getEmergencyPhone());
            } catch (Exception e) {
                e.printStackTrace();
            }

            List<HtmlButton> button = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[26]/button");
            button.get(0).click();
            System.out.println(getTimes() + "  " + user.getName() + "打卡成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(getTimes() + "  " + user.getName() + "打卡失败");
        }
    }

    public String getTimes() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss.ms");
        return sdf.format(new Date());
    }

    public boolean confirm(WebClient webClient, User user) {
        System.out.println(getTimes() + "  " + user.getName() + "检查打卡");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());
        HtmlPage page = null;
        try {
            page = webClient.getPage("https://htu.g8n.cn/student/course/31030/profiles/6099?op=getlist");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert page != null;
        String s = page.asXml();
        if (s.contains("登陆: 身份验证")) {
            System.out.println(getTimes() + "  " + user.getName() + "服务器验证失败");
            masterService.sendMail(user.getMail(), "Punch in information", "Cookie异常");
        }
        return s.contains(date);
    }

    public int getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("HH");
        return Integer.parseInt(sdf.format(new Date()));
    }

    public WebClient createWebClient(User user) {
        WebClient webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getCookieManager().addCookie(new Cookie("htu.g8n.cn", user.getCookieName(), user.getCookieValue()));
        return webClient;
    }
}

