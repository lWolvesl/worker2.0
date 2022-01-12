package com.li.worker2.controller;


import com.li.worker2.entity.Time;
import com.li.worker2.service.MasterService;
import com.li.worker2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        System.out.println(Time.getTimes() + "  " + "服务启动成功");
        thread = new Thread(userService::run);
        thread.start();
        return Time.getTimes() + "  " + "服务启动成功";
    }

    @RequestMapping("/stop")
    public String stop() {
        if (thread == null) {
            return Time.getTimes() + "  " + "服务尚未启动";
        }
        try {
            thread.interrupt();
            thread = null;
            System.out.println(Time.getTimes() + "  " + "服务停止成功");
        } catch (Exception e) {
            System.out.println(Time.getTimes() + "  " + "服务停止失败");
        }
        return Time.getTimes() + "  " + "服务停止成功";
    }
}

