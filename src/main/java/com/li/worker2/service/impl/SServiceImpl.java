package com.li.worker2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.li.worker2.entity.Master;
import com.li.worker2.entity.OwnRandom;
import com.li.worker2.entity.Time;
import com.li.worker2.entity.User;
import com.li.worker2.mapper.UserMapper;
import com.li.worker2.service.MasterService;
import com.li.worker2.service.RecordService;
import com.li.worker2.service.SService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author li
 * @since 2021-12-31
 */
@Service
public class SServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<UserMapper, User> implements SService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MasterService masterService;

    @Autowired
    private RecordService recordService;

    private Master master;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    ExecutorService executor;

    @Override
    public List<User> getAllEnable() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("enable", "1");
        return userMapper.selectList(wrapper);
    }


    public void ini() {
        if (master == null) {
            master = masterService.getOne(null);
        }
    }

    @Override
    public String start() {
        if (master == null) {
            ini();
        }
        if (executor != null) {
            stop();
        }
        executor = Executors.newFixedThreadPool(7);
        executor.submit(this::run);
        masterService.sendMail(master.getMailRemind(), "Punch in information", "Punch service start");
        logger.info("服务启动成功");
        return Time.getTimes() + "  " + "服务启动成功";
    }

    @Override
    public String stop() {
        if (executor == null) {
            return Time.getTimes() + "  " + "服务尚未启动";
        }
        try {
            executor.shutdownNow();
            executor = null;
            masterService.sendMail(master.getMailRemind(), "Punch in information", "Punch service stop");
            logger.info("服务停止成功");
        } catch (Exception e) {
            logger.info("服务停止失败");
        }
        return Time.getTimes() + "  " + "服务停止成功";
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {

        }
        while (true) {
            int time = Time.getTime();
            if (time >= 6 && time < 15) {
                logger.info(Time.getDate()+"打卡服务启动");
                event();
            }
            try {
                TimeUnit.MINUTES.sleep(36);
            } catch (InterruptedException ignored) {

            }
        }
    }

    public void event() {
        List<User> all = getAllEnable();
        try {
            TimeUnit.SECONDS.sleep(OwnRandom.getTen());
        } catch (InterruptedException ignored) {

        }
        for (User user : all) {
            executor.submit(() -> {
                implement(user);
            });
        }
        try {
            TimeUnit.HOURS.sleep(15);
        } catch (InterruptedException ignored) {

        }
    }

    public void implement(User user) {
        WebClient webClient = createWebClient(user);
        if (!confirm(webClient, user)) {
            logger.info(user.getName() + "今日已打卡");
        } else {
            submit(user, webClient);
            if (confirm(webClient, user)) {
                logger.info(user.getName() + "打卡成功");
                masterService.sendMail(user.getMail(), "Punch success", "Punch success");
                recordService.save(user, 1, null);
            } else {
                logger.info(user.getName() + "打卡失败");
                masterService.sendMail(user.getMail(), "Punch fail", "Punch fail");
                recordService.save(user, 0, null);
            }
        }
    }

    public boolean submit(User user, WebClient webClient) {
        logger.info(user.getName() + "打卡");
        HtmlPage home = null;
        try {
            home = webClient.getPage(user.getHost());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert home != null;
        try {
            try {
                List<HtmlTextInput> location = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[1]/div/input");
                location.get(0).setText(user.getLocation());

                String isInSchool = user.getIsInschool() ? "/html/body/div[1]/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[2]/div/label[1]/input" : "/html/body/div[1]/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[2]/div/label[2]/input";
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

            List<HtmlButton> button = home.getByXPath("/html/body/div/div/div[3]/div/div[2]/div/div/div/div[1]/form/div[23]/button");
            button.get(0).click();
            if (user.getSc()) {
                try {
                    new Sc().Screen(user);
                    logger.info("图片保存成功");
                } catch (Exception e) {
                    logger.info(e.getMessage());
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean confirm(WebClient webClient, User user) {
        logger.info(user.getName() + "检查打卡");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());
        HtmlPage page = null;
        try {
            page = webClient.getPage(user.getHost() + "?op=getlist");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert page != null;
        String s = page.asXml();
        if (s.contains("登陆: 身份验证")) {
            logger.info(user.getName() + "服务器验证失败");
            masterService.sendMail(user.getMail(), "Punch in information", "Cookie异常");
        }
        return s.contains(date);
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
