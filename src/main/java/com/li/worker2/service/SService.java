package com.li.worker2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.li.worker2.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author li
 * @since 2021-12-31
 */
public interface SService extends IService<User> {
    List<User> getAllEnable();

    void run();

    String start();

    String stop();
}
