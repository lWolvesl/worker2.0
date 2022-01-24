package com.li.worker2.controller;


import com.li.worker2.entity.Time;
import com.li.worker2.service.MasterService;
import com.li.worker2.service.RecordService;
import com.li.worker2.service.impl.SServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    public MasterService masterService;

    @Autowired
    public RecordService recordService;

    @Autowired
    public SServiceImpl service;

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
}

