package com.li.worker2.controller;

import com.gargoylesoftware.htmlunit.WebClient;
import com.li.worker2.entity.Time;
import com.li.worker2.entity.User;
import com.li.worker2.mapper.UserMapper;
import com.li.worker2.service.MasterService;
import com.li.worker2.service.RecordService;
import com.li.worker2.service.impl.SServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author li
 * @since 2021-12-31
 */
@RestController
@RequestMapping("/master")
public class MasterController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MasterService masterService;

    @Autowired
    public RecordService recordService;

    @Autowired
    public SServiceImpl service;

    @Autowired
    private UserMapper userMapper;

    private final String token = "12345679";

    @RequestMapping("/sendMail")
    public String send(@RequestParam String mail,@RequestParam String sub,@RequestParam String msg,@RequestParam String key){
        if (!token.equals(key)){
            return Time.getTimes() + "  " + "key错误";
        }
        masterService.sendMail(mail,sub,msg);
        return "邮件发送成功";
    }

    @RequestMapping("/allUser")
    public String allUser(@RequestParam String key){
        if (!token.equals(key)){
            return Time.getTimes() + "  " + "key错误";
        }
        return masterService.getAllUser();
    }


    @RequestMapping("/allRecord")
    public String allRecord(@RequestParam String key){
        if (!token.equals(key)){
            return Time.getTimes() + "  " + "key错误";
        }
        return recordService.getAllRecord();
    }

    @RequestMapping("/test")
    public String testEnable(@RequestParam String name) {
        List<User> users = userMapper.selectList(null);
        User user = null;
        for (User userx : users) {
            if (name.equals(userx.getName())) {
                user = userx;
            }
        }
        assert user != null;
        WebClient webClient = service.createWebClient(user);
        service.submit(user, webClient);
        if (service.confirm(webClient, user)) {
            logger.info("检查成功");
            return "sccess";
        } else {
            logger.info("检查失败");
            return "fail";
        }
    }
}

