package com.li.worker2.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.li.worker2.entity.Time;
import com.li.worker2.entity.User;
import com.li.worker2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    private final String token = "112345679";

    @RequestMapping("/createUser")
    public String createUser(@RequestParam String name,@RequestParam String cookieValue,@RequestParam String personalPhone,@RequestParam String emergency,@RequestParam String emergencyPhone,@RequestParam String location, @RequestParam String isInschool,@RequestParam String mail,@RequestParam String enable,@RequestParam String status,@RequestParam String key){
        if (!token.equals(key)){
            return Time.getTimes() + "  " + "key错误";
        }
        User user = new User();
        user.setEmergency(emergency);
        user.setCookieValue(cookieValue);
        user.setEnable(Integer.parseInt(enable));
        user.setMail(mail);
        user.setIsInschool(Integer.parseInt(isInschool));
        user.setName(name);
        user.setPersonalPhone(personalPhone);
        user.setEmergencyPhone(emergencyPhone);
        user.setLocation(location);
        user.setStatus(Integer.parseInt(isInschool));
        boolean save = userService.save(user);
        if (save){
            return Time.getTimes() + "  保存成功\n" + user;
        }
        return Time.getTimes() + "  保存失败\n" + user;
    }

    @RequestMapping("delUser")
    public String delUser(@RequestParam String name,@RequestParam String key){
        if (!token.equals(key)){
            return Time.getTimes() + "  " + "key错误";
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        boolean remove = userService.remove(wrapper);
        if (remove){
            return Time.getTimes() + "  删除成功";
        }
        return Time.getTimes() + "  删除失败";
    }
}

