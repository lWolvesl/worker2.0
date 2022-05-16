package com.li.worker2.controller;

import com.li.worker2.entity.Time;
import com.li.worker2.service.SService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li
 */
@RestController
public class ServiceController {
    @Autowired
    private SService SService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String token = "12345679";

    @RequestMapping("/start")
    public String start(@RequestParam String key) {
        if (!token.equals(key)) {
            return Time.getTimes() + "  " + "key错误";
        }
        return SService.start();
    }

    @RequestMapping("/head")
    public String head() {
        SService.event();
        logger.info("手动打卡启动");
        return Time.getTimes() + "手动打卡启动成功";
    }

    @RequestMapping("/stop")
    public String stop(@RequestParam String key) {
        if (!token.equals(key)) {
            return Time.getTimes() + "  " + "key错误";
        }
        return SService.stop();
    }
}
