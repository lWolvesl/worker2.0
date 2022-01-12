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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author li
 * @since 2021-12-31
 */
@RestController
public class MasterController {

    @Autowired
    public MasterService masterService;

    @RequestMapping("/iniMail")
    public String iniMail(){
        return masterService.iniMail();
    }

    @RequestMapping("/testMail")
    public String send(@RequestParam String mail,@RequestParam String sub,@RequestParam String msg){
        masterService.sendMail(mail,sub,msg);
        return "邮件发送成功";
    }
}

